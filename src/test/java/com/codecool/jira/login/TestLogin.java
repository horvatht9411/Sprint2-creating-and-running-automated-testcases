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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TestResultLoggerExtension.class)
public class TestLogin {

    private static final String ERROR_MESSAGE = "Sorry, your username and password are incorrect - please try again.";
    LoginPage loginPage;
    Login2Page login2Page;
    DashboardPage dashboardPage;
    UserPage userPage;

    @BeforeEach
    void init() {
        loginPage = new LoginPage();
        login2Page = new Login2Page();
        dashboardPage = new DashboardPage();
        userPage = new UserPage();
    }

    @AfterEach
    void close() {
        loginPage.closeWebDriver();
    }

    @Test
    @DisplayName("Correct username and password")
    public void correctCredential() throws InterruptedException {
        String userName = Util.readProperty("username");
        String password = Util.readProperty("password");
        loginPage.login(userName, password);
        assertTrue(dashboardPage.isLogoutButtonVisible());

        dashboardPage.navigateToProfilePage();
        assertEquals(userName, userPage.getUserName());
    }

    @Test
    @DisplayName("Empty Credentials")
    public void emptyCredentials() {
        loginPage.login("", "");
        assertEquals(ERROR_MESSAGE, loginPage.getErrorMessage());
    }

    @Test
    @DisplayName("Incorrect Username")
    public void wrongUsername() {
        loginPage.login("incorrect", Util.readProperty("password"));
        assertEquals(ERROR_MESSAGE, loginPage.getErrorMessage());

        loginPage.loginSuccessfully();
    }

    @Test
    @DisplayName("Incorrect Password")
    public void wrongPassword() {
        loginPage.login(Util.readProperty("username"), "incorrect");
        assertEquals(ERROR_MESSAGE, loginPage.getErrorMessage());

        loginPage.loginSuccessfully();
    }

    @Test
    @DisplayName("Login with Enter key")
    public void loginEnter() {
        String userName = Util.readProperty("username");
        String password = Util.readProperty("password");
        loginPage.loginUsingEnterKey(userName, password);
        assertTrue(dashboardPage.isLogoutButtonVisible());

        dashboardPage.navigateToProfilePage();
        assertEquals(userName, userPage.getUserName());
    }

    @Test
    @DisplayName("Login on different link with correct credentials")
    public void correctCredentialII() {
        String userName = Util.readProperty("username");
        String password = Util.readProperty("password");
        login2Page.login(userName, password);
        assertTrue(dashboardPage.isLogoutButtonVisible());

        dashboardPage.navigateToProfilePage();
        assertEquals(userName, userPage.getUserName());
    }
}
