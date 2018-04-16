import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by Mohammad on 4/6/2018.
 */
public class synchronize {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","E://Tutorial//Selenium//chromedriver.exe");
        try{
            WebDriver driveChrome = new ChromeDriver();
            driveChrome.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // wait 5 seconde for event of ajax
            driveChrome.get("https://alaskatrips.poweredbygps.com/g/pt/vacationpackages?MDPCID=ALASKA-US.TPS.BRAND.VACATIONPACKAGES.PACKAGE");
            driveChrome.findElement(By.id("temp")).sendKeys("nyc");
            driveChrome.findElement(By.id("temp")).sendKeys(Keys.TAB);
            driveChrome.findElement(By.id("temp2")).sendKeys(Keys.ENTER); // change page
            driveChrome.findElement(By.xpath("dffddsfg")).click();

            // explicit
            WebDriverWait d = new WebDriverWait(driveChrome,20);
            d.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("asdf")));
            driveChrome.findElement(By.xpath("dffddsfg")).click();

        }
        catch(Exception e){
            System.err.print(e.getMessage());
        }
    }
}
