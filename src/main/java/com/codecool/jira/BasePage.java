package com.codecool.jira;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class BasePage {

    protected WebDriver webDriver;
    protected WebDriverWait wait;

    protected String baseUrl = Util.readProperty("url");
    private WebdriverUtil webdriverUtil;


    public BasePage() {
        webdriverUtil = WebdriverUtil.getInstance();
        this.webDriver = webdriverUtil.getWebDriver();
        wait = initWebdriverWait();
        PageFactory.initElements(webDriver, this);
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    private WebDriverWait initWebdriverWait() {
        return webdriverUtil.getWebDriverWait();
    }

    public void closeWebDriver() {
        webdriverUtil.shutDown();
    }
}
