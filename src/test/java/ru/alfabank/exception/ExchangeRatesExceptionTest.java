package ru.alfabank.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("ru.alfabank")
public class ExchangeRatesExceptionTest {
    @Test
    public void whenGetExchangeRatesException() {
        Mockito.doThrow(ExchangeRatesException.class).doNothing();
    }
}
