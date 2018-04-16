import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * Created by Mohammad on 4/7/2018.
 */
public class ActionsDemo {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "E://Tutorial//Selenium//chromedriver.exe");
        try {
            WebDriver driveChrome = new ChromeDriver();
            driveChrome.manage().window().maximize();
            driveChrome.get("http://javanelec.com/Shops/Index");
            Actions a = new Actions(driveChrome);
            WebElement move = driveChrome.findElement(By.id("asdfdsaf"));

            a.moveToElement(driveChrome.findElement(By.xpath("asdf0"))).click().keyDown(Keys.SHIFT).sendKeys("hello").build().perform();

            // move to specefic element
            a.moveToElement(driveChrome.findElement(By.xpath("adfadsf"))).build().perform();
            // right click
            a.moveToElement(driveChrome.findElement(By.xpath("adfadsf"))).contextClick().build().perform();

        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }
}
