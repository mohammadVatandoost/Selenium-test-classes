import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ScrollTest {
    public static void main(String[] args) {
        try {

            System.out.println("Start");
            System.setProperty("webdriver.chrome.driver", GlobalData.CHROME_DRIVER);
            // Webdriver
            WebDriver driveChrome = new ChromeDriver();//BrowserVersion.CHROME
            driveChrome.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

            driveChrome.get("https://www.instagram.com/accounts/login/?next=%2Fhashbazaar%2F&source=desktop_nav");

//            }
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }
}
