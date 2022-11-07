package com.codecool.tests.projects;

import com.codecool.TestResultLoggerExtension;
import com.codecool.Util;
import com.codecool.pages.LoginPage;
import com.codecool.pages.ProjectPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Properties;

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
        projectPage = new ProjectPage(webDriver);
    }

    @AfterEach
    void close() {
        webDriver.quit();
    }

    @Test
    @DisplayName("Browse existing project")
    public void browseExistingProject() {
        webDriver.get("https://jira-auto.codecool.metastage.net/projects/MTP/summary"); //TODO read out from Excel the url
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated((By) projectPage.projectSummary));
        assertEquals("MTP", projectPage.getProjectKey()); //TODO read out from Excel the expected projectname
    }

    @Test
    @DisplayName("Browse non existing project")
    public void browseNonExistingProject() {
        webDriver.get("https://jira-auto.codecool.metastage.net/projects/ANIMAL/summary"); //TODO read out from Excel the url
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated((By) projectPage.errorMessage));
        assertEquals("You can't view this project", projectPage.getErrorMessage());
    }

    @Test
    @DisplayName("Browse existing project without permission")
    public void browseProjectWithoutPermission() {
        webDriver.get("https://jira-auto.codecool.metastage.net/projects/MTP-1/summary"); //TODO read out from Excel the url
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated((By) projectPage.errorMessage));
        assertEquals("You can't view this project", projectPage.getErrorMessage());
    }

    @Test
    @DisplayName("Browse TOUCAN project")
    public void browseToucanProject() {
        webDriver.get("https://jira-auto.codecool.metastage.net/projects/TOUCAN/summary");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("summary-subnav-title")));
        String expectedProjectKey = "TOUCAN";
        String projectKey = webDriver.findElement(By.cssSelector("#summary-body > div > div.aui-item.project-meta-column > dl > dd:nth-child(4)")).getText();

        assertEquals(expectedProjectKey, projectKey);
    }

    @Test
    @DisplayName("Browse COALA project")
    public void browseCoalaProject() {
        webDriver.get("https://jira-auto.codecool.metastage.net/projects/COALA/summary");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("summary-subnav-title")));
        String expectedProjectKey = "COALA";
        String projectKey = webDriver.findElement(By.cssSelector("#summary-body > div > div.aui-item.project-meta-column > dl > dd:nth-child(4)")).getText();

        assertEquals(expectedProjectKey, projectKey);
    }

    @Test
    @DisplayName("Browse JETI project")
    public void browseJetiProject() {
        webDriver.get("https://jira-auto.codecool.metastage.net/projects/JETI/summary");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("summary-subnav-title")));
        String expectedProjectKey = "JETI";
        String projectKey = webDriver.findElement(By.cssSelector("#summary-body > div > div.aui-item.project-meta-column > dl > dd:nth-child(4)")).getText();

        assertEquals(expectedProjectKey, projectKey);
    }
}
