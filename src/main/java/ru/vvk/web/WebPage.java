package ru.vvk.web;

import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Data
public class WebPage {
    /**
     * Поле драйвера
     */
    private WebDriver driver;
    /**
     * Поле элемента
     */
    private WebElement element;

    /**
     * С помощью xpath-запроса получается элемент. Для этого элемента вызывается метод click().
     *
     * @param path - xpath запрос. Передается в переменой типа String.
     */
    public void waitAndClick(String path) {
        WebDriverWait wait = new WebDriverWait(driver, 30, 1000);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(path))));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(path)));
        driver.findElement(By.xpath(path)).click();
    }

    /**
     * Ожидается загрузка запрошенного с помощью xpath-запроса элемента.
     *
     * @param path - xpath запрос. Передается в переменой типа String.
     * @return - Возвращается true, если элемент был загруженн. Возвращается false, если элемент загружен не был.
     */
    public boolean isLoaded(String path) {
        Long timeOut = new Long(20);
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOut, 1000);
            WebElement element = wait.until(drv -> driver.findElement(By.xpath(path)));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    /**
     * С помощью xpath получаеются элементы. Находится их количество.
     *
     * @param path - xpath запрос. Передается в переменой типа String.
     * @return - Возвращается количество найденных элементов.
     */
    public Long getElementsCount(String path) {
        Long timeOut = new Long(20);
        WebDriverWait wait = new WebDriverWait(driver, timeOut, 1000);
        List<WebElement> element = wait.until(drv -> driver.findElements(By.xpath(path)));
        return new Long(element.size());
    }

    /**
     * С помощью xpath получается элемент. У него получается текст.
     *
     * @param path - xpath запрос. Передается в переменой типа String.
     * @return - Возвращается текст элемента.
     */
    public String getElementsText(String path) {
        Long timeOut = new Long(20);
        WebDriverWait wait = new WebDriverWait(driver, timeOut, 1000);
        WebElement element = wait.until(drv -> driver.findElement(By.xpath(path)));
        if (element != null) {
            return element.getText();
        } else {
            return "";
        }
    }

    /**
     * Первый пункт. Запускается переданная вебстраница.
     *
     * @param webpage URL вебстраницы.
     */
    public void open(String webpage) {
        driver.get(webpage);
    }

    /**
     * Браузер закрывается.
     */
    public void close() {
        driver.quit();
    }

    /**
     * Конструктор - инициализируется объект driver
     */
    public WebPage() {
        driver = new FirefoxDriver();
    }
}
