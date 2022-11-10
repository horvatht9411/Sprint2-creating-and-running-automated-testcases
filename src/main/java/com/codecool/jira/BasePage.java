package com.codecool.jira;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    protected static final String LOGIN_URL = "https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa";
    protected static final String SECONDARY_LOGIN_URL = "https://jira-auto.codecool.metastage.net/login.jsp?";
    protected static final String PROFILE_PAGE_URL = "https://jira-auto.codecool.metastage.net/secure/ViewProfile.jspa";

    protected static final String BROWSE_ISSUE_URL = "https://jira-auto.codecool.metastage.net/browse/%s";
    protected static final String CREATE_ISSUE_URL = "https://jira-auto.codecool.metastage.net/secure/CreateIssue.jspa";
    protected static final String ISSUE_DISPLAY_FRONT = "https://jira-auto.codecool.metastage.net/browse/MTP-2459?jql=summary%20~%20%22";
    protected static final String ISSUE_DISPLAY_BACK = "%22";

    protected WebDriver webDriver;
    protected WebDriverWait wait;
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
