import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
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
    @DisplayName("Edit Issue Successfully")
    public void editIssueSuccessfully() throws InterruptedException {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/MTP-2507");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        String expectedIssueId = "MTP-2507";
        webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-issue-dialog")));
        String issueId = webDriver.findElement(By.cssSelector("#edit-issue-dialog > header > h2\n")).getText();
        assertTrue(issueId.contains(expectedIssueId));
        WebElement summaryField = webDriver.findElement(By.id("summary"));
        summaryField.click();
        summaryField.clear();
        String newSummary = UUID.randomUUID().toString();
        summaryField.sendKeys(newSummary);
        webDriver.findElement(By.id("edit-issue-submit")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        try {
            String editedText = webDriver.findElement(By.id("summary-val")).getText();
            assertEquals(newSummary, editedText);
        }catch (StaleElementReferenceException | ElementNotInteractableException e){
            String editedText = webDriver.findElement(By.id("summary-val")).getText();
            assertEquals(newSummary, editedText);
        }
        String id = webDriver.findElement(By.id("key-val")).getText();
        assertEquals(expectedIssueId, id);
    }

    @Test
    @DisplayName("Edit Issue With Blank Fields")
    public void editIssueWithBlankFields() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/MTP-2507");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        String expectedIssueId = "MTP-2507";
        webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-issue-dialog")));
        String issueId = webDriver.findElement(By.cssSelector("#edit-issue-dialog > header > h2\n")).getText();
        assertTrue(issueId.contains(expectedIssueId));
        WebElement summaryField = webDriver.findElement(By.id("summary"));
        summaryField.click();
        summaryField.clear();
        webDriver.findElement(By.id("edit-issue-submit")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#dialog-form > div > div > div:nth-child(1) > div")));
        String errorMessage = webDriver.findElement(By.cssSelector("#dialog-form > div > div > div:nth-child(1) > div")).getText();
        String expectedErrorMessage = "You must specify a summary of the issue.";
        assertEquals(errorMessage, expectedErrorMessage);
        webDriver.findElement(By.cssSelector("#edit-issue-dialog > footer > div > div > button")).click();
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }

    @Test
    @DisplayName("Cancel Issue Screen Before Updating")
    public void cancelIssueScreenBeforeUpdating() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/MTP-2507");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        String expectedIssueId = "MTP-2507";
        webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-issue-dialog")));
        String issueId = webDriver.findElement(By.cssSelector("#edit-issue-dialog > header > h2\n")).getText();
        assertTrue(issueId.contains(expectedIssueId));
        WebElement summaryField = webDriver.findElement(By.id("summary"));
        summaryField.click();
        summaryField.clear();
        summaryField.sendKeys("Edited but canceling");
        webDriver.findElement(By.cssSelector("#edit-issue-dialog > footer > div > div > button")).click();
        webDriverWait.until(ExpectedConditions.alertIsPresent());
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        String id = webDriver.findElement(By.id("key-val")).getText();
        String editedText = webDriver.findElement(By.id("summary-val")).getText();
        assertEquals(expectedIssueId, id);
        assertNotEquals(editedText, "Edited but canceling");
    }

    @Test
    @DisplayName("Edit issues 1 for TOUCAN project")
    public void editToucanIssues1() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/TOUCAN-1");
        try {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
            assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
        } catch (TimeoutException | NoSuchElementException e) {
            Assertions.fail("Exception " + e);
        }
    }

    @Test
    @DisplayName("Edit issues 2 for TOUCAN project")
    public void editToucanIssues2() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/TOUCAN-2");
        try {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
            assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
        } catch (TimeoutException | NoSuchElementException e) {
            Assertions.fail("Exception " + e);
        }
    }

    @Test
    @DisplayName("Edit issues 3 for TOUCAN project")
    public void editToucanIssues3() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/TOUCAN-3");
        try {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
            assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
        } catch (TimeoutException | NoSuchElementException e) {
            Assertions.fail("Exception " + e);
        }
    }

    @Test
    @DisplayName("Edit issues 1 for COALA project")
    public void editCoalaIssues1() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/COALA-1");
        try {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
            assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
        } catch (TimeoutException | NoSuchElementException e) {
            Assertions.fail("Exception " + e);
        }
    }

    @Test
    @DisplayName("Edit issues 2 for COALA project")
    public void editCoalaIssues2() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/COALA-2");
        try {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
            assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
        } catch (TimeoutException | NoSuchElementException e) {
            Assertions.fail("Exception " + e);
        }
    }

    @Test
    @DisplayName("Edit issues 3 for COALA project")
    public void editCoalaIssues3() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/COALA-3");
        try {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
            assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
        } catch (TimeoutException | NoSuchElementException e) {
            Assertions.fail("Exception " + e);
        }
    }

    @Test
    @DisplayName("edit issues 1 for JETI project")
    public void editJetiIssues1() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/JETI-1");
        try {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
            assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
        } catch (TimeoutException | NoSuchElementException e) {
            Assertions.fail("Exception " + e);
        }
    }

    @Test
    @DisplayName("Edit issues 2 for JETI project")
    public void editJetiIssues2() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/JETI-2");
        try {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
            assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
        } catch (TimeoutException | NoSuchElementException e) {
            Assertions.fail("Exception " + e);
        }
    }

    @Test
    @DisplayName("Edit issues 3 for JETI project")
    public void editJetiIssues3() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/JETI-3");
        try {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
            assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
        } catch (TimeoutException | NoSuchElementException e) {
            Assertions.fail("Exception " + e);
        }
    }
}
