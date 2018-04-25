import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mohammad on 4/5/2018.
 */
public class JavanElec {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","E://Tutorial//Selenium//chromedriver.exe");
        try{
            WebDriver driveChrome = new ChromeDriver();
            driveChrome.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driveChrome.get("http://javanelec.com/Shops/Index");
            driveChrome.findElement(By.xpath("//*[@id=\"ShopSearchResult\"]/div[2]/div/div[2]/div/div[1]/a")).click();
            System.out.println("start");
           for(int i =0 ; i < 5 ; i++) {
               System.out.println("i :" + i);
               Thread.sleep(20000L);
               if (driveChrome.findElements(By.id("more_result")).size() > 0) {
                   driveChrome.findElement(By.id("more_result")).click();
                   System.out.println("founded");
               }
           }
            Thread.sleep(10000L);
          //  List<WebElement> allProductsName = driveChrome.findElements(By.cssSelector("div[class='product-item-tile col-xs-12 col-lg-6 text-xs-left text-capitalize pull-left ltr nopadding'] span[class='text-ellipsis orginal-font']"));
          //  List<WebElement> allProductsName = driveChrome.findElements(By.cssSelector("div.product-item-tile:nth-child(2)>span"));
//*[@id="search_result_content"]/div[1]/div/div[2]/div[1]/span[2]

            //*[@id="search_result_content"]/div[2]/div/div[2]/div[1]/span[2]
            List<WebElement> allProductsName = driveChrome.findElements(By.className("product-item"));
            System.out.println("Find Elements");
            System.out.println("allProductsName : " + allProductsName.size());

           // System.out.println(driveChrome.findElement(By.xpath("//*[@id=\"search_result_content\"]/div[1]/div/div[2]/div[1]/span[2]")).getText());
           // System.out.println(driveChrome.findElement(By.xpath("//*[@id=\"search_result_content\"]/div[2]/div/div[2]/div[1]/span[2]")).getText());
           // ArrayList<String> partName = new ArrayList<String>();
            String[] partName = new String[allProductsName.size()];
            int partNameCounter = 0 ;
            String[] partNameNotFound = new String[allProductsName.size()];
            int partNameNotFoundCounter = 0;
            JavanElec downloadImage = new JavanElec();
            for(int i=1;i<allProductsName.size()+1;i++) {
                String temp = driveChrome.findElement(By.xpath("//*[@id=\"search_result_content\"]/div["+i+"]/div/div[2]/div[1]/span[2]")).getText();
                System.out.println(temp);
                if( ( !temp.contains("/") ) && ( !temp.contains("*") )) {
                    WebElement imageTag = driveChrome.findElement(By.xpath("//*[@id=\"search_result_content\"]/div["+i+"]/div/div[1]/a/img"));
                    downloadImage.downloadImages(imageTag,temp);
                    partName[partNameCounter] = temp ;
                    partNameCounter++;
                    Thread.sleep(1500L);
                } else {
                    partNameNotFound[partNameNotFoundCounter] = temp;
                    partNameNotFoundCounter++;
                }
            }

            FileOutputStream partNameFile = new FileOutputStream("partName.json");
            ObjectOutputStream oos = new ObjectOutputStream(partNameFile);
            oos.writeObject(partName);
            oos.close();

            FileOutputStream partNameNotFoundFile = new FileOutputStream("partNameNotFoundFile.json");
            ObjectOutputStream oospartNameNotFoundFile = new ObjectOutputStream(partNameNotFoundFile);
            oospartNameNotFoundFile.writeObject(partNameNotFound);
            oospartNameNotFoundFile.close();
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

    public void downloadImages(WebElement image,String name) {
        String smallImage = image.getAttribute("src");
        try {
            URL smallImageURL = new URL(smallImage);
            System.out.println("smallImageURL : " +smallImageURL);
            BufferedImage saveSmallImage = ImageIO.read(smallImageURL);
//            System.out.println("saveSmallImage : "+saveSmallImage);
            ImageIO.write(saveSmallImage, "jpg", new File(name+".jpg"));
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}
