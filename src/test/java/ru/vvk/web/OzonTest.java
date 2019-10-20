package ru.vvk.web;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static java.lang.String.format;

public class OzonTest {

    private class ProductInfo {
        int index;
        String title;
        int price;
    }

    WebPage page;
    //Number of elements
    private int goodsCount = 0;
    private ProductInfo product1 = new ProductInfo();
    private ProductInfo product2 = new ProductInfo();

    private static final String OZON_URL = "https://www.ozon.ru/";

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
    private static final String HAS_2_GOODS_IN_CARD = "//span[@data-v-c66bfbbc='' and @class='f-caption--bold ef9580' and contains(text(),'2')]";

    //Получаем общую сумму
    private static final String RESULTING_COST = "//span[@data-v-c66bfbbc='' and @class='total-middle-footer-text']";
    //Delete all goods from the card
    private static final String DELETE_ALL_FROM_CARD = "//span[contains(text(),'Удалить выбранные')]";
    //Confirm deletion
    private static final String CONFIRM_DELETION = "//button[@class='button button blue']";
    private static final String IS_CARD_EMPTY = "//h1[contains(text(),'Корзина пуста')]";

    @BeforeTest
    public void setup() {
        page = new WebPage();
    }

    @AfterTest
    public void teardown() {
        page.close();
    }

    @Test(description = "1. Открыть в браузере сайт https://www.ozon.ru/. Если откроется всплывающее окно – закрыть его.")
    public void openWebPage() {
        page.open(OZON_URL);
        if (page.isExists(CLOSE_BUTTON)) {
            page.waitAndClick(CLOSE_BUTTON);
        }
    }

    @Test(description = "2. В меню 'Все разделы' выбрать категорию 'Музыка'.", dependsOnMethods = "openWebPage")
    public void chooseMusicCategory() {
        page.waitAndClick(ALL_CATEGORIES);
        page.waitAndClick(MUSIC_CATEGORY);
        page.waitAndClick(SEARCH_BUTTON);
    }

    @Test(description = "3.  С открывшейся страницы переход на страницу 'Виниловые пластинки'.", dependsOnMethods = "chooseMusicCategory")
    public void vinylCategory() {
        page.waitAndClick(VYNILE_CATEGORY);
    }

    @Test(description = "4. Проверяется, что открылся список товаров.", dependsOnMethods = "vinylCategory")
    public void isListOfGoodsLoaded() {
        Assert.assertTrue(page.isExists(GOODS_LIST));
    }

    @Test(description = "5. Получить количество товаров на странице.", dependsOnMethods = "isListOfGoodsLoaded")
    public void goodsCount() {
        goodsCount = page.getElementsCount(GOODS_COUNT);
    }

    @Test(description = "6. Сгенерировать случайное число в диапазоне от 1 до количества товаров, полученного в п.5.", dependsOnMethods = "goodsCount")
    public void generateRandomNumber() {
        product1.index = (int) (Math.random() * goodsCount);
        Assert.assertTrue(product1.index < goodsCount);
    }

    @Test(description = "7. Выбрать товар под номером, полученным в п.6. ( Перейти на страницу товара ).", dependsOnMethods = "generateRandomNumber")
    public void chooseProductByNumber() {
        page.waitAndClick(format("//div[@data-index='%s']", product1.index));
    }

    @Test(description = "8. Запомнить стоимость и название данного товара.", dependsOnMethods = "chooseProductByNumber")
    public void rememberCostAndTitle() {
        product1.title = page.getElementText(PRODUCT_TITLE);
        product1.price = Integer.parseInt(page.getElementText(PRODUCT_PRICE).replaceAll("[^0-9]+", ""));
    }

    @Test(description = "9. Добавить товар в корзину", dependsOnMethods = "rememberCostAndTitle")
    public void addFirstProductToCard() {
        page.waitAndClick(ADD_TO_CARD);
    }

    @Test(description = "10. Проверить то, что в корзине появился добавленный в п.9 товар. ( Проверка данных\n" +
            "  определенного товара. Необходим переход в корзину для этого.", dependsOnMethods = "addFirstProductToCard")
    public void checkGoodsInTheCard() {
        //  Utils.wait(1000);
        page.waitAndClick(OPEN_CARD);
        Assert.assertEquals(page.getElementText(FIRST_PRODUCT_TITLE), product1.title);
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
        do {
            product2.index = (int) (Math.random() * goodsCount);
        } while (product1.index == product2.index);

        Assert.assertTrue(product2.index < goodsCount);
    }

    @Test(description = "13. Выбрать товар под номером, полученным в п.12. (Перейти на страницу товара)", dependsOnMethods = "generateSecondRandomNumber")
    public void chooseSecondProductByNumber() {
        if (page.isExists(GOODS_LIST)) {
            page.waitAndClick(format("//div[@data-index='%s']", product2.index));
        } else {
            Assert.fail("Не загрузился лист товаров");
        }
    }

    @Test(description = "14. Запомнить стоимость и название данного товара.", dependsOnMethods = "chooseSecondProductByNumber")
    public void rememberSecondCostAndTitle() {
        product2.title = page.getElementText(PRODUCT_TITLE);
        product2.price = Integer.parseInt(page.getElementText(PRODUCT_PRICE).replaceAll("[^0-9]+", ""));
    }

    @Test(description = "15. Добавить товар в корзину.", dependsOnMethods = "rememberSecondCostAndTitle")
    public void addSecondProductToCard() {
        page.waitAndClick(ADD_TO_CARD);
    }

    @Test(description = "16. Проверить то, что в корзине два товара. (Проевряется header сайта)", dependsOnMethods = "addSecondProductToCard")
    public void checkGoodsCountInCard() {
        Assert.assertTrue(page.isExists(HAS_2_GOODS_IN_CARD));
    }

    @Test(description = "17. Открыть корзину.", dependsOnMethods = "checkGoodsCountInCard")
    public void openCard() {
        page.waitAndClick(OPEN_CARD);
    }

    @Test(description = "18. Проверить то, что в корзине раннее выбранные товары и итоговая стоимость по двум\n" +
            "         товарам рассчитана верно.", dependsOnMethods = "openCard")
    public void checkResultingGoodAndTheirCost() {
        Assert.assertTrue(page.isExists(format("//span[contains(text(),'%s')]", product1.title)));
        Assert.assertTrue(page.isExists(format("//span[contains(text(),'%s')]", product2.title)));

        int fullCost = Integer.parseInt(page.getElementText(RESULTING_COST).replaceAll("[^0-9]+", ""));
        ;
        Assert.assertEquals(fullCost, product1.price + product2.price);
    }

    @Test(description = "19. Удалить из корзины все товары", dependsOnMethods = "checkResultingGoodAndTheirCost")
    public void deleteAll() {
        page.waitAndClick(DELETE_ALL_FROM_CARD);
        page.waitAndClick(CONFIRM_DELETION);
    }

    @Test(description = "20. Проверить, что корзина пуста.", dependsOnMethods = "deleteAll")
    public void isCardEmpty() {
        page.isExists(IS_CARD_EMPTY);
    }
}
