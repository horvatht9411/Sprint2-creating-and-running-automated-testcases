import com.codecool.App;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCreateIssue {

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
    @DisplayName("Create new issue successfully")
    public void createNewIssue() throws InterruptedException {
        webDriver.findElement(By.id("create_link")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-issue-dialog")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.click();
        project.sendKeys(Keys.BACK_SPACE);
        project.sendKeys("Main Testing Project");
        Thread.sleep(1000);
        project.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        String value = UUID.randomUUID().toString();
        webDriver.findElement(By.id("summary")).sendKeys(value);
        webDriver.findElement(By.id("create-issue-submit")).click();
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
    public void createNewIssueII() throws InterruptedException {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("Main Testing Project");
        Thread.sleep(1000);
        project.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        project.sendKeys("Story");
        Thread.sleep(1000);
        project.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
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
    public void cancel() throws InterruptedException {
        webDriver.findElement(By.id("create_link")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create-issue-dialog")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.click();
        project.sendKeys(Keys.BACK_SPACE);
        project.sendKeys("Main Testing Project");
        Thread.sleep(1000);
        project.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        String value = UUID.randomUUID().toString();
        webDriver.findElement(By.id("summary")).sendKeys(value);
        webDriver.findElement(By.cssSelector("#create-issue-dialog > footer > div > div > button")).click();
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
    public void jetiBugIssues() throws InterruptedException {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("JETI");
        Thread.sleep(1000);
        project.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        type.click();
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
    public void jetiTaskIssues() throws InterruptedException {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("JETI");
        Thread.sleep(1000);
        project.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        type.click();
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
    public void jetiStoryIssues() throws InterruptedException {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("JETI");
        Thread.sleep(1000);
        project.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        type.click();
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
    public void toucanBugIssues() throws InterruptedException {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("TOUCAN");
        Thread.sleep(1000);
        project.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        type.click();
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
    public void toucanTaskIssues() throws InterruptedException {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("TOUCAN");
        Thread.sleep(1000);
        project.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        type.click();
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
    public void toucanStoryIssues() throws InterruptedException {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("TOUCAN");
        Thread.sleep(1000);
        project.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        type.click();
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
    public void coalaBugIssues() throws InterruptedException {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("COALA");
        Thread.sleep(1000);
        project.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        type.click();
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
    public void coalaTaskIssues() throws InterruptedException {
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        WebElement project = webDriver.findElement(By.id("project-field"));
        project.sendKeys("COALA");
        Thread.sleep(1000);
        project.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        type.click();
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
        Thread.sleep(1000);
        project.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        WebElement type = webDriver.findElement(By.id("issuetype-field"));
        type.click();
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
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        String expectedIssueId = "JETI-1";
        String issueId = webDriver.findElement(By.id("key-val")).getText();
        assertEquals(expectedIssueId, issueId);
        webDriver.findElement(By.id("opsbar-operations_more")).click();
        String expected = "Create sub-task";
        String result = webDriver.findElement(By.cssSelector("#create-subtask > a > span")).getText();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Create subtask for COALA project")
    public void createCoalaSubtask() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/COALA-1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        String expectedIssueId = "COALA-1";
        String issueId = webDriver.findElement(By.id("key-val")).getText();
        assertEquals(expectedIssueId, issueId);
        webDriver.findElement(By.id("opsbar-operations_more")).click();
        String expected = "Create sub-task";
        String result = webDriver.findElement(By.cssSelector("#create-subtask > a > span")).getText();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Create subtask for TOUCAN project")
    public void createToucanSubtask() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/TOUCAN-1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        String expectedIssueId = "TOUCAN-1";
        String issueId = webDriver.findElement(By.id("key-val")).getText();
        assertEquals(expectedIssueId, issueId);
        webDriver.findElement(By.id("opsbar-operations_more")).click();
        String expected = "Create sub-task";
        String result = webDriver.findElement(By.cssSelector("#create-subtask > a > span")).getText();
        assertEquals(expected, result);
    }
}


