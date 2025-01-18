package com.practicetestautomation.tests.exception;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v130.css.model.Value;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionTests {
    private WebDriver driver;
    private Logger logger;

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(String browser){
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

        //Open page
        driver.get("https://practicetestautomation.com/practice-test-exceptions/");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
        logger.info("Driver is quit");
    }

    @Test
    public void noSuchElementExceptionTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));

        //Click Add button
        driver.findElement(By.xpath("//button[@class='btn' and @name='Add']")).click();
        WebElement row2Input =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input[@class='input-field']")));

        //Verify Row 2 input field is displayed
        Assert.assertTrue(row2Input.isDisplayed());
    }

    @Test
    public void timeoutExceptionTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        //Click Add button
        driver.findElement(By.xpath("//button[@class='btn' and @name='Add']")).click();

        //Wait for 3 seconds for the second input field to be displayed
        WebElement row2InputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input[@class='input-field']")));

        //Verify second input field is displayed
        Assert.assertTrue(row2InputField.isDisplayed());
    }

    @Test
    public void elementNotInteractableExceptionTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Click Add button
        driver.findElement(By.xpath("//button[@class='btn' and @name='Add']")).click();

        //Wait for the second row to load

        WebElement row2InputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input[@class='input-field']")));

        //Type text into the second input field
        row2InputField.sendKeys("pizza");

        //Push Save button using locator By.name(“Save”)
        driver.findElement(By.xpath("//div[@id='row2']/button[@id='save_btn']")).click();

        //Verify text saved
        WebElement saveConfirmationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));

        String expectedMessage = "Row 2 was saved";
        Assert.assertEquals(saveConfirmationElement.getText(),"Row 2 was saved");

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

        System.out.println("instructionTextLocator.isDisplayed(): " + instructionTextLocator.isDisplayed());
        Assert.assertTrue(instructionTextLocator.isDisplayed());

        addButtonLocator.click();

        System.out.println("instructionTextLocator.isDisplayed(): " + instructionTextLocator.isDisplayed());
//        Assert.assertTrue(instructionTextLocator.isDisplayed());


    }


    /*
    *************************************************************************************
        helper methods
    *************************************************************************************
    */




}
