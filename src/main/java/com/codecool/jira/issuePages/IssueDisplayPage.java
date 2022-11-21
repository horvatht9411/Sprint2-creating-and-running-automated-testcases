package com.codecool.jira.issuePages;

import com.codecool.jira.BasePage;
import org.asynchttpclient.util.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
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

    @FindBy(xpath = "//p[contains('@class', 'no-results-hint')]/preceding-sibling::*") // TODO: check if its valid
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
        String idText = "";
        try {
            wait.until(ExpectedConditions.visibilityOf(issueId));
            idText = issueId.getText();
        } catch (NoSuchElementException | TimeoutException e){
//            Assertions.fail("Exception " + e); // TODO: fix me
        }

        return idText;
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
        boolean isOnTheSite = false;
        try {
            isOnTheSite = editIssueButton.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
//            Assertions.fail("Exception " + e); // Todo: fix me
        }
        return isOnTheSite;
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

    public void editIssueSuccessfully(String newSummary) {
        editIssueSummary.click();
        editIssueSummary.clear();
        editIssueSummary.sendKeys(newSummary);
        updateButton.click();
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
        try {
            wait.until(ExpectedConditions.visibilityOf(summaryDisplay));
            return summaryDisplay.getText();
        } catch (NoSuchElementException e){
            return null;
        }

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
        try {
            moreMenu.click();
        } catch (NoSuchElementException | TimeoutException e ){
//            Assertions.fail("Exception " + e); // TODO: fix me
        }
    }

    public String getNoIssueErrorMessage() {
        return noIssueErrorMessage.getText();
    }

    public String getCreateSubTaskText() {
        String subTaskText = "";
        try {
            subTaskText = createSubTask.getText();
        } catch (NoSuchElementException | TimeoutException e) {
//            Assertions.fail("Exception " + e); // TODO: fix me
        }
        return subTaskText;
    }

    public void navigateTo(String issueName) {
        webDriver.get(String.format(BROWSE_ISSUE_URL, issueName));
    }

    public void waitForChangingSummary(String newSummary) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(summaryDisplay, newSummary));
        } catch (TimeoutException | NoSuchElementException e) {
//            Assertions.fail("Exception " + e); // TODO: fix me
        }

    }
}
