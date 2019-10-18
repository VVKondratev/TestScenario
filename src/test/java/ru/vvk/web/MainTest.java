package ru.vvk.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MainTest {

    private WebDriver driver;

    @BeforeTest
    public void setup(){
        String pathToFirefoxDriver = "c:/Program/geckodriver-v0.26.0-win64/geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", pathToFirefoxDriver);
    }

    @AfterTest
    public void teardown(){
        driver.close();
    }

    @Test
    public void openWebPage(){
        driver = new FirefoxDriver();
        driver.get("https://www.ozon.ru/");
    }

}
