import com.codecool.App;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLogin {

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
        userName = webDriver.findElement(By.id("login-form-username"));
        password = webDriver.findElement(By.id("login-form-password"));
        loginButton = webDriver.findElement(By.id("login"));
    }

    void setupDifferentLink() throws IOException {
        webDriver.close();
        webDriver = Util.setup("https://jira-auto.codecool.metastage.net/login.jsp?");
        webDriverWait = Util.initWebdriverWait(webDriver);
        appProps = Util.read();
        userName = webDriver.findElement(By.id("login-form-username"));
        password = webDriver.findElement(By.id("login-form-password"));
        loginButton = webDriver.findElement(By.id("login-form-submit"));
    }

    private void loginSuccessfully() {
        webDriver.findElement(By.id("login-form-username")).sendKeys(appProps.getProperty("username"));
        webDriver.findElement(By.id("login-form-password")).sendKeys(appProps.getProperty("password"));
        webDriver.findElement(By.id("login")).click();
    }

    private String checkValidUsername() {
        webDriver.findElement(By.id("view_profile")).click();
        String name = webDriver.findElement(By.id("up-d-username")).getText();
        return name;
    }

    private String checkLogOutButtonVisibility() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-details-user-fullname")));
        webDriver.findElement(By.id("header-details-user-fullname")).click();
        String logOut = webDriver.findElement(By.id("log_out")).getText();
        return logOut;
    }

    private String getErrorMessage() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("usernameerror")));
        String errorMessage = webDriver.findElement(By.id("usernameerror")).getText();
        return errorMessage;
    }

    @AfterEach
    void close() {
        webDriver.quit();
    }

    @Test
    @DisplayName("Correct username and password")
    public void correctCredential() {
        loginSuccessfully();
        String logOut = checkLogOutButtonVisibility();
        assertEquals("Log Out", logOut);
        String name = checkValidUsername();
        assertEquals(appProps.getProperty("username"), name);
    }

    @Test
    @DisplayName("Empty Credentials")
    public void emptyCredentials() {
        userName.clear();
        password.clear();
        loginButton.click();
        String errorMessage = getErrorMessage();
        assertEquals("Sorry, your username and password are incorrect - please try again.", errorMessage);

    }


    @Test
    @DisplayName("Wrong password")
    public void wrongPassword() {
        userName.sendKeys(appProps.getProperty("username"));
        password.sendKeys("wrongPassword");
        loginButton.click();
        String errorMessage = getErrorMessage();
        assertEquals("Sorry, your username and password are incorrect - please try again.", errorMessage);
        loginSuccessfully();
    }

    @Test
    @DisplayName("Wrong Username")
    public void wrongUsername() {
        userName.sendKeys("wrongUsername");
        password.sendKeys("wrongPassword");
        loginButton.click();
        String errorMessage = getErrorMessage();
        assertEquals("Sorry, your username and password are incorrect - please try again.", errorMessage);
    }

    @Test
    @DisplayName("Login with Enter key")
    public void loginEnter() {
        userName.sendKeys(appProps.getProperty("username"));
        password.sendKeys(appProps.getProperty("password"));
        password.sendKeys(Keys.ENTER);
        String logOut = checkLogOutButtonVisibility();
        assertEquals("Log Out", logOut);
        String name = checkValidUsername();
        assertEquals(appProps.getProperty("username"), name);
    }

    @Test
    @DisplayName("Correct username and password different link")
    public void correctCredentialII() throws IOException {
        setupDifferentLink();
        userName.sendKeys(appProps.getProperty("username"));
        password.sendKeys(appProps.getProperty("password"));
        loginButton.click();
        String logOut = checkLogOutButtonVisibility();
        assertEquals("Log Out", logOut);
        String name = checkValidUsername();
        assertEquals(appProps.getProperty("username"), name);
    }


}


