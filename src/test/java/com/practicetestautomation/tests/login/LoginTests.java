package com.practicetestautomation.tests.login;

import com.practicetestautomation.pageObjects.BasePage;
import com.practicetestautomation.pageObjects.LoginPage;
import com.practicetestautomation.pageObjects.SuccessfulLoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginTests {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(LoginTests.class);
    private WebDriver driver;
    private Logger logger;

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("Chrome") String browser){
        logger = Logger.getLogger(LoginTests.class.getName());
        logger.setLevel(Level.INFO);
        logger.info("Running Tests in " + browser);

        //Open page
        switch (browser.toLowerCase()){
            case "chrome":
                driver = new ChromeDriver();
                System.out.println("Test is executing in chrome browser");
                break;
            case "edge":
                driver = new EdgeDriver();
                System.out.println("Test is executing in edge browser");
                break;
            case "firefox":
                driver = new FirefoxDriver();
                System.out.println("Test is executing in firefox browser");
                break;
            default:
                driver = new ChromeDriver();
                System.out.println("Test is executing in chrome browser as requested browser: " + browser + " configurations are missing");
                logger.warning("Test is executing in chrome browser as requested browser: " + browser + " configurations are missing");
                break;
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
        logger.info("Driver has been quit!");
    }

    @Test(groups = {"positive", "regression", "smoke"})
    public void testLoginFunctionality(){
        logger.info("starting test execution of testLoginFunctionality");

        //execution
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        SuccessfulLoginPage successfulLoginPage = loginPage.executeLogin("student","Password123");
        successfulLoginPage.load();

        //assertions
        Assert.assertEquals(successfulLoginPage.getCurrentUrl(),"https://practicetestautomation.com/logged-in-successfully/");
        logger.info("Current page URL validation is done ");
        Assert.assertTrue(successfulLoginPage.getPageSource().contains("Congratulations student. You successfully logged in!"));
        logger.info("Verified whether the page contains expected text");
        Assert.assertTrue(successfulLoginPage.isLogOutButtonDisplayed());
        logger.info("Verified if the logout button is displayed");

    }

    @Parameters({"username", "password", "expectedErrorMessage"})
    @Test(groups = {"negative", "regression"})
    public void negativeLoginTest(String username, String password, String expectedErrorMessage){
        logger.info("starting test execution of negativeLoginTest");
        //Type username student into Username field
        sendKeys(findWebElementByXPath(driver, "//input[@name='username']"), username);
        logger.info("Username is entered: " + username);

        //Type password incorrectPassword into Password field
        sendKeys(findWebElementByXPath(driver, "//input[@name='password']"), password);
        logger.info("Password is entered in the password field");

        //Push Submit button
        clickElement(findWebElementByXPath(driver, "//button[@id='submit']"));
        logger.info("Submit button is clicked");

        //Verify error message is displayed
        if (isWebElementDisplayed(findWebElementByXPath(driver, "//div[@id='error']"))){
            try {
                wait(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Assert.assertTrue(isWebElementDisplayed(findWebElementByXPath(driver, "//div[@id='error']")));
        logger.info("Verified if the error is displayed");

        //Verify error message text is Your password is invalid!
        Assert.assertEquals(getTextFromElement(findWebElementByXPath(driver, "//div[@id='error']")),expectedErrorMessage);
        logger.info("verified the error message content & it is: " + getTextFromElement(findWebElementByXPath(driver, "//div[@id='error']")));

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
