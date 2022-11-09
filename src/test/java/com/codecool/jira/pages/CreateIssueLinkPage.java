package com.codecool.jira.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.UUID;

public class CreateIssueLinkPage {
    WebDriver webDriver;

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

    public CreateIssueLinkPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void fillUpProjectName(WebDriverWait wait, String projectName) {
        wait.until(ExpectedConditions.visibilityOf(project));
        project.sendKeys(projectName);
        project.sendKeys(Keys.ENTER);
    }

    public void fillUpIssueType(String issueType) {
        this.issueType.click();
        this.issueType.sendKeys(Keys.BACK_SPACE);
        this.issueType.sendKeys(issueType);
        this.issueType.sendKeys(Keys.ENTER);
    }

    public void clickNextButton() {
        nextButton.click();
    }

    public String fillUpSummaryField(WebDriverWait wait) {
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
}
