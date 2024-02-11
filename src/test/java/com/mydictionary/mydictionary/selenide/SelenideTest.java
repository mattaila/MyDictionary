package com.mydictionary.mydictionary.selenide;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.codeborne.selenide.Selenide;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SelenideTest {
    
    @AfterEach
	void finishTestMethod() {
		Selenide.clearBrowserCookies();
	}

    @LocalServerPort
    private int port;
 
    private String url(String path) {
        return "http://localhost:%d%s".formatted(port, path);
    }

    @Test
    @DisplayName("indexページの挙動確認:実行成功")
    public void indexPage01() {
        open(url("/"));
        $("#word").should(exist);  
        $("#word").shouldBe(visible);
        $("#word").setValue("controversial");
        $("#submit").click();

        $("body").shouldHave(text("JP Translation"));
        $("body").shouldHave(text("controversial"));
        screenshot("success");
        
    }

    @Test
    @DisplayName("indexページの挙動確認:単語未指定")
    public void indexPage02() {
        open(url("/"));
        $("#word").should(exist);  
        $("#word").shouldBe(visible);
        $("#word").setValue("");
        $("#submit").click();

        $("body").shouldNotHave(text("JP Translation"));
        screenshot("blankword");
        
    }
}
