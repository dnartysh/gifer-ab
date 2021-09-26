package ru.alfabank.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alfabank.model.ExchangeRates;

@FeignClient(name = "ExchangeRatesClient", url = "${openexchangerates.url}")
public interface ExchangeRatesClient {
    @GetMapping("/latest.json")
    ExchangeRates getRates(@RequestParam("app_id") String appId);

    @GetMapping("/historical/{date}.json")
    ExchangeRates getRatesByDate(@PathVariable String date,
                                 @RequestParam("app_id") String appId);
}
