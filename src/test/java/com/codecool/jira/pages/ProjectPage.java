package com.codecool.jira.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProjectPage {
    WebDriver webDriver;

    @FindBy(xpath = "//*[text()='Key']/following::*")
    public WebElement projectKey;

    @FindBy(xpath = "//*[@id='main']/child::h1")
    public WebElement errorMessage;

    public ProjectPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public String getProjectKey(){
        return projectKey.getText();
    }

    public String getErrorMessage(){
        return errorMessage.getText();
    }
}
