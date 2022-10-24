import com.codecool.App;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
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
    public void correctCredential() throws InterruptedException {
        userName.sendKeys(System.getProperty("username"));
        Thread.sleep(10000);
        password.sendKeys((System.getProperty("password")));
        Thread.sleep(10000);
        loginButton.click();
        Thread.sleep(10000);
        webDriver.findElement(By.id("user-options-content")).click();
        Thread.sleep(10000);
        String logOut = webDriver.findElement(By.id("log_out")).getText();
        Thread.sleep(10000);
        Assertions.assertEquals("Log Out", logOut);
        Thread.sleep(10000);
    }
}


