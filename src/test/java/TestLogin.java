import com.codecool.App;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogin {

    Logger logger = LoggerFactory.getLogger(App.class);
    WebDriver webDriver;
    WebElement loginButton;
    WebElement userName;
    WebElement password;
    String url = "https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa";

    @BeforeEach
    void init() {
        WebDriverManager.chromedriver().setup();
        webDriver = Util.visibilitySetup();
        webDriver.manage().window().maximize();
        webDriver.get(url);
        userName = webDriver.findElement(By.id("login-form-username"));
        password = webDriver.findElement(By.id("login-form-password"));
        loginButton = webDriver.findElement(By.id("login"));
    }

    @AfterEach
    void close() {

    }

    @Test
    @DisplayName("Correct username and password")
    public void correctCredential() {
        userName.sendKeys(System.getProperty("username"));
        password.sendKeys((System.getProperty("password")));
        loginButton.click();
        webDriver.findElement(By.id("user-options-content")).click();
        String logOut = webDriver.findElement(By.id("log_out")).getText();
        Assert.assertEquals("Log Out", logOut);
    }
}


