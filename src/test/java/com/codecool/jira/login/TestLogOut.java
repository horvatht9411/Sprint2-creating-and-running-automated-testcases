package com.codecool.jira.login;

import com.codecool.jira.TestResultLoggerExtension;
import com.codecool.jira.loginPages.DashboardPage;
import com.codecool.jira.loginPages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(TestResultLoggerExtension.class)
public class TestLogOut {
    LoginPage loginPage = new LoginPage();
    DashboardPage dashboardPage = new DashboardPage();

    @BeforeEach
    void init() {
        loginPage.loginSuccessfully();
    }

    @AfterEach
    void close() {
        loginPage.closeWebDriver();
    }

    @Test
    @DisplayName("Log out successfully")
    public void logout() {
        dashboardPage.logout();
        assertEquals("Log In", loginPage.getSignInText(), "Login button is visible");

        dashboardPage.navigateToProfilePage();
        assertEquals("You must log in to access this page.", loginPage.getLoginWarningMessage(), "Error message is the same");
    }
}
