package com.codecool.jira.loginPages;

import com.codecool.jira.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

// page_url = https://jira-auto.codecool.metastage.net/secure/ViewProfile.jspa
public class DashboardPage extends BasePage {

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
        webDriver.get(PROFILE_PAGE_URL);
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
