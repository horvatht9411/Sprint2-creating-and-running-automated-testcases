package com.codecool.tests.login;

import com.codecool.ReadFromExcel;
import com.codecool.TestResultLoggerExtension;
import com.codecool.Util;
import com.codecool.pages.DashboardPage;
import com.codecool.pages.Login2Page;
import com.codecool.pages.LoginPage;
import com.codecool.pages.UserPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Stream;

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

    private static String[][] readDataFromExcel() {
        return ReadFromExcel.get("Login");
    }

    static Stream<Arguments> initData() {
        return Arrays.stream(readDataFromExcel()).map(row -> Arguments.of(Named.of(row[0], row[1]), row[2]));
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

    @ParameterizedTest
    @MethodSource(value = "initData")
    public void wrongCredential(String userName, String password) {
        loginPage.login(userName, password);
        assertEquals(ERRORMESSAGE, getErrorMessage());
        loginPage.loginSuccessfully();
    }

//    @Test
//    @DisplayName("Wrong Username")
//    public void wrongUsername() {
//        loginPage.login("Wrong", "Wrong");
//        assertEquals(ERRORMESSAGE, getErrorMessage());
//        loginPage.loginSuccessfully();
//    }

    @Test
    @DisplayName("Login with Enter key")
    public void loginEnter() {
        loginPage.login(appProps.getProperty("username"), appProps.getProperty("password"));
        assertTrue(checkLogOutButtonIsVisible());
        assertEquals(appProps.getProperty("username"), getLoggedInUsername());
    }

    @Test
    @DisplayName("Correct username and password different link")
    public void correctCredentialII() throws IOException {
        setupDifferentLink();
        Login2Page login2Page = new Login2Page(webDriver);
        login2Page.login(appProps.getProperty("username"), appProps.getProperty("password"));
        assertTrue(checkLogOutButtonIsVisible());
        assertEquals(appProps.getProperty("username"), getLoggedInUsername());
    }


}
