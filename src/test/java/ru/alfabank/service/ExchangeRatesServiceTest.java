package ru.alfabank.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import ru.alfabank.client.ExchangeRatesClient;
import ru.alfabank.exception.ExchangeRatesException;
import ru.alfabank.model.ExchangeRates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("ru.alfabank")
public class ExchangeRatesServiceTest {
    @Value("${openexchangerates.base}")
    private String base;
    @Autowired
    private ExchangeRatesService exchangeRatesService;
    @MockBean
    private ExchangeRatesClient openExchangeRatesClient;

    private ExchangeRates currentRates;
    private ExchangeRates previousRates;

    @Before
    public void init() {
        int time = 1632757754;
        currentRates = new ExchangeRates();
        currentRates.setTimestamp(time);
        currentRates.setBase("BASE");

        Map<String, Double> currentRatesMap = new HashMap<>();
        currentRatesMap.put("E1", 0.1);
        currentRatesMap.put("E2", 0.5);
        currentRatesMap.put("E3", 1.0);
        currentRatesMap.put(base, 72.471);
        currentRatesMap.put("BASE", 1.0);
        currentRates.setRates(currentRatesMap);

        time = 1609343354;
        previousRates = new ExchangeRates();
        previousRates.setTimestamp(time);
        previousRates.setBase("BASE");

        Map<String, Double> previousRatesMap = new HashMap<>();
        previousRatesMap.put("E1", 0.1);
        previousRatesMap.put("E2", 1.0);
        previousRatesMap.put("E3", 0.5);
        previousRatesMap.put(base, 72.471);
        previousRatesMap.put("BASE", 1.0);
        previousRates.setRates(previousRatesMap);
    }

    @Test
    public void whenNoChanges() throws ExchangeRatesException {
        Mockito.when(openExchangeRatesClient.getRates(anyString()))
                .thenReturn(currentRates);
        Mockito.when(openExchangeRatesClient.getRatesByDate(anyString(), anyString()))
                .thenReturn(previousRates);

        int result = exchangeRatesService.getDifferentRatios("E1");

        assertEquals(0, result);
    }

    @Test
    public void whenNegativeChanges() throws ExchangeRatesException {
        Mockito.when(openExchangeRatesClient.getRates(anyString()))
                .thenReturn(currentRates);
        Mockito.when(openExchangeRatesClient.getRatesByDate(anyString(), anyString()))
                .thenReturn(previousRates);

        int result = exchangeRatesService.getDifferentRatios("E2");

        assertEquals(-1, result);
    }

    @Test
    public void whenPositiveChanges() throws ExchangeRatesException {
        Mockito.when(openExchangeRatesClient.getRates(anyString()))
                .thenReturn(currentRates);
        Mockito.when(openExchangeRatesClient.getRatesByDate(anyString(), anyString()))
                .thenReturn(previousRates);

        int result = exchangeRatesService.getDifferentRatios("E3");

        assertEquals(1, result);
    }

    @Test
    public void whenGetListOfCodes() throws ExchangeRatesException {
        Mockito.when(openExchangeRatesClient.getRates(anyString()))
                .thenReturn(currentRates);
        Mockito.when(openExchangeRatesClient.getRatesByDate(anyString(), anyString()))
                .thenReturn(previousRates);

        List<String> result = exchangeRatesService.getCodes();

        assertThat(result, containsInAnyOrder("E1", "E2", "E3", base, "BASE"));
    }

    @Test
    public void whenAppBaseIsChanged() throws ExchangeRatesException {
        currentRates.getRates().put(base, 73.945);
        Mockito.when(openExchangeRatesClient.getRates(anyString()))
                .thenReturn(currentRates);
        Mockito.when(openExchangeRatesClient.getRatesByDate(anyString(), anyString()))
                .thenReturn(previousRates);

        int result = exchangeRatesService.getDifferentRatios(base);

        assertEquals(0, result);
    }
}
