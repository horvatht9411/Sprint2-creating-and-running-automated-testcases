package com.codecool.jira.loginPages;

import com.codecool.jira.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UserPage extends BasePage {

    @FindBy(xpath = "//*[@id='up-d-username']")
    public WebElement username;

    public String getUserName() {
        wait.until(ExpectedConditions.visibilityOf(username));
        return username.getText();
    }
}
