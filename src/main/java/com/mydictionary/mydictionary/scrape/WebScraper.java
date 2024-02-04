package com.mydictionary.mydictionary.scrape;

import com.mydictionary.mydictionary.dto.WordInfo;

public interface WebScraper {
    public WordInfo doWebScraping(final String word);
}
