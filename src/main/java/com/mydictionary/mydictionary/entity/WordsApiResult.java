package com.mydictionary.mydictionary.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WordsApiResult {
    
    @JsonProperty(value="definition")
    private String definition;

    @JsonProperty(value="similarTo")
    private List<String> similarTo;

    @JsonProperty(value="antonyms")
    private List<String> antonyms;
}
