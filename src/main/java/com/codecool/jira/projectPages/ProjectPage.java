package com.codecool.jira.projectPages;

import com.codecool.jira.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProjectPage extends BasePage {

    @FindBy(xpath = "//*[text()='Key']/following::*")
    public WebElement projectKey;

    @FindBy(xpath = "//*[@id='main']/child::h1")
    public WebElement errorMessage;

    public void navigateToProjectPage(String projectKey) {
        this.webDriver.get(String.format(PROJECT_SUMMARY_URL, projectKey));
    }

    public String getProjectKey() {
        wait.until(ExpectedConditions.visibilityOf(projectKey));
        return projectKey.getText();
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.getText();
    }
}
