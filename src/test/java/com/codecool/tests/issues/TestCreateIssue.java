package com.codecool.tests.issues;

import com.codecool.TestResultLoggerExtension;
import com.codecool.Util;
import com.codecool.pages.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(TestResultLoggerExtension.class)
public class TestCreateIssue {
    WebDriver webDriver;
    WebDriverWait webDriverWait;
    DashboardPage dashboardPage;
    IssueDisplayPage issueDisplayPage;
    CreateIssueModalPage createIssueModalPage;
    CreateIssueLinkPage createIssueLinkPage;
    String url = "https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa";
    String CREATE_ISSUE_URL = "https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa";
    String BROWSE_ISSUE_URL = "https://jira-auto.codecool.metastage.net/browse/%s";

    @BeforeEach
    void init() throws IOException {
        webDriver = Util.setup(url);
        webDriverWait = Util.initWebdriverWait(webDriver);
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.loginSuccessfully();
        dashboardPage = new DashboardPage(webDriver);
        webDriverWait.until(ExpectedConditions.visibilityOf(dashboardPage.profileMenu));
        issueDisplayPage = new IssueDisplayPage(webDriver);
        createIssueModalPage = new CreateIssueModalPage(webDriver);
        createIssueLinkPage = new CreateIssueLinkPage(webDriver);
    }

    @AfterEach
    void close() {
        webDriver.quit();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/createIssue.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Create new issue successfully")
    public void createNewIssue(String projectName) throws InterruptedException {
        dashboardPage.createNewIssue();
        webDriverWait.until(ExpectedConditions.visibilityOf(createIssueModalPage.issueModal));
        createIssueModalPage.fillUpProjectName(projectName);
        String expectedSummaryText = createIssueModalPage.fillUpSummary(webDriverWait);
        createIssueModalPage.submitNewIssue();

        webDriverWait.until(ExpectedConditions.visibilityOf(createIssueModalPage.newIssueLink));
        createIssueModalPage.clickOnNewIssueLink();
        String actualSummaryText = issueDisplayPage.getSummaryText();
        assertEquals(expectedSummaryText, actualSummaryText);

        issueDisplayPage.deleteNewlyCreatedIssue(webDriverWait);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/createIssue.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Create new issue in a new tab successfully")
    public void createNewIssueII(String projectName, String issueType) {
        webDriver.get(CREATE_ISSUE_URL);
        createIssueLinkPage.fillUpProjectName(webDriverWait, projectName);
        createIssueLinkPage.fillUpIssueType(issueType);
        createIssueLinkPage.clickNextButton();

        createIssueLinkPage.fillUpSummaryField(webDriverWait);
        String expectedSummaryText = createIssueLinkPage.getSummaryText();
        createIssueLinkPage.submitNewIssue();

        webDriverWait.until(ExpectedConditions.visibilityOf(issueDisplayPage.summary));
        String actualSummaryText = issueDisplayPage.getSummaryText();
        assertEquals(expectedSummaryText, actualSummaryText);

        issueDisplayPage.deleteNewlyCreatedIssue(webDriverWait);
    }

    @Test
    @DisplayName("Create new issue with blank mandatory fields")
    public void blankFields() {
        dashboardPage.createNewIssue();
        webDriverWait.until(ExpectedConditions.visibilityOf(createIssueModalPage.issueModal));
        createIssueModalPage.submitNewIssue();

        String expectedErrorMessage = "You must specify a summary of the issue.";
        String actualErrorMessage = createIssueModalPage.getWarningMessageToFillSummary();
        assertEquals(expectedErrorMessage, actualErrorMessage);

        createIssueModalPage.closeCreateModal(webDriverWait);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/createIssue.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Cancel creating new issue")
    public void cancel(String projectName) {
        dashboardPage.createNewIssue();
        webDriverWait.until(ExpectedConditions.visibilityOf(createIssueModalPage.issueModal));
        createIssueModalPage.fillUpProjectName(projectName);
        String expectedSummaryText = createIssueModalPage.fillUpSummary(webDriverWait);
        createIssueModalPage.closeCreateModal(webDriverWait);

        webDriver.get(String.format("https://jira-auto.codecool.metastage.net/browse/MTP-2459?jql=summary%20~%20%22%s%22", expectedSummaryText));
        String expectedErrorMessage = "No issues were found to match your search";
        String actualErrorMessage = issueDisplayPage.getNoIssueErrorMessage();
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/issueTypes.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Check issue types for existing projects")
    public void issueTypes(String projectName, String issueType) {
        webDriver.get(CREATE_ISSUE_URL);
        webDriverWait.until(ExpectedConditions.visibilityOf(createIssueLinkPage.project));
        createIssueLinkPage.fillUpProjectName(webDriverWait, projectName);
        try {
            createIssueLinkPage.fillUpIssueType(issueType);
        } catch (ElementClickInterceptedException e) {
            Assertions.fail("Exception " + e);
        }
        createIssueLinkPage.clickNextButton();
        String actualIssueType = createIssueLinkPage.getSelectedIssueTypeText();
        assertEquals(issueType, actualIssueType);
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/issueTypes.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Check subtask visibility for projects")
    public void createJetiSubtask(String description, String projectName, String issueId) {
        webDriver.get(String.format(BROWSE_ISSUE_URL, issueId));
        try {
            webDriverWait.until(ExpectedConditions.visibilityOf(issueDisplayPage.issueId));
            String actualIssueId = issueDisplayPage.getIssueIdText();
            assertEquals(issueId, actualIssueId);
            issueDisplayPage.openMoreMenu();
            String expected = "Create sub-task";
            String actual = issueDisplayPage.getCreateSubTaskText();
            assertEquals(expected, actual);
        } catch (NoSuchElementException | TimeoutException e) {
            Assertions.fail("Exception " + e);
        }

    }

}
