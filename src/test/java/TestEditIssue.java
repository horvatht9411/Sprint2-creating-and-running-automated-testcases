import com.codecool.App;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEditIssue {

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
        summaryField.sendKeys(Keys.BACK_SPACE, "B");
        webDriver.findElement(By.id("edit-issue-submit")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        String id = webDriver.findElement(By.id("key-val")).getText();
        String editedText = webDriver.findElement(By.id("summary-val")).getText();
        assertEquals(expectedIssueId, id);
        assertEquals("Test Edit B", editedText);
        webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-issue-dialog")));
        WebElement summaryField2 =  webDriver.findElement(By.id("summary"));
        summaryField2.click();
        summaryField2.sendKeys(Keys.BACK_SPACE, "A");
        webDriver.findElement(By.id("edit-issue-submit")).click();
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
        summaryField.sendKeys(Keys.BACK_SPACE, "B");
        webDriver.findElement(By.cssSelector("#edit-issue-dialog > footer > div > div > button")).click();
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        String id = webDriver.findElement(By.id("key-val")).getText();
        String editedText = webDriver.findElement(By.id("summary-val")).getText();
        assertEquals(expectedIssueId, id);
        assertEquals("Test Edit A", editedText);
    }


    @Test
    @DisplayName("Edit issues 1 for TOUCAN project")
    public void editToucanIssues1() {
            webDriver.get("https://jira-auto.codecool.metastage.net/browse/TOUCAN-1");
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
            assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
    }

    @Test
    @DisplayName("Edit issues 2 for TOUCAN project")
    public void editToucanIssues2() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/TOUCAN-2");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
    }

    @Test
    @DisplayName("Edit issues 3 for TOUCAN project")
    public void editToucanIssues3() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/TOUCAN-3");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
    }

    @Test
    @DisplayName("Edit issues 1 for COALA project")
    public void editCoalaIssues1() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/COALA-1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
    }

    @Test
    @DisplayName("Edit issues 2 for COALA project")
    public void editCoalaIssues2() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/COALA-2");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
    }

    @Test
    @DisplayName("Edit issues 3 for COALA project")
    public void editCoalaIssues3() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/COALA-3");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
    }

    @Test
    @DisplayName("edit issues 1 for JETI project")
    public void editJetiIssues1() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/JETI-1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
    }

    @Test
    @DisplayName("Edit issues 2 for JETI project")
    public void editJetiIssues2() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/JETI-2");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
    }

    @Test
    @DisplayName("Edit issues 3 for JETI project")
    public void editJetiIssues3() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/JETI-3");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        assertTrue(webDriver.findElement(By.cssSelector("#edit-issue > span.trigger-label")).isDisplayed());
    }

}


