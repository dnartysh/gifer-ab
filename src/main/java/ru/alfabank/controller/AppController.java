package ru.alfabank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alfabank.exception.ExchangeRatesException;
import ru.alfabank.service.ExchangeRatesInterface;
import ru.alfabank.service.ExchangeRatesService;
import ru.alfabank.service.GiphyGifInterface;
import ru.alfabank.service.GiphyGifService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class AppController {
    private ExchangeRatesInterface exchangeRatesService;
    private GiphyGifInterface gifService;
    @Value("${giphy.richTag}")
    private String richTag;
    @Value("${giphy.brokeTag}")
    private String brokeTag;

    @Autowired
    public AppController(ExchangeRatesService exchangeRatesService, GiphyGifService gifService) {
        this.exchangeRatesService = exchangeRatesService;
        this.gifService = gifService;
    }

    @GetMapping("gifer/code")
    public List<String> getCodes() throws ExchangeRatesException {
        return exchangeRatesService.getCodes();
    }

    @GetMapping("gifer/gif/{code}")
    public ResponseEntity<Map> getGif(@PathVariable String code) throws ExchangeRatesException {
        return gifService.getGif(exchangeRatesService.getDifferentRatios(code.toUpperCase()) < 0 ? brokeTag : richTag);
    }
}
