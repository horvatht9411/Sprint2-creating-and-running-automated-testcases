package com.codecool.jira.issues;

import com.codecool.jira.TestResultLoggerExtension;
import com.codecool.jira.Util;
import com.codecool.jira.issuePages.CreateIssueLinkPage;
import com.codecool.jira.issuePages.CreateIssueModalPage;
import com.codecool.jira.issuePages.IssueDisplayPage;
import com.codecool.jira.loginPages.DashboardPage;
import com.codecool.jira.loginPages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(TestResultLoggerExtension.class)
public class TestCreateIssue {

    LoginPage loginPage;
    DashboardPage dashboardPage;
    IssueDisplayPage issueDisplayPage;
    CreateIssueModalPage createIssueModalPage;
    CreateIssueLinkPage createIssueLinkPage;

    boolean isDeletable;
    boolean closeAfterEditing;

    @BeforeEach
    void init() {
        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        loginPage.loginSuccessfully();
        dashboardPage.waitForSucessfullyLogin();
        issueDisplayPage = new IssueDisplayPage();
        createIssueModalPage = new CreateIssueModalPage();
        createIssueLinkPage = new CreateIssueLinkPage();
        isDeletable = false;
        closeAfterEditing = false;
    }

    @AfterEach
    void close() {
        if (isDeletable) {
            issueDisplayPage.deleteNewlyCreatedIssue();
        }
        if (closeAfterEditing) {
            createIssueModalPage.closeCreateModal();
        }
        loginPage.closeWebDriver();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/createIssue.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Create new issue successfully")
    public void createNewIssue(String projectName) {
        isDeletable = true;
        dashboardPage.clickCreateNewIssueButton();
        String expectedSummaryText = Util.generateRandomSummary();
        createIssueModalPage.fillUpSummary(expectedSummaryText);
        createIssueModalPage.fillUpProjectName(projectName);
        createIssueModalPage.submitNewIssue();
        createIssueModalPage.clickOnNewIssueLink();
        String actualSummaryText = issueDisplayPage.getSummaryDisplayText();

        assertEquals(expectedSummaryText, actualSummaryText, "Summary is the same");

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/createIssue.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Create new issue in a new tab successfully")
    public void createNewIssueII(String projectName, String issueType) {
        isDeletable = true;
        createIssueModalPage.navigateToCreateIssuePage();
        createIssueLinkPage.fillUpProjectName(projectName);
        createIssueLinkPage.fillUpIssueType(issueType);
        createIssueLinkPage.clickNextButton();
        String expectedSummaryText = createIssueLinkPage.fillUpSummaryField();
        createIssueLinkPage.submitNewIssue();
        String actualSummaryText = issueDisplayPage.getSummaryDisplayText();

        assertEquals(expectedSummaryText, actualSummaryText, "Summary is the same");
    }

    @Test
    @DisplayName("Create new issue with blank mandatory fields")
    public void blankFields() {
        closeAfterEditing = true;
        dashboardPage.clickCreateNewIssueButton();
        createIssueModalPage.waitForModal();
        createIssueModalPage.submitNewIssue();
        String expectedErrorMessage = "You must specify a summary of the issue.";
        String actualErrorMessage = createIssueModalPage.getWarningMessageToFillSummary();

        assertEquals(expectedErrorMessage, actualErrorMessage, "Error message is the same");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/createIssue.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Cancel creating new issue")
    public void cancel(String description, String projectName) {
        dashboardPage.clickCreateNewIssueButton();
        createIssueModalPage.fillUpProjectName(projectName);
        String expectedSummaryText = Util.generateRandomSummary();
        createIssueModalPage.fillUpSummary(expectedSummaryText);
        createIssueModalPage.closeCreateModal();
        String issueUrl = createIssueModalPage.navigateToIssuDisplayPage(expectedSummaryText);
        createIssueModalPage.navigateTo(issueUrl);
        String expectedErrorMessage = "No issues were found to match your search";
        String actualErrorMessage = issueDisplayPage.getNoIssueErrorMessage();

        assertEquals(expectedErrorMessage, actualErrorMessage, "Error message is the same");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/issueTypes.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Check issue types for existing projects")
    public void issueTypes(String description, String projectName, String issueType) {
        createIssueLinkPage.navigateToCreateIssueUrl();
        createIssueLinkPage.fillUpProjectName(projectName);
        createIssueLinkPage.fillUpIssueType(issueType);
        createIssueLinkPage.clickNextButton();
        String actualIssueType = createIssueLinkPage.getSelectedIssueTypeText();

        assertEquals(issueType, actualIssueType, "Issue type is the same");

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/issueSubtask.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Check subtask visibility for projects")
    public void createJetiSubtask(String description, String projectName, String issueId) {
        issueDisplayPage.navigateTo(issueId);
        String actualIssueId = issueDisplayPage.getIssueIdText();
        assertEquals(issueId, actualIssueId, "Issue id is the same");
        issueDisplayPage.openMoreMenu();
        String expected = "Create sub-task";
        String actual = issueDisplayPage.getCreateSubTaskText();

        assertEquals(expected, actual, "Create sub-task is visible");
    }
}
