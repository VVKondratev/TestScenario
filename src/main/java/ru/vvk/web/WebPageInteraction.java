package ru.vvk.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Random;

public class WebPageInteraction {
    final int ACTION_CHAIN = 5;
    private WebDriver driver;

    public WebPageInteraction() {
        driver = new FirefoxDriver();
        // tries to open chercher.tech page
        this.open("https://www.ozon.ru/");
        ElementAccess element = new ElementAccess();
        String[] path = initActionChain();
        for (int i = 0; i < ACTION_CHAIN; i++) {
            burnThisChain(element, path[i]);
        }
        if (isGoodsListLoaded(element)) {
            Long numberOfElements = goodsNumber(element);
            chooseProductByNumber(Utils.randomize(numberOfElements));
        } else {
            System.out.println("Goods list wasn't load");
        }
    }

    private void open(String webpage) {
        driver.get(webpage);
    }

    private void burnThisChain(ElementAccess element, String path) {
        element.getAccessAndClick(path, driver);
    }

    //Fourth item
    private boolean isGoodsListLoaded(ElementAccess element) {
        //Check if table is open tile m-default m-border
        String path = "//div[@class='tiles']";
        return element.isLoaded(path, driver);
    }

    //Fifth item
    private Long goodsNumber(ElementAccess element) {
        //Check number of goods in the table
        String path = "//div[@class='tile m-default m-border']";

        return new Long(element.getElementsNumber(path, driver));
    }

    private void chooseProductByNumber(Long id){

    }

    private String[] initActionChain() {
        String[] path = new String[ACTION_CHAIN];
        //Close popup window
        path[0] = "//button[@class='close']";
        //Open all category
        path[1] = "//*[@class='context-chip m-all-contexts']";
        //Set to music category
        path[2] = "//div[contains(text(),'Музыка')]";
        //Search
        path[3] = "//button[@class='m-search button default flat-button']";
        //Choose vynile
        path[4] = "//a[@class='_7eb738']";
        return path;
    }
}