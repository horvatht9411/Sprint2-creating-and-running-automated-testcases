package com.codecool.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IssuePage {

    WebDriver webDriver;

    @FindBy(xpath = "//*[@id='key-val']")
    public WebElement issueId;

    @FindBy(xpath = "//*[@id='main']")
    public WebElement alertBox;

    @FindBy(xpath = "//*[@id='main']/div/header/h1")
    public WebElement notExistingErrorMessage;

    @FindBy(xpath = "//*[@id='issue-content']/div/div/h1")
    public WebElement noPermissionErrorMessage;


    public IssuePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public String getIssueIdText(){ return issueId.getText(); }

    public String getNotExistingErrorMessageText(){ return notExistingErrorMessage.getText(); }
    public String getNoPermissionErrorMessage(){ return noPermissionErrorMessage.getText(); }
}
