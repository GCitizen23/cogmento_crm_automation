package com.academy.techncenture.tests;

import com.academy.techcenture.driver.Driver;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.Locale;
import static com.academy.techcenture.config.ConfigReader.getProperty;
import static com.academy.techcenture.utils.Utils.randomStringNumber;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class WebOrdersOldTest {

    private WebDriver driver;
    private Faker faker;
    private WebDriverWait wait;
    @BeforeMethod
    public void setUp(){
        driver = Driver.getDriver();
        faker = new Faker(new Locale("en-us"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown(){
        Driver.quitDriver();
    }

    @Test(enabled = false)
    public void placeOrderPositiveTest() throws InterruptedException {

        driver.get(getProperty("url"));


        assertEquals(driver.getTitle(), "Web Orders Login", "Login Page titles do not match");

        //the below 2 elements belong to login page
        driver.findElement(By.id("ctl00_MainContent_username")).sendKeys(getProperty("username"));
        driver.findElement(By.id("ctl00_MainContent_password")).sendKeys(getProperty("password"));

        WebElement loginBtn = driver.findElement(By.id("ctl00_MainContent_login_button"));
        assertTrue(loginBtn.isEnabled(),"Login Button is not enabled");
        loginBtn.click();

        assertEquals(driver.getTitle(), "Web Orders", "Account Page titles do not match");

        WebElement loginInfo = driver.findElement(By.className("login_info"));
        assertTrue(loginInfo.getText().contains("Welcome, Tester!"), "Welcome Message is not correct");

        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        assertTrue(logoutLink.isDisplayed(), "Logout Link is not displayed");

        WebElement logo = driver.findElement(By.tagName("h1"));
        assertTrue(logo.isDisplayed(), "Logo is not displayed");

        WebElement orderLink = driver.findElement(By.linkText("Order"));
        orderLink.click();

        WebElement productInfoHeader = driver.findElement(By.xpath("//h3[text()='Product Information']"));
        assertTrue(productInfoHeader.isDisplayed(), "Product Information Header is not displayed");

        WebElement productSelect = driver.findElement(By.id("ctl00_MainContent_fmwOrder_ddlProduct"));
        Select select = new Select(productSelect);
        select.selectByVisibleText("FamilyAlbum");

        int randomQuantity = (int)(Math.random() * 11 + 1);
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity")).sendKeys(String.valueOf(randomQuantity));

        WebElement calculateBtn = driver.findElement(By.xpath("//input[@value='Calculate']"));
        assertTrue(calculateBtn.isEnabled(), "Calculate Button is not enabled");

        calculateBtn.click();

        WebElement addressInfoHeader = driver.findElement(By.xpath("//h3[text()='Address Information']"));
        assertTrue(addressInfoHeader.isDisplayed(), "Address Information Header is not displayed");

        String randomName = faker.name().fullName();
        String randomStreet = faker.address().streetAddress();
        String randomCity = faker.address().city();
        String randomState = faker.address().stateAbbr();
        String randomZip = faker.address().zipCodeByState(randomState);

        driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtName")).sendKeys(randomName);
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox2")).sendKeys(randomStreet);
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox3")).sendKeys(randomCity);
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox4")).sendKeys(randomState);
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox5")).sendKeys(randomZip);

        WebElement paymentInfoHeader = driver.findElement(By.xpath("//h3[text()='Payment Information']"));
        assertTrue(paymentInfoHeader.isDisplayed(), "Payment Information Header is not displayed");


        int randomCardIndex = (int)Math.random() * 3; //2
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_"+randomCardIndex)).click();

        String randomCardNumber = (randomCardIndex == 2) ? randomStringNumber(15) : randomStringNumber(16);

        //handled by the above ternary 
//        String randomCardNumber = "";
//        if (randomCardIndex == 2){
//            randomCardNumber = Utils.randomStringNumber(15);
//        }else{
//            randomCardNumber = Utils.randomStringNumber(16);
//        }


        int randomMonth = (int)(Math.random() * 13); //11
        String randomDate = (randomMonth/10 == 0 ? "0"+randomMonth : randomMonth)  +"/" +  (int) ((Math.random() * 6) + 23);

        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6")).sendKeys(randomCardNumber);
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox1")).sendKeys(randomDate);


        WebElement processBtn = driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton"));
        WebElement resetBtn = driver.findElement(By.xpath("//input[@value='Reset']"));

        assertTrue(processBtn.isEnabled(), "Process Button is not enabled");
        assertTrue(resetBtn.isEnabled(), "Reset Button is not enabled");

        processBtn.click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.tagName("strong"))));

        WebElement orderConfirmMessage = driver.findElement(By.tagName("strong"));

        assertTrue(orderConfirmMessage.isDisplayed(), "Order Confirmation Message is not displayed");
        assertEquals(orderConfirmMessage.getText().trim(), "New order has been successfully added.",
                "Order Confirmation message is not correct");

        logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();

    }

}
