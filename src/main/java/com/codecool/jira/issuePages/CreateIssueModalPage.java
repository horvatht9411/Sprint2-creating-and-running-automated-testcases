package com.codecool.jira.issuePages;

import com.codecool.jira.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CreateIssueModalPage extends BasePage {

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
        try {
            // TODO: wait for some element change eg: projectname or submit button
        } catch (StaleElementReferenceException e){
    }
    }

    public void fillUpSummary(String summary) {
        // TODO: check
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(this.summary)));
        this.summary.click();
        this.summary.sendKeys(summary);
    }

    public void waitForModal() {
        wait.until(ExpectedConditions.elementToBeClickable(issueModal));
    }

    public void submitNewIssue() {
        //TODO: wait for element not exist
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(submitButton)));
//        webDriver.findElement(By.xpath("//*[@id=\"labels-textarea\"]")).click();
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

    public String navigateToIssuDisplayPage(String expectedSummaryText) {
        return ISSUE_DISPLAY_FRONT + expectedSummaryText + ISSUE_DISPLAY_BACK;
    }
}
