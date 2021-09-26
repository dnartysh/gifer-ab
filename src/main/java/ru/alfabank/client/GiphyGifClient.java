package ru.alfabank.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "GiphyGifClient", url = "${giphy.url}")
public interface GiphyGifClient {
    @GetMapping("/random")
        ResponseEntity<Map> getRandomGif(@RequestParam("api_key") String apiKey,
                                   @RequestParam("tag") String tag);
}
