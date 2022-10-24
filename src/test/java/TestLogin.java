import com.codecool.App;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

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
        WebDriverManager.chromedriver().setup();
        webDriver = Util.visibilitySetup();
        webDriver.manage().window().maximize();
        webDriver.get(url);
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        appProps = Util.read();
        userName = webDriver.findElement(By.id("login-form-username"));
        password = webDriver.findElement(By.id("login-form-password"));
        loginButton = webDriver.findElement(By.id("login"));
    }

    @AfterEach
    void close() {
        webDriver.close();
    }

    @Test
    @DisplayName("Correct username and password")
    public void correctCredential(){
        loginSuccessfully();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-details-user-fullname")));
        webDriver.findElement(By.id("header-details-user-fullname")).click();
        String logOut = webDriver.findElement(By.id("log_out")).getText();
        assertEquals("Log Out", logOut);
        webDriver.findElement(By.id("view_profile")).click();
        String name = webDriver.findElement(By.id("up-d-username")).getText();
        assertEquals(appProps.getProperty("username"), name);
    }

    @Test
    @DisplayName("Wrong password")
    public void wrongPassword() throws InterruptedException {
        userName.sendKeys(appProps.getProperty("username"));
        password.sendKeys("wrongPassword");
        loginButton.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("usernameerror")));
        assertEquals("Sorry, your username and password are incorrect - please try again.",
                webDriver.findElement(By.id("usernameerror")).getText());
        loginSuccessfully();
    }

    private void loginSuccessfully() {
        webDriver.findElement(By.id("login-form-username")).sendKeys(appProps.getProperty("username"));
        webDriver.findElement(By.id("login-form-password")).sendKeys(appProps.getProperty("password"));
        webDriver.findElement(By.id("login")).click();
    }

}


