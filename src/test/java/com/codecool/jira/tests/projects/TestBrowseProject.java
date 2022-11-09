package com.codecool.jira.tests.projects;

import com.codecool.jira.TestResultLoggerExtension;
import com.codecool.jira.Util;
import com.codecool.jira.pages.DashboardPage;
import com.codecool.jira.pages.LoginPage;
import com.codecool.jira.pages.ProjectPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(TestResultLoggerExtension.class)
public class TestBrowseProject {
    WebDriver webDriver;
    WebDriverWait webDriverWait;
    ProjectPage projectPage;
    String url = "https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa";

    @BeforeEach
    void init() throws IOException {
        webDriver = Util.setup(url);
        webDriverWait = Util.initWebdriverWait(webDriver);
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.loginSuccessfully();
        DashboardPage dashboardPage = new DashboardPage(webDriver);
        webDriverWait.until(ExpectedConditions.visibilityOf(dashboardPage.profileMenu));
        projectPage = new ProjectPage(webDriver);
    }

    @AfterEach
    void close() {
        webDriver.quit();
    }

    @ParameterizedTest
    @DisplayName("Browse existing projects")
    @CsvFileSource(resources = "/browseProject.csv", numLinesToSkip = 1, delimiter = ';')
    public void browseExistingProject(String description, String projectKey){
        webDriver.get(String.format("https://jira-auto.codecool.metastage.net/projects/%s/summary", projectKey));
        webDriverWait.until(ExpectedConditions.visibilityOf(projectPage.projectKey));
        assertEquals(projectKey, projectPage.getProjectKey());
    }

    @ParameterizedTest
    @DisplayName("Browse non visible projects")
    @CsvFileSource(resources = "/browseProjectWrong.csv", numLinesToSkip = 1, delimiter = ';')
    public void browseOtherProject(String description, String projectKey) {
        webDriver.get(String.format("https://jira-auto.codecool.metastage.net/projects/%s/summary", projectKey));
        webDriverWait.until(ExpectedConditions.visibilityOf(projectPage.errorMessage));
        assertEquals("You can't view this project", projectPage.getErrorMessage());
    }

}
