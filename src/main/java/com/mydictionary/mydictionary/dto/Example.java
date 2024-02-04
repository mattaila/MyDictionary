package com.mydictionary.mydictionary.dto;

import lombok.Data;

@Data
public class Example {
    
    private String exampleSentence;
    private String jpTranslation;

    public Example(final String sentence, final String translation) {
        this.exampleSentence = sentence;
        this.jpTranslation = translation;
    }
}
