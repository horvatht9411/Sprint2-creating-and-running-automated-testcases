package com.codecool.jira.loginPages;

import com.codecool.jira.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

// page_url = https://jira-auto.codecool.metastage.net/secure/ViewProfile.jspa
public class UserPage extends BasePage {

    @FindBy(xpath = "//*[@id='up-d-username']")
    public WebElement username;

    public String getUserName(){
        wait.until(ExpectedConditions.visibilityOf(username));
        return username.getText();
    }
}
