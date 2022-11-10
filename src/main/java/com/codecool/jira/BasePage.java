package com.codecool.jira;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    public static final String LOGIN_URL = "https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa";
    public static final String SECONDARY_LOGIN_URL = "https://jira-auto.codecool.metastage.net/login.jsp?";
    public static final String PROFILE_PAGE_URL = "https://jira-auto.codecool.metastage.net/secure/ViewProfile.jspa";
    public static final int SECONDS = 15;
    private final boolean headless = Boolean.parseBoolean(Util.readProperty("headless"));
    protected WebDriver webDriver;
    protected WebDriverWait wait;

    public BasePage() {
        webDriver = setupWebdriver();
        wait = initWebdriverWait(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    private WebDriver setupWebdriver() {
        WebDriverManager.chromedriver().setup();
        if (headless) {
            // Run in background
            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            webDriver = new ChromeDriver(options);
        } else {
            //Open browser
            webDriver = new ChromeDriver();
            webDriver.manage().window().maximize();
        }
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
//        webDriver.get(BasePage.LOGIN_URL);
        return webDriver;
    }

    private WebDriverWait initWebdriverWait(WebDriver webDriver) {
        return new WebDriverWait(webDriver, Duration.ofSeconds(SECONDS));
    }

    public void closeWebDriver(){
        webDriver.quit();
    }
}
