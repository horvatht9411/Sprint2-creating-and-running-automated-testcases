package com.codecool.tests.issues;

import com.codecool.TestResultLoggerExtension;
import com.codecool.Util;
import com.codecool.pages.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(TestResultLoggerExtension.class)
public class TestCreateIssue {
    WebDriver webDriver;
    WebDriverWait webDriverWait;
    DashboardPage dashboardPage;
    IssueDisplayPage issueDisplayPage;
    CreateIssueModalPage createIssueModalPage;
    CreateIssueLinkPage createIssueLinkPage;
    String url = "https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa";

    @BeforeEach
    void init() throws IOException {
        webDriver = Util.setup(url);
        webDriverWait = Util.initWebdriverWait(webDriver);
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.loginSuccessfully();
        dashboardPage = new DashboardPage(webDriver);
        webDriverWait.until(ExpectedConditions.visibilityOf(dashboardPage.profileMenu));
        issueDisplayPage = new IssueDisplayPage(webDriver);
        createIssueModalPage = new CreateIssueModalPage(webDriver);
        createIssueLinkPage = new CreateIssueLinkPage(webDriver);
    }

    @AfterEach
    void close() {
        webDriver.quit();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/createIssue.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Create new issue successfully")
    public void createNewIssue(String projectName) throws InterruptedException {
        dashboardPage.createNewIssue();
        webDriverWait.until(ExpectedConditions.visibilityOf(createIssueModalPage.issueModal));
        createIssueModalPage.fillUpProjectName(projectName);
        createIssueModalPage.fillUpSummary();
        String expectedSummaryText = createIssueModalPage.getSummaryText();
        createIssueModalPage.submitNewIssue();

        webDriverWait.until(ExpectedConditions.visibilityOf(createIssueModalPage.newIssueLink));
        createIssueModalPage.clickOnNewIssueLink();
        String actualSummaryText = issueDisplayPage.getSummaryText();
        assertEquals(expectedSummaryText, actualSummaryText);

        createIssueModalPage.deleteNewlyCreatedIssue(webDriverWait);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/createIssue.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Create new issue in a new tab successfully")
    public void createNewIssueII(String projectName, String issueType) {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        createIssueLinkPage.fillUpProjectName(webDriverWait, projectName);
        createIssueLinkPage.fillUpIssueType(issueType);
        createIssueLinkPage.clickNextButton();

        createIssueLinkPage.fillUpSummaryField(webDriverWait);
        String expectedSummaryText = createIssueLinkPage.getSummaryText();
        createIssueLinkPage.submitNewIssue();

        webDriverWait.until(ExpectedConditions.visibilityOf(issueDisplayPage.summary));
        String actualSummaryText = issueDisplayPage.getSummaryText();
        assertEquals(expectedSummaryText, actualSummaryText);

        createIssueModalPage.deleteNewlyCreatedIssue(webDriverWait);
    }

    @Test
    @DisplayName("Create new issue with blank mandatory fields")
    public void blankFields() {
        dashboardPage.createNewIssue();
        webDriverWait.until(ExpectedConditions.visibilityOf(createIssueModalPage.issueModal));
        createIssueModalPage.submitNewIssue();

        String expectedErrorMessage = "You must specify a summary of the issue.";
        String actualErrorMessage = createIssueModalPage.getWarningMessageToFillSummary();
        assertEquals(expectedErrorMessage, actualErrorMessage);

        createIssueModalPage.closeCreateModal(webDriverWait);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/createIssue.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Cancel creating new issue")
    public void cancel(String projectName) {
        dashboardPage.createNewIssue();
        webDriverWait.until(ExpectedConditions.visibilityOf(createIssueModalPage.issueModal));
        createIssueModalPage.fillUpProjectName(projectName);
        createIssueModalPage.fillUpSummary();
        String expectedSummaryText = createIssueModalPage.getSummaryText();
        createIssueModalPage.closeCreateModal(webDriverWait);

        webDriver.get(String.format("https://jira-auto.codecool.metastage.net/browse/MTP-2459?jql=summary%20~%20%22%s%22", expectedSummaryText));
        String expectedErrorMessage = "No issues were found to match your search";
        String actualErrorMessage = issueDisplayPage.getNoIssueErrorMessage();
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    @DisplayName("Check bug issue type for JETI")
    public void jetiBugIssues() {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("JETI");
        project.sendKeys(Keys.ENTER);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        try {
            type.click();
        } catch (ElementClickInterceptedException e) {
            Assertions.fail("Exception " + e);
        }
        type.sendKeys(Keys.BACK_SPACE);
        String expected = "Bug";
        type.sendKeys(expected);
        type.sendKeys(Keys.ENTER);
        webDriver.findElement(By.id("issue-create-submit")).click();
        String result = webDriver.findElement(By.id("issue-create-issue-type")).getText();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Check task issue type for JETI")
    public void jetiTaskIssues() {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("JETI");
        project.sendKeys(Keys.ENTER);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        try {
            type.click();
        } catch (ElementClickInterceptedException e) {
            Assertions.fail("Exception " + e);
        }
        type.sendKeys(Keys.BACK_SPACE);
        String expected = "Task";
        type.sendKeys(expected);
        type.sendKeys(Keys.ENTER);
        webDriver.findElement(By.id("issue-create-submit")).click();
        String result = webDriver.findElement(By.id("issue-create-issue-type")).getText();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Check story issue type for JETI")
    public void jetiStoryIssues() {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("JETI");
        project.sendKeys(Keys.ENTER);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        try {
            type.click();
        } catch (ElementClickInterceptedException e) {
            Assertions.fail("Exception " + e);
        }
        type.sendKeys(Keys.BACK_SPACE);
        String expected = "Story";
        type.sendKeys(expected);
        type.sendKeys(Keys.ENTER);
        webDriver.findElement(By.id("issue-create-submit")).click();
        String result = webDriver.findElement(By.id("issue-create-issue-type")).getText();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Check bug issue type for TOUCAN")
    public void toucanBugIssues() {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("TOUCAN");
        project.sendKeys(Keys.ENTER);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        try {
            type.click();
        } catch (ElementClickInterceptedException e) {
            Assertions.fail("Exception " + e);
        }
        type.sendKeys(Keys.BACK_SPACE);
        String expected = "Bug";
        type.sendKeys(expected);
        type.sendKeys(Keys.ENTER);
        webDriver.findElement(By.id("issue-create-submit")).click();
        String result = webDriver.findElement(By.id("issue-create-issue-type")).getText();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Check task issue type for TOUCAN")
    public void toucanTaskIssues() {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("TOUCAN");
        project.sendKeys(Keys.ENTER);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        try {
            type.click();
        } catch (ElementClickInterceptedException e) {
            Assertions.fail("Exception " + e);
        }
        type.sendKeys(Keys.BACK_SPACE);
        String expected = "Task";
        type.sendKeys(expected);
        type.sendKeys(Keys.ENTER);
        webDriver.findElement(By.id("issue-create-submit")).click();
        String result = webDriver.findElement(By.id("issue-create-issue-type")).getText();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Check story issue type for TOUCAN")
    public void toucanStoryIssues() {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("TOUCAN");
        project.sendKeys(Keys.ENTER);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        try {
            type.click();
        } catch (ElementClickInterceptedException e) {
            Assertions.fail("Exception " + e);
        }
        type.sendKeys(Keys.BACK_SPACE);
        String expected = "Story";
        type.sendKeys(expected);
        type.sendKeys(Keys.ENTER);
        webDriver.findElement(By.id("issue-create-submit")).click();
        String result = webDriver.findElement(By.id("issue-create-issue-type")).getText();
        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Check bug issue type for COALA")
    public void coalaBugIssues() {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("COALA");
        project.sendKeys(Keys.ENTER);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        try {
            type.click();
        } catch (ElementClickInterceptedException e) {
            Assertions.fail("Exception " + e);
        }
        type.sendKeys(Keys.BACK_SPACE);
        String expected = "Bug";
        type.sendKeys(expected);
        type.sendKeys(Keys.ENTER);
        webDriver.findElement(By.id("issue-create-submit")).click();
        String result = webDriver.findElement(By.id("issue-create-issue-type")).getText();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Check task issue type for COALA")
    public void coalaTaskIssues() {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("COALA");
        project.sendKeys(Keys.ENTER);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        try {
            type.click();
        } catch (ElementClickInterceptedException e) {
            Assertions.fail("Exception " + e);
        }
        type.sendKeys(Keys.BACK_SPACE);
        String expected = "Task";
        type.sendKeys(expected);
        type.sendKeys(Keys.ENTER);
        webDriver.findElement(By.id("issue-create-submit")).click();
        String result = webDriver.findElement(By.id("issue-create-issue-type")).getText();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Check story issue type for COALA")
    public void coalaStoryIssues() throws InterruptedException {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("COALA");
        project.sendKeys(Keys.ENTER);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        try {
            type.click();
        } catch (ElementClickInterceptedException e) {
            Assertions.fail("Exception " + e);
        }
        type.sendKeys(Keys.BACK_SPACE);
        String expected = "Story";
        type.sendKeys(expected);
        type.sendKeys(Keys.ENTER);
        webDriver.findElement(By.id("issue-create-submit")).click();
        String result = webDriver.findElement(By.id("issue-create-issue-type")).getText();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Create subtask for JETI project")
    public void createJetiSubtask() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/JETI-1");
        try {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
            String expectedIssueId = "JETI-1";
            String issueId = webDriver.findElement(By.id("key-val")).getText();
            assertEquals(expectedIssueId, issueId);
            webDriver.findElement(By.id("opsbar-operations_more")).click();
            String expected = "Create sub-task";
            String result = webDriver.findElement(By.cssSelector("#create-subtask > a > span")).getText();
            assertEquals(expected, result);
        } catch (NoSuchElementException | TimeoutException e) {
            Assertions.fail("Exception " + e);
        }

    }

    @Test
    @DisplayName("Create subtask for COALA project")
    public void createCoalaSubtask() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/COALA-1");
        try {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
            String expectedIssueId = "COALA-1";
            String issueId = webDriver.findElement(By.id("key-val")).getText();
            assertEquals(expectedIssueId, issueId);
            webDriver.findElement(By.id("opsbar-operations_more")).click();
            String expected = "Create sub-task";
            String result = webDriver.findElement(By.cssSelector("#create-subtask > a > span")).getText();
            assertEquals(expected, result);
        } catch (NoSuchElementException | TimeoutException e) {
            Assertions.fail("Exception " + e);
        }
    }

    @Test
    @DisplayName("Create subtask for TOUCAN project")
    public void createToucanSubtask() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/TOUCAN-1");
        try {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
            String expectedIssueId = "TOUCAN-1";
            String issueId = webDriver.findElement(By.id("key-val")).getText();
            assertEquals(expectedIssueId, issueId);
            webDriver.findElement(By.id("opsbar-operations_more")).click();
            String expected = "Create sub-task";
            String result = webDriver.findElement(By.cssSelector("#create-subtask > a > span")).getText();
            assertEquals(expected, result);
        } catch (NoSuchElementException | TimeoutException e) {
            Assertions.fail("Exception " + e);
        }

    }
}
