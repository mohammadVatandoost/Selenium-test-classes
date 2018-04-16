import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Mohammad on 4/7/2018.
 */
public class NewTabHandling {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "E://Tutorial//Selenium//chromedriver.exe");
        try {
            WebDriver driveChrome = new ChromeDriver();
            driveChrome.manage().window().maximize();
            driveChrome.get("http://javanelec.com/Shops/Index");
            driveChrome.findElement(By.id("sdaf")).click();
            System.out.println(driveChrome.getTitle());
//            Set is an interface which extends Collection. It is an unordered collection of objects in which duplicate values cannot be stored.
//            Basically, Set is implemented by HashSet, LinkedSet or TreeSet (sorted representation).
//            Set has various methods to add, remove clear, size, etc to enhance the usage of this interface
            Set<String>ids = driveChrome.getWindowHandles();
//            Iterators are used in Collection framework in Java to retrieve elements one by one.
            Iterator<String> it = ids.iterator();
            String parentId = it.next();
            String childId = it.next();
            driveChrome.switchTo().window(childId);
            System.out.println(driveChrome.getTitle());
            driveChrome.switchTo().window(parentId);
            System.out.println(driveChrome.getTitle());
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }
}
