package ru.vvk.web.config;

public class WebConfig {
    public static void configure(){
        String pathToFirefoxDriver = "c:/Program/geckodriver-v0.26.0-win64/geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", pathToFirefoxDriver);
    }
}
