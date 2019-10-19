package ru.vvk.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Sleeper;

import java.util.NoSuchElementException;
import java.util.Random;

public class WebPageInteraction {
    final int ACTION_CHAIN = 5;
    ElementAccess element;

    public WebPageInteraction() {
        element = new ElementAccess();
        element.open("https://www.ozon.ru/");
        String[] costAndTitle = new String[2];
        String[] secondCostAndTitle = new String[2];
        Long numberOfElements = 0L;
        String[] path = initActionChain();
        Utils.wait(1000);
        for (int i = 0; i < ACTION_CHAIN; i++) {
            burnThisChain(path[i]);
        }
        if (isGoodsListLoaded()) {
            numberOfElements = goodsNumber();
            chooseProductByNumber(Utils.randomize(numberOfElements));
        } else {
            System.out.println("Goods list wasn't load");
        }
        costAndTitle = rememberCostAndTitle(costAndTitle);
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
        secondCostAndTitle = rememberCostAndTitle(secondCostAndTitle);
        //Fifteen
        addToCart();
        Utils.wait(1000);
        //Sixteen
        System.out.println("Number of goods is 2: " + checkGoodsNum());
        //17-18
        checkResultingGoodAndTheirCost(costAndTitle, secondCostAndTitle);
        //Nineteen
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
        element.getAccessAndClick(path);
    }

    //Eighth point
    private String[] rememberCostAndTitle(String[] costAndTitle) {
        String path = "//h1[@class='_718dda']";
        costAndTitle[0] = element.getElementsText(path);
        path = "//span[@data-v-58d56c0e='']";
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
        Utils.wait(4000);
        String path = "//a[contains(text(),'В корзине')]";
        element.getAccessAndClick(path);
        path = "//span[@data-v-7246cfc8='']";
        System.out.println("The product "+title+" was added to the card:"+element.getElementsText(path).equals(title));
    }

    //Eleventh point
    private void returnToVynil(String[] chain) {
        for (int i = 1; i < ACTION_CHAIN; i++) {
            element.getAccessAndClick(chain[i]);
        }
    }

    //Sixteen point
    private String checkGoodsNum() {
        String path = "//span[@data-v-c66bfbbc='' and @class='f-caption--bold ef9580']";
        return element.getElementsText(path);
    }

    //Nineteen point
    private void deleteAll() {
        String path = "//span[contains(text(),'Удалить выбранные')]";
        element.getAccessAndClick(path);
        path = "//button[@class='button button blue']";
        element.getAccessAndClick(path);
    }

    //17-18 points
    private boolean checkResultingGoodAndTheirCost(String[] expectedProductFirst, String[] expectedProductSecond) {
        Utils.wait(4000);
        String path = "//a[contains(text(),'В корзине')]";
        element.getAccessAndClick(path);
        try {
            path = "//span[contains(text(),'" + expectedProductFirst[0] + "')]";
            System.out.println("Product "+element.getElementsText(path)+" was added to card");
            path = "//span[contains(text(),'" + expectedProductSecond[0] + "')]";
            System.out.println("Product "+element.getElementsText(path)+" was added to card");
        } catch (NoSuchElementException e) {
            System.out.println("One of the goods wasn't added to the list");
        }
        //Получаем общую сумму
        path = "//span[@data-v-c66bfbbc='' and @class='total-middle-footer-text']";
        String resCost = element.getElementsText(path);
        resCost=resCost.substring(0,resCost.length()-2).replaceAll("\\s+","");
        expectedProductFirst[1]=expectedProductFirst[1].substring(0,expectedProductFirst[1].length()-2).replaceAll("\\s+","");
        expectedProductSecond[1]=expectedProductSecond[1].substring(0,expectedProductSecond[1].length()-2).replaceAll("\\s+","");
        System.out.println(expectedProductFirst[1]+"+"+expectedProductSecond[1]+"="+resCost);
        Integer fp = Integer.parseInt(expectedProductFirst[1]);
        Integer sp = Integer.parseInt(expectedProductSecond[1]);
        Integer res = Integer.parseInt(resCost);
        return ((fp+sp)==res);
    }

    //Twenty one
    private void closeIt() {
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