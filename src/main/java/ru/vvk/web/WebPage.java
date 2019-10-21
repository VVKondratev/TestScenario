package ru.vvk.web;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WebPage {
    private static final String FIREFOX_DRIVER = "./lib/geckodriver.exe";
    private static final long TIME_OUT = 30L;
    private static final long SLEEP_INTERVAL = 1000L;

    /**
     * Поле драйвера
     */
    private WebDriver driver;

    public WebPage() {
        System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER);
        driver = new FirefoxDriver();
    }

    /**
     * Ожидает появление элемента. После появления нажимает на элемент.
     *
     * @param path - xpath запрос. Передается в переменой типа String.
     */
    public void waitAndClick(String path) {
        WebDriverWait wait = new WebDriverWait(driver, TIME_OUT, SLEEP_INTERVAL);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(path))));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(path)));
        do {
            try {
                driver.findElement(By.xpath(path)).click();
                break;
            } catch (Throwable e) {
                try {
                    Thread.sleep(SLEEP_INTERVAL);
                } catch (InterruptedException ex) {
                    continue;
                }
            }
        } while (true);
    }

    /**
     * Проверяется существование элемента на странице.
     *
     * @param path - xpath запрос. Передается в переменой типа String.
     * @return - Возвращается true, если элемент был загруженн. Возвращается false, если элемент загружен не был.
     */
    public boolean isExists(String path) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, TIME_OUT, SLEEP_INTERVAL);
            wait.until(drv -> driver.findElement(By.xpath(path)));
            return true;
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    /**
     * Количество элементов, найденных по xpath.
     *
     * @param path - xpath запрос. Передается в переменой типа String.
     * @return - Возвращается количество найденных элементов.
     */
    public int getElementsCount(String path) {
        WebDriverWait wait = new WebDriverWait(driver, TIME_OUT, SLEEP_INTERVAL);
        List<WebElement> element = wait.until(drv -> driver.findElements(By.xpath(path)));
        return element.size();
    }

    /**
     * Ожидает появления элмента и возвращает его текстовое содержимое.
     *
     * @param path - xpath запрос. Передается в переменой типа String.
     * @return - Возвращается текст элемента.
     */
    public String getElementText(String path) {
        WebDriverWait wait = new WebDriverWait(driver, TIME_OUT, SLEEP_INTERVAL);
        WebElement element = wait.until(drv -> driver.findElement(By.xpath(path)));
        return element.getText();
    }

    /**
     * Запускается переданная вебстраница.
     *
     * @param url URL вебстраницы.
     */
    public void open(String url) {
        driver.get(url);
    }

    /**
     * Браузер закрывается.
     */
    public void close() {
        driver.quit();
    }
}
