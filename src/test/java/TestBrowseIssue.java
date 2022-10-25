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


    @Test
    @DisplayName("Browse issues 1 for TOUCAN project")
    public void browseToucanIssues1() {
            webDriver.get("https://jira-auto.codecool.metastage.net/browse/TOUCAN-1");
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
            String expectedIssueId = "TOUCAN-1";
            String issueId = webDriver.findElement(By.id("key-val")).getText();

            assertEquals(expectedIssueId, issueId);
    }

    @Test
    @DisplayName("Browse issues 2 for TOUCAN project")
    public void browseToucanIssues2() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/TOUCAN-2");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        String expectedIssueId = "TOUCAN-2";
        String issueId = webDriver.findElement(By.id("key-val")).getText();

        assertEquals(expectedIssueId, issueId);
    }

    @Test
    @DisplayName("Browse issues 3 for TOUCAN project")
    public void browseToucanIssues3() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/TOUCAN-3");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        String expectedIssueId = "TOUCAN-3";
        String issueId = webDriver.findElement(By.id("key-val")).getText();

        assertEquals(expectedIssueId, issueId);
    }

    @Test
    @DisplayName("Browse issues 1 for COALA project")
    public void browseCoalaIssues1() {
            webDriver.get("https://jira-auto.codecool.metastage.net/browse/COALA-1");
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
            String expectedIssueId = "COALA-1";
            String issueId = webDriver.findElement(By.id("key-val")).getText();

            assertEquals(expectedIssueId, issueId);
    }

    @Test
    @DisplayName("Browse issues 2 for COALA project")
    public void browseCoalaIssues2() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/COALA-2");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        String expectedIssueId = "COALA-2";
        String issueId = webDriver.findElement(By.id("key-val")).getText();

        assertEquals(expectedIssueId, issueId);
    }

    @Test
    @DisplayName("Browse issues 3 for COALA project")
    public void browseCoalaIssues3() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/COALA-3");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        String expectedIssueId = "COALA-3";
        String issueId = webDriver.findElement(By.id("key-val")).getText();

        assertEquals(expectedIssueId, issueId);
    }

    @Test
    @DisplayName("Browse issues 1 for JETI project")
    public void browseJetiIssues1() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/JETI-1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        String expectedIssueId = "JETI-1";
        String issueId = webDriver.findElement(By.id("key-val")).getText();

        assertEquals(expectedIssueId, issueId);
    }

    @Test
    @DisplayName("Browse issues 2 for JETI project")
    public void browseJetiIssues2() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/JETI-2");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        String expectedIssueId = "JETI-2";
        String issueId = webDriver.findElement(By.id("key-val")).getText();

        assertEquals(expectedIssueId, issueId);
    }

    @Test
    @DisplayName("Browse issues 3 for JETI project")
    public void browseJetiIssues3() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/JETI-3");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        String expectedIssueId = "JETI-3";
        String issueId = webDriver.findElement(By.id("key-val")).getText();

        assertEquals(expectedIssueId, issueId);
    }

}


