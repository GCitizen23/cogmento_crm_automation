package com.academy.techncenture.tests;

import com.academy.techcenture.driver.Driver;
import com.academy.techcenture.pages.LoginPage;
import com.academy.techcenture.pages.OrdersPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WebOrderTest {
    private WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = Driver.getDriver();
    }

    @AfterMethod
    public void tearDown(){
        Driver.quitDriver();
    }

    @Test
    public void viewOrdersTest(){
        LoginPage loginPage = new LoginPage(driver);
        OrdersPage ordersPage = new OrdersPage(driver);

        loginPage.navigateToLoginPage();
        loginPage.login();
        ordersPage.clickOnViewAllOrdersLink();
        ordersPage.checkAllOrders();
        ordersPage.uncheckAllOrders();
        ordersPage.deleteOrder();
        ordersPage.logout();
    }
}
