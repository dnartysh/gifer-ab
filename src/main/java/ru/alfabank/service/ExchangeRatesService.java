package ru.alfabank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.alfabank.client.ExchangeRatesClient;
import ru.alfabank.exception.ExchangeRatesException;
import ru.alfabank.model.ExchangeRates;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExchangeRatesService implements ExchangeRatesInterface {
    @Value("${openexchangerates.app.id}")
    private String appId;
    @Value("${openexchangerates.base}")
    private String base;
    private ExchangeRatesClient exchangeRatesClient;
    private ExchangeRates previousRates;
    private ExchangeRates currentRates;

    @Autowired
    public ExchangeRatesService(ExchangeRatesClient exchangeRatesClient) {
        this.exchangeRatesClient = exchangeRatesClient;
    }

    @Override
    public void updateRates() {
        currentRates = exchangeRatesClient.getRates(appId);
    }

    @Override
    public void updatePreviousRates() {
        String previousDate = new SimpleDateFormat("yyyy-MM-dd").format(getPreviousDay());

        previousRates = exchangeRatesClient.getRatesByDate(previousDate, appId);
    }

    private Date getPreviousDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);

        return new Date(cal.getTimeInMillis());
    }

    @Override
    public List<String> getCodes() throws ExchangeRatesException {
        if (currentRates.getRates().size() == 0) {
            throw new ExchangeRatesException("List of rates is empty");
        }

        return new ArrayList<>(currentRates.getRates().keySet());
    }

    @Override
    public int getKeyTag(String charCode) throws ExchangeRatesException {
        updateRates();
        updatePreviousRates();

        Double previousRatio = getRatio(previousRates, charCode);
        Double currentRatio = getRatio(currentRates, charCode);

        return Double.compare(currentRatio, previousRatio);
    }

    private Double getRatio(ExchangeRates rates, String charCode) throws ExchangeRatesException {
        if (rates == null) {
            throw new ExchangeRatesException("Exchange rates is null");
        }

        Map<String, Double> map = rates.getRates();

        if (map == null) {
            throw new ExchangeRatesException("List of rates is empty");
        }

        Double baseRate = map.get(base);
        Double targetRate = map.get(charCode);
        Double defaultRate = map.get(rates.getBase());

        if (baseRate == null || targetRate == null || defaultRate == null) {
            throw new ExchangeRatesException("Rates is null");
        }

        return new BigDecimal((defaultRate / baseRate) * targetRate)
                .setScale(4, RoundingMode.UP)
                .doubleValue();
    }
}
