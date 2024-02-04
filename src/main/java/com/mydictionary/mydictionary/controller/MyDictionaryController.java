package com.mydictionary.mydictionary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String searchWordInfo(Model model, String word) {
        service.execute(model, word);
        return "index";
    }
}
