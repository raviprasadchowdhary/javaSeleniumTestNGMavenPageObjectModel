package com.practicetestautomation.tests.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests {
    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp(){
        //Open page
        driver = new ChromeDriver();
        driver.get("https://practicetestautomation.com/practice-test-login/");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }

    @Test(groups = {"positive", "regression", "smoke"})
    public void testLoginFunctionality(){

        //Type username student into Username field
        WebElement usernameInput = driver.findElement(By.xpath("//input[@id='username']"));
        usernameInput.sendKeys("student");

        //Type password Password123 into Password field
        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='password']"));
        passwordInput.sendKeys("Password123");

        //Push Submit button
        WebElement submitButton = driver.findElement(By.xpath("//button[@id='submit']"));
        submitButton.click();

        //Verify new page URL contains practicetestautomation.com/logged-in-successfully/
        String expectedCurrentPageUrl = "https://practicetestautomation.com/logged-in-successfully/";
        String actualCurrentPageUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualCurrentPageUrl, expectedCurrentPageUrl);

        //Verify new page contains expected text ('Congratulations' or 'successfully logged in')
        String expectedloginSuccessMessage = "Congratulations student. You successfully logged in!";
        String pageSource = driver.getPageSource();
        if (pageSource != null) {
            Assert.assertTrue(pageSource.contains(expectedloginSuccessMessage));
        }

        //Verify button Log out is displayed on the new page
        WebElement logoutButton = driver.findElement(By.linkText("Log out"));
        Assert.assertTrue(logoutButton.isDisplayed());

    }

    @Parameters({"username", "password", "expectedErrorMessage"})
    @Test(groups = {"negative", "regression"})
    public void negativeLoginTest(String username, String password, String expectedErrorMessage){
        //Type username student into Username field
        sendKeys(findWebElementByXPath(driver, "//input[@name='username']"), username);

        //Type password incorrectPassword into Password field
        sendKeys(findWebElementByXPath(driver, "//input[@name='password']"), password);

        //Push Submit button
        clickElement(findWebElementByXPath(driver, "//button[@id='submit']"));

        //Verify error message is displayed
        if (isWebElementDisplayed(findWebElementByXPath(driver, "//div[@id='error']"))){
            try {
                wait(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Assert.assertTrue(isWebElementDisplayed(findWebElementByXPath(driver, "//div[@id='error']")));

        //Verify error message text is Your password is invalid!
        Assert.assertEquals(getTextFromElement(findWebElementByXPath(driver, "//div[@id='error']")),expectedErrorMessage);

    }

    /*
    *************************************************************************************
        helper methods
    *************************************************************************************
    */

    //open page
    public static void openPage(WebDriver driver, String url){
        driver.get(url);
    }

    //quit driver
    public static void quitDriver(WebDriver driver){
        driver.quit();
    }

    //find web element by XPath
    public static WebElement findWebElementByXPath(WebDriver driver, String xPath){
        return driver.findElement(By.xpath(xPath));
    }

    //send keys
    public static void sendKeys(WebElement webElement, String keyStrokes){
        webElement.sendKeys(keyStrokes);
    }

    // click web element
    public static void clickElement(WebElement webElement){
        webElement.click();
    }
    
    //get text from a web element
    public static String getTextFromElement(WebElement webElement){
        return webElement.getText();
    }

    // check if a web element is displayed
    public static boolean isWebElementDisplayed(WebElement webElement){
        return webElement.isDisplayed();
    }

    // wait
    public static void wait(int seconds) throws InterruptedException {
        Thread.sleep(seconds* 1000L);
    }
}
