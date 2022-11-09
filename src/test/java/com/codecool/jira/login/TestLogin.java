package com.codecool.jira.login;

import com.codecool.jira.TestResultLoggerExtension;
import com.codecool.jira.Util;
import com.codecool.jira.loginPages.DashboardPage;
import com.codecool.jira.loginPages.Login2Page;
import com.codecool.jira.loginPages.LoginPage;
import com.codecool.jira.loginPages.UserPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TestResultLoggerExtension.class)
public class TestLogin {

    private static final String ERRORMESSAGE = "Sorry, your username and password are incorrect - please try again.";
    WebDriver webDriver;
    Properties appProps;
    WebDriverWait webDriverWait;
    LoginPage loginPage;
    String url = "https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa";

    List<List<String>> dataSource;

    @BeforeEach
    void init() throws IOException {
        webDriver = Util.setup(url);
        webDriverWait = Util.initWebdriverWait(webDriver);
        loginPage = new LoginPage(webDriver);
        appProps = Util.read();
    }

    void setupDifferentLink() {
        webDriver.get("https://jira-auto.codecool.metastage.net/login.jsp?");
    }

    private String getLoggedInUsername() {
        DashboardPage dashboardPage = new DashboardPage(webDriver);
        dashboardPage.viewProfileName.click();
        UserPage userPage = new UserPage(webDriver);
        return userPage.getUserName();
    }

    private boolean checkLogOutButtonIsVisible() {
        DashboardPage dashboardPage = new DashboardPage(webDriver);
//        Logout button is contained in the profile menu
        webDriverWait.until(ExpectedConditions.visibilityOf(dashboardPage.profileMenu));
        return dashboardPage.checkLogoutButtonVisibility();
    }

    private String getErrorMessage() {
        webDriverWait.until(ExpectedConditions.visibilityOf(loginPage.errorMessage));
        return loginPage.getErrorMessage();
    }

    @AfterEach
    void close() {
        webDriver.quit();
    }

    @Test
    @DisplayName("Correct username and password")
    public void correctCredential() {
        loginPage.login(appProps.getProperty("username"), appProps.getProperty("password"));
        assertTrue(checkLogOutButtonIsVisible());
        assertEquals(appProps.getProperty("username"), getLoggedInUsername());
    }

    @Test
    @DisplayName("Empty Credentials")
    public void emptyCredentials() {
        loginPage.login("", "");
        assertEquals(ERRORMESSAGE, getErrorMessage());

    }

    @Test
    @DisplayName("Incorrect Username")
    public void wrongUsername() {
        loginPage.login("userName", "password");
        assertEquals(ERRORMESSAGE, getErrorMessage());
        loginPage.loginSuccessfully();
    }

    @Test
    @DisplayName("Incorrect Password")
    public void wrongPassword() {
        loginPage.login(appProps.getProperty("username"), "password");
        assertEquals(ERRORMESSAGE, getErrorMessage());
        loginPage.loginSuccessfully();
    }

    @Test
    @DisplayName("Login with Enter key")
    public void loginEnter() {
        loginPage.loginUsingEnterKey(appProps.getProperty("username"), appProps.getProperty("password"));
        assertTrue(checkLogOutButtonIsVisible());
        assertEquals(appProps.getProperty("username"), getLoggedInUsername());
    }

    @Test
    @DisplayName("Correct username and password different link")
    public void correctCredentialII() {
        setupDifferentLink();
        Login2Page login2Page = new Login2Page(webDriver);
        login2Page.login(appProps.getProperty("username"), appProps.getProperty("password"));
        assertTrue(checkLogOutButtonIsVisible());
        assertEquals(appProps.getProperty("username"), getLoggedInUsername());
    }
}
