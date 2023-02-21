package com.academy.techcenture.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.academy.techcenture.config.ConfigReader.getProperty;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginPage {
    private WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //web elements
    @FindBy(id = "ctl00_MainContent_username")
    private WebElement userNameInput;

    @FindBy(id = "ctl00_MainContent_password")
    private WebElement passwordInput;

    @FindBy(id = "ctl00_MainContent_login_button")
    private WebElement loginBtn;

    @FindBy(id = "ctl00_MainContent_status")
    private WebElement errorMessage;


    /**
     * This method will navigate the driver to Login page
     */
    public void navigateToLoginPage(){
        driver.get(getProperty("url"));
        assertEquals(driver.getTitle(), "Web Orders Login", "Login Page Titles do not match");
    }

    /**
     * This method is used to log in to that application
     * It gets the username and password from the config file
     */
    public void login(){
        enterUserName(getProperty("username"));
        enterPassword(getProperty("password"));
        clickOnLoginButton();
    }

    public void loginNegative(){
        enterUserName("Tester");
        enterPassword("abc123");
        clickOnLoginButton();
        verifyLoginErrorMessage();
    }

    private void verifyLoginErrorMessage(){
        assertTrue(errorMessage.isDisplayed(), "Error Message is not displayed");
    }
    private void enterUserName(String username){
        userNameInput.clear();
        userNameInput.sendKeys(username);
    }
    private void enterPassword(String password){
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }
    private void clickOnLoginButton(){
        assertTrue(loginBtn.isEnabled(), "Login Button is not enabled");
        loginBtn.click();
    }
}
