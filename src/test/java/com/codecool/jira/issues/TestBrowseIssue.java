package com.codecool.jira.issues;

import com.codecool.jira.TestResultLoggerExtension;
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
public class TestBrowseIssue {
    LoginPage loginPage;
    DashboardPage dashboardPage;
    IssueDisplayPage issueDisplayPage;


    @BeforeEach
    void init() {
        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        loginPage.loginSuccessfully();
        dashboardPage.waitForSucessfullyLogin();
        issueDisplayPage = new IssueDisplayPage();
    }

    @AfterEach
    void close() {
        loginPage.closeWebDriver();
    }

    @ParameterizedTest
    @DisplayName("Browse existing issues")
    @CsvFileSource(resources = "/browseIssue.csv", numLinesToSkip = 1, delimiter = ';')
    public void browseExistingIssues(String description, String issueName) {
        issueDisplayPage.openPage(issueName);
        String actualIssueName = issueDisplayPage.getIssueIdText();

        assertEquals(issueName, actualIssueName, "Issue id is the same");
    }

    @Test
    @DisplayName("Browse non existing issue")
    public void browseNonExistingIssue() {
        issueDisplayPage.openPage("ANIMAL-X");
        String errorMessage = issueDisplayPage.getNotExistingErrorMessageText();
        assertEquals("Cannot open project/issue", errorMessage, "Error message is the same");
    }

    @Test
    @DisplayName("Browse existing project without permission")
    public void browseProjectWithoutPermission() {
        issueDisplayPage.openPage("MTP-1-1");
        String errorMessage = issueDisplayPage.getNoPermissionErrorMessage();
        assertEquals("You can't view this issue", errorMessage, "Error message is the same");
    }

}
