package com.mydictionary.mydictionary.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mydictionary.mydictionary.api.WordsApiExecutor;
import com.mydictionary.mydictionary.dto.WordInfo;
import com.mydictionary.mydictionary.entity.WordsApiEntity;
import com.mydictionary.mydictionary.scrape.WebScraper;

@Service
@Scope("request")
public class MyDictionaryServiceImpl implements MyDictionaryService {
    
    @Autowired
    private WebScraper<WordInfo> weblioWebscraper;

    @Autowired
    private WordsApiExecutor wordsApiExecutor;

    @Override
    public WordInfo execute(String word) {

        //Web Scraping Weblio
        Optional<WordInfo> weblioWordInfoOpt = weblioWebscraper.doWebScraping(word);
        
        weblioWordInfoOpt.ifPresent(e -> {
            //Call Words API
            ResponseEntity<WordsApiEntity> response = wordsApiExecutor.get(word);
            if(HttpStatus.OK.equals(response.getStatusCode())) {
                e.setWordsApiResult(response.getBody().getResults());
            }
        });
        
        return weblioWordInfoOpt.orElse(new WordInfo(word));
    }
}
