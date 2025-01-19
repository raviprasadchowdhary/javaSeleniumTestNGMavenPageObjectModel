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
        ExceptionsPage exceptionsPage = new ExceptionsPage(driver);
        exceptionsPage.visit();
        exceptionsPage.clickAddButton();

        //assertions
        Assert.assertTrue(exceptionsPage.isRow2InputFieldDisplayed());
    }

    @Test
    public void timeoutExceptionTest() {

        //execution
        ExceptionsPage exceptionsPage = new ExceptionsPage(driver);
        exceptionsPage.visit();
        exceptionsPage.clickAddButton();

        //asserts
        Assert.assertTrue(exceptionsPage.isRow2InputFieldDisplayed());
    }

    @Test
    public void elementNotInteractableExceptionTest() {

        //execution
        ExceptionsPage exceptionsPage = new ExceptionsPage(driver);
        exceptionsPage.visit();
        exceptionsPage.addRow2SaveInput("Sushi");

        //assertions
        Assert.assertEquals(exceptionsPage.getSaveConfirmationMessage(), "Row 2 was saved");

    }
    @Test
    public void invalidElementStateExceptionTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement row1InputField = driver.findElement(By.xpath("//div[@id='row1']/input"));
        WebElement editButtonLocator = driver.findElement(By.xpath("//button[@id='edit_btn']"));
        WebElement saveButtonLocator = driver.findElement(By.xpath("//button[@id='save_btn']"));

        String row1InputFieldTextBefore = row1InputField.getAttribute("value");
        System.out.println("row1InputFieldTextBefore: " + row1InputFieldTextBefore);

        //clcik edit
        editButtonLocator.click();

        //Clear input field
        row1InputField.clear();

        //Type text into the input field
        row1InputField.sendKeys("Choco");

        //click save
        saveButtonLocator.click();

        String row1InputFieldTextAfter = row1InputField.getAttribute("value");
        System.out.println("row1InputFieldTextAfter: " + row1InputFieldTextAfter);

        Assert.assertNotEquals(row1InputFieldTextBefore, row1InputFieldTextAfter);
    }

    @Test
    public void staleElementReferenceExceptionTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Open page
        //Find the instructions text element
        //Push add button
        //Verify instruction text element is no longer displayed

        WebElement instructionTextLocator = driver.findElement(By.id("instructions"));
        WebElement addButtonLocator = driver.findElement(By.id("add_btn"));

        Assert.assertTrue(instructionTextLocator.isDisplayed());

        addButtonLocator.click();

        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOf(instructionTextLocator)));

    }
}
