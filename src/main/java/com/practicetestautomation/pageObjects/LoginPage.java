package com.practicetestautomation.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{

    //Constructor
    public LoginPage(WebDriver driver){
        super(driver);
    }

    /*
    *************************************************************************************
        Locators
    *************************************************************************************
    */

    private By usernameInputLocator = By.id("username");
    private By passwordInputLocator = By.id("password");
    private By submitButtonLocator = By.id("submit");
    private By errorMessageLocator = By.id("error");

    /*
    *************************************************************************************
        Methods
    *************************************************************************************
    */

    public void visit(){
        super.visit("https://practicetestautomation.com/practice-test-login/");
    }

    public void enterUsername(String username){
        driver.findElement(usernameInputLocator).sendKeys(username);
    }

    public void enterPassword(String password){
        driver.findElement(passwordInputLocator).sendKeys(password);
    }

    public void clickSubmitButton(){
        driver.findElement(submitButtonLocator).click();
    }

    public SuccessfulLoginPage executeLogin(String username, String password){
        enterUsername(username);
        enterPassword(password);
        clickSubmitButton();
        return new SuccessfulLoginPage(driver);
    }

    public boolean isErrorDisplayed(){
        return isElementDisplayed(errorMessageLocator);
    }

    public String getErrorMessageText(){
       return waitForElement(errorMessageLocator).getText();
    }
}
