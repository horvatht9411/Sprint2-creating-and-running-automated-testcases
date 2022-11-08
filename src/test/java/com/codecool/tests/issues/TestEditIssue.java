package com.codecool.tests.issues;

import com.codecool.TestResultLoggerExtension;
import com.codecool.Util;
import com.codecool.pages.DashboardPage;
import com.codecool.pages.IssuePage;
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
import java.util.Properties;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestResultLoggerExtension.class)
public class TestEditIssue {

    WebDriver webDriver;
    WebDriverWait webDriverWait;
    IssuePage issuePage;
    String url = "https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa";

    @BeforeEach
    void init() throws IOException {
        webDriver = Util.setup(url);
        webDriverWait = Util.initWebdriverWait(webDriver);
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.loginSuccessfully();
        DashboardPage dashboardPage = new DashboardPage(webDriver);
        webDriverWait.until(ExpectedConditions.visibilityOf(dashboardPage.profileMenu));
        issuePage = new IssuePage(webDriver);
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
        webDriverWait.until(ExpectedConditions.visibilityOf(issuePage.issueId));
        issuePage.openEditIssueModal();
        webDriverWait.until(ExpectedConditions.visibilityOf(issuePage.editIssueDialog));
        assertTrue(issuePage.editIssueDialogHeaderText().contains(issueName));
        String newSummary = issuePage.editIssueSuccessfully();
        try {
            webDriverWait.until(ExpectedConditions.textToBePresentInElement(issuePage.summary, newSummary));
        } catch (TimeoutException | NoSuchElementException e) {
            Assertions.fail("Exception " + e);
        }
        assertEquals(newSummary, issuePage.getSummaryText());
        assertEquals(issueName, issuePage.getIssueIdText());
    }

    @ParameterizedTest
    @DisplayName("Edit Issue With Blank Fields")
    @ValueSource(strings = "MTP-2507")
    public void editIssueWithBlankFields(String issueName) {
        webDriver.get(String.format("https://jira-auto.codecool.metastage.net/browse/%s", issueName));
        webDriverWait.until(ExpectedConditions.visibilityOf(issuePage.issueId));
        issuePage.openEditIssueModal();
        webDriverWait.until(ExpectedConditions.visibilityOf(issuePage.editIssueDialog));
        assertTrue(issuePage.editIssueDialogHeaderText().contains(issueName));
        issuePage.leaveSummaryEmpty();
        webDriverWait.until(ExpectedConditions.visibilityOf(issuePage.alertBox));
        assertEquals("You must specify a summary of the issue.", issuePage.getErrorBoxTest());
        issuePage.cancelEditIssueModal();
    }

    @ParameterizedTest
    @DisplayName("Cancel Edit Issue")
    @ValueSource(strings = "MTP-2507")
    public void cancelIssueScreenBeforeUpdating(String issueName) {
        webDriver.get(String.format("https://jira-auto.codecool.metastage.net/browse/%s", issueName));
        webDriverWait.until(ExpectedConditions.visibilityOf(issuePage.issueId));
        issuePage.openEditIssueModal();
        webDriverWait.until(ExpectedConditions.visibilityOf(issuePage.editIssueDialog));
        assertTrue(issuePage.editIssueDialogHeaderText().contains(issueName));
        String canceledSummary = issuePage.cancelEditIssue();
        webDriverWait.until(ExpectedConditions.visibilityOf(issuePage.issueId));
        assertEquals(issueName, issuePage.getIssueIdText());
        assertNotEquals(canceledSummary, issuePage.getSummaryText());
    }

    @ParameterizedTest
    @DisplayName("Edit Button Visibility")
    @CsvFileSource(resources = "/editIssue.csv", numLinesToSkip = 1, delimiter = ';')
    public void editIssues(String description, String issueName) {
        webDriver.get(String.format("https://jira-auto.codecool.metastage.net/browse/%s", issueName));
        try {
            webDriverWait.until(ExpectedConditions.visibilityOf(issuePage.issueId));
            assertEquals(issueName, issuePage.getIssueIdText());
            assertTrue(issuePage.editIssueButtonIsDisplayed());
        } catch (TimeoutException | NoSuchElementException e) {
            Assertions.fail("Exception " + e);
        }
    }
}
