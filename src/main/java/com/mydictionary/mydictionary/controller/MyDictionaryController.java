package com.mydictionary.mydictionary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mydictionary.mydictionary.dto.WordInfo;
import com.mydictionary.mydictionary.service.MyDictionaryService;

@Controller
@Scope("request")
public class MyDictionaryController {
    
    @Autowired
    private MyDictionaryService service;

    @RequestMapping("/")
    public String get(Model model) {
        return "index";
    }

    @RequestMapping("/word")
    public String searchWordInfo(Model model, @RequestParam String word) {

        WordInfo wordInfo = service.execute(word);
        model.addAttribute("searchedWord", word);
        model.addAttribute("meaning", wordInfo.getMeaningInfo());
        model.addAttribute("phoneticSymbol", wordInfo.getPhoneticSymbol());
        model.addAttribute("pronunciation", wordInfo.getPronunciation());
        model.addAttribute("example", wordInfo.getExampleInfo());
        model.addAttribute("resultMessage", wordInfo.getResultMessage());
        model.addAttribute("wordsApiResultList", wordInfo.getWordsApiResult());
        
        return "index";
    }
}
