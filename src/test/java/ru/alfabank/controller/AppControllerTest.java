package ru.alfabank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.alfabank.exception.ExchangeRatesException;
import ru.alfabank.service.ExchangeRatesService;
import ru.alfabank.service.GiphyGifService;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(AppController.class)
public class AppControllerTest {
    @Value("${giphy.richTag}")
    private String richTag;
    @Value("${giphy.brokeTag}")
    private String brokeTag;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ExchangeRatesService exchangeRatesService;
    @MockBean
    private GiphyGifService gifService;

    @Test
    public void whenGetDifferentRatios() throws ExchangeRatesException {
        Mockito.when(exchangeRatesService.getDifferentRatios(anyString()))
                .thenReturn(Double.compare(3.25d, 3.45d))
                .thenReturn(Double.compare(3.45d, 3.45d))
                .thenReturn(Double.compare(3.55d, 3.45d));
    }

    @Test
    public void whenGetCodes() throws ExchangeRatesException {
        Mockito.when(exchangeRatesService.getCodes())
                .thenReturn(Arrays.asList("TEST1", "TEST2", "TEST3"));
    }

    @Test
    public void whenReturnListOfCodes() throws Exception {
        List<String> responseList = new ArrayList<>();
        responseList.add("TEST");
        Mockito.when(exchangeRatesService.getCodes())
                .thenReturn(responseList);

        mockMvc.perform(get("/gifer/code")
                        .content(mapper.writeValueAsString(responseList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[0]").value("TEST"));
    }

    @Test
    public void whenListOfCodesIsNull() throws Exception {
        Mockito.when(exchangeRatesService.getCodes())
                .thenReturn(null);

        mockMvc.perform(get("/gifer/code")
                        .content(mapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    public void whenReturnRich() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("compareResult", richTag);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);

        Mockito.when(exchangeRatesService.getDifferentRatios(anyString()))
                .thenReturn(1);

        Mockito.when(gifService.getGif(richTag))
                .thenReturn(responseEntity);

        mockMvc.perform(get("/gifer/gif/TESTCODE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.compareResult").value(richTag));
    }

    @Test()
    public void whenReturnBroke() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("compareResult", brokeTag);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);

        Mockito.when(exchangeRatesService.getDifferentRatios(anyString()))
                .thenReturn(-1);

        Mockito.when(gifService.getGif(brokeTag))
                .thenReturn(responseEntity);

        mockMvc.perform(get("/gg/gif/TESTCODE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.compareResult").value(brokeTag));
    }
}
