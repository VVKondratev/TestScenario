package ru.vvk.web;

import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Data
public class ElementAccess {

    private WebDriver driver;
    private WebElement element;

    public boolean getAccessAndClick(String path) {
        WebDriverWait wait = new WebDriverWait(driver, 30, 1000);
        element = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(path))));
        // element = wait.until(drv -> driver.findElement(By.xpath(path)));
        if (element != null) {
            if (element.isDisplayed()) {
                element.click();
                return true;
            } else {
                System.out.println("Element is not displayed");
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isLoaded(String path) {
        Long timeOut = new Long(20);
        WebDriverWait wait = new WebDriverWait(driver, timeOut,1000);
        WebElement element = wait.until(drv -> driver.findElement(By.xpath(path)));
        if (element != null) {
            return true;
        } else {
            return false;
        }
    }

    public Long getElementsNumber(String path) {
        Long timeOut = new Long(20);
        WebDriverWait wait = new WebDriverWait(driver, timeOut,1000);
        List<WebElement> element = wait.until(drv -> driver.findElements(By.xpath(path)));
        return new Long(element.size());
    }

    public String getElementsText(String path) {
        Long timeOut = new Long(20);
        WebDriverWait wait = new WebDriverWait(driver, timeOut,1000);
        WebElement element = wait.until(drv -> driver.findElement(By.xpath(path)));
        if (element != null) {
            return element.getText();
        } else {
            return "";
        }
    }

    //First point
    public void open(String webpage) {
        driver.get(webpage);
    }

    public void close(){
        driver.quit();
    }


    public ElementAccess() {
        driver = new FirefoxDriver();
    }
}
