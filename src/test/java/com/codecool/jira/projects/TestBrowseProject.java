package com.codecool.jira.projects;

import com.codecool.jira.TestResultLoggerExtension;
import com.codecool.jira.loginPages.DashboardPage;
import com.codecool.jira.loginPages.LoginPage;
import com.codecool.jira.projectPages.ProjectPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(TestResultLoggerExtension.class)
public class TestBrowseProject {

    ProjectPage projectPage;
    LoginPage loginPage;

    DashboardPage dashboardPage;


    @BeforeEach
    void init() {
        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        loginPage.loginSuccessfully();
        dashboardPage.waitForSucessfullyLogin();
        projectPage = new ProjectPage();
    }

    @AfterEach
    void close() {
        loginPage.closeWebDriver();
    }

    @ParameterizedTest
    @DisplayName("Browse existing projects")
    @CsvFileSource(resources = "/browseProject.csv", numLinesToSkip = 1, delimiter = ';')
    public void browseExistingProject(String description, String projectKey) {
        projectPage.navigateToProjectPage(projectKey);
        assertEquals(projectKey, projectPage.getProjectKey());
    }

    @ParameterizedTest
    @DisplayName("Browse non visible projects")
    @CsvFileSource(resources = "/browseProjectWrong.csv", numLinesToSkip = 1, delimiter = ';')
    public void browseOtherProject(String description, String projectKey) {
        projectPage.navigateToProjectPage(projectKey);
        assertEquals("You can't view this project", projectPage.getErrorMessage());
    }

}
