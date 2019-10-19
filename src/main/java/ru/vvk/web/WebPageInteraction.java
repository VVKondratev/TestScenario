package ru.vvk.web;

import java.util.NoSuchElementException;

public class WebPageInteraction {
    /** Количество действий */
    final int ACTION_CHAIN = 5;
    /** Поле элемент */
    ElementAccess element;

    public WebPageInteraction() {
        element = new ElementAccess();
        /** Пункт один. Октрывается сайт. */
        element.open("https://www.ozon.ru/");
        String[] costAndTitle = new String[2];
        String[] secondCostAndTitle = new String[2];
        Long numberOfElements = 0L;
        String[] path = initActionChain();
        Utils.wait(1000);
        /** Последовательно выполняется пункты с первого по четвертый.
         *  Первый пункт. Закрывается всплывающее окно.
         *  Второй пункт. Выбирается меню "Все разделы". В меню "Все разделы" выбрается категория "Музыка". Переход на эту страницу.
         *  Третий пункт.  С открывшейся страницы переход на страницу "Виниловые пластинки".
         * */
        for (int i = 0; i < ACTION_CHAIN; i++) {
            element.getAccessAndClick(path[i]);
        }
        /** Пункт четыре. Проверяется, что открылся список товаров. */
        if (isGoodsListLoaded()) {
            /** Пункт пять. Получить количество товаров на странице. */
            numberOfElements = goodsNumber();
            /** Пункт шесть. Сгенерировать случайное число в диапазоне от 1 до количества товаров, полученного в п.5. */
            /** 7. Выбрать товар под номером, полученным в п.6. ( Перейти на страницу товара ). */
            chooseProductByNumber(Utils.randomize(numberOfElements));
        } else {
            System.out.println("Goods list wasn't load");
        }
        /** 8. Запомнить стоимость и название данного товара. */
        rememberCostAndTitle(costAndTitle);
        /** 9. Добавить товар в корзину */
        addToCart();
        /** 10. Проверить то, что в корзине появился добавленный в п.9 товар. ( Проверка данных
         определенного товара. Необходим переход в корзину для этого. ) */
        checkGoodsInTheCard(costAndTitle[0]);
        /** 11. Вернуться на страницу "Виниловые пластинки". */
        returnToVynil(path);
        if (isGoodsListLoaded()) {
            numberOfElements = goodsNumber();
            /** 12. Сгенерировать случайное число в диапазоне от 1 до количества товаров, полученного в
             п.5 */
            /** 13. Выбрать товар под номером, полученным в п.12. ( Перейти на страницу товара ) */
            chooseProductByNumber(Utils.randomize(numberOfElements));
        } else {
            System.out.println("Goods list wasn't load");
        }
        /** 14. Запомнить стоимость и название данного товара. */
        rememberCostAndTitle(secondCostAndTitle);
        /** 15. Добавить товар в корзину */
        addToCart();
        Utils.wait(1000);
        /** 16. Проверить то, что в корзине два товара. ( Проевряется header сайта) */
        System.out.println("Number of goods is 2: " + checkGoodsNum());
        /** 17. Открыть корзину. */
        /** 18. Проверить то, что в корзине раннее выбранные товары и итоговая стоимость по двум
         товарам рассчитана верно. */
        checkResultingGoodAndTheirCost(costAndTitle, secondCostAndTitle);
        /** 19. Удалить из корзины все товары */
        deleteAll();
        /** 20. Проверить, что корзина пуста. */
        System.out.println(isCardEmpty());
        /** 21. Закрыть браузер. */
        closeIt();
    }

    /**
     * Четвертый пункт. Проверяется, открылся ли список товаров.
     * @return - Возвращется true, если список открылся. Возвращется false, если список не открылся.
     */
    private boolean isGoodsListLoaded() {
        String path = "//div[@class='tiles']";
        return element.isLoaded(path);
    }

    /**
     * Пятый пункт. Получается количество товаров в списке.
     * @return - число товаров в списке.
     */
    private Long goodsNumber() {
        String path = "//div[@class='tile m-default m-border']";
        return new Long(element.getElementsNumber(path));
    }

    /**
     * Седьмой пункт. Выбирается товар по переданному номеру.
     * @param id - номер товара в списке.
     */
    private void chooseProductByNumber(Long id) {
        String path = "//div[@data-index='" + id.toString() + "']";
        element.getAccessAndClick(path);
    }

    /**
     * Восьмой/четырнадцатый пункт. Запоминается название товара, и его стоимость.
     * @param costAndTitle - Переменная, в которую сохраняются название товара и его стоимость.
     */
    private void rememberCostAndTitle(String[] costAndTitle) {
        String path = "//h1[@class='_718dda']";
        costAndTitle[0] = element.getElementsText(path);
        path = "//span[@data-v-58d56c0e='']";
        costAndTitle[1] = element.getElementsText(path);
    }

    /**
     * Девятый/пятнадцатый пункт. Продукт добавляется в корзину.
     */
    private void addToCart() {
        String path = "//button[@class='_652bc6']";
        element.getAccessAndClick(path);
    }

    /**
     * Десятый пункт. По названию продукта проверяется, что он был добавлен в корзину.
     * @param title - название товара, запомненное в пункте девять.
     */
    private void checkGoodsInTheCard(String title) {
        Utils.wait(4000);
        String path = "//a[contains(text(),'В корзине')]";
        element.getAccessAndClick(path);
        path = "//span[@data-v-7246cfc8='']";
        System.out.println("The product " + title + " was added to the card:" + element.getElementsText(path).equals(title));
    }

    /**
     * Одинадцатый пункт. Возвращение к категории виниловых пластинок.
     * @param chain - массив xpath-запросов.
     */
    private void returnToVynil(String[] chain) {
        for (int i = 1; i < ACTION_CHAIN; i++) {
            element.getAccessAndClick(chain[i]);
        }
    }

    /**
     * Шестнадцатый пункт. Проверяется, что в корзине два товара.
     * @return - Возвращается true, если товаров два. Возвращается false, если товаров не два.
     */
    private String checkGoodsNum() {
        String path = "//span[@data-v-c66bfbbc='' and @class='f-caption--bold ef9580']";
        return element.getElementsText(path);
    }

    /**
     * Девятнадцатый пункт. Все товары удаляются из корзины.
     */
    private void deleteAll() {
        String path = "//span[contains(text(),'Удалить выбранные')]";
        element.getAccessAndClick(path);
        path = "//button[@class='button button blue']";
        element.getAccessAndClick(path);
    }

    /**
     * Семнадцатый/восемнадцатый пункт. Переход в корзину, проверка товаров по названию и расчет итоговой стоимости.
     * @param expectedProductFirst - название и цена первого товара.
     * @param expectedProductSecond - название и цена второго товара.
     * @return - результаты проверки.
     */
    private boolean checkResultingGoodAndTheirCost(String[] expectedProductFirst, String[] expectedProductSecond) {
        Utils.wait(4000);
        String path = "//a[contains(text(),'В корзине')]";
        element.getAccessAndClick(path);
        try {
            path = "//span[contains(text(),'" + expectedProductFirst[0] + "')]";
            System.out.println("Product " + element.getElementsText(path) + " was added to card");
            path = "//span[contains(text(),'" + expectedProductSecond[0] + "')]";
            System.out.println("Product " + element.getElementsText(path) + " was added to card");
        } catch (NoSuchElementException e) {
            System.out.println("One of the goods wasn't added to the list");
        }
        //Получаем общую сумму
        path = "//span[@data-v-c66bfbbc='' and @class='total-middle-footer-text']";
        String resCost = element.getElementsText(path);
        resCost = resCost.substring(0, resCost.length() - 2).replaceAll("\\s+", "");
        expectedProductFirst[1] = expectedProductFirst[1].substring(0, expectedProductFirst[1].length() - 2).replaceAll("\\s+", "");
        expectedProductSecond[1] = expectedProductSecond[1].substring(0, expectedProductSecond[1].length() - 2).replaceAll("\\s+", "");
        System.out.println(expectedProductFirst[1] + "+" + expectedProductSecond[1] + "=" + resCost);
        Integer fp = Integer.parseInt(expectedProductFirst[1]);
        Integer sp = Integer.parseInt(expectedProductSecond[1]);
        Integer res = Integer.parseInt(resCost);
        return ((fp + sp) == res);
    }

    /**
     * Двадцатый пункт. Проверяется, что корзина пуста.
     * @return - результаты проверки.
     */
    //Twenty
    private boolean isCardEmpty(){
        Utils.wait(1000);
        String path ="//h1[contains(text(),'Корзина пуста')]";
        return element.isLoaded(path);
    }

    /**
     * Двадцать первый пункт. Firefox закрывается.
     */
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