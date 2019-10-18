package ru.vvk.web;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.vvk.web.config.WebConfig;

/**
 * Entrance point. This class contains method main.
 *
 * @Author Kondratev Vladislav
 */
public class Main {


    /**
     * Method main
     *
     * @param args
     */
    public static void main(String[] args) {
        WebConfig.configure();
        WebPageInteraction wpi = new WebPageInteraction();
    }
}
