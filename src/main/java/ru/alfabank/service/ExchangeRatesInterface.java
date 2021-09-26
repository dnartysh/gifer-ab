package ru.alfabank.service;

import ru.alfabank.exception.ExchangeRatesException;

import java.util.List;

public interface ExchangeRatesInterface {
    void updateRates();

    void updatePreviousRates();

    List<String> getCodes() throws ExchangeRatesException;

    int getKeyTag(String charCode) throws ExchangeRatesException;
}
