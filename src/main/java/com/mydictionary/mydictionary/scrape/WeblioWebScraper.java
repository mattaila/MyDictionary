package com.mydictionary.mydictionary.scrape;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mydictionary.mydictionary.constant.Constants;
import com.mydictionary.mydictionary.dto.Example;
import com.mydictionary.mydictionary.dto.WordInfo;

@Service
@Scope("request")
public class WeblioWebScraper implements WebScraper<WordInfo> {

    @Override
    public Optional<WordInfo> doWebScraping(final String word) {

        WordInfo wordInfo = null;
        try {
            //get meaning
            wordInfo = getMeaningInfo(word);
            
            //get example sentences
            wordInfo.setExampleInfo(getExampleSenteces(word));
            return Optional.of(wordInfo);

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private WordInfo getMeaningInfo(final String word) throws IOException {
        WordInfo wordInfo = new WordInfo(word);

        Document doc = Jsoup.connect(Constants.BASE_URL_WEBLIO + word).get();
        Optional<Element> meaning = Optional.ofNullable(doc.selectFirst(".content-explanation"));
        
        meaning.ifPresent(e -> {
            wordInfo.setMeaningInfo(meaning.get().text());

            Element phoneticSymbol = doc.selectFirst("#phoneticEjjeNavi");
            wordInfo.setPhoneticSymbol(phoneticSymbol.text());

            Element audioSource = doc.selectFirst(".contentAudio > source");
            String pronunciation = audioSource.attr("src");
            wordInfo.setPronunciation(pronunciation);
        });

        return wordInfo;
    }

    private List<Example> getExampleSenteces(final String word) throws IOException {
        List<Example> list = new ArrayList<>();

        Document doc = Jsoup.connect(Constants.BASE_URL_WEBLIO_EXAMPLE + word).get();
        Optional<Elements> elements = Optional.ofNullable(doc.select(".qotC"));
        elements.orElse(new Elements()).forEach(e -> {
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
