package com.codecool.pages;

import com.codecool.Util;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class LoginPage {

    WebDriver webDriver;
    Properties appProps = Util.read();

    @FindBy(id = "login-form-username")
    WebElement userName;

    @FindBy(id = "login-form-password")
    WebElement password;

    @FindBy(id = "login")
    WebElement loginButton;

    @FindBy(id = "login-form-submit")
    WebElement loginButtonTwo;

    @FindBy(id = "usernameerror")
    public WebElement errorMessage;

    @FindBy(css = "#user-options > a")
    public WebElement signIn;

    @FindBy(css = "#login-form > div.form-body > div.aui-message.aui-message-warning > p:nth-child(1)")
    public WebElement loginWarning;


    public LoginPage(WebDriver webDriver) throws IOException {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void login(String userName, String password) {
        this.userName.sendKeys(userName);
        this.password.sendKeys(password);
        this.loginButton.click();
    }

    public void loginUsingEnterKey(String userName, String password) {
        this.userName.sendKeys(userName);
        this.password.sendKeys(password);
        this.password.sendKeys(Keys.ENTER);
    }

    public void loginSuccessfully() {
        this.userName.sendKeys(appProps.getProperty("username"));
        this.password.sendKeys(appProps.getProperty("password"));
        this.loginButton.click();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public String getSignInText() {
        return signIn.getText();
    }

    public String getLoginWarningMessage() {
        return loginWarning.getText();
    }
}


