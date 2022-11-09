package com.codecool.jira;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class Util {
    private final static int SECONDS = 15;

    static void executeScript(WebElement webElement, JavascriptExecutor executor) {
        executor.executeScript("arguments[0].click();", webElement);
    }

    public static Properties read() throws IOException {
        Properties appProps = new Properties();
        String appConfigPath = "src/main/resources/init.properties";
        appProps.load(new FileInputStream(appConfigPath));
        return appProps;
    }

    public static WebDriverWait initWebdriverWait(WebDriver webDriver) {
        return new WebDriverWait(webDriver, Duration.ofSeconds(SECONDS));
    }

    public static WebDriver setup(String url) throws IOException {
        Properties appProps = read();
        WebDriver webDriver;
        WebDriverManager.chromedriver().setup();
        if (appProps.getProperty("headless").equals("true")) {
            // Run in background
            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            webDriver = new ChromeDriver(options);
        } else {
            //Open browse
            webDriver = new ChromeDriver();
            webDriver.manage().window().maximize();
        }
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        webDriver.get(url);
        return webDriver;
    }

    public static void login(WebDriver webDriver, Properties appProps, WebDriverWait webDriverWait) {
        webDriver.findElement(By.id("login-form-username")).sendKeys(appProps.getProperty("username"));
        webDriver.findElement(By.id("login-form-password")).sendKeys(appProps.getProperty("password"));
        webDriver.findElement(By.id("login")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-details-user-fullname")));
    }

}
