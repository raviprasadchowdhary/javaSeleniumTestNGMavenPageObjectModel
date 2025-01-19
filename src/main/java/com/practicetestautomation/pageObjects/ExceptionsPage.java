package com.practicetestautomation.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ExceptionsPage extends BasePage{

    //constructor
    public ExceptionsPage(WebDriver driver) {
        super(driver);
    }

    /*
    *************************************************************************************
        Locators
    *************************************************************************************
    */
    public By addButtonLocator = By.xpath("//button[@class='btn' and @name='Add']");
    public By row1InputFieldLocator = By.xpath("//div[@id='row1']/input");
    public By row2InputFieldLocator = By.xpath("//div[@id='row2']/input[@class='input-field']");
    public By saveButtonLocator = By.id("save_btn");
    public By saveButtonRow2Locator = By.xpath("//div[@id='row2']/button[@id='save_btn']");
    public By confirmationLocator = By.id("confirmation");
    public By editButtonLocator = By.id("edit_btn");
    public By instructionsLocator = By.id("instructions");

    /*
    *************************************************************************************
        Methods
    *************************************************************************************
    */

    public void visit(){
        visit("https://practicetestautomation.com/practice-test-exceptions/");
    }

    public void clickAddButton(){
        clickElement(addButtonLocator);
    }

    public boolean isRow2InputFieldDisplayed(){
        return isElementDisplayed(row2InputFieldLocator);
    }

    public void clickSaveButtonRow2(){
        clickElement(saveButtonRow2Locator);
    }

    public void addRow2SaveInput(String input){
        clickAddButton();
        sendKeysIntoInputField(row2InputFieldLocator, input);
        clickSaveButtonRow2();
    }

    public String getSaveConfirmationMessage(){
        return getTextFromElementLocator(confirmationLocator);
    }

    public void editRow1InputFieldAndSave(String input){
        clickElement(editButtonLocator);
        clearKeysFromInputField(row1InputFieldLocator);
        sendKeysIntoInputField(row1InputFieldLocator, input);
        clickElement(saveButtonLocator);
    }

    public String getRow1InputFieldText(){
        return getAttributeValue(row1InputFieldLocator,"value");
    }

    public String getRow2InputFieldText(){
        return getAttributeValue(row2InputFieldLocator, "value");
    }

}
