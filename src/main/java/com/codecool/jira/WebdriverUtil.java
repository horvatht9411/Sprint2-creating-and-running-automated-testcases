package com.codecool.jira;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class WebdriverUtil {

    private final boolean headless = Boolean.parseBoolean(Util.readProperty("headless"));

    private final int SECONDS = 15;

    private WebDriver webDriver;

    private WebDriverWait webDriverWait;

    private static WebdriverUtil INSTANCE;

    private WebdriverUtil(){
        if (Boolean.parseBoolean(Util.readProperty("local"))){
            webDriver = setupWebdriver();
        } else {
            webDriver = setupRemoteWebdriver();
        }
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(SECONDS));
    }

    private void setSystemProperties() {
        System.setProperty("username", Util.readProperty("Pusername"));
        System.setProperty("password", Util.readProperty("Ppassword"));
        System.setProperty("username", Util.readProperty("Pusername"));
        System.setProperty("headless", Util.readProperty("Pheadless"));
        System.setProperty("remoteBrowser", Util.readProperty("PremoteBrowser"));
        System.setProperty("localConnection", Util.readProperty("PlocalConnection"));
        System.setProperty("url", Util.readProperty("Purl"));

    }

    public static WebdriverUtil getInstance(){
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

    private WebDriver setupWebdriver(){
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

    private WebDriver setupRemoteWebdriver(){
        String remoteBrowser = Util.readProperty("remoteBrowser");
        String password = Util.readProperty("password");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if ("chrome".equals(remoteBrowser)) {
            capabilities.setBrowserName("chrome");
        } else {
            capabilities.setBrowserName("firefox");
        }
        capabilities.setPlatform(Platform.LINUX);
        try {
            return new RemoteWebDriver(new
                    URL("https://selenium:" + password + "@seleniumhub.codecool.metastage.net/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void shutDown(){
        webDriver.quit();
        INSTANCE = null;
    }

}
