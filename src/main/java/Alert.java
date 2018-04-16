import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by Mohammad on 4/5/2018.
 */
public class Alert {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","E://Tutorial//Selenium//chromedriver.exe");
        try{
            WebDriver driveChrome = new ChromeDriver();
            driveChrome.get("http://www.spicejet.com");
            driveChrome.findElement(By.xpath("temp")).click();
            driveChrome.switchTo().alert().getText();
            driveChrome.switchTo().alert().sendKeys("temp");
            driveChrome.switchTo().alert().accept();
//            driveChrome.switchTo().alert().dismiss();
        }
        catch(Exception e){
            System.err.print(e.getMessage());
        }
    }
}
