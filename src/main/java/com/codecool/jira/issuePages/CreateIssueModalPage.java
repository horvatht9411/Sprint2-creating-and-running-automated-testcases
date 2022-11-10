package com.codecool.jira.issuePages;

import com.codecool.jira.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.UUID;

public class CreateIssueModalPage extends BasePage {

    String BROWSE_ISSUE_URL = "https://jira-auto.codecool.metastage.net/browse/%s";

    String CREATE_ISSUE_URL = "https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa";

    @FindBy(xpath = "//*[@id='create-issue-dialog']")
    public WebElement issueModal;

    @FindBy(xpath = "//*[@id='project-field']")
    public WebElement projectSelector;

    @FindBy(xpath = "//*[@id='summary']")
    public WebElement summary;

    @FindBy(xpath = "//*[@id='create-issue-submit']")
    public WebElement submitButton;

    @FindBy(xpath = "//*[@id='aui-flag-container']//a")
    public WebElement newIssueLink;

    @FindBy(xpath = "//*[@id='summary']/following::div[@class='error']")
    public WebElement warningMessageToFillSummary;

    @FindBy(xpath = "//*[@id='create-issue-submit']/following::*")
    public WebElement cancelButton;


    public void fillUpProjectName(String projectName) {
        wait.until(ExpectedConditions.visibilityOf(issueModal));
        projectSelector.click();
        projectSelector.sendKeys(Keys.BACK_SPACE);
        projectSelector.sendKeys(projectName);
        projectSelector.sendKeys(Keys.ENTER);
    }

    public String fillUpSummary() {
        //TODO: repair me!!!
        String value = UUID.randomUUID().toString();
        wait.until(ExpectedConditions.elementToBeClickable(summary));
        summary.click();
        summary.sendKeys(value);
        wait.until(ExpectedConditions.textToBePresentInElement(summary, value));
        return value;
    }

    public String getSummaryText() {
        return summary.getText();
    }

    public void waitForModal() {
        wait.until(ExpectedConditions.elementToBeClickable(issueModal));
    }

    public void submitNewIssue() {
        submitButton.click();
    }

    public void clickOnNewIssueLink() {
        wait.until(ExpectedConditions.visibilityOf(newIssueLink));
        newIssueLink.click();
    }

    public String getWarningMessageToFillSummary() {
        return warningMessageToFillSummary.getText();
    }

    public void closeCreateModal() {
        cancelButton.click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }


    public void navigateToCreateIssuePage() {
        webDriver.get(CREATE_ISSUE_URL);
    }

    public void navigateTo(String issueUrl) {
        webDriver.get(issueUrl);
    }
}
