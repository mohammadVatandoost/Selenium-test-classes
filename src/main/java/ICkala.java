import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ICkala {
    public static void main(String[] args) {
        try {

            System.out.println("Start");
            System.setProperty("webdriver.chrome.driver", GlobalData.CHROME_DRIVER);
            // Webdriver
            WebDriver driveChrome = new ChromeDriver();//BrowserVersion.CHROME
            driveChrome.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

            getCapacitorsCeramic(driveChrome);
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

    static void ceramicCapacitorsDataProcessing() {
        try {
            // read javan data
            FileInputStream fis = new FileInputStream(GlobalData.ICKala_CAPACITORS_CERAMIC);
            ObjectInputStream ois = new ObjectInputStream(fis);
            String[] productNames = (String[]) ois.readObject();
            ois.close();
            PrintWriter pw = new PrintWriter(new File("commonCap.csv"));
            PrintWriter pw2 = new PrintWriter(new File("Cap_Ceramic_Capacitors.csv"));
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            for(int i=0;i< productNames.length;i++) {
                if(productNames[i] != null) {
                    sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");
                    sb.append(getdecodeCName(productNames[i],"name"));sb.append(",");sb.append("0");sb.append(",");sb.append("0");sb.append(","); // name,  quantity available, (price)(buy),
                    sb.append("Samsung Electronics");sb.append(","); //manufacturer
                    sb.append("CAP CER"+ getdecodeCName(productNames[i],"name") +" "+getdecodeCName(productNames[i],"voltage")+" X7R "+getdecodeCName(productNames[i],"package"));sb.append(","); // , description,
                    sb.append("Cut Tape (CT)");sb.append(",");sb.append(" - ");sb.append(","); // packing series
                    sb.append("Active");sb.append(","); //  part Status
                    sb.append("50");sb.append(","); // minimum quantity
                    sb.append("232");sb.append(","); // Category
                    sb.append(Integer.toString(i));sb.append(","); // row number in category
                    sb.append('\n');
                    sb2.append("±10%");sb.append(","); // Tolerance
                    sb2.append(getdecodeCName(productNames[i],"voltage"));sb.append(","); // Voltage - Rated
                    sb2.append("X7R");sb.append(","); // Temperature Coefficient
                    sb2.append("X7R");sb.append(","); // Operating Temperature
                    sb2.append(" - ");sb.append(","); // Features
                    sb2.append(" - ");sb.append(","); // Ratings
                    sb2.append("General Purpose");sb.append(","); // Applications
                    sb2.append(" - ");sb.append(","); // Failure Rate
                    sb2.append("Surface Mount, MLCC");sb.append(","); // Mounting Type
                    sb2.append(getdecodeCName(productNames[i],"package"));sb.append(","); // Package / Case
                    sb2.append(getdecodeCName(productNames[i],"package"));sb.append(","); // Size / Dimension
                    sb2.append(" - ");sb.append(","); // Height - Seated (Max)
                    sb2.append(" - ");sb.append(","); // Thickness (Max)
                    sb2.append(" - ");sb.append(","); // Lead Spacing
                    sb2.append(" - ");sb.append(","); // Lead Style
                }
            }
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

    static String getdecodeCName(String temp, String featrure) {
        StringBuilder sb = new StringBuilder();
        int flag = 0;
        for (int i = 0; i < temp.length(); i++) {
            if(featrure.equals("name")) {
                if(temp.charAt(i) == 'C') {
                    flag = 1 ;
                } else if(temp.charAt(i) == 'F') {sb.append('F'); break;} else if(flag ==1) {sb.append(temp.charAt(i));}
            } else if(featrure.equals("voltage")) {
                if(temp.charAt(i) == 'F') {
                    flag = 1 ;
                } else if(temp.charAt(i) == 'V') {sb.append('V'); break;} else if(flag ==1) {sb.append(temp.charAt(i));}
            } else if(featrure.equals("package")) {
                if(temp.charAt(i) == 'S') {
                    flag = 1 ;
                } else if(flag ==1) {sb.append(temp.charAt(i));}
            }
        }
        return sb.toString();
    }

    static void getCapacitorsCeramic(WebDriver driveChrome) {
        driveChrome.get("https://www.ickala.com/191-multilayer-ceramic-smd-capacitor?id_category=191&n=390");
        if(driveChrome.findElements(By.className("product-name")).size()>0)
        {
            List<WebElement> names = driveChrome.findElements(By.className("product-name"));
            String[] productNames = new String[names.size()-6]; // 6 element does not exist
            for(int i=0;i< productNames.length;i++) {
                productNames[i] = names.get(i).getText();
                System.out.println(i+" : " + productNames[i]);
            }
            try {
              // store All Data
              FileOutputStream partNameFile = new FileOutputStream("ICKala_Capacitors_Ceramic.json");
              ObjectOutputStream oosFound = new ObjectOutputStream(partNameFile);
              oosFound.writeObject(productNames);
              oosFound.close();
            } catch (Exception e) {
                System.err.print(e.getMessage());
            }
        }
    }
}
