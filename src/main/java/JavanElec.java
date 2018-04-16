import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohammad on 4/5/2018.
 */
public class JavanElec {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","E://Tutorial//Selenium//chromedriver.exe");
        try{
            WebDriver driveChrome = new ChromeDriver();
            driveChrome.get("http://javanelec.com/Shops/Index");
            driveChrome.findElement(By.xpath("//*[@id=\"ShopSearchResult\"]/div[2]/div/div[2]/div/div[1]/a")).click();
            WebDriverWait d = new WebDriverWait(driveChrome,20);
            System.out.println("start");
//           for(int i =0 ; i < 2 ; i++)
//           {
//               System.out.println(i);
               Thread.sleep(10000L);
////               d.until(ExpectedConditions.visibilityOfElementLocated(By.id("more_result")));
//               driveChrome.findElement(By.id("more_result")).click();
//           }
          //  List<WebElement> allProductsName = driveChrome.findElements(By.cssSelector("div[class='product-item-tile col-xs-12 col-lg-6 text-xs-left text-capitalize pull-left ltr nopadding'] span[class='text-ellipsis orginal-font']"));
            List<WebElement> allProductsName = driveChrome.findElements(By.cssSelector("div.product-item-tile:nth-child(2)>span"));
//*[@id="search_result_content"]/div[1]/div/div[2]/div[1]/span[2]

            //*[@id="search_result_content"]/div[2]/div/div[2]/div[1]/span[2]
           // List<WebElement> allProductsName = driveChrome.findElements(By.cssSelector("div.product-item-tile span.text-ellipsis"));
            System.out.println("Find Elements");
           // System.out.println(driveChrome.findElement(By.xpath("//*[@id=\"search_result_content\"]/div[1]/div/div[2]/div[1]/span[2]")).getText());
           // System.out.println(driveChrome.findElement(By.xpath("//*[@id=\"search_result_content\"]/div[2]/div/div[2]/div[1]/span[2]")).getText());
            ArrayList<String> partName = new ArrayList<String>();
            for(int i=1;i<10;i++) {
                String temp = driveChrome.findElement(By.xpath("//*[@id=\"search_result_content\"]/div["+i+"]/div/div[2]/div[1]/span[2]")).getText();
                partName.add(i-1,temp);
                System.out.println(temp);
            }
            FileOutputStream partNameFile = new FileOutputStream("partName.json");
            ObjectOutputStream oos = new ObjectOutputStream(partNameFile);
            oos.writeObject(partName);
            oos.close();
//            for(WebElement w : allProductsName) {
//                System.out.println("has one");
//                System.out.println(w.getText());
//            }
//            Thread.sleep(3000L); // wait 3000 milli seconds
//            driveChrome.findElement(By.xpath("//*[@id=\"Product_Categories\"]/div[2]")).click();
//            driveChrome.findElement(By.id("more_result")).click();
            System.out.println("End");
        }
        catch(Exception e){
            System.err.print(e.getMessage());
        }
    }
}
