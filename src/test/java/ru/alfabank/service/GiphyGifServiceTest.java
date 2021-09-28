package ru.alfabank.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.alfabank.client.GiphyGifClient;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("ru.alfabank")
public class GiphyGifServiceTest {

    @Autowired
    private GiphyGifService gifService;
    @MockBean
    private GiphyGifClient gifClient;

    @Test
    public void whenGetGif() {
        ResponseEntity<Map> testEntity = new ResponseEntity<>(new HashMap(), HttpStatus.OK);

        Mockito.when(gifClient.getRandomGif(anyString(), anyString()))
                .thenReturn(testEntity);

        ResponseEntity<Map> result = gifService.getGif("TEST");

        assertEquals("TEST", result.getBody().get("compareResult"));
    }

}