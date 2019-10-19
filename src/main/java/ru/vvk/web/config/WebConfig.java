package ru.vvk.web.config;

/**
 * Класс конфигурации
 */
public class WebConfig {
    /**
     * Статический метод, задающий путь к geckodriver
     */
    public static void configure(){
        String pathToFirefoxDriver = "c:/Program/geckodriver-v0.26.0-win64/geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", pathToFirefoxDriver);
    }
}
