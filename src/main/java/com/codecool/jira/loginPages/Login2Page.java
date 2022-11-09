package com.codecool.jira.loginPages;

import com.codecool.jira.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Login2Page extends BasePage {

    @FindBy(xpath = "//*[@id='login-form-username']")
    WebElement userName;

    @FindBy(xpath = "//*[@id='login-form-password']")
    WebElement password;

    @FindBy(xpath = "//*[@id='login-form-submit']")
    WebElement loginButton;

    public void login(String name, String password) {
        webDriver.get(SECONDARY_LOGIN_URL);
        wait.until(ExpectedConditions.visibilityOf(userName));
        this.userName.sendKeys(name);
        this.password.sendKeys(password);
        this.loginButton.click();
    }
}
