import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {
    static boolean isVisible = false;

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

    static WebDriver visibilitySetup(){
        WebDriver webDriver;
        if (!isVisible) {
            // Run in background
            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            webDriver = new ChromeDriver(options);
        } else {
            //Open browse
            webDriver = new ChromeDriver();
        }
        return webDriver;
    }

}
