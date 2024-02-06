package com.mydictionary.mydictionary.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mydictionary.mydictionary.constant.Constants;
import com.mydictionary.mydictionary.entity.WordsApiEntity;
import com.mydictionary.mydictionary.util.PropertyUtil;

@Service
@Scope("request")
public class WordsApiExecutor {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<WordsApiEntity> get(final String word) {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set(Constants.X_RAPID_API_KEY, PropertyUtil.getProperty(Constants.X_RAPID_API_KEY));
        headers.set(Constants.X_RAPID_API_HOST, PropertyUtil.getProperty(Constants.X_RAPID_API_HOST));
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<WordsApiEntity> response = restTemplate.exchange(Constants.BASE_URL_WORDS_API + word, HttpMethod.GET, request, WordsApiEntity.class);
        return response;
    }
}
