package com.practicetestautomation.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SuccessfulLoginPage extends BasePage {
    private WebDriver driver;
    private WebDriverWait wait;

    //Constructor
    public SuccessfulLoginPage(WebDriver driver){
        super(driver);
    }

    //locators
    private By logOutButtonLocator = By.linkText("Log out");

    //methods
    public boolean isLogOutButtonDisplayed(){
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(logOutButtonLocator)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
