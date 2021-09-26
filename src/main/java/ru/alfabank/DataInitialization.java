package ru.alfabank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alfabank.service.ExchangeRatesInterface;

import javax.annotation.PostConstruct;

@Component
public class DataInitialization {
    private ExchangeRatesInterface exchangeRatesService;

    @Autowired
    public DataInitialization(ExchangeRatesInterface exchangeRatesService) {
        this.exchangeRatesService = exchangeRatesService;
    }

    @PostConstruct
    public void init() {
        exchangeRatesService.updateRates();
        exchangeRatesService.updatePreviousRates();
    }
}
