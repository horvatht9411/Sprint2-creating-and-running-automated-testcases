package com.codecool.jira.loginPages;

import com.codecool.jira.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class DashboardPage extends BasePage {

    private final String profilePageUrl = baseUrl + "secure/ViewProfile.jspa";

    @FindBy(xpath = "//*[@id='header-details-user-fullname']")
    public WebElement profileMenu;

    @FindBy(xpath = "//*[@id='log_out']")
    public WebElement logOutButton;

    @FindBy(xpath = "//*[@id='create_link']")
    public WebElement createIssueButton;

    public boolean isLogoutButtonVisible() {
        wait.until(ExpectedConditions.visibilityOf(profileMenu));
        profileMenu.click();
        return logOutButton.isDisplayed();
    }

    public void navigateToProfilePage() {
        webDriver.get(profilePageUrl);
    }

    public void waitForSucessfullyLogin() {
        wait.until(ExpectedConditions.visibilityOf(profileMenu));
    }

    public void logout() {
        wait.until(ExpectedConditions.visibilityOf(profileMenu));
        profileMenu.click();
        logOutButton.click();
    }

    public void clickCreateNewIssueButton() {
        createIssueButton.click();
    }
}
