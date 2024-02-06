package com.mydictionary.mydictionary.scrape;

import java.util.Optional;

public interface WebScraper<T> {
    
    public Optional<T> doWebScraping(final String word);
}
