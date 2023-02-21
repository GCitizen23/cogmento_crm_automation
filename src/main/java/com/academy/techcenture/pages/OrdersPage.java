package com.academy.techcenture.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class OrdersPage {

    private WebDriver driver;

    public OrdersPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    //web elements
    @FindBy(className = "login_info")
    private WebElement loginInfo;

    @FindBy(linkText = "Logout")
    private WebElement logoutLink;

    @FindBy(tagName = "h1")
    private WebElement logo;

    @FindBy(xpath = "//h2[contains(text(),'List of All Orders')]")
    private WebElement listOfAllOrdersHeader;

    @FindBy(id = "ctl00_MainContent_btnCheckAll")
    private WebElement checkAllBtn;

    @FindBy(id = "ctl00_MainContent_btnUncheckAll")
    private WebElement uncheckAllBtn;

    @FindBy(id = "ctl00_MainContent_orderGrid")
    private WebElement ordersTable;

    @FindBy(id = "ctl00_MainContent_btnDelete")
    private WebElement deleteSelectedBtn;

    @FindBy(xpath = "//input[starts-with(@id,'ctl00_MainContent_orderGrid_ctl0')]")
    private List<WebElement> orders;

    @FindBy(linkText = "View all orders")
    private WebElement viewAllOrdersLink;


    public void deleteOrder(){
        int orderSize = orders.size();
        int random = (int)(Math.random() * 8) + 2; //2 and 9
        String xpath = "//input[@id='ctl00_MainContent_orderGrid_ctl0"+random+"_OrderSelector']";
        WebElement randomOrderInput = driver.findElement(By.xpath(xpath));
        randomOrderInput.click();
        assertTrue(deleteSelectedBtn.isEnabled(), "Delete Button is not enabled");
        deleteSelectedBtn.click();

        assertTrue(orders.size() == orderSize-1, "Order was not deleted");

    }


    public void clickOnViewAllOrdersLink(){
        assertTrue(viewAllOrdersLink.isDisplayed(), "View All Orders Links is not displayed");
        viewAllOrdersLink.click();
    }

    public void logout(){
        assertTrue(logoutLink.isDisplayed(), "Logout Link is not displayed");
        logoutLink.click();
    }

    public void checkAllOrders(){
        //this method should check all orders on the order table
        assertTrue(checkAllBtn.isEnabled(), "Check all btn is not enabled");
        checkAllBtn.click();
        for (int i = 0; i < orders.size(); i++) {
            assertTrue(orders.get(i).isSelected(), "Order " + (i+1) + " failed" );
        }
    }

    public void uncheckAllOrders(){
        //this method should uncheck all orders on the order table
        assertTrue(uncheckAllBtn.isEnabled(), "Uncheck all btn is not enabled");
        uncheckAllBtn.click();
        for (int i = 0; i < orders.size(); i++) {
            assertFalse(orders.get(i).isSelected(), "Order " + (i+1) + " failed" );
        }
    }



}
