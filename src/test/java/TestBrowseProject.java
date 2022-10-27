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
    @DisplayName("Browse existing project")
    public void browseExistingProject() {
        webDriver.get("https://jira-auto.codecool.metastage.net/projects/MTP/summary");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("summary-subnav-title")));
        String expectedProjectKey = "MTP";
        String projectKey = webDriver.findElement(By.cssSelector("#summary-body > div > div.aui-item.project-meta-column > dl > dd:nth-child(6)")).getText();

        assertEquals(expectedProjectKey, projectKey);
    }

    @Test
    @DisplayName("Browse non existing project")
    public void browseNonExistingProject() {
        webDriver.get("https://jira-auto.codecool.metastage.net/projects/ANIMAL/summary");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("main")));
        String expectedMessage = "You can't view this project";
        String message = webDriver.findElement(By.cssSelector("#main > h1")).getText();

        assertEquals(expectedMessage, message);
    }

    @Test
    @DisplayName("Browse existing project without permission")
    public void browseProjectWithoutPermission() {
        webDriver.get("https://jira-auto.codecool.metastage.net/projects/MTP-1/summary");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("main")));
        String expectedMessage = "You can't view this project";
        String message = webDriver.findElement(By.cssSelector("#main > h1")).getText();

        assertEquals(expectedMessage, message);
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
