package com.codecool.jira;

import io.selenium.utils.ElementContextLocatorFactory;
import io.selenium.utils.FieldContextDecorator;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class BasePage {

    protected WebDriver webDriver;
    protected WebDriverWait wait;

    protected String baseUrl = System.getProperty("url");
    private WebdriverUtil webdriverUtil;


    public BasePage() {
        webdriverUtil = WebdriverUtil.getInstance();
        this.webDriver = webdriverUtil.getWebDriver();
        wait = initWebdriverWait();
//        PageFactory.initElements(webDriver, this);
        PageFactory.initElements(new FieldContextDecorator(new ElementContextLocatorFactory(webDriver)), this);
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    private WebDriverWait initWebdriverWait() {
        return (WebDriverWait) webdriverUtil.getWebDriverWait().ignoring(StaleElementReferenceException.class);
    }

    public void closeWebDriver() {
        webdriverUtil.shutDown();
    }
}
