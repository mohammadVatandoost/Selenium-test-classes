import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Mohammad on 4/7/2018.
 */
public class findAllTag {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", GlobalData.CHROME_DRIVER);
        try {
            WebDriver driveChrome = new ChromeDriver();
            driveChrome.manage().window().maximize();
            driveChrome.get("http://javanelec.com/Shops/Index");
            // count all of links
            System.out.println(driveChrome.findElement(By.tagName("a")).getSize());
            // count all links in footer
            WebElement footer = driveChrome.findElement(By.xpath(".//*[@id='glbfooter'"));
            System.out.println(footer.findElement(By.tagName("a")).getSize());
            driveChrome.findElement(By.id("sdaf")).click();

        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }
}
