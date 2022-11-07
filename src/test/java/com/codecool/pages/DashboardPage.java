package com.codecool.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = https://jira-auto.codecool.metastage.net/secure/ViewProfile.jspa
public class DashboardPage {

    WebDriver webDriver;

    @FindBy(id = "header-details-user-fullname")
    public WebElement profileMenu;

    @FindBy(id = "log_out")
    public WebElement logOutButton;

    @FindBy(id = "view_profile")
    public WebElement viewProfileName;




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
}