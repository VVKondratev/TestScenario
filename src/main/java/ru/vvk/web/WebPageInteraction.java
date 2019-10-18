package ru.vvk.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Sleeper;

import java.util.Random;

public class WebPageInteraction {
    final int ACTION_CHAIN = 5;
    ElementAccess element;

    public WebPageInteraction() {
        element = new ElementAccess();
        element.open("https://www.ozon.ru/");
        String[] costAndTitle;
        String[] secondCostAndTitle;
        Long numberOfElements = 0L;
        String[] path = initActionChain();

        for (int i = 0; i < ACTION_CHAIN; i++) {
            burnThisChain(path[i]);
        }
        if (isGoodsListLoaded()) {
            numberOfElements = goodsNumber();
            chooseProductByNumber(Utils.randomize(numberOfElements));
        } else {
            System.out.println("Goods list wasn't load");
        }
        costAndTitle = rememberCostAndTitle();
        addToCart();
        checkGoodsInTheCard(costAndTitle[0]);
        //Eleventh
        returnToVynil(path);
        if (isGoodsListLoaded()) {
            //Twelfth
            numberOfElements = goodsNumber();
            //Thirteen
            chooseProductByNumber(Utils.randomize(numberOfElements));
        } else {
            System.out.println("Goods list wasn't load");
        }

        //Fourteen
        secondCostAndTitle = rememberCostAndTitle();
        //Fifteen
        addToCart();
        //17-18 заглушка
        checkGoodsInTheCard(secondCostAndTitle[0]);
        //Twenties
        deleteAll();
        //21
        closeIt();
    }

    private void burnThisChain(String path) {
        element.getAccessAndClick(path);
    }

    //Fourth point
    private boolean isGoodsListLoaded() {
        //Check if table is open tile m-default m-border
        String path = "//div[@class='tiles']";
        return element.isLoaded(path);
    }

    //Fifth point
    private Long goodsNumber() {
        //Check number of goods in the table
        String path = "//div[@class='tile m-default m-border']";
        return new Long(element.getElementsNumber(path));
    }

    //Seventh point
    private void chooseProductByNumber(Long id) {
        String path = "//div[@data-index='" + id.toString() + "']";
        System.out.println(id.toString());
        element.getAccessAndClick(path);
    }

    //Eighth point
    private String[] rememberCostAndTitle() {
        String[] costAndTitle = new String[2];
        String path = "//h1[@class='_718dda']";
        costAndTitle[0] = element.getElementsText(path);
        path = "//span[@data-v-c66bfbbc='']";
        costAndTitle[1] = element.getElementsText(path);
        return costAndTitle;
    }

    //Nines point
    private void addToCart() {
        String path = "//button[@class='_652bc6']";
        element.getAccessAndClick(path);
    }

    //Tens point
    private void checkGoodsInTheCard(String title) {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        String path = "//a[contains(text(),'В корзине')]";
        element.getAccessAndClick(path);
        path = "//span[@data-v-7246cfc8='']";
        System.out.println(element.getElementsText(path).equals(title));
    }
    //Eleventh point
    private void returnToVynil(String[] chain){
        for (int i = 1; i < ACTION_CHAIN; i++) {
            element.getAccessAndClick(chain[i]);
        }
    }

    //twenties point
    private void deleteAll(){
        String path = "//span[contains(text(),'Удалить выбранные')]";
        element.getAccessAndClick(path);
        path = "//button[@class='button button blue']";
        element.getAccessAndClick(path);
    }

    private void closeIt(){
        element.close();
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