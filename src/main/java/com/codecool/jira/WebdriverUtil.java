package com.codecool.jira;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebdriverUtil {

    private final boolean headless = Boolean.parseBoolean(Util.readProperty("headless"));

    private final int SECONDS = 15;

    private WebDriver webDriver;

    private WebDriverWait webDriverWait;

    private static WebdriverUtil INSTANCE;

    private WebdriverUtil(){
        webDriver = setupWebdriver();
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(SECONDS));
    }

    public static WebdriverUtil getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new WebdriverUtil();
        }

        return INSTANCE;
    }

    public WebDriver getWebDriver() {
        return this.webDriver;
    }

    public WebDriverWait getWebDriverWait() {
        return webDriverWait;
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

    public void shutDown(){
        webDriver.quit();
        INSTANCE = null;
    }

}
