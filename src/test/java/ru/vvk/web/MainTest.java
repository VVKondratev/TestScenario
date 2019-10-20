package ru.vvk.web;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;

public class MainTest {

    WebPage page;
    //Number of elements
    private static Long goodsCount = 0L;
    //Random product from list of goods
    private static Long randomProduct = 0L;
    //Cost and title of the first product
    private static String[] costAndTitle = new String[2];
    //Cost and title of the second product
    private static String[] secondCostAndTitle = new String[2];
    //Close pop-up window
    private static final String CLOSE_BUTTON = "//button[@class='close']";
    //Open all category
    private static final String ALL_CATEGORIES = "//*[@class='context-chip m-all-contexts']";
    //Set to music category
    private static final String MUSIC_CATEGORY = "//div[contains(text(),'Музыка')]";
    //Search
    private static final String SEARCH_BUTTON = "//button[@class='m-search button default flat-button']";
    //Choose vynile
    private static final String VYNILE_CATEGORY = "//a[@class='_7eb738']";
    //List of Goods
    private static final String GOODS_LIST = "//div[@class='tiles']";
    //All elements for this class
    private static final String GOODS_COUNT = "//div[@class='tile m-default m-border']";
    //Product title
    private static final String PRODUCT_TITLE = "//h1[@class='_718dda']";
    //Product price
    private static final String PRODUCT_PRICE = "//span[contains(@class,'b3411b')]";
    //Add to card
    private static final String ADD_TO_CARD = "//button[@class='_652bc6']";
    //Move to card
    private static final String OPEN_CARD = "//a[contains(text(),'В корзине')]";
    //Title of the first element in the card
    private static final String FIRST_PRODUCT_TITLE = "//span[@data-v-7246cfc8='']";
    //Number of goods in the card
    private static final String GOODS_COUNT_IN_CARD = "//span[@data-v-c66bfbbc='' and @class='f-caption--bold ef9580']";
    //Получаем общую сумму
    private static final String RESULTING_COST = "//span[@data-v-c66bfbbc='' and @class='total-middle-footer-text']";
    //Delete all goods from the card
    private static final String DELETE_ALL_FROM_CARD = "//span[contains(text(),'Удалить выбранные')]";
    //Confirm deletion
    private static final String CONFIRM_DELETION = "//button[@class='button button blue']";
    private static final String IS_CARD_EMPTY = "//h1[contains(text(),'Корзина пуста')]";

    @BeforeTest
    public void setup() {
        String pathToFirefoxDriver = "./src/main/resources/lib/geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", pathToFirefoxDriver);
        page = new WebPage();
    }

    @AfterTest
    public void teardown() {
        //page.close();
    }

    @Test(description = "1. Открыть в браузере сайт https://www.ozon.ru/. Если откроется всплывающее окно – закрыть его.")
    public void openWebPage() {
        page.open("https://www.ozon.ru/");
        if(page.isLoaded(CLOSE_BUTTON)){
        page.waitAndClick(CLOSE_BUTTON);}
    }

    @Test(description = "2. В меню 'Все разделы' выбрать категорию 'Музыка'.", dependsOnMethods = "openWebPage")
    public void chooseMusicCategory() {
        page.waitAndClick(ALL_CATEGORIES);
        page.waitAndClick(MUSIC_CATEGORY);
        page.waitAndClick(SEARCH_BUTTON);
    }

    @Test(description = "3.  С открывшейся страницы переход на страницу 'Виниловые пластинки'.", dependsOnMethods = "chooseMusicCategory")
    public void vynileCategory() {
        page.waitAndClick(VYNILE_CATEGORY);
    }

    @Test(description = "4. Проверяется, что открылся список товаров.", dependsOnMethods = "vynileCategory")
    public void isListOfGoodsLoaded() {
        Assert.assertEquals(true, page.isLoaded(GOODS_LIST));
    }

    @Test(description = "5. Получить количество товаров на странице.", dependsOnMethods = "isListOfGoodsLoaded")
    public void goodsCount() {
        goodsCount = new Long(page.getElementsCount(GOODS_COUNT));
    }

    @Test(description = "6. Сгенерировать случайное число в диапазоне от 1 до количества товаров, полученного в п.5.", dependsOnMethods = "goodsCount")
    public void generateRandomNumber() {
        randomProduct = Utils.randomize(goodsCount);
        if ((randomProduct <= goodsCount) & (randomProduct >= 0)) {
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }
    }

    @Test(description = "7. Выбрать товар под номером, полученным в п.6. ( Перейти на страницу товара ).", dependsOnMethods = "generateRandomNumber")
    public void chooseProductByNumber() {
        String path = "//div[@data-index='" + randomProduct.toString() + "']";
        page.waitAndClick(path);
    }

    @Test(description = "8. Запомнить стоимость и название данного товара.", dependsOnMethods = "chooseProductByNumber")
    public void rememberCostAndTitle() {
        costAndTitle[0] = page.getElementsText(PRODUCT_TITLE);
        costAndTitle[1] = page.getElementsText(PRODUCT_PRICE);
    }

    @Test(description = "9. Добавить товар в корзину", dependsOnMethods = "rememberCostAndTitle")
    public void addFirstProductToCard() {
        page.waitAndClick(ADD_TO_CARD);
    }

    @Test(description = "10. Проверить то, что в корзине появился добавленный в п.9 товар. ( Проверка данных\n" +
            "  определенного товара. Необходим переход в корзину для этого.", dependsOnMethods = "addFirstProductToCard")
    public void checkGoodsInTheCard() {
        Utils.wait(1000);
        page.waitAndClick(OPEN_CARD);
        Assert.assertEquals(page.getElementsText(FIRST_PRODUCT_TITLE), costAndTitle[0]);
    }

    @Test(description = "11. Вернуться на страницу 'Виниловые пластинки'.", dependsOnMethods = "checkGoodsInTheCard")
    public void returnToVynilCategory() {
        page.waitAndClick(ALL_CATEGORIES);
        page.waitAndClick(MUSIC_CATEGORY);
        page.waitAndClick(SEARCH_BUTTON);
        page.waitAndClick(VYNILE_CATEGORY);
    }

    @Test(description = "12. Сгенерировать случайное число в диапазоне от 1 до количества товаров, полученного в\n" +
            " п.5.", dependsOnMethods = "returnToVynilCategory")
    public void generateSecondRandomNumber() {
        randomProduct = Utils.randomize(goodsCount);
        if ((randomProduct <= goodsCount) & (randomProduct >= 0)) {
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }
    }

    @Test(description = "13. Выбрать товар под номером, полученным в п.12. (Перейти на страницу товара)", dependsOnMethods = "generateSecondRandomNumber")
    public void chooseSecondProductByNumber() {
        page.isLoaded(GOODS_LIST);
        String path = "//div[@data-index='" + randomProduct.toString() + "']";
        page.waitAndClick(path);
    }

    @Test(description = "14. Запомнить стоимость и название данного товара.", dependsOnMethods = "chooseSecondProductByNumber")
    public void rememberSecondCostAndTitle() {
        secondCostAndTitle[0] = page.getElementsText(PRODUCT_TITLE);
        secondCostAndTitle[1] = page.getElementsText(PRODUCT_PRICE);
    }

    @Test(description = "15. Добавить товар в корзину.", dependsOnMethods = "rememberSecondCostAndTitle")
    public void addSecondProductToCard() {
        page.waitAndClick(ADD_TO_CARD);
    }

    @Test(description = "16. Проверить то, что в корзине два товара. (Проевряется header сайта)", dependsOnMethods = "addSecondProductToCard")
    public void checkGoodsCountInCard() {
        Utils.wait(1000);
        Assert.assertEquals("2", page.getElementsText(GOODS_COUNT_IN_CARD));
    }

    @Test(description = "17. Открыть корзину.", dependsOnMethods = "checkGoodsCountInCard")
    public void openCard() {
        page.waitAndClick(OPEN_CARD);
    }

    @Test(description = "18. Проверить то, что в корзине раннее выбранные товары и итоговая стоимость по двум\n" +
            "         товарам рассчитана верно.", dependsOnMethods = "openCard")
    public void checkResultingGoodAndTheirCost() {
        String path = "//span[contains(text(),'" + costAndTitle[1] + "')]";
        try {
            //System.out.println("Product " + page.getElementsText(path) + " was added to card");
            path = "//span[contains(text(),'" + costAndTitle[1] + "')]";
            //System.out.println("Product " + page.getElementsText(path) + " was added to card");
        } catch (NoSuchElementException e) {
            System.out.println("One of the goods wasn't added to the list");
        }
        String resCost = page.getElementsText(RESULTING_COST);
        resCost = resCost.substring(0, resCost.length() - 2).replaceAll("\\s+", "");
        System.out.println(resCost+"="+costAndTitle[1]+"+"+secondCostAndTitle[1]);
        costAndTitle[1] = costAndTitle[1].substring(0, costAndTitle[1].length() - 2).replaceAll("\\s+", "");
        secondCostAndTitle[1] = secondCostAndTitle[1].substring(0, secondCostAndTitle[1].length() - 2).replaceAll("\\s+", "");
        int fp = Integer.parseInt(costAndTitle[1]);
        int sp = Integer.parseInt(secondCostAndTitle[1]);
        int res = Integer.parseInt(resCost);
        System.out.println(resCost+"="+costAndTitle[1]+"+"+secondCostAndTitle[1]);
        System.out.println(res+"="+fp+"+"+sp);
        Assert.assertEquals(res, fp + sp);
    }

    @Test(description = "19. Удалить из корзины все товары", dependsOnMethods = "checkResultingGoodAndTheirCost")
    public void deleteAll() {
        page.waitAndClick(DELETE_ALL_FROM_CARD);
        page.waitAndClick(CONFIRM_DELETION);
    }

    @Test(description = "20. Проверить, что корзина пуста.", dependsOnMethods = "deleteAll")
    public void isCardEmpty() {
        Utils.wait(1000);
        page.isLoaded(IS_CARD_EMPTY);
    }
}
