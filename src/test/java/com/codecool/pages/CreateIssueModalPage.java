package com.codecool.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.UUID;

public class CreateIssueModalPage {
    WebDriver webDriver;

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

    public CreateIssueModalPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void fillUpProjectName(String projectName) {
        projectSelector.click();
        projectSelector.sendKeys(Keys.BACK_SPACE);
        projectSelector.sendKeys(projectName);
        projectSelector.sendKeys(Keys.ENTER);
    }

    public String fillUpSummary(WebDriverWait wait) {
        wait.until(ExpectedConditions.elementToBeClickable(summary));
        summary.click();
        String value = UUID.randomUUID().toString();
        summary.sendKeys(value);
        return value;
    }

    public String getSummaryText() {
        return summary.getText();
    }

    public void waitForModal(WebDriverWait wait) {
        wait.until(ExpectedConditions.elementToBeClickable(issueModal));
    }

    public void submitNewIssue() {
        submitButton.click();
    }

    public void clickOnNewIssueLink() {
        newIssueLink.click();
    }

    public String getWarningMessageToFillSummary() {
        return warningMessageToFillSummary.getText();
    }

    public void closeCreateModal(WebDriverWait wait) {
        cancelButton.click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }


}
