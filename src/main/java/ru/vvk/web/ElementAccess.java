package ru.vvk.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementAccess {

    private WebElement element;

    public void getAccess(String path, WebDriver driver){
        element = driver.findElement(By.xpath(path));
    }

    public boolean click(){
        if(element != null){
        if (element.isDisplayed()) {
            element.click();
            return true;
        }else{
            System.out.println("Element is not displaed");
            return false;
        }}else{
            return false;
        }
    }

    public ElementAccess(){}
}
