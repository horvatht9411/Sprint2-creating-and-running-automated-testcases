package com.codecool.jira.issues;

import com.codecool.jira.TestResultLoggerExtension;
import com.codecool.jira.Util;
import com.codecool.jira.issuePages.CreateIssueLinkPage;
import com.codecool.jira.issuePages.CreateIssueModalPage;
import com.codecool.jira.issuePages.IssueDisplayPage;
import com.codecool.jira.loginPages.DashboardPage;
import com.codecool.jira.loginPages.LoginPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(TestResultLoggerExtension.class)
public class TestCreateIssue {


    LoginPage loginPage;

    DashboardPage dashboardPage;

    IssueDisplayPage issueDisplayPage;
    CreateIssueModalPage createIssueModalPage;
    CreateIssueLinkPage createIssueLinkPage;


    @BeforeEach
    void init() throws IOException {
        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        loginPage.loginSuccessfully();
        dashboardPage.waitForSucessfullyLogin();
        issueDisplayPage = new IssueDisplayPage();
        createIssueModalPage = new CreateIssueModalPage();
        createIssueLinkPage = new CreateIssueLinkPage();
    }

    @AfterEach
    void close() {
        loginPage.closeWebDriver();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/createIssue.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Create new issue successfully")
    public void createNewIssue(String projectName) {
        dashboardPage.clickCreateNewIssueButton();
        createIssueModalPage.fillUpProjectName(projectName);
        String expectedSummaryText = createIssueModalPage.fillUpSummary();
        createIssueModalPage.submitNewIssue();
        createIssueModalPage.clickOnNewIssueLink();
        String actualSummaryText = issueDisplayPage.getSummaryDisplayText();
        assertEquals(expectedSummaryText, actualSummaryText);

        issueDisplayPage.deleteNewlyCreatedIssue();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/createIssue.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Create new issue in a new tab successfully")
    public void createNewIssueII(String projectName, String issueType) {
        createIssueModalPage.navigateToCreateIssuePage();
        createIssueLinkPage.fillUpProjectName(projectName);
        createIssueLinkPage.fillUpIssueType(issueType);
        createIssueLinkPage.clickNextButton();
        String expectedSummaryText = createIssueLinkPage.fillUpSummaryField();
        createIssueLinkPage.submitNewIssue();

        String actualSummaryText = issueDisplayPage.getSummaryDisplayText();
        assertEquals(expectedSummaryText, actualSummaryText);

        issueDisplayPage.deleteNewlyCreatedIssue();
    }

    @Test
    @DisplayName("Create new issue with blank mandatory fields")
    public void blankFields() {
        dashboardPage.clickCreateNewIssueButton();
        createIssueModalPage.waitForModal();
        createIssueModalPage.submitNewIssue();

        String expectedErrorMessage = "You must specify a summary of the issue.";
        String actualErrorMessage = createIssueModalPage.getWarningMessageToFillSummary();
        assertEquals(expectedErrorMessage, actualErrorMessage);

        createIssueModalPage.closeCreateModal();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/createIssue.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Cancel creating new issue")
    public void cancel(String description, String projectName) {
        dashboardPage.clickCreateNewIssueButton();
        createIssueModalPage.fillUpProjectName(projectName);
        String expectedSummaryText = createIssueModalPage.fillUpSummary();
        createIssueModalPage.closeCreateModal();
        String issueUrl = "https://jira-auto.codecool.metastage.net/browse/MTP-2459?jql=summary%20~%20%22" + expectedSummaryText + "%22";
        createIssueModalPage.navigateTo(issueUrl);
        String expectedErrorMessage = "No issues were found to match your search";
        String actualErrorMessage = issueDisplayPage.getNoIssueErrorMessage();
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/issueTypes.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Check issue types for existing projects")
    public void issueTypes(String description, String projectName, String issueType) {
        createIssueLinkPage.navigateToCreateIssueUrl();
        createIssueLinkPage.fillUpProjectName(projectName);
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
    @CsvFileSource(resources = "/issueSubtask.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Check subtask visibility for projects")
    public void createJetiSubtask(String description, String projectName, String issueId) {
        issueDisplayPage.navigateTo(issueId);
        try {
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
