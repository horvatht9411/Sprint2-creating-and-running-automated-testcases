package com.codecool.tests.issues;

import com.codecool.TestResultLoggerExtension;
import com.codecool.Util;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(TestResultLoggerExtension.class)
public class TestCreateIssue {

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
    @DisplayName("Create new issue successfully")
    public void createNewIssue() throws InterruptedException {
        webDriver.findElement(By.id("create_link")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-issue-dialog")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.click();
        project.sendKeys(Keys.BACK_SPACE);
        project.sendKeys("Main Testing Project");
        project.sendKeys(Keys.ENTER);
        String value = UUID.randomUUID().toString();
        try {
            webDriver.findElement(By.id("summary")).sendKeys(value);
            webDriver.findElement(By.id("create-issue-submit")).click();
        } catch (StaleElementReferenceException | ElementNotInteractableException e){
            webDriver.findElement(By.id("summary")).sendKeys(value);
            webDriver.findElement(By.id("create-issue-submit")).click();
        }
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#aui-flag-container > div > div > a")));
        webDriver.findElement(By.cssSelector("#aui-flag-container > div > div > a")).click();
        String result = webDriver.findElement(By.id("summary-val")).getText();
        assertEquals(value, result);
        webDriver.findElement(By.id("opsbar-operations_more")).click();
        webDriver.findElement(By.cssSelector("#delete-issue > a > span")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-issue-dialog")));
        webDriver.findElement(By.id("delete-issue-submit")).click();
    }

    @Test
    @DisplayName("Create new issue in a new tab successfully")
    public void createNewIssueII() {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));

        try {
            WebElement project = webDriver.findElement(By.id("project-field"));
            project.sendKeys("Main Testing Project");
            project.sendKeys(Keys.ENTER);
            WebElement type = webDriver.findElement(By.id("issuetype-field"));
            project.sendKeys("Story");
            project.sendKeys(Keys.ENTER);
        }catch (StaleElementReferenceException | ElementNotInteractableException e){
            WebElement project = webDriver.findElement(By.id("project-field"));
            project.sendKeys("Main Testing Project");
            project.sendKeys(Keys.ENTER);
            WebElement type = webDriver.findElement(By.id("issuetype-field"));
            project.sendKeys("Story");
            project.sendKeys(Keys.ENTER);
        }
        webDriver.findElement(By.id("issue-create-submit")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("summary")));
        String value = UUID.randomUUID().toString();
        webDriver.findElement(By.id("summary")).sendKeys(value);
        webDriver.findElement(By.id("issue-create-submit")).click();
        String result = webDriver.findElement(By.id("summary-val")).getText();
        assertEquals(value, result);
        webDriver.findElement(By.id("opsbar-operations_more")).click();
        webDriver.findElement(By.cssSelector("#delete-issue > a > span")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-issue-dialog")));
        webDriver.findElement(By.id("delete-issue-submit")).click();
    }


    @Test
    @DisplayName("Create new issue with blank mandatory fields")
    public void blankFields() {
        webDriver.findElement(By.id("create_link")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-issue-dialog")));
        webDriver.findElement(By.id("create-issue-submit")).click();
        String expectedErrorMessage = "You must specify a summary of the issue.";
        String errorMessage = webDriver.findElement(By.cssSelector("#dialog-form > div > div.content > div:nth-child(1) > div")).getText();
        assertEquals(expectedErrorMessage, errorMessage);
        webDriver.findElement(By.cssSelector("#create-issue-dialog > footer > div > div > button")).click();
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }

    @Test
    @DisplayName("Cancel creating new issue")
    public void cancel() {
        webDriver.findElement(By.id("create_link")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-issue-dialog")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.click();
        project.sendKeys(Keys.BACK_SPACE);
        project.sendKeys("Main Testing Project");
        project.sendKeys(Keys.ENTER);
        String value = UUID.randomUUID().toString();
        try {
            webDriver.findElement(By.id("summary")).sendKeys(value);
            webDriver.findElement(By.cssSelector("#create-issue-dialog > footer > div > div > button")).click();
        }catch (StaleElementReferenceException | ElementNotInteractableException e){
            webDriver.findElement(By.id("summary")).sendKeys(value);
            webDriver.findElement(By.cssSelector("#create-issue-dialog > footer > div > div > button")).click();
        }
        webDriverWait.until(ExpectedConditions.alertIsPresent());
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
        String link = "https://jira-auto.codecool.metastage.net/browse/MTP-2459?jql=summary%20~%20%22" + value + "%22";
        webDriver.get(link);
        String expectedErrorMessage = "No issues were found to match your search";
        String errorMessage = webDriver.findElement(By.cssSelector("#main > div > div.navigator-group > div > div > div > div > div > div > h2")).getText();
        assertEquals(expectedErrorMessage, errorMessage);
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
