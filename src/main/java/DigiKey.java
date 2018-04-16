import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mohammad on 4/8/2018.
 */
public class DigiKey {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "E://Tutorial//Selenium//chromedriver.exe");
        try {
            WebDriver driveChrome = new ChromeDriver();
            driveChrome.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driveChrome.get("https://www.digikey.com/products/en/integrated-circuits-ics/logic-buffers-drivers-receivers-transceivers/704?FV=ffe002c0&quantity=&ColumnSort=0&page=1&k=74LVT16245BDL&pageSize=25");
//            WebDriverWait d = new WebDriverWait(driveChrome,20);
            // read javan data
            System.out.println("start");
            FileInputStream fis = new FileInputStream("partName.json");
            ObjectInputStream ois = new ObjectInputStream(fis);
            List<String> partName = (List<String>) ois.readObject();
            ois.close();
            // not founded parts
              //ArrayList<String> notFoundedPartName = new ArrayList<String>();
              String[] notFoundedPartName = new String[partName.size()];
            int notFoundedCounter = 0;
            // make AllParts
             String[][] AllParts = new String[partName.size()*10][];
            // List<List<String>> AllParts = new ArrayList<List<String>>();
            int foundedCounter = 0;
            System.out.println("partName size : "+ partName.size());
            for (int i = 0; i < partName.size(); i++) {
                //System.out.println("partName :" + partName.get(i));
                // id = pagelayout_0_content_2__searchText
                driveChrome.findElement(By.xpath("//*[@id=\"_idOfSearchbarPleaseIgnore\"]/div[2]/div[3]/input")).sendKeys(partName.get(i));
                // id = pagelayout_0_content_2__searchImg
                driveChrome.findElement(By.xpath("//*[@id=\"_idOfSearchbarPleaseIgnore\"]/div[2]/div[4]/input")).click();
               // Thread.sleep(7000L);
                if( driveChrome.findElements(By.id("lnkPart")).size() > 0 ) {
                    // find table
                    WebElement table = driveChrome.findElement(By.id("lnkPart"));
                    // find all rows
                    List<WebElement> tableRow = table.findElements(By.tagName("tr"));
                    //System.out.println("tableRow.size() : "+tableRow.size());
                    for (int j = 0; j < tableRow.size(); j++) {
                        // find all columns in a row
                        List<WebElement> tableColumn = tableRow.get(j).findElements(By.tagName("td"));
                       //System.out.println(j);
                        AllParts[foundedCounter] = new String[tableColumn.size()-3];
                      //  System.out.println("tableColumn.size() : "+tableColumn.size());
                        for (int t = 3; t < tableColumn.size(); t++) {

                         //   System.out.println("j :"+j+" t : " + t + " : " + tableColumn.get(t).getText());
                            AllParts[foundedCounter][t-3] = tableColumn.get(t).getText();
                           // AllParts.get(foundedCounter).set(t-3, tableColumn.get(t).getText());
                        }
                        foundedCounter++;
                      //  System.out.println("foundedCounter : "+foundedCounter);
                    }
                    this.downloadImages(driveChrome);
                } else {
                    notFoundedPartName[notFoundedCounter] = partName.get(i);
                    //notFoundedPartName.add(notFoundedCounter,partName.get(i));
                    notFoundedCounter++;
                    System.out.println("not find : "+partName.get(i));
                    driveChrome.navigate().back();
                }
             //   System.out.println("i :" + i );
            }
            // store All Data
            FileOutputStream partNameFile = new FileOutputStream("AllParts.json");
            ObjectOutputStream oos = new ObjectOutputStream(partNameFile);
            oos.writeObject(AllParts);
            oos.close();
            // store notFoundedData
            FileOutputStream notFoundedPartNameFile = new FileOutputStream("notFoundedPartName.json");
            ObjectOutputStream oosNotFoundedPartNameFile = new ObjectOutputStream(notFoundedPartNameFile);
            oosNotFoundedPartNameFile.writeObject(notFoundedPartName);
            oosNotFoundedPartNameFile.close();

            System.out.println("notFoundedCounter :" + notFoundedCounter);
            System.out.println("foundedCounter :" + foundedCounter);
            System.out.println("End");
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }
    public void downloadImages(WebDriver driveChrome) {
        // download image
        List<WebElement> imageTable = driveChrome.findElements(By.className("pszoomer"));
        System.out.println("imageTable.size() : "+imageTable.size());
        for (int j = 0; j < imageTable.size(); j++) {
            WebElement logo = imageTable.get(j);
            String logoSRC = logo.getAttribute("src");
            System.out.println("logoSRC : "+logoSRC);

            URL imageURL = new URL(logoSRC);
            BufferedImage saveImage = ImageIO.read(imageURL);
//
//                        ImageIO.write(saveImage, "png", new File(tableColumn.get(4).getText()+".png"));
        }
    }
}
