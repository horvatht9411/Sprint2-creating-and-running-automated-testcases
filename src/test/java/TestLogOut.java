import com.codecool.App;
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
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLogOut {

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
    }


    private void loginSuccessfully() {
        webDriver.findElement(By.id("login-form-username")).sendKeys(appProps.getProperty("username"));
        webDriver.findElement(By.id("login-form-password")).sendKeys(appProps.getProperty("password"));
        webDriver.findElement(By.id("login")).click();
    }

    @AfterEach
    void close() {
        webDriver.quit();
    }

    @Test
    @DisplayName("Successfully log out")
    public void logout() {
        loginSuccessfully();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-details-user-fullname")));
        webDriver.findElement(By.id("header-details-user-fullname")).click();
        webDriver.findElement(By.id("log_out")).click();
        String loginText = webDriver.findElement(By.cssSelector("#user-options > a")).getText();
        assertEquals("Log In", loginText);
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/ViewProfile.jspa");
        String expectedMessage =  "You must log in to access this page.";
        String errorMessage = webDriver.findElement(By.cssSelector("#login-form > div.form-body > div.aui-message.aui-message-warning > p:nth-child(1)")).getText();
        assertEquals(expectedMessage, errorMessage);
    }


}


