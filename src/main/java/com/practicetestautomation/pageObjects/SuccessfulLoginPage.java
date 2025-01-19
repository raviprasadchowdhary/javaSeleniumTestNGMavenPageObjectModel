package com.practicetestautomation.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SuccessfulLoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    //Constructor
    public SuccessfulLoginPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //locators
    private By logOutButtonLocator = By.linkText("Log out");

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public String getPageSource(){
        return driver.getPageSource();
    }

    public boolean isLogOutButtonDisplayed(){
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(logOutButtonLocator)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
