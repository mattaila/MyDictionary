package com.mydictionary.mydictionary.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WordsApiEntity {
    
    @JsonProperty(value="word")
    private String word;

    @JsonProperty(value="results")
    private List<WordsApiResult> results;
}
