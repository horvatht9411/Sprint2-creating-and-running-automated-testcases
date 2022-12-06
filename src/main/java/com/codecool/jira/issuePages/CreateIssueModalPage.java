package com.codecool.jira.issuePages;

import com.codecool.jira.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CreateIssueModalPage extends BasePage {

    private final String createIssueUrl = baseUrl + "secure/CreateIssue.jspa";

    private final String issueDisplayFront = baseUrl + "browse/MTP-2459?jql=summary%20~%20%22";
    private final String issueDisplayBack = "%22";

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
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
    }

    public void fillUpSummary(String summary) {
        wait.until(ExpectedConditions.elementToBeClickable(this.summary));
//        this.summary.click();
        try {
            this.summary.sendKeys(summary);
        } catch (StaleElementReferenceException | ElementNotInteractableException e){
            webDriver.findElement(By.id("summary")).sendKeys(summary);
        }
    }

    public void waitForModal() {
        wait.until(ExpectedConditions.elementToBeClickable(issueModal));
    }

    public void submitNewIssue() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
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
        webDriver.get(createIssueUrl);
    }

    public void navigateTo(String issueUrl) {
        webDriver.get(issueUrl);
    }

    public String navigateToIssuDisplayPage(String expectedSummaryText) {
        return issueDisplayFront + expectedSummaryText + issueDisplayBack;
    }
}
