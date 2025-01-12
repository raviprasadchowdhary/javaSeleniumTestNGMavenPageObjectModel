package com.practicetestautomation.tests.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.Bidi;

public class PositiveLoginTests {
    @Test
    public void testLoginFunctionality(){
        //Open page
        WebDriver driver = new EdgeDriver();
        driver.get("https://practicetestautomation.com/practice-test-login/");

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
        Assert.assertTrue(pageSource.contains(expectedloginSuccessMessage));

        //Verify button Log out is displayed on the new page
        WebElement logoutButton = driver.findElement(By.linkText("Log out"));
        Assert.assertTrue(logoutButton.isDisplayed());

        driver.quit();
    }
}
