package ru.alfabank.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import ru.alfabank.service.ExchangeRatesService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("ru.alfabank")
public class ExchangeRatesExceptionTest {

    @MockBean
    private ExchangeRatesService exchangeRatesService;

    @Test
    public void whenGetExchangeRatesException() throws ExchangeRatesException {
        Mockito.when(exchangeRatesService.getCodes()).thenThrow(ExchangeRatesException.class);
    }
}
