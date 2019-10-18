package ru.vvk.web;

import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Data
public class ElementAccess {

    private WebElement element;

    public boolean getAccessAndClick(String path, WebDriver driver) {
        Long timeOut = new Long(60);
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(path)));
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

    public boolean isLoaded(String path, WebDriver driver) {
        Long timeOut = new Long(20);
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        WebElement element = wait.until(drv -> driver.findElement(By.xpath(path)));
        if(element !=null){
            return true;
        }
        else{
            return false;
        }
    }
    public Long getElementsNumber(String path, WebDriver driver) {
        Long timeOut = new Long(20);
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        List<WebElement> element = wait.until(drv -> driver.findElements(By.xpath(path)));
        return new Long(element.size());
    }

    public ElementAccess() {
//        List<WebElement> elements = driver.findElements(By.xpath(path));
//        System.out.println(elements.size());
    }
}
