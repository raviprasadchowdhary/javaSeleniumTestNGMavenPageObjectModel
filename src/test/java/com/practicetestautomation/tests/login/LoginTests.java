package com.practicetestautomation.tests.login;

import com.practicetestautomation.pageObjects.LoginPage;
import com.practicetestautomation.pageObjects.SuccessfulLoginPage;
import org.openqa.selenium.WebDriver;
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

    /*
    *************************************************************************************
        Tests
    *************************************************************************************
    */

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
    public void negativeLoginTest(@Optional("student") String username, @Optional("Paassword123") String password, @Optional("Your password is invalid!") String expectedErrorMessage){
        logger.info("starting test execution of negativeLoginTest");

        //execution
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        loginPage.executeLogin(username, password);

        //assertions
        Assert.assertTrue(loginPage.isErrorDisplayed());
        logger.info("Verified if the error is displayed");
        Assert.assertEquals(loginPage.getErrorMessageText(), expectedErrorMessage);
        logger.info("verified the error message content");
    }
}
