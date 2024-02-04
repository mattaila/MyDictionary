package com.mydictionary.mydictionary.scrape;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mydictionary.mydictionary.dto.Example;
import com.mydictionary.mydictionary.dto.WordInfo;

@Service
@Scope("request")
public class WeblioWebScraper implements WebScraper {

    @Override
    public WordInfo doWebScraping(String word) {

        WordInfo wordInfo = null;
        try {
            //get meaning
            wordInfo = getMeaningInfo(word);
            
            if(wordInfo.isError()) {
                return wordInfo;
            }

            //get example sentences
            wordInfo.setExampleInfo(getExampleSenteces(word));
            return wordInfo;

        } catch (Exception e) {
            e.printStackTrace();
            return wordInfo;
        }
    }

    private WordInfo getMeaningInfo(final String word) throws Exception {
        WordInfo wordInfo = new WordInfo(word);

        Document doc = Jsoup.connect("https://ejje.weblio.jp/content/" + word).get();
        Optional<Element> meaning = Optional.ofNullable(doc.selectFirst(".content-explanation"));
        
        if(meaning.isPresent()) {
            wordInfo.setMeaningInfo(meaning.get().text());

            Element phoneticSymbol = doc.selectFirst("#phoneticEjjeNavi");
            wordInfo.setPhoneticSymbol(phoneticSymbol.text());

            Element audioSource = doc.selectFirst(".contentAudio > source");
            String pronunciation = audioSource.attr("src");
            wordInfo.setPronunciation(pronunciation);
        } else {
            wordInfo.setResultMessage(String.format("not found \"%s\"", word));
            wordInfo.setError(true);
        }

        return wordInfo;
    }

    private List<Example> getExampleSenteces(final String word) throws Exception {
        List<Example> list = new ArrayList<>();

        Document doc = Jsoup.connect("https://ejje.weblio.jp/sentence/content/" + word).get();
        Elements elements = doc.select(".qotC");
        elements.forEach(e -> {
            //get example sentence
            Element sentenceElement = e.selectFirst(".qotCE");
            String sentence = sentenceElement.text().replace("例文帳に追加", "");

            //get japanese translation 
            Element translationElement = e.selectFirst(".qotCJ");
            String translation = translationElement.text().split("-")[0];
            list.add(new Example(sentence, translation));
        });
        
        return list;
    }
    
}