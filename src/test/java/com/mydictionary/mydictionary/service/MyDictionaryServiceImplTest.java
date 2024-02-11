package com.mydictionary.mydictionary.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mydictionary.mydictionary.api.WordsApiExecutor;
import com.mydictionary.mydictionary.dto.WordInfo;
import com.mydictionary.mydictionary.entity.WordsApiEntity;
import com.mydictionary.mydictionary.entity.WordsApiResult;
import com.mydictionary.mydictionary.scrape.WebScraper;

@ExtendWith(MockitoExtension.class)
public class MyDictionaryServiceImplTest {
    
    @Mock
    private WebScraper<WordInfo> weblioWebscraper;

    @Mock
    private WordsApiExecutor wordsApiExecutor;

    @InjectMocks
    private MyDictionaryServiceImpl service;

    @BeforeEach
    public void before() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("WEBスクレイピング成功、API成功")
    public void test01() {

        WordInfo expect = createReturn();
        Optional<WordInfo> expectOpt = createSuccessWordInfo();
        when(weblioWebscraper.doWebScraping(anyString())).thenReturn(expectOpt);
        ArgumentCaptor<String> captorScrape = ArgumentCaptor.forClass(String.class);

        ResponseEntity<WordsApiEntity> expectResponse = createResponse();
        when(wordsApiExecutor.get(anyString())).thenReturn(expectResponse);
        ArgumentCaptor<String> captorApi = ArgumentCaptor.forClass(String.class);

        WordInfo actual = service.execute("word");

        //メソッド呼び出しの検証
        verify(weblioWebscraper, times(1)).doWebScraping(captorScrape.capture());
        verify(wordsApiExecutor, times(1)).get(captorApi.capture());

        //引数渡しの検証
        assertEquals("word", captorScrape.getValue());
        assertEquals("word", captorApi.getValue());
        //戻り値の検証
        assertEquals(expect.getWord(), actual.getWord());
    }

    @Test
    @DisplayName("WEBスクレイピング失敗")
    public void test02() {

        WordInfo expect = createReturn();
        Optional<WordInfo> expectOpt = createFailWordInfo();
        when(weblioWebscraper.doWebScraping(anyString())).thenReturn(expectOpt);
        ArgumentCaptor<String> captorScrape = ArgumentCaptor.forClass(String.class);

        WordInfo actual = service.execute("word");

        //メソッド呼び出しの検証
        verify(weblioWebscraper, times(1)).doWebScraping(captorScrape.capture());
        verify(wordsApiExecutor, times(0)).get(anyString());

        //引数渡しの検証
        assertEquals("word", captorScrape.getValue());
        //戻り値の検証
        assertEquals(expect.getWord(), actual.getWord());
        assertNull(actual.getExampleInfo());
        assertNull(actual.getPronunciation());
        assertNull(actual.getPhoneticSymbol());
        assertNull(actual.getWordsApiResult());
    }

    @Test
    @DisplayName("WEBスクレイピング成功、API失敗")
    public void test03() {

        WordInfo expect = createReturn();
        Optional<WordInfo> expectOpt = createSuccessWordInfo();
        when(weblioWebscraper.doWebScraping(anyString())).thenReturn(expectOpt);
        ArgumentCaptor<String> captorScrape = ArgumentCaptor.forClass(String.class);

        ResponseEntity<WordsApiEntity> expectResponse = createErrorResponse();
        when(wordsApiExecutor.get(anyString())).thenReturn(expectResponse);
        ArgumentCaptor<String> captorApi = ArgumentCaptor.forClass(String.class);

        WordInfo actual = service.execute("word");

        //メソッド呼び出しの検証
        verify(weblioWebscraper, times(1)).doWebScraping(captorScrape.capture());
        verify(wordsApiExecutor, times(1)).get(captorApi.capture());

        //引数渡しの検証
        assertEquals("word", captorScrape.getValue());
        assertEquals("word", captorApi.getValue());
        //戻り値の検証
        assertEquals(expect.getWord(), actual.getWord());
        assertNull(actual.getWordsApiResult());
    }

    private WordInfo createReturn() {
        WordInfo wordInfo = new WordInfo("word");

        return wordInfo;
    }

    private Optional<WordInfo> createSuccessWordInfo() {
        return Optional.ofNullable(createReturn());
    }

    private Optional<WordInfo> createFailWordInfo() {
        return Optional.ofNullable(null);
    }

    private ResponseEntity<WordsApiEntity> createResponse() {
        WordsApiEntity entity = new WordsApiEntity();
        ResponseEntity<WordsApiEntity> response = new ResponseEntity<WordsApiEntity>(entity, HttpStatus.OK);

        List<WordsApiResult> list = new ArrayList<>();
        response.getBody().setResults(list);
        return response;
    }

    private ResponseEntity<WordsApiEntity> createErrorResponse() {
        WordsApiEntity entity = new WordsApiEntity();
        ResponseEntity<WordsApiEntity> response = new ResponseEntity<WordsApiEntity>(entity, HttpStatus.NOT_FOUND);

        List<WordsApiResult> list = new ArrayList<>();
        response.getBody().setResults(list);
        return response;
    }
}
