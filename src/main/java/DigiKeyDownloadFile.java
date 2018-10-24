import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mohammad on 10/20/2018.
 */
public class DigiKeyDownloadFile {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", GlobalData.CHROME_DRIVER);
        try {
          // read every file in loop
          FileInputStream fis = new FileInputStream(GlobalData.DigiKey_Directory+"AllICParts.json");
          ObjectInputStream ois = new ObjectInputStream(fis);
          String[][] partNameFile = (String[][]) ois.readObject();
          ois.close();
          int counter = 0;
          System.out.println("partNameFile length"+partNameFile.length);
          for(int i=1565;i<partNameFile.length;i++){
              System.out.println("name "+i+" : "+partNameFile[i][4]);
              if(partNameFile[i][1] != null) {
                  System.out.println("image "+i+" : "+partNameFile[i][1]);
                  downloadImages(partNameFile[i][1],partNameFile[i][4]);
              }
              if(partNameFile[i][2] != null) {
                  System.out.println("dataSheet " + i + " : " + partNameFile[i][2]);
//              downloadUsingHTTP(partNameFile[i][1],partNameFile[i][3]);
                  try {
                      FileUtils.copyURLToFile(new URL(partNameFile[i][2]), new File(partNameFile[i][4] + ".pdf"));
                  } catch (Exception e) {
                      System.err.print(e.getMessage());
                      downloadUsingChrome(partNameFile[i][2], partNameFile[i][4]);
                      counter++;
                      System.out.println("counter  : " + counter);
                  }
              }
//              downloadUsingChrome(partNameFile[i][1],partNameFile[i][3]+".pdf");

          }
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }

    }

    public static void downloadUsingChrome(String urlStr, String file) throws IOException {
        // Webdriver
        WebDriver driveChrome = new ChromeDriver();//BrowserVersion.CHROME
        driveChrome.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driveChrome.get(urlStr);
        try {
            Thread.sleep(3000L);
//            Actions builder = new Actions(driveChrome);
//            WebElement Element = driveChrome.findElement(By.xpath("/html/body"));
//            builder.moveToElement(Element, 100, 100).build().perform();
//            Thread.sleep(1000L);
//            if(driveChrome.findElements(By.xpath("//*[@id=\"icon\"]")).size() >0) {
//                driveChrome.findElement(By.xpath("//*[@id=\"icon\"]")).click();System.out.println("Founded ");
//            } else {
//                System.out.println("Not Founded");
//            }
        } catch (Exception e) {

         }
    }

    public static void downloadUsingHTTP(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        InputStream in = conn.getInputStream();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int counter = 0;
        int i;
        while((i = in.read())!= -1)
        {
            bos.write(i);
            counter++;
        }
        System.out.println("counter : "+ counter);
        byte [] b = bos.toByteArray();

        FileOutputStream fos = new FileOutputStream(file+".pdf");
        fos.write(b);
        fos.close();


        conn.disconnect();
    }

    public static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file+".pdf");
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

    public static void downloadUsingStream(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file+".pdf");
        byte[] buffer = new byte[10240];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

    public static void downloadImages(String imageUrl, String name) {
            try {
                URL smallImageURL = new URL(imageUrl);
                BufferedImage saveSmallImage = ImageIO.read(smallImageURL);
                name = name.replace("/","-");
                ImageIO.write(saveSmallImage, "jpg", new File(name+".jpg"));
            } catch (IOException e) {

            }
    }
}
