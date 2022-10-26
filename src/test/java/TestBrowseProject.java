import com.codecool.App;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBrowseProject {

    Logger logger = LoggerFactory.getLogger(App.class);
    WebDriver webDriver;
    WebElement loginButton;
    WebElement userName;
    WebElement password;
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
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sidebar > div > div.aui-sidebar-body > div > div > div:nth-child(2) > h1 > div > div > a")));
        String expectedProjectName = "TOUCAN project";
        String expectedProjectKey = "TOUCAN";
        String projectName = webDriver.findElement(By.cssSelector("#sidebar > div > div.aui-sidebar-body > div > div > div:nth-child(2) > h1 > div > div > a")).getText();
        String projectKey = webDriver.findElement(By.cssSelector("#summary-body > div > div.aui-item.project-meta-column > dl > dd:nth-child(4)")).getText();

        assertEquals(expectedProjectName, projectName);
        assertEquals(expectedProjectKey, projectKey);
    }
    @Test
    @DisplayName("Browse COALA project")
    public void browseCoalaProject() {
        webDriver.get("https://jira-auto.codecool.metastage.net/projects/COALA/summary");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sidebar > div > div.aui-sidebar-body > div > div > div:nth-child(2) > h1 > div > div > a")));
        String expectedProjectName = "COALA project";
        String expectedProjectKey = "COALA";
        String projectName = webDriver.findElement(By.cssSelector("#sidebar > div > div.aui-sidebar-body > div > div > div:nth-child(2) > h1 > div > div > a")).getText();
        String projectKey = webDriver.findElement(By.cssSelector("#summary-body > div > div.aui-item.project-meta-column > dl > dd:nth-child(4)")).getText();

        assertEquals(expectedProjectName, projectName);
        assertEquals(expectedProjectKey, projectKey);
    }

    @Test
    @DisplayName("Browse JETI project")
    public void browseJetiProject() {
        webDriver.get("https://jira-auto.codecool.metastage.net/projects/JETI/summary");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sidebar > div > div.aui-sidebar-body > div > div > div:nth-child(2) > h1 > div > div > a")));
        String expectedProjectName = "JETI project";
        String expectedProjectKey = "JETI";
        String projectName = webDriver.findElement(By.cssSelector("#sidebar > div > div.aui-sidebar-body > div > div > div:nth-child(2) > h1 > div > div > a")).getText();
        String projectKey = webDriver.findElement(By.cssSelector("#summary-body > div > div.aui-item.project-meta-column > dl > dd:nth-child(4)")).getText();

        assertEquals(expectedProjectName, projectName);
        assertEquals(expectedProjectKey, projectKey);
    }

}

