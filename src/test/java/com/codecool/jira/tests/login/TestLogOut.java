package com.codecool.jira.tests.login;

import com.codecool.jira.TestResultLoggerExtension;
import com.codecool.jira.Util;
import com.codecool.jira.pages.DashboardPage;
import com.codecool.jira.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(TestResultLoggerExtension.class)
public class TestLogOut {

    WebDriver webDriver;
    Properties appProps;
    WebDriverWait webDriverWait;
    LoginPage loginPage;
    DashboardPage dashboardPage;

    String url = "https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa";

    @BeforeEach
    void init() throws IOException {
        webDriver = Util.setup(url);
        webDriverWait = Util.initWebdriverWait(webDriver);
        loginPage = new LoginPage(webDriver);
        dashboardPage = new DashboardPage(webDriver);
        appProps = Util.read();
    }

    @AfterEach
    void close() {
        webDriver.quit();
    }

    @Test
    @DisplayName("Successfully log out")
    public void logout() {
        loginPage.loginSuccessfully();
        webDriverWait.until(ExpectedConditions.visibilityOf(dashboardPage.profileMenu));
        dashboardPage.logout();
        assertEquals("Log In", loginPage.getSignInText());
        webDriver.get("https://jira-auto.codecool.metastage.net/secure/ViewProfile.jspa");
        assertEquals("You must log in to access this page.", loginPage.getLoginWarningMessage());
    }
}
