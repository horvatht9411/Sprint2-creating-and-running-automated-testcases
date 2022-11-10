package com.codecool.jira.issuePages;

import com.codecool.jira.BasePage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.UUID;

public class IssueDisplayPage extends BasePage {

    @FindBy(xpath = "//*[@id='key-val']")
    public WebElement issueId;

    @FindBy(xpath = "//*[@id='summary-val']")
    public WebElement summaryDisplay;

    @FindBy(xpath = "//*[@id='main']")
    public WebElement alertBox;

    @FindBy(xpath = "//*[@id='main']/div/header/h1")
    public WebElement notExistingErrorMessage;

    @FindBy(xpath = "//*[@id='issue-content']/div/div/h1")
    public WebElement noPermissionErrorMessage;

    @FindBy(xpath = "//*[@id='edit-issue']/span[2]")
    public WebElement editIssueButton;

    @FindBy(xpath = "//*[@id='edit-issue-dialog']")
    public WebElement editIssueDialog;

    @FindBy(xpath = "//*[@id='summary']")
    public WebElement editIssueSummary;

    @FindBy(xpath = "//*[@id='edit-issue-submit']")
    public WebElement updateButton;

    @FindBy(xpath = "//*[@id='edit-issue-dialog']/header/h2")
    public WebElement editIssueDialogHeader;

    @FindBy(xpath = "//*[@id='summary']/following::*")
    public WebElement errorBox;

    @FindBy(xpath = "//*[@id='edit-issue-submit']/following::*")
    public WebElement cancelButton;

    @FindBy(xpath = "//p[@class='no-results-hint']/preceding-sibling::*")
    public WebElement noIssueErrorMessage;

    @FindBy(xpath = "//*[@id='opsbar-operations_more']")
    public WebElement moreMenu;

    @FindBy(xpath = "//*[@id='delete-issue']/a/span")
    public WebElement delete;

    @FindBy(xpath = "//*[@id='delete-issue-dialog']")
    public WebElement deleteIssueDialog;

    @FindBy(xpath = "//*[@id='delete-issue-submit']")
    public WebElement deleteIssueSubmit;

    @FindBy(xpath = "//*[@id='create-subtask']//span")
    public WebElement createSubTask;



    public String getIssueIdText() {
        wait.until(ExpectedConditions.visibilityOf(issueId));
        return issueId.getText();
    }

    public String getNotExistingErrorMessageText() {
        return notExistingErrorMessage.getText();
    }

    public String getErrorBoxTest() {
        wait.until(ExpectedConditions.visibilityOf(alertBox));
        return errorBox.getText();
    }

    public String getNoPermissionErrorMessage() {
        return noPermissionErrorMessage.getText();
    }

    public boolean editIssueButtonIsDisplayed() {
        return editIssueButton.isDisplayed();
    }

    public String editIssueDialogHeaderText() {
        wait.until(ExpectedConditions.visibilityOf(editIssueDialog));
        return editIssueDialogHeader.getText();
    }

    public void openEditIssueModal() {
        wait.until(ExpectedConditions.visibilityOf(issueId));
        editIssueButton.click();
    }

    public void cancelEditIssueModal() {
        cancelButton.click();
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }

    public String editIssueSuccessfully() {
        editIssueSummary.click();
        editIssueSummary.clear();
        String newSummary = UUID.randomUUID().toString();
        editIssueSummary.sendKeys(newSummary);
        updateButton.click();
        return newSummary;
    }

    public String cancelEditIssue() {
        editIssueSummary.click();
        editIssueSummary.clear();
        String newSummary = "Edited but canceling";
        editIssueSummary.sendKeys(newSummary);
        cancelButton.click();
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
        return newSummary;
    }

    public String getSummaryDisplayText() {
        wait.until(ExpectedConditions.visibilityOf(summaryDisplay));
        return summaryDisplay.getText();
    }

    public void leaveSummaryEmpty() {
        editIssueSummary.click();
        editIssueSummary.clear();
        updateButton.click();
    }

    public void deleteNewlyCreatedIssue() {
        moreMenu.click();
        delete.click();
        wait.until(ExpectedConditions.visibilityOf(deleteIssueDialog));
        deleteIssueSubmit.click();
    }

    public void openMoreMenu() {
        moreMenu.click();
    }

    public String getNoIssueErrorMessage() {
        return noIssueErrorMessage.getText();
    }

    public String getCreateSubTaskText() {
        return createSubTask.getText();
    }

    public void navigateTo(String issueName) {
        webDriver.get(String.format("https://jira-auto.codecool.metastage.net/browse/%s", issueName));
    }

    public void waitForChangingSummary(String newSummary) {
        wait.until(ExpectedConditions.textToBePresentInElement(summaryDisplay, newSummary));
    }
}
