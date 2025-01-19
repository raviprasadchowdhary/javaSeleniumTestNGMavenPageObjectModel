package com.practicetestautomation.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    //constructor
    public BasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /*
    *************************************************************************************
        Methods
    *************************************************************************************
    */

    protected void visit(String Url){
        driver.get(Url);
    }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public String getPageSource(){
        return driver.getPageSource();
    }

    protected WebElement waitForElement(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected boolean isElementDisplayed(By locator){
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void clickElement(By locator){
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
    }

    protected void sendKeysIntoInputField(By locator, String input){
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(input);
    }

    protected String getTextFromElementLocator(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    protected String getAttributeValue(By locator, String attribute){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getAttribute(attribute);
    }

    protected void clearKeysFromInputField(By locator){
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).clear();
    }
}
