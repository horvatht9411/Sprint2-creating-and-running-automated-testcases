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

    protected WebDriver webDriver;
    protected WebDriverWait wait;
    private WebdriverUtil webdriverUtil;

    public BasePage() {
        webdriverUtil = WebdriverUtil.getInstance();
        this.webDriver = webdriverUtil.getWebDriver();
        wait = initWebdriverWait();
        PageFactory.initElements(webDriver, this);
    }



    private WebDriverWait initWebdriverWait() {
        return webdriverUtil.getWebDriverWait();
    }

    public void closeWebDriver(){
        webdriverUtil.shutDown();
    }
}
