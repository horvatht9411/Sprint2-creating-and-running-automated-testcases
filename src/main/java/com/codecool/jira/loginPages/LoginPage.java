package com.codecool.jira.loginPages;

import com.codecool.jira.BasePage;
import com.codecool.jira.Util;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    @FindBy(xpath = "//*[@id='login-form-username']")
    WebElement userName;

    @FindBy(xpath = "//*[@id='login-form-password']")
    WebElement password;

    @FindBy(xpath = "//*[@id='login']")
    WebElement loginButton;

    @FindBy(xpath = "//*[@id='usernameerror']")
    WebElement errorMessageOfIncorrectCredentials;

    @FindBy(xpath = "//*[@id='user-options']/child::a")
    WebElement signIn;

    @FindBy(xpath = "//*[@id='login-form']/descendant::p")
    WebElement loginWarning;

    public void login(String name, String password) {
        webDriver.get(LOGIN_URL);
        wait.until(ExpectedConditions.visibilityOf(userName));
        this.userName.sendKeys(name);
        this.password.sendKeys(password);
        this.loginButton.click();
    }

    public void loginUsingEnterKey(String name, String password) {
        this.userName.sendKeys(name);
        this.password.sendKeys(password);
        this.password.sendKeys(Keys.ENTER);
    }

    public void loginSuccessfully() {
        this.userName.sendKeys(Util.readProperty("username"));
        this.password.sendKeys(Util.readProperty("password"));
        this.loginButton.click();
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(errorMessageOfIncorrectCredentials));
        return errorMessageOfIncorrectCredentials.getText();
    }

    public String getSignInText() {
        return signIn.getText();
    }

    public String getLoginWarningMessage() {
        return loginWarning.getText();
    }
}
