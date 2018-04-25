import com.google.gson.JsonArray;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mohammad on 4/23/2018.
 */
public class DigiKeyProductList {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "E://Tutorial//Selenium//chromedriver.exe");
        try {
//            WebDriver driveChrome = new ChromeDriver();
//            driveChrome.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//            driveChrome.get("https://www.digikey.com/products/en?WT.z_vanity=productsDM");
//
//            List<WebElement> productTitle = driveChrome.findElements(By.className("catfiltertopitem"));
//            List<WebElement> productLists = driveChrome.findElements(By.className("catfiltersub"));
//            System.out.println("productTitle size : "+ productTitle.size());
//            System.out.println("productList size : "+ productLists.size());
//            String[][] productTitleString = new String[productTitle.size()][];
//            for (int i = 0; i < productTitle.size(); i++) {
//                System.out.println("productTitle : "+ productTitle.get(i).getText());
//                List<WebElement> productList = productLists.get(i).findElements(By.tagName("a"));
//                productTitleString[i] =  new String[productList.size()+1];
//                productTitleString[i][0] = productTitle.get(i).getText();
//                for (int j = 0; j < productList.size(); j++) {
//                    productTitleString[i][j+1] = productList.get(j).getText();
//                }
//            }


            // write to file
//            FileOutputStream Category = new FileOutputStream("Category.json");
//            ObjectOutputStream oos = new ObjectOutputStream(Category);
//            oos.writeObject(productTitleString);
//            oos.close();

            // read file for test
            FileInputStream fis = new FileInputStream("Category.json");
            ObjectInputStream ois = new ObjectInputStream(fis);
            String[][] productTitleString = (String[][]) ois.readObject();
            ois.close();
//            for (int t = 0; t < productTitleString.length; t++) {
//               for (int j = 0; j < productTitleString[t].length; j++) {
//                   System.out.println(productTitleString[t][j]);
//               }
//            }


           // JsonArray parentJsonArray = new JsonArray();
            JSONObject parentJsonArray = new JSONObject();
            // loop through your elements
            for (int i=0; i<productTitleString.length; i++){
               // JsonArray childJsonArray = new JsonArray();
                JSONArray list = new JSONArray();
                for (int j =1; j<productTitleString[i].length; j++){
                    System.out.println();
                    list.add(productTitleString[i][j]);
                   // childJsonArray.add(productTitleString[i][j]);
                }
                parentJsonArray.put(productTitleString[i][0], list);
               // parentJsonArray.add(childJsonArray);
            }


            FileWriter file = new FileWriter("Category2.json");
            file.write(parentJsonArray.toJSONString());
            file.flush();
            // write to file
//            FileOutputStream Category = new FileOutputStream("Category2.json");
//            ObjectOutputStream oos = new ObjectOutputStream(Category);
//            oos.writeObject(parentJsonArray.toJSONString());
//            oos.close();
            System.out.print("End : ");
            System.out.print(parentJsonArray);
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

}
