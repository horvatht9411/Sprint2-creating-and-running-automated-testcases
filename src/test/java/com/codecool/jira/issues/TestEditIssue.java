package com.codecool.jira.issues;

import com.codecool.jira.TestResultLoggerExtension;
import com.codecool.jira.Util;
import com.codecool.jira.issuePages.IssueDisplayPage;
import com.codecool.jira.loginPages.DashboardPage;
import com.codecool.jira.loginPages.LoginPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestResultLoggerExtension.class)
public class TestEditIssue {

    LoginPage loginPage;

    DashboardPage dashboardPage;

    IssueDisplayPage issueDisplayPage;


    @BeforeEach
    void init(){
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
    @DisplayName("Edit Issue Successfully")
    @ValueSource(strings = "MTP-2507")
    public void editIssueSuccessfully(String issueName){
        issueDisplayPage.navigateTo(issueName);
        issueDisplayPage.openEditIssueModal();
        assertTrue(issueDisplayPage.editIssueDialogHeaderText().contains(issueName), "Issue id is the same");
        String expectedSummaryText = Util.generateRandomSummary();
        issueDisplayPage.editIssueSuccessfully(expectedSummaryText);
        issueDisplayPage.waitForChangingSummary(expectedSummaryText);

        assertEquals(expectedSummaryText, issueDisplayPage.getSummaryDisplayText(), "Summary is the same");
        assertEquals(issueName, issueDisplayPage.getIssueIdText(), "Issue id is the same");
    }

    @ParameterizedTest
    @DisplayName("Edit Issue With Blank Fields")
    @ValueSource(strings = "MTP-2507")
    public void editIssueWithBlankFields(String issueName) {
        issueDisplayPage.navigateTo(issueName);
        issueDisplayPage.openEditIssueModal();
        assertTrue(issueDisplayPage.editIssueDialogHeaderText().contains(issueName), "Issue id is the same");
        issueDisplayPage.leaveSummaryEmpty();
        assertEquals("You must specify a summary of the issue.", issueDisplayPage.getErrorBoxTest(), "Error message is the same");
        issueDisplayPage.cancelEditIssueModal();
    }

    @ParameterizedTest
    @DisplayName("Cancel Edit Issue")
    @ValueSource(strings = "MTP-2507")
    public void cancelIssueScreenBeforeUpdating(String issueName) {
        issueDisplayPage.navigateTo(issueName);
        issueDisplayPage.openEditIssueModal();
        assertTrue(issueDisplayPage.editIssueDialogHeaderText().contains(issueName), "Issue id is the same");
        String canceledSummary = issueDisplayPage.cancelEditIssue();
        assertEquals(issueName, issueDisplayPage.getIssueIdText(), "Issue id is the same");
        assertNotEquals(canceledSummary, issueDisplayPage.getSummaryDisplayText(), "Summary is the same");
    }

    @ParameterizedTest
    @DisplayName("Edit Button Visibility")
    @CsvFileSource(resources = "/editIssue.csv", numLinesToSkip = 1, delimiter = ';')
    public void editIssues(String description, String issueName) {
        issueDisplayPage.navigateTo(issueName);
        String actualIssueName = issueDisplayPage.getIssueIdText();
        boolean editIssueButtonVisibile = issueDisplayPage.editIssueButtonIsDisplayed();
        assertTrue(editIssueButtonVisibile, "Edit issue button is visible");
        assertEquals(issueName, actualIssueName, "Issue id is the same");
    }
}
