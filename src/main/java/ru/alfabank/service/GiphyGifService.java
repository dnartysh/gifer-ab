package ru.alfabank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.alfabank.client.GiphyGifClient;

import java.util.Map;

@Service
public class GiphyGifService implements GiphyGifInterface {

    private GiphyGifClient gifClient;
    @Value("${giphy.app.id}")
    private String appId;

    @Autowired
    public GiphyGifService(GiphyGifClient gifClient) {
        this.gifClient = gifClient;
    }

    @Override
    public ResponseEntity<Map> getGif(String tag) {
        ResponseEntity<Map> response = gifClient.getRandomGif(appId, tag);
        response.getBody().put("compareResult", tag);

        return response;
    }
}
