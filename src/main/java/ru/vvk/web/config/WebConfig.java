package ru.vvk.web.config;

/**
 * Класс конфигурации
 */
public class WebConfig {
    /**
     * Статический метод, задающий путь к geckodriver
     */
    public static void configure(){
        String pathToFirefoxDriver = "./src/main/resources/lib/geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", pathToFirefoxDriver);
    }
}
