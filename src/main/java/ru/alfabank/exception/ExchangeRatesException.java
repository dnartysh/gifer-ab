package ru.alfabank.exception;

public class ExchangeRatesException extends Exception {

    public ExchangeRatesException() {}

    public ExchangeRatesException(String message) {
        super(message);
    }
}
