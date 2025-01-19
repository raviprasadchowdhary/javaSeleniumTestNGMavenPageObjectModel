package com.practicetestautomation.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    //Constructor
    public LoginPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Locators
    private By usernameInputLocator = By.id("username");
    private By passwordInputLocator = By.id("password");
    private By submitButtonLocator = By.id("submit");
    private By errorMessageLocator = By.id("error");

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

    public String getErrorMessageText(){
       return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageLocator)).getText();
    }
}
