package ru.alfabank.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface GiphyGifInterface {
    ResponseEntity<Map> getGif(String tag);
}