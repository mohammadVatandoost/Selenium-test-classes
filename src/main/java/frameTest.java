import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Mohammad on 4/7/2018.
 */
public class frameTest {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "E://Tutorial//Selenium//chromedriver.exe");
        try {
            WebDriver driveChrome = new ChromeDriver();
            driveChrome.manage().window().maximize();
            driveChrome.get("http://javanelec.com/Shops/Index");
            // switch to frame id
            // driveChrome.switchTo().frame(0);  // frame one
            // driveChrome.switchTo().frame(1);  // frame two
            // driveChrome.switchTo().defaultContent() // back to first page openning
            driveChrome.switchTo().frame(driveChrome.findElement(By.cssSelector("iframe[class='demo-frame']")));
            driveChrome.findElement(By.id("sdaf")).click();
            Actions a = new Actions(driveChrome);
            WebElement source = driveChrome.findElement(By.id("draggable"));
            WebElement target = driveChrome.findElement(By.id("droppable"));
            a.dragAndDrop(source,target).build().perform();

        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }
}
