package com.mydictionary.mydictionary.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.mydictionary.mydictionary.entity.WordsApiEntity;

@ExtendWith(MockitoExtension.class)
public class WordsApiExecutorTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WordsApiExecutor executor;

    @BeforeEach
    public void before() {
        MockitoAnnotations.openMocks(this);
        //restTemplate = mock(RestTemplate.class);
    }

    @Test
    @DisplayName("API実行成功")
    public void test01() {
        //RestTemplate restTemplate = mock(RestTemplate.class);
        String word = "word";
        ResponseEntity<WordsApiEntity> expect = createResponse();
        when(restTemplate.exchange(anyString(), any(HttpMethod.class),
            any(HttpEntity.class), eq(WordsApiEntity.class))).thenReturn(expect);

        ResponseEntity<WordsApiEntity> actual = executor.get(word);
        verify(restTemplate, times(1)).exchange(
            anyString(), any(HttpMethod.class), any(HttpEntity.class),eq(WordsApiEntity.class));

        assertEquals(expect, actual);
    }

    @Test
    @DisplayName("API実行失敗")
    public void test02() {
        //RestTemplate restTemplate = mock(RestTemplate.class);
        String word = "word";
        ResponseEntity<WordsApiEntity> expect = createFailResponse();
        when(restTemplate.exchange(anyString(), any(HttpMethod.class),
            any(HttpEntity.class), eq(WordsApiEntity.class)))
            .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        ResponseEntity<WordsApiEntity> actual = executor.get(word);
        verify(restTemplate, times(1)).exchange(
            anyString(), any(HttpMethod.class), any(HttpEntity.class),eq(WordsApiEntity.class));

        assertEquals(expect, actual);
    }

    private ResponseEntity<WordsApiEntity> createResponse() {
        ResponseEntity<WordsApiEntity> response 
            = new ResponseEntity<WordsApiEntity>(new WordsApiEntity(), HttpStatus.OK);
        return response;
    }

    private ResponseEntity<WordsApiEntity> createFailResponse() {
        ResponseEntity<WordsApiEntity> response 
            = new ResponseEntity<WordsApiEntity>(new WordsApiEntity(), HttpStatus.NOT_FOUND);
        return response;
    }
}
