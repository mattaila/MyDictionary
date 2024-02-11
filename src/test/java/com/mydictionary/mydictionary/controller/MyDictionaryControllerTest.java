package com.mydictionary.mydictionary.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mydictionary.mydictionary.dto.Example;
import com.mydictionary.mydictionary.dto.WordInfo;
import com.mydictionary.mydictionary.entity.WordsApiResult;
import com.mydictionary.mydictionary.service.MyDictionaryService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MyDictionaryControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    MyDictionaryController target;

    @Mock
    private MyDictionaryService service;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }

    @Test
    @DisplayName("indexテスト")
    public void indexTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    @DisplayName("/wordテスト")
    public void wordTest() throws Exception {

        String expectParam = "word";
        when(service.execute(any())).thenReturn(createWordInfo());

        mockMvc.perform(MockMvcRequestBuilders.get("/word?word=word"))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.view().name("index"))
           .andExpect(MockMvcResultMatchers.model().attributeExists("searchedWord"))
           .andExpect(MockMvcResultMatchers.model().attributeExists("meaning"))
           .andExpect(MockMvcResultMatchers.model().attributeExists("phoneticSymbol"))
           .andExpect(MockMvcResultMatchers.model().attributeExists("pronunciation"))
           .andExpect(MockMvcResultMatchers.model().attributeExists("example"))
           .andExpect(MockMvcResultMatchers.model().attributeExists("wordsApiResultList"));

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(service, times(1)).execute(captor.capture());

        String actualParam = captor.getValue();
        assertEquals(expectParam, actualParam);
    }

    private WordInfo createWordInfo() {
        WordInfo info = new WordInfo("word");
        info.setMeaningInfo("meaning");
        info.setPhoneticSymbol("phoneticSymbol");
        info.setPronunciation("pronunciation");
        info.setExampleInfo(new ArrayList<Example>());
        info.setWordsApiResult(new ArrayList<WordsApiResult>());
        return info;
    }
}
