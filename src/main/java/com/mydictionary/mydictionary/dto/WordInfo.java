package com.mydictionary.mydictionary.dto;

import java.util.List;

import com.mydictionary.mydictionary.entity.WordsApiResult;

import lombok.Data;

@Data
public class WordInfo {

    private String word;
    private String meaningInfo;
    private List<Example> exampleInfo;
    private String pronunciation;
    private String phoneticSymbol;
    private String resultMessage;
    private boolean error = false;
    private List<WordsApiResult> wordsApiResult;

    public WordInfo(final String word) {
        this.word = word;
    }

}
