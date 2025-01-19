package com.practicetestautomation.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SuccessfulLoginPage extends BasePage {

    //Constructor
    public SuccessfulLoginPage(WebDriver driver){
        super(driver);
    }

    //locators
    private By logOutButtonLocator = By.linkText("Log out");

    //methods
    public boolean isLogOutButtonDisplayed(){
        return isElementDisplayed(logOutButtonLocator);
    }

    public void load(){
        waitForElement(logOutButtonLocator);
    }
}
