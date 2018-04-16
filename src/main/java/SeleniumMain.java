import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by Mohammad on 3/30/2018.
 */
public class SeleniumMain {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","E://Tutorial//Selenium//chromedriver.exe");
        try{
            WebDriver driveChrome = new ChromeDriver();
            driveChrome.get("http://www.qaclickacademy.com");
        }
        catch(Exception e){
            System.err.print(e.getMessage());
        }
    }
}
