import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class InstagramBot {
    public static void main(String[] args) {
        try {

            System.out.println("Start");
            System.setProperty("webdriver.chrome.driver", GlobalData.CHROME_DRIVER);
            // Webdriver
            WebDriver driveChrome = new ChromeDriver();//BrowserVersion.CHROME
            driveChrome.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

            driveChrome.get("https://www.instagram.com/accounts/login/?next=%2Fhashbazaar%2F&source=desktop_nav");
            Thread.sleep(2000L);
            driveChrome.findElement(By.cssSelector("input[name='username']")).sendKeys("hashbazaar");
            driveChrome.findElement(By.cssSelector("input[name='password']")).sendKeys("81018101");
            Thread.sleep(1000L);
            driveChrome.findElement(By.xpath("//*[text() = 'Log in']")).click();
            Thread.sleep(5000L);
            driveChrome.findElement(By.cssSelector("input[placeholder='Search']")).sendKeys("bitcoin.inf");
            Thread.sleep(2000L);
            driveChrome.findElement(By.xpath("//*[text() = 'bitcoin.info']")).click();
            Thread.sleep(2000L);
            driveChrome.findElement(By.xpath("//*[text() = ' followers']")).click();
            Thread.sleep(2000L);
//            List<WebElement> notFollowed =  driveChrome.findElements(By.xpath("//*[text() = 'Follow']"));
//            System.out.println("notFollowed.size() : " + notFollowed.size());
//            if(notFollowed.size() > 0) {
                for (int i=0; i<100;i++) {
                    List<WebElement> notFollowed =  driveChrome.findElements(By.xpath("//*[text() = 'Follow']"));
                    WebElement element = notFollowed.get(0);
                    Actions actions = new Actions(driveChrome);
                    actions.moveToElement(element).click().perform();
                    Thread.sleep(2000L);
//                    if(i%5 == 4) {
                        JavascriptExecutor jse = (JavascriptExecutor)driveChrome;

//                        jse.executeScript("scroll(250, 0)"); // if the element is on top.

                        jse.executeScript("scroll(0, 250)"); // if the element is on bottom.
//                    }
                }
//            }
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }
}
