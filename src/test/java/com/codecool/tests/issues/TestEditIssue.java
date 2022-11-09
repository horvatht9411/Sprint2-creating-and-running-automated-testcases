package com.codecool.tests.issues;

import com.codecool.TestResultLoggerExtension;
import com.codecool.Util;
import com.codecool.pages.DashboardPage;
import com.codecool.pages.IssueDisplayPage;
import com.codecool.pages.LoginPage;
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

    WebDriver webDriver;
    WebDriverWait webDriverWait;
    IssueDisplayPage issueDisplayPage;
    String url = "https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa";

    @BeforeEach
    void init() throws IOException {
        webDriver = Util.setup(url);
        webDriverWait = Util.initWebdriverWait(webDriver);
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.loginSuccessfully();
        DashboardPage dashboardPage = new DashboardPage(webDriver);
        webDriverWait.until(ExpectedConditions.visibilityOf(dashboardPage.profileMenu));
        issueDisplayPage = new IssueDisplayPage(webDriver);
    }

    @AfterEach
    void close() {
        webDriver.quit();
    }

    @ParameterizedTest
    @DisplayName("Edit Issue Successfully")
    @ValueSource(strings = "MTP-2507")
    public void editIssueSuccessfully(String issueName){
        webDriver.get(String.format("https://jira-auto.codecool.metastage.net/browse/%s", issueName));
        webDriverWait.until(ExpectedConditions.visibilityOf(issueDisplayPage.issueId));
        issueDisplayPage.openEditIssueModal();
        webDriverWait.until(ExpectedConditions.visibilityOf(issueDisplayPage.editIssueDialog));
        assertTrue(issueDisplayPage.editIssueDialogHeaderText().contains(issueName));
        String newSummary = issueDisplayPage.editIssueSuccessfully();
        try {
            webDriverWait.until(ExpectedConditions.textToBePresentInElement(issueDisplayPage.summary, newSummary));
        } catch (TimeoutException | NoSuchElementException e) {
            Assertions.fail("Exception " + e);
        }
        assertEquals(newSummary, issueDisplayPage.getSummaryText());
        assertEquals(issueName, issueDisplayPage.getIssueIdText());
    }

    @ParameterizedTest
    @DisplayName("Edit Issue With Blank Fields")
    @ValueSource(strings = "MTP-2507")
    public void editIssueWithBlankFields(String issueName) {
        webDriver.get(String.format("https://jira-auto.codecool.metastage.net/browse/%s", issueName));
        webDriverWait.until(ExpectedConditions.visibilityOf(issueDisplayPage.issueId));
        issueDisplayPage.openEditIssueModal();
        webDriverWait.until(ExpectedConditions.visibilityOf(issueDisplayPage.editIssueDialog));
        assertTrue(issueDisplayPage.editIssueDialogHeaderText().contains(issueName));
        issueDisplayPage.leaveSummaryEmpty();
        webDriverWait.until(ExpectedConditions.visibilityOf(issueDisplayPage.alertBox));
        assertEquals("You must specify a summary of the issue.", issueDisplayPage.getErrorBoxTest());
        issueDisplayPage.cancelEditIssueModal();
    }

    @ParameterizedTest
    @DisplayName("Cancel Edit Issue")
    @ValueSource(strings = "MTP-2507")
    public void cancelIssueScreenBeforeUpdating(String issueName) {
        webDriver.get(String.format("https://jira-auto.codecool.metastage.net/browse/%s", issueName));
        webDriverWait.until(ExpectedConditions.visibilityOf(issueDisplayPage.issueId));
        issueDisplayPage.openEditIssueModal();
        webDriverWait.until(ExpectedConditions.visibilityOf(issueDisplayPage.editIssueDialog));
        assertTrue(issueDisplayPage.editIssueDialogHeaderText().contains(issueName));
        String canceledSummary = issueDisplayPage.cancelEditIssue();
        webDriverWait.until(ExpectedConditions.visibilityOf(issueDisplayPage.issueId));
        assertEquals(issueName, issueDisplayPage.getIssueIdText());
        assertNotEquals(canceledSummary, issueDisplayPage.getSummaryText());
    }

    @ParameterizedTest
    @DisplayName("Edit Button Visibility")
    @CsvFileSource(resources = "/editIssue.csv", numLinesToSkip = 1, delimiter = ';')
    public void editIssues(String description, String issueName) {
        webDriver.get(String.format("https://jira-auto.codecool.metastage.net/browse/%s", issueName));
        try {
            webDriverWait.until(ExpectedConditions.visibilityOf(issueDisplayPage.issueId));
            assertEquals(issueName, issueDisplayPage.getIssueIdText());
            assertTrue(issueDisplayPage.editIssueButtonIsDisplayed());
        } catch (TimeoutException | NoSuchElementException e) {
            Assertions.fail("Exception " + e);
        }
    }
}
