import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DigikeyDescProduct {
    public static void main(String[] args) {
        try {

            System.out.println("Start");


                FileInputStream fis = new FileInputStream(GlobalData.DigiKey_Directory+"noDesProduct.json");
                ObjectInputStream ois = new ObjectInputStream(fis);
                String[] AllParts = (String[]) ois.readObject();
                ois.close();

            System.out.println("All noDesProduct number : " + AllParts.length);

            System.setProperty("webdriver.chrome.driver", GlobalData.CHROME_DRIVER);
            // Webdriver
            WebDriver driveChrome = new ChromeDriver();//BrowserVersion.CHROME
            driveChrome.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            driveChrome.get("https://www.digikey.com/products/en/integrated-circuits-ics/logic-buffers-drivers-receivers-transceivers/704?FV=ffe002c0&quantity=&ColumnSort=0&page=1&k=74LVT16245BDL&pageSize=25");
            // data Pack Counter
            int pacckCounter = 17;
            for(int i =3417; i<AllParts.length;i++) {
                int foundedCounter = 0;
                // make AllParts
                String[][] partsPack = new String[200][2];
                for(int j=0;j<200;j++) {
                    System.out.println(i + " : " + AllParts[i]);
                    if (AllParts[i] != null) {
                        // id = pagelayout_0_content_2__searchText
                        driveChrome.findElement(By.xpath("//*[@id=\"header\"]/div[3]/div[1]/div[2]/div[3]/input")).sendKeys(AllParts[i]);
                        // id = pagelayout_0_content_2__searchImg
                        driveChrome.findElement(By.xpath("//*[@id=\"header-search-button\"]")).click();
                        // exactly find one part
                        if (driveChrome.findElements(By.xpath("//*[@id=\"exactPartList\"]/table/tbody/tr/td[2]/span[1]/a")).size() > 0) {
                            driveChrome.findElement(By.xpath("//*[@id=\"exactPartList\"]/table/tbody/tr/td[2]/span[1]/a")).click();
                            if (driveChrome.findElements(By.xpath("//*[@id=\"product-attribute-title\"]")).size() > 0) {
                                partsPack[foundedCounter] = getDataFromComponentPage(driveChrome, AllParts[i]);
                                foundedCounter++;
                            } else {
                                System.out.println("not find : " + AllParts[i]);
                            }
                        } else if (driveChrome.findElements(By.xpath("//*[@id=\"product-attribute-title\"]")).size() > 0) {
                            partsPack[foundedCounter] = getDataFromComponentPage(driveChrome, AllParts[i]);
                            foundedCounter++;
                        } else {
                            System.out.println("not find : " + AllParts[i]);
                        }
                        i++;
                    }
                }
                // store All Data
                FileOutputStream partNameFile = new FileOutputStream("productDes"+pacckCounter+".json");
                ObjectOutputStream oosFound = new ObjectOutputStream(partNameFile);
                oosFound.writeObject(partsPack);
                oosFound.close();
                pacckCounter++;
                System.out.println("pacckCounter : " + pacckCounter);
            }
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

    static String[] getDataFromComponentPage(WebDriver driveChrome, String name) {
        String[] temp = new String[2];
        temp[0] = name;
        // find component name
        if(driveChrome.findElements(By.xpath("//*[@id=\"product-overview\"]/tbody/tr[5]/td[1]")).size() > 0) {
            WebElement nameElement = driveChrome.findElement(By.xpath("//*[@id=\"product-overview\"]/tbody/tr[5]/td[1]"));
            temp[1] = nameElement.getText();
            System.out.println("desc : " + temp[1]);
        }
        return temp;
    }
}
