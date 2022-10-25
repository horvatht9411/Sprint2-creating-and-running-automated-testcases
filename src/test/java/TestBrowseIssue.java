import com.codecool.App;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBrowseIssue {

    Logger logger = LoggerFactory.getLogger(App.class);
    WebDriver webDriver;
    Properties appProps;




    WebDriverWait webDriverWait;

    String url = "https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa";

    @BeforeEach
    void init() throws IOException {
        webDriver = Util.setup(url);
        webDriverWait = Util.initWebdriverWait(webDriver);
        appProps = Util.read();
        Util.login(webDriver, appProps, webDriverWait);
    }


    @AfterEach
    void close() {
        webDriver.quit();
    }

    @Test
    @DisplayName("Browse existing issue")
    public void browseExistingIssue() throws InterruptedException {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/MTP-1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        String expectedIssueId = "MTP-1";
        String issueId = webDriver.findElement(By.id("key-val")).getText();

        assertEquals(expectedIssueId, issueId);
    }

    @Test
    @DisplayName("Browse non existing issue")
    public void browseNonExistingIssue() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/ANIMAL-X");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#main > div > header > h1")));
        String expectedErrorMessage = "Cannot open project/issue";
        String errorMessage = webDriver.findElement(By.cssSelector("#main > div > header > h1")).getText();

        assertEquals(expectedErrorMessage, errorMessage);
    }

    @Test
    @DisplayName("Browse existing project without permission")
    public void browseProjectWithoutPermission() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/MTP-1-1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#issue-content > div > div > h1")));
        String expectedErrorMessage = "You can't view this issue";
        String errorMessage = webDriver.findElement(By.cssSelector("#issue-content > div > div > h1")).getText();

        assertEquals(expectedErrorMessage, errorMessage);
    }


    @ParameterizedTest
    @EnumSource(Issue.class)
    @DisplayName("Browse issues for TOUCAN project")
    public void browseToucanIssues(Issue issue) {
//        for (Issue issue : issues.values()) {
            webDriver.get(issue.getUrl());
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
            String expectedIssueId = issue.getId();
            String issueId = webDriver.findElement(By.id("key-val")).getText();

            assertEquals(expectedIssueId, issueId);
//        }

    }
//
//
//    @Test
//    @DisplayName("Browse COALA project")
//    public void browseCoalaProject() {
//        webDriver.get("https://jira-auto.codecool.metastage.net/projects/COALA/summary");
//        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sidebar > div > div.aui-sidebar-body > div > div > div:nth-child(2) > h1 > div > div > a")));
//        String expectedProjectName = "COALA project";
//        String expectedProjectKey = "COALA";
//        String projectName = webDriver.findElement(By.cssSelector("#sidebar > div > div.aui-sidebar-body > div > div > div:nth-child(2) > h1 > div > div > a")).getText();
//        String projectKey = webDriver.findElement(By.cssSelector("#summary-body > div > div.aui-item.project-meta-column > dl > dd:nth-child(4)")).getText();
//
//        assertEquals(expectedProjectName, projectName);
//        assertEquals(expectedProjectKey, projectKey);
//    }
//
//    @Test
//    @DisplayName("Browse JETI project")
//    public void browseJetiProject() {
//        webDriver.get("https://jira-auto.codecool.metastage.net/projects/JETI/summary");
//        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sidebar > div > div.aui-sidebar-body > div > div > div:nth-child(2) > h1 > div > div > a")));
//        String expectedProjectName = "JETI project";
//        String expectedProjectKey = "JETI";
//        String projectName = webDriver.findElement(By.cssSelector("#sidebar > div > div.aui-sidebar-body > div > div > div:nth-child(2) > h1 > div > div > a")).getText();
//        String projectKey = webDriver.findElement(By.cssSelector("#summary-body > div > div.aui-item.project-meta-column > dl > dd:nth-child(4)")).getText();
//
//        assertEquals(expectedProjectName, projectName);
//        assertEquals(expectedProjectKey, projectKey);
//    }

}


