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

    private int goodsCount = 0;
    private ProductInfo product1 = new ProductInfo();
    private ProductInfo product2 = new ProductInfo();

    private static final String OZON_URL = "https://www.ozon.ru/";

    /**
     * Максимальное время выполнения.
     */
    private static final long TIME_OUT = 20000;

    /**
     * Кнопка закрытия всплывающего окна.
     */
    private static final String CLOSE_BUTTON = "//button[@class='close']";
    /**
     * Меню "Все категории".
     */
    private static final String ALL_CATEGORIES = "//*[@class='context-chip m-all-contexts']";
    /**
     * Категория "музыка".
     */
    private static final String MUSIC_CATEGORY = "//div[contains(text(),'Музыка')]";
    /**
     * Кнопка поиска.
     */
    private static final String SEARCH_BUTTON = "//button[@class='m-search button default flat-button']";
    /**
     * Категория "виниловые пластинки".
     */
    private static final String VYNILE_CATEGORY = "//a[@class='_7eb738']";
    /**
     * Список товаров.
     */
    private static final String GOODS_LIST = "//div[@class='tiles']";
    /**
     * Товары.
     */
    private static final String GOODS_COUNT = "//div[@class='tile m-default m-border']";
    /**
     * Название товара.
     */
    private static final String PRODUCT_TITLE = "//h1[@class='_718dda']";
    /**
     * Стоимость товара.
     */
    private static final String PRODUCT_PRICE = "//span[contains(@class,'b3411b')]";
    /**
     * Кнопка "Добавить в корзину".
     */
    private static final String ADD_TO_CARD = "//button[@class='_652bc6']";
    /**
     * Кнопка перехода в корзину.
     */
    private static final String OPEN_CARD = "//a[contains(text(),'В корзине')]";
    /**
     * Название первого товара в корзине.
     */
    private static final String FIRST_PRODUCT_TITLE = "//span[@data-v-7246cfc8='']";
    /**
     * Количество товаров в корзине - два.
     */
    private static final String HAS_2_GOODS_IN_CARD = "//span[@data-v-c66bfbbc='' and @class='f-caption--bold ef9580' and contains(text(),'2')]";
    /**
     * Общая стоимость товаров в корзине.
     */
    private static final String RESULTING_COST = "//span[@data-v-c66bfbbc='' and @class='total-middle-footer-text']";
    /**
     * Кнопка "удалить выбранное".
     */
    private static final String DELETE_ALL_FROM_CARD = "//span[contains(text(),'Удалить выбранные')]";
    /**
     * Кнопка подтверждающая удаление.
     */
    private static final String CONFIRM_DELETION = "//button[@class='button button blue']";
    /**
     * Надпись "Корзина пуста".
     */
    private static final String IS_CARD_EMPTY = "//h1[contains(text(),'Корзина пуста')]";

    @BeforeTest
    public void setup() {
        page = new WebPage();
    }

    @AfterTest
    public void teardown() {
        // page.close();
    }

    @Test(description = "1. Открыть в браузере сайт https://www.ozon.ru/. Если откроется всплывающее окно – закрыть его.", timeOut = TIME_OUT)
    public void openWebPage() {
        page.open(OZON_URL);
        if (page.isExists(CLOSE_BUTTON)) {
            page.waitAndClick(CLOSE_BUTTON);
        }
    }

    @Test(description = "2. В меню 'Все разделы' выбрать категорию 'Музыка'.", dependsOnMethods = "openWebPage", timeOut = TIME_OUT)
    public void chooseMusicCategory() {
        page.waitAndClick(ALL_CATEGORIES);
        page.waitAndClick(MUSIC_CATEGORY);
        page.waitAndClick(SEARCH_BUTTON);
    }

    @Test(description = "3.  С открывшейся страницы переход на страницу 'Виниловые пластинки'.", dependsOnMethods = "chooseMusicCategory", timeOut = TIME_OUT)
    public void vinylCategory() {
        page.waitAndClick(VYNILE_CATEGORY);
    }

    @Test(description = "4. Проверяется, что открылся список товаров.", dependsOnMethods = "vinylCategory", timeOut = TIME_OUT)
    public void isListOfGoodsLoaded() {
        Assert.assertTrue(page.isExists(GOODS_LIST));
    }

    @Test(description = "5. Получить количество товаров на странице.", dependsOnMethods = "isListOfGoodsLoaded", timeOut = TIME_OUT)
    public void goodsCount() {
        goodsCount = page.getElementsCount(GOODS_COUNT);
    }

    @Test(description = "6. Сгенерировать случайное число в диапазоне от 1 до количества товаров, полученного в п.5.", dependsOnMethods = "goodsCount", timeOut = TIME_OUT)
    public void generateRandomNumber() {
        product1.index = (int) (Math.random() * goodsCount);
        Assert.assertTrue(product1.index < goodsCount);
    }

    @Test(description = "7. Выбрать товар под номером, полученным в п.6. ( Перейти на страницу товара ).", dependsOnMethods = "generateRandomNumber", timeOut = TIME_OUT)
    public void chooseProductByNumber() {
        page.waitAndClick(format("//div[@data-index='%s']", product1.index));
    }

    @Test(description = "8. Запомнить стоимость и название данного товара.", dependsOnMethods = "chooseProductByNumber", timeOut = TIME_OUT)
    public void rememberCostAndTitle() {
        product1.title = page.getElementText(PRODUCT_TITLE).replaceAll("'.*", "");
        product1.price = Integer.parseInt(page.getElementText(PRODUCT_PRICE).replaceAll("[^0-9]+", ""));
    }

    @Test(description = "9. Добавить товар в корзину", dependsOnMethods = "rememberCostAndTitle", timeOut = TIME_OUT)
    public void addFirstProductToCard() {
        page.waitAndClick(ADD_TO_CARD);
    }

    @Test(description = "10. Проверить то, что в корзине появился добавленный в п.9 товар. ( Проверка данных\n" +
            "  определенного товара. Необходим переход в корзину для этого.", dependsOnMethods = "addFirstProductToCard", timeOut = TIME_OUT)
    public void checkGoodsInTheCard() {
        //  Utils.wait(1000);
        page.waitAndClick(OPEN_CARD);
        Assert.assertEquals(page.getElementText(FIRST_PRODUCT_TITLE), product1.title);
    }

    @Test(description = "11. Вернуться на страницу 'Виниловые пластинки'.", dependsOnMethods = "checkGoodsInTheCard", timeOut = TIME_OUT)
    public void returnToVynilCategory() {
        page.waitAndClick(ALL_CATEGORIES);
        page.waitAndClick(MUSIC_CATEGORY);
        page.waitAndClick(SEARCH_BUTTON);
        page.waitAndClick(VYNILE_CATEGORY);
    }

    @Test(description = "12. Сгенерировать случайное число в диапазоне от 1 до количества товаров, полученного в\n" +
            " п.5.", dependsOnMethods = "returnToVynilCategory", timeOut = TIME_OUT)
    public void generateSecondRandomNumber() {
        do {
            product2.index = (int) (Math.random() * goodsCount);
        } while (product1.index == product2.index);

        Assert.assertTrue(product2.index < goodsCount);
    }

    @Test(description = "13. Выбрать товар под номером, полученным в п.12. (Перейти на страницу товара)", dependsOnMethods = "generateSecondRandomNumber", timeOut = TIME_OUT)
    public void chooseSecondProductByNumber() {
        if (page.isExists(GOODS_LIST)) {
            page.waitAndClick(format("//div[@data-index='%s']", product2.index));
        } else {
            Assert.fail("Не загрузился лист товаров");
        }
    }

    @Test(description = "14. Запомнить стоимость и название данного товара.", dependsOnMethods = "chooseSecondProductByNumber", timeOut = TIME_OUT)
    public void rememberSecondCostAndTitle() {
        product2.title = page.getElementText(PRODUCT_TITLE).replaceAll("'.*", "");
        product2.price = Integer.parseInt(page.getElementText(PRODUCT_PRICE).replaceAll("[^0-9]+", ""));
    }

    @Test(description = "15. Добавить товар в корзину.", dependsOnMethods = "rememberSecondCostAndTitle", timeOut = TIME_OUT)
    public void addSecondProductToCard() {
        page.waitAndClick(ADD_TO_CARD);
    }

    @Test(description = "16. Проверить то, что в корзине два товара. (Проевряется header сайта)", dependsOnMethods = "addSecondProductToCard", timeOut = TIME_OUT)
    public void checkGoodsCountInCard() {
        Assert.assertTrue(page.isExists(HAS_2_GOODS_IN_CARD));
    }

    @Test(description = "17. Открыть корзину.", dependsOnMethods = "checkGoodsCountInCard")
    public void openCard() {
        page.waitAndClick(OPEN_CARD);
    }

    @Test(description = "18. Проверить то, что в корзине раннее выбранные товары и итоговая стоимость по двум\n" +
            "         товарам рассчитана верно.", dependsOnMethods = "openCard", timeOut = TIME_OUT)
    public void checkResultingGoodAndTheirCost() {
        Assert.assertTrue(page.isExists(format("//span[contains(text(),'%s')]", product1.title)));
        Assert.assertTrue(page.isExists(format("//span[contains(text(),'%s')]", product2.title)));

        int fullCost = Integer.parseInt(page.getElementText(RESULTING_COST).replaceAll("[^0-9]+", ""));
        ;
        Assert.assertEquals(fullCost, product1.price + product2.price);
    }

    @Test(description = "19. Удалить из корзины все товары", dependsOnMethods = "checkResultingGoodAndTheirCost", timeOut = TIME_OUT)
    public void deleteAll() {
        page.waitAndClick(DELETE_ALL_FROM_CARD);
        page.waitAndClick(CONFIRM_DELETION);
    }

    @Test(description = "20. Проверить, что корзина пуста.", dependsOnMethods = "deleteAll", timeOut = TIME_OUT)
    public void isCardEmpty() {
        page.isExists(IS_CARD_EMPTY);
    }
}
