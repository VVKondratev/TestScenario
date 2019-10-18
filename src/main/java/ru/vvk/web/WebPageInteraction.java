package ru.vvk.web;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class WebPageInteraction {
    final int OPEN_CATEGORY = 3;
    WebDriver driver;

    public WebPageInteraction() {
        driver = new FirefoxDriver();
        // tries to open chercher.tech page
        this.open("https://www.ozon.ru/");
        if (this.isLoad()) {
            ElementAccess element = new ElementAccess();
            closePopUpWindow(element);
            openAllCategories(element);
            getMusicCategory(element);
        }
    }

    private void open(String webpage) {
        driver.get(webpage);
    }

    private boolean isLoad() {
        Thread onload = new Thread(() -> {
            String pageLoadStatus = null;
            JavascriptExecutor js;
            do {
                js = (JavascriptExecutor) driver;
                pageLoadStatus = (String) js.executeScript("return document.readyState");
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (!pageLoadStatus.equals("complete"));
        });
        onload.start();
        return true;
    }

    private void closePopUpWindow(ElementAccess element){
        element.getAccess("//button[@class='close']", driver);
        element.click();
    }

    private void openAllCategories(ElementAccess element){
        element.getAccess("//*[@class='context-chip m-all-contexts']", driver);
        element.click();
    }
    private void getMusicCategory(ElementAccess element){
        element.getAccess("//div[contains(text(),'Музыка')]", driver);
        element.click();
    }
}
