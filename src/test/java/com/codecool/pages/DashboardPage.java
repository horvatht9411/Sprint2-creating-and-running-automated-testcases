package com.codecool.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = https://jira-auto.codecool.metastage.net/secure/ViewProfile.jspa
public class DashboardPage {

    WebDriver webDriver;

    @FindBy(xpath = "//*[@id='header-details-user-fullname']")
    public WebElement profileMenu;

    @FindBy(xpath = "//*[@id='log_out']")
    public WebElement logOutButton;

    @FindBy(xpath = "//*[@id='view_profile']")
    public WebElement viewProfileName;

    @FindBy(xpath = "//*[@id='create_link']")
    public WebElement createIssueButton;


    public DashboardPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }


    public boolean checkLogoutButtonVisibility() {
        profileMenu.click();
        return logOutButton.isDisplayed();
    }

    public void logout(){
        profileMenu.click();
        logOutButton.click();
    }

    public void createNewIssue() {
        createIssueButton.click();
    }
}