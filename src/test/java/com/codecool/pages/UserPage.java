package com.codecool.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = https://jira-auto.codecool.metastage.net/secure/ViewProfile.jspa
public class UserPage {

    WebDriver webDriver;

    @FindBy(xpath = "//*[@id='up-d-username']")
    public WebElement upDUsernameDd;


    public UserPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public String getUserName(){
        return upDUsernameDd.getText();
    }
}