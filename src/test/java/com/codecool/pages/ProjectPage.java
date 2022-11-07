package com.codecool.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProjectPage {
    WebDriver webDriver;

    @FindBy(id = "summary-subnav-title")
    public WebElement projectSummary;

    @FindBy(css = "#summary-body > div > div.aui-item.project-meta-column > dl > dd:nth-child(6)")
    public WebElement projectKey;

    @FindBy(css = "#main > h1")
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
