package com.codecool.jira.issuePages;

import com.codecool.jira.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.UUID;

public class CreateIssueLinkPage extends BasePage {

    @FindBy(xpath = "//*[@id='project-field']")
    public WebElement project;

    @FindBy(xpath = "//*[@id='issuetype-field']")
    public WebElement issueType;

    @FindBy(xpath = "//*[@id='issue-create-submit']")
    public WebElement nextButton;

    @FindBy(xpath = "//*[@id='summary']")
    public WebElement summary;

    @FindBy(xpath = "//*[@id='issue-create-submit']")
    public WebElement createNewIssueButton;

    @FindBy(xpath = "//*[@id='issue-create-issue-type']")
    public WebElement selectedIssueType;


    public void fillUpProjectName(String projectName) {
        wait.until(ExpectedConditions.visibilityOf(project));
        project.sendKeys(projectName);
        project.sendKeys(Keys.ENTER);
    }

    public void fillUpIssueType(String issueType) {
        wait.until(ExpectedConditions.elementToBeClickable(this.issueType));
        this.issueType.click();
        this.issueType.sendKeys(Keys.BACK_SPACE);
        this.issueType.sendKeys(issueType);
        this.issueType.sendKeys(Keys.ENTER);
    }

    public void clickNextButton() {
        nextButton.click();
    }

    public String fillUpSummaryField() {
        wait.until(ExpectedConditions.visibilityOf(summary));
        String value = UUID.randomUUID().toString();
        summary.sendKeys(value);
        return value;
    }

    public void submitNewIssue() {
        createNewIssueButton.click();
    }

    public String getSelectedIssueTypeText() {
        return selectedIssueType.getText();
    }

    public void navigateToCreateIssueUrl() {
        webDriver.get(CREATE_ISSUE_URL);
    }
}
