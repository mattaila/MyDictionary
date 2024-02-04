package com.mydictionary.mydictionary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mydictionary.mydictionary.dto.WordInfo;
import com.mydictionary.mydictionary.scrape.WeblioWebScraper;

@Service
@Scope("request")
public class MyDictionaryServiceImpl implements MyDictionaryService {
    
    @Autowired
    private WeblioWebScraper weblioWebscraper;

    @Override
    public void execute(Model model, String word) {
        WordInfo weblioWordInfo = weblioWebscraper.doWebScraping(word);
        model.addAttribute("searchedWord", weblioWordInfo.getWord());
        model.addAttribute("meaning", weblioWordInfo.getMeaningInfo());
        model.addAttribute("phoneticSymbol", weblioWordInfo.getPhoneticSymbol());
        model.addAttribute("pronunciation", weblioWordInfo.getPronunciation());
        model.addAttribute("example", weblioWordInfo.getExampleInfo());
        model.addAttribute("resultMessage", weblioWordInfo.getResultMessage());
    }
}
