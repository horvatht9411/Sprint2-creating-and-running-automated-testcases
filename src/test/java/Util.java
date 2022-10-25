import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class Util {
    static boolean isVisible = false;
    private static int seconds = 10;

    static void executeScript(WebElement webElement, JavascriptExecutor executor) {
        executor.executeScript("arguments[0].click();", webElement);
    }



    static Properties read() throws IOException {
        Properties appProps = new Properties();
//        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = "src/main/resources/init.properties";
        appProps.load(new FileInputStream(appConfigPath));
        return appProps;
    }


    static boolean checkModalVisibility(WebDriver webDriver){
        var modal = webDriver.findElements(By.cssSelector("body > div.fade.modal.show > div > div"));
        boolean exist = false;
        if (!modal.isEmpty()){
            if (modal.get(0).isDisplayed()) {
                String modalHeader = webDriver.findElement(By.id("example-modal-sizes-title-lg")).getText();
                if("Thanks for submitting the form".equals(modalHeader)) exist = true;
            }}
        return exist;
    }

    static WebDriverWait initWebdriverWait(WebDriver webDriver){
        return new WebDriverWait(webDriver, Duration.ofSeconds(seconds));
    }

    static WebDriver setup(String url){
        WebDriver webDriver;
        WebDriverManager.chromedriver().setup();
        if (!isVisible) {
            // Run in background
            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            webDriver = new ChromeDriver(options);
        } else {
            //Open browse
            webDriver = new ChromeDriver();
            webDriver.manage().window().maximize();
        }
        webDriver.get(url);
        return webDriver;
    }

    static void login(WebDriver webDriver, Properties appProps, WebDriverWait webDriverWait){
        webDriver.findElement(By.id("login-form-username")).sendKeys(appProps.getProperty("username"));
        webDriver.findElement(By.id("login-form-password")).sendKeys(appProps.getProperty("password"));
        webDriver.findElement(By.id("login")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-details-user-fullname")));
    }

}
