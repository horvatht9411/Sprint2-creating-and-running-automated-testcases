package com.codecool.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.UUID;
import java.util.WeakHashMap;

public class IssuePage {

    WebDriver webDriver;

    @FindBy(xpath = "//*[@id='key-val']")
    public WebElement issueId;

    @FindBy(xpath = "//*[@id='summary-val']")
    public WebElement summary;

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

    @FindBy(xpath = "//*[@id='dialog-form']/div/div/div[1]/div")
    public WebElement errorBox;

    @FindBy(xpath = "//*[@id='edit-issue-dialog']/footer/div/div/button")
    public WebElement cancelButton;




    public IssuePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public String getIssueIdText(){ return issueId.getText(); }

    public String getNotExistingErrorMessageText(){ return notExistingErrorMessage.getText(); }
    public String getErrorBoxTest(){ return errorBox.getText(); }
    public String getNoPermissionErrorMessage(){ return noPermissionErrorMessage.getText(); }
    public boolean editIssueButtonIsDisplayed() { return editIssueButton.isDisplayed(); }
    public String editIssueDialogHeaderText() { return editIssueDialogHeader.getText(); }

    public void openEditIssueModal(){ editIssueButton.click();}
    public void cancelEditIssueModal(){
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

    public String getSummaryText() { return summary.getText(); }

    public void leaveSummaryEmpty() {
        editIssueSummary.click();
        editIssueSummary.clear();
        updateButton.click();
    }
}
