import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by Mohammad on 4/5/2018.
 */
public class DropDown {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","E://Tutorial//Selenium//chromedriver.exe");
        try{
            WebDriver driveChrome = new ChromeDriver();
            driveChrome.get("http://www.spicejet.com");
            Select s = new Select(driveChrome.findElement(By.id("ctl00_mainContent_ddl_Adult")));
            s.selectByValue("2");
//            s.selectByIndex(6);
//            s.selectByVisibleText("5 Adults");

            // dynamic dropdown
//            driveChrome.findElement(By.xpath("fdsaf")).click();
//            driveChrome.findElement(By.xpath("asdfsdaf")).click();

            System.out.println(driveChrome.findElement(By.id("ctl00_mainContent_chk_Unmr")).isSelected());
            driveChrome.findElement(By.id("ctl00_mainContent_chk_Unmr")).click();
            System.out.println(driveChrome.findElement(By.id("ctl00_mainContent_chk_Unmr")).isSelected());
        }
        catch(Exception e){
            System.err.print(e.getMessage());
        }
    }
}
