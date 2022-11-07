package com.codecool.tests.login;

import com.codecool.TestResultLoggerExtension;
import com.codecool.Util;
import com.codecool.pages.DashboardPage;
import com.codecool.pages.Login2Page;
import com.codecool.pages.LoginPage;
import com.codecool.pages.UserPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
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
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated((By) dashboardPage.profileMenu));
        return dashboardPage.checkLogoutButtonVisibility();
    }

    private String getErrorMessage() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated((By) loginPage.errorMessage));
        return loginPage.getErrorMessage();
    }

    @AfterEach
    void close() {
        webDriver.quit();
    }

    @Test
    @DisplayName("Correct username and password")
    public void correctCredential() {
        loginPage.login("userName", "P@ssword"); //TODO: Read from Excel
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
    @DisplayName("Wrong password")
    public void wrongPassword() {
        loginPage.login("automation33", "Wrong");
        assertEquals(ERRORMESSAGE, getErrorMessage());
        loginPage.loginSuccessfully();
    }

    @Test
    @DisplayName("Wrong Username")
    public void wrongUsername() {
        loginPage.login("Wrong", "Wrong");
        assertEquals(ERRORMESSAGE, getErrorMessage());
        loginPage.loginSuccessfully();
    }

    @Test
    @DisplayName("Login with Enter key")
    public void loginEnter() {
        loginPage.loginUsingEnterKey("userName", "P@ssword"); //TODO: Read from Excel
        assertTrue(checkLogOutButtonIsVisible());
        assertEquals(appProps.getProperty("username"), getLoggedInUsername());
    }

    @Test
    @DisplayName("Correct username and password different link")
    public void correctCredentialII() throws IOException {
        setupDifferentLink();
        Login2Page login2Page = new Login2Page(webDriver);
        login2Page.login("userName", "P@ssword"); //TODO: Read from Excel
        assertTrue(checkLogOutButtonIsVisible());
        assertEquals(appProps.getProperty("username"), getLoggedInUsername());
    }


}
