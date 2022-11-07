package com.codecool.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = about:blank
public class Login2Page {
    WebDriver webDriver;


    @FindBy(id = "login-form-username")
    WebElement userName;

    @FindBy(id = "login-form-password")
    WebElement password;

    @FindBy(id = "login-form-submit")
    WebElement loginButton;


    public Login2Page(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }


    public void login(String userName, String password) {
        this.userName.sendKeys(userName);
        this.password.sendKeys(password);
        this.loginButton.click();
    }
}