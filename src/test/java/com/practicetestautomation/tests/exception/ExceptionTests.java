package com.practicetestautomation.tests.exception;

import com.practicetestautomation.pageObjects.ExceptionsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionTests {
    private WebDriver driver;
    private Logger logger;
    private ExceptionsPage exceptionsPage;

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser){
        logger = Logger.getLogger(ExceptionTests.class.getName());
        logger.setLevel(Level.INFO);
        logger.info("Running Test case in: " + browser);

        switch (browser.toLowerCase()){
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            case  "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                logger.warning("Running test in chrome, as configurations are not available for " + browser);
                driver = new ChromeDriver();
                break;
        }

        //implicit wait
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
        logger.info("Driver is quit");
    }

    /*
    *************************************************************************************
        Tests
    *************************************************************************************
    */

    @Test
    public void noSuchElementExceptionTest() {

        //execution
        exceptionsPage = new ExceptionsPage(driver);
        exceptionsPage.visit();
        exceptionsPage.clickAddButton();

        //assertions
        Assert.assertTrue(exceptionsPage.isRow2InputFieldDisplayed());
    }

    @Test
    public void timeoutExceptionTest() {

        //execution
        exceptionsPage = new ExceptionsPage(driver);
        exceptionsPage.visit();
        exceptionsPage.clickAddButton();

        //asserts
        Assert.assertTrue(exceptionsPage.isRow2InputFieldDisplayed());
    }

    @Test
    public void elementNotInteractableExceptionTest() {

        //execution
        exceptionsPage = new ExceptionsPage(driver);
        exceptionsPage.visit();
        exceptionsPage.addRow2SaveInput("Sushi");

        //assertions
        Assert.assertEquals(exceptionsPage.getSaveConfirmationMessage(), "Row 2 was saved");

    }
    @Test
    public void invalidElementStateExceptionTest() {

        //execution
        exceptionsPage = new ExceptionsPage(driver);
        exceptionsPage.visit();
        String row1InputFieldTextBefore = exceptionsPage.getRow1InputFieldText();
        exceptionsPage.editRow1InputFieldAndSave("Choco");
        String row1InputFieldTextAfter = exceptionsPage.getRow1InputFieldText();

        //assertions
        Assert.assertNotEquals(row1InputFieldTextBefore, row1InputFieldTextAfter);

    }

    @Test
    public void staleElementReferenceExceptionTest() {

        //execution
        exceptionsPage = new ExceptionsPage(driver);
        exceptionsPage.visit();
        //asserts
        Assert.assertTrue(exceptionsPage.isInstructionsElementDisplayed());

        //execution
        exceptionsPage.clickAddButton();
        //asserts
        Assert.assertTrue(exceptionsPage.isInstructionsElementNotDisplayed());

    }
}
