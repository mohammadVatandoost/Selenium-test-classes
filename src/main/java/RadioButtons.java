import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by Mohammad on 4/5/2018.
 */
public class RadioButtons {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","E://Tutorial//Selenium//chromedriver.exe");
        try{
            WebDriver driveChrome = new ChromeDriver();
            driveChrome.get("http://www.spicejet.com");
            driveChrome.findElement(By.xpath("temp")).click();
            int count = driveChrome.findElements(By.xpath("temp")).size(); // find number of radio buttons
            for(int i=0;i<count;i++) {
                String test = driveChrome.findElements(By.xpath("temp")).get(i).getAttribute("value");
                if (driveChrome.findElements(By.xpath("temp")).get(i).getAttribute("value")=="Cheese")
                {
                    // test.equals("Cheese");
                    driveChrome.findElements(By.xpath("temp")).get(i).click();// start from 0
                }
            }
        }
        catch(Exception e){
            System.err.print(e.getMessage());
        }
    }
}
