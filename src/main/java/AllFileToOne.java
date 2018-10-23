import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Created by Mohammad on 6/6/2018.
 */
public class AllFileToOne {
    public static void main(String[] args) {

        try {
            // define variable
            int dataCounter = 0 ;
            String[] AllParts = new String[14000];
            // get all files name in  directory
            final File folder = new File("E:\\Tutorial\\Selenium\\Code\\Selenium\\DigiKey\\notFounded");
            System.out.println("Start");


            for (final File file : folder.listFiles()) {
                    System.out.println(file.getName());
                    // read every file in loop
                    FileInputStream fis = new FileInputStream("E:\\Tutorial\\Selenium\\Code\\Selenium\\DigiKey\\notFounded\\"+file.getName());
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    String[] partNameFile = (String[]) ois.readObject();
                    ois.close();
                    // push all the data in one 2dArray
                    for(int i=0;i<partNameFile.length;i++) {
                        if(partNameFile[i] != null) {
                            AllParts[dataCounter] = partNameFile[i];
                            dataCounter++;
                        }
                }
            }
            System.out.println("All parts number : " + dataCounter);
            // store all data
//            FileOutputStream AllData = new FileOutputStream("AllNotFoundedData.json");
//            ObjectOutputStream oos = new ObjectOutputStream(AllData);
//            oos.writeObject(AllParts);
//            oos.close();
            // search data in digikey
            System.setProperty("webdriver.chrome.driver", "E://Tutorial//Selenium//chromedriver.exe");
            // Webdriver
            WebDriver driveChrome = new ChromeDriver();//BrowserVersion.CHROME
            driveChrome.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            driveChrome.get("https://www.digikey.com/products/en/integrated-circuits-ics/logic-buffers-drivers-receivers-transceivers/704?FV=ffe002c0&quantity=&ColumnSort=0&page=1&k=74LVT16245BDL&pageSize=25");
            // data Pack Counter
            int pacckCounter = 64;
            for(int i =12800; i<AllParts.length;i++) {
                // not founded parts
                String[] notFoundedPartName = new String[200];
                int notFoundedCounter = 0;
                int foundedCounter = 0;
                // make AllParts
                String[][] partsPack = new String[200][];
                for(int j=0;j<200;j++) {
                    System.out.println(i+" : "+AllParts[i] );
                    if( (AllParts[i] != null) && ( !AllParts[i].substring(AllParts[i].length() - 1).equals(".") ) )   {
                        // id = pagelayout_0_content_2__searchText
                        driveChrome.findElement(By.xpath("//*[@id=\"_idOfSearchbarPleaseIgnore\"]/div[2]/div[3]/input")).sendKeys(AllParts[i]);
                        // id = pagelayout_0_content_2__searchImg
                        driveChrome.findElement(By.xpath("//*[@id=\"_idOfSearchbarPleaseIgnore\"]/div[2]/div[4]/input")).click();
                        if (driveChrome.findElements(By.id("lnkPart")).size() > 0) {
                            // find table
                            WebElement table = driveChrome.findElement(By.id("lnkPart"));
                            // find all rows
                            List<WebElement> tableRow = table.findElements(By.tagName("tr"));
                            AllFileToOne useDownloadImages = new AllFileToOne();
                            String category = driveChrome.findElement(By.xpath("//*[@id=\"content\"]/h1")).getText();
                            System.out.println("Category : " + category);
                            // find all columns in a row
                            List<WebElement> tableColumn = tableRow.get(0).findElements(By.tagName("td"));
                            //download image and datasheet URL
                            String smallImageUrl = null;
                            String datasheetUrl = useDownloadImages.datasheetURL(driveChrome);
                            if(tableColumn.get(2).findElements(By.className("pszoomer")).size()>0) {
                                WebElement temp = tableColumn.get(2).findElement(By.className("pszoomer"));
                                smallImageUrl = useDownloadImages.imageURL(temp);
                            }
                            partsPack[foundedCounter] = new String[tableColumn.size()];
                            partsPack[foundedCounter][0] = category;
                            partsPack[foundedCounter][1] = smallImageUrl;
                            partsPack[foundedCounter][2] = datasheetUrl;
                            for (int t = 3; t < tableColumn.size(); t++) {
                                System.out.println(t + " : " + tableColumn.get(t).getText());
                                partsPack[foundedCounter][t] = tableColumn.get(t).getText();
                            }
                            foundedCounter++;
                        } else if(driveChrome.findElements(By.xpath("//*[@id=\"exactPartList\"]/tbody/tr/td[2]/span[1]/a")).size() > 0) {
                            driveChrome.findElement(By.xpath("//*[@id=\"exactPartList\"]/tbody/tr/td[2]/span[1]/a")).click();
                            if(driveChrome.findElements(By.xpath("//*[@id=\"prod-att-table\"]")).size() > 0) {
                                String category = null;
                                String smallImageUrl = null;
                                String datasheetUrl = null;
                                // find Category
                                if(driveChrome.findElements(By.xpath("//*[@id=\"sharebread\"]")).size() > 0) {
                                    category = driveChrome.findElement(By.xpath("//*[@id=\"sharebread\"]/div[1]/a[2]")).getText() + driveChrome.findElement(By.xpath("//*[@id=\"sharebread\"]/div[1]/a[3]")).getText() ;
                                }
                                // find image URL
                                if(driveChrome.findElements(By.className("bota-image-large")).size() > 0) {
                                    WebElement image = driveChrome.findElement(By.xpath("//*[@id=\"product-photo-wrapper\"]/a/img"));
                                    smallImageUrl = image.getAttribute("src");
                                }
                                // find datasheet URL
                                if(driveChrome.findElements(By.className("lnkDatasheet")).size() > 0) {
                                    WebElement datasheet = driveChrome.findElement(By.className("lnkDatasheet"));
                                    datasheetUrl = datasheet.getAttribute("href");
                                }
                                List<WebElement> tableRow = driveChrome.findElement(By.xpath("//*[@id=\"prod-att-table\"]/tbody")).findElements(By.tagName("tr"));
                                partsPack[foundedCounter] = new String[tableRow.size()+2];
                                partsPack[foundedCounter][0] = category;
                                partsPack[foundedCounter][1] = smallImageUrl;
                                partsPack[foundedCounter][2] = datasheetUrl;
                                System.out.println(0 + " : " + category);
                                System.out.println(1 + " : " + smallImageUrl);
                                System.out.println(2 + " : " + datasheetUrl);
                                for (int t = 1; t < tableRow.size(); t++) {
                                    System.out.println(t+2 + " : " + tableRow.get(t).getText());
                                    partsPack[foundedCounter][t+2] = tableRow.get(t).getText();
                                }
                                foundedCounter++;
                            } else {
                                notFoundedPartName[notFoundedCounter] = AllParts[i];
                                notFoundedCounter++;
                                System.out.println("not find : " + AllParts[i]);
                                driveChrome.navigate().back();
                            }
                        } else if(driveChrome.findElements(By.id("prod-att-title-row")).size() > 0) {
                            String category = null;
                            String smallImageUrl = null;
                            String datasheetUrl = null;
                            // find Category
                            if(driveChrome.findElements(By.xpath("//*[@id=\"sharebread\"]")).size() > 0) {
                                category = driveChrome.findElement(By.xpath("//*[@id=\"sharebread\"]/div[1]/a[2]")).getText() + driveChrome.findElement(By.xpath("//*[@id=\"sharebread\"]/div[1]/a[3]")).getText() ;
                            }
                            // find image URL
                            if(driveChrome.findElements(By.className("bota-image-large")).size() > 0) {
                                WebElement image = driveChrome.findElement(By.xpath("//*[@id=\"product-photo-wrapper\"]/a/img"));
                                smallImageUrl = image.getAttribute("src");
                            }
                            // find datasheet URL
                            if(driveChrome.findElements(By.className("lnkDatasheet")).size() > 0) {
                                WebElement datashet = driveChrome.findElement(By.className("lnkDatasheet"));
                                datasheetUrl = datashet.getAttribute("href");
                            }
                            List<WebElement> tableRow = driveChrome.findElement(By.xpath("//*[@id=\"prod-att-table\"]/tbody")).findElements(By.tagName("tr"));
                            partsPack[foundedCounter] = new String[tableRow.size()+2];
                            partsPack[foundedCounter][0] = category;
                            partsPack[foundedCounter][1] = smallImageUrl;
                            partsPack[foundedCounter][2] = datasheetUrl;
                            System.out.println(0 + " : " + category);
                            System.out.println(1 + " : " + smallImageUrl);
                            System.out.println(2 + " : " + datasheetUrl);
                            for (int t = 1; t < tableRow.size(); t++) {
                                System.out.println(t+2 + " : " + tableRow.get(t).getText());
                                partsPack[foundedCounter][t+2] = tableRow.get(t).getText();
                            }
                            foundedCounter++;
                        } else {
                            notFoundedPartName[notFoundedCounter] = AllParts[i];
                            notFoundedCounter++;
                            System.out.println("not find : " + AllParts[i]);
                            driveChrome.navigate().back();
                        }
                    }
                    i++;
                }
                // store All Data
                FileOutputStream partNameFile = new FileOutputStream("SecondSearch"+pacckCounter+".json");
                ObjectOutputStream oosFound = new ObjectOutputStream(partNameFile);
                oosFound.writeObject(partsPack);
                oosFound.close();
                // store notFoundedData
                FileOutputStream notFoundedPartNameFile = new FileOutputStream("SecondSearchNotFound"+pacckCounter+".json");
                ObjectOutputStream oosNotFoundedPartNameFile = new ObjectOutputStream(notFoundedPartNameFile);
                oosNotFoundedPartNameFile.writeObject(notFoundedPartName);
                oosNotFoundedPartNameFile.close();
                pacckCounter++;
                System.out.println("pacckCounter : " + pacckCounter);
            }
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

    public String imageURL(WebElement image) {
        String smallImage = image.getAttribute("src");
        System.out.println("smallImageURL : " +smallImage);
        return smallImage;
    }
    public String datasheetURL(WebDriver driveChrome) {
        List<WebElement> dataSheets = driveChrome.findElements(By.className("lnkDatasheet"));
        String datasheet = null;
        try {
            for (int j = 0; j < dataSheets.size(); j++) {
                datasheet = dataSheets.get(j).getAttribute("href");
                System.out.println("dataSheets : "+datasheet);
                Thread.sleep(10L);
                j = dataSheets.size();
            }
        } catch (InterruptedException e) {

        }
        return datasheet;
    }

    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }
}
