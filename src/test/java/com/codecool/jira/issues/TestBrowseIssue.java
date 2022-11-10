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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(TestResultLoggerExtension.class)
public class TestBrowseIssue {

    WebDriver webDriver;
    WebDriverWait webDriverWait;
    IssueDisplayPage issueDisplayPage;
    String url = "https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa";

    @BeforeEach
    void init() throws IOException {
//        webDriver = Util.setup(url);
//        webDriverWait = Util.initWebdriverWait(webDriver);
        LoginPage loginPage = new LoginPage();
        loginPage.loginSuccessfully();
        DashboardPage dashboardPage = new DashboardPage();
        webDriverWait.until(ExpectedConditions.visibilityOf(dashboardPage.profileMenu));
        issueDisplayPage = new IssueDisplayPage(webDriver);
    }

    @AfterEach
    void close() {
        webDriver.quit();
    }

    @ParameterizedTest
    @DisplayName("Browse existing issues")
    @CsvFileSource(resources = "/browseIssue.csv", numLinesToSkip = 1, delimiter = ';')
    public void browseExistingIssues(String description, String issueName) {
        webDriver.get(String.format("https://jira-auto.codecool.metastage.net/browse/%s", issueName));
        try {
            webDriverWait.until(ExpectedConditions.visibilityOf(issueDisplayPage.issueId));
        } catch (TimeoutException | NoSuchElementException e) {
            Assertions.fail("Exception " + e);
        }
        assertEquals(issueName, issueDisplayPage.getIssueIdText());
    }


    @Test
    @DisplayName("Browse non existing issue")
    public void browseNonExistingIssue() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/ANIMAL-X");
        webDriverWait.until(ExpectedConditions.visibilityOf(issueDisplayPage.alertBox));
        assertEquals("Cannot open project/issue", issueDisplayPage.getNotExistingErrorMessageText());
    }

    @Test
    @DisplayName("Browse existing project without permission")
    public void browseProjectWithoutPermission() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/MTP-1-1");
        webDriverWait.until(ExpectedConditions.visibilityOf(issueDisplayPage.alertBox));
        assertEquals("You can't view this issue", issueDisplayPage.getNoPermissionErrorMessage());
    }

}
