package com.mydictionary.mydictionary.dto;

import java.util.List;

import lombok.Data;

@Data
public class WordInfo {

    private String word;
    private String meaningInfo;
    private List<Example> exampleInfo;
    private List<Synonym> synonymInfo;
    private String pronunciation;
    private String phoneticSymbol;
    private String resultMessage;
    private boolean error = false;

    public WordInfo(final String word) {
        this.word = word;
    }

}
