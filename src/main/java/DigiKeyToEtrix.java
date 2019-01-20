//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import com.google.gson.JsonArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DigiKeyToEtrix {

    public static JSONObject catergoriesJsonFormat;

    public static void main(String[] args) {
        String[] urls = {"https://www.digikey.com/product-detail/en/maxim-integrated/MAX2607EUT-T/MAX2607EUT-TTR-ND/1938023"};
        try {
            ArrayList<String> testArrayList = new ArrayList<String>();
            testArrayList.add("RF_IF_and_RFID");testArrayList.add("RF");testArrayList.add("Power_Dividers_Splitters");
            String content = readFile(GlobalData.Categories_Features, StandardCharsets.UTF_8);
            catergoriesJsonFormat = new JSONObject(content);
            findCategoryFeatures(testArrayList);
//            temp.put("categories",JSONcontent);
//            JSONArray categories = (JSONArray)temp.get("categories");
//            System.out.println(temp.toString());
//            String[][] partNameFile = (JSONArray) ois.readObject();
//            ois.close();
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
//        getFromDigikey(urls[0]);
    }

     static JSONObject findCategoryFeatures(ArrayList<String> categories) {
        JSONArray categoriesJsonArray = catergoriesJsonFormat.getJSONArray("categories");
        for (int i = 0; i < categoriesJsonArray.length(); i++) {
            JSONObject explrObject = categoriesJsonArray.getJSONObject(i);
            JSONArray category = explrObject.getJSONArray("category");
            if(categories.size() == category.length()) {
                boolean equality =true;
                for (int j = 0; j < category.length(); j++) {
                    if (!category.getString(j).equals(categories.get(j))) {
                        equality = false;
                    }

                }
                if(equality) {
                    System.out.println("founded");
                    return explrObject;
                }
            }
        }
        return null;
    }

     static String readFile(String path, Charset encoding)
        throws IOException
    {
       byte[] encoded = Files.readAllBytes(Paths.get(path));
       return new String(encoded, encoding);
    }

    public static void getFromDigikey(String url) {
        System.setProperty("webdriver.chrome.driver", GlobalData.CHROME_DRIVER);
        try {
            WebDriver driveChrome = new ChromeDriver();
            driveChrome.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driveChrome.get(url);
            if(driveChrome.findElements(By.xpath("//*[@id=\"product-attribute-title\"]")).size() > 0) {
               String[] data = getDataFromComponentPage(driveChrome);
            } else {
                System.out.println("not founded" );
            }
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

    public static String[] getDataFromComponentPage(WebDriver driveChrome) {
        String[] temp;
        String category = "";
        String smallImageUrl = null;
        String datasheetUrl = null;
        String name = null;
        // find Category
        if(driveChrome.findElements(By.className("attributes-td-categories-link")).size() > 0) {
            List<WebElement> elements = driveChrome.findElements(By.className("attributes-td-categories-link"));
            ArrayList<String> categories = new ArrayList<String>();
            for(WebElement ele : elements){
                category = category + ele.getText();
                categories.add(ele.getText());
            }

        }
        // find image URL
        if(driveChrome.findElements(By.xpath("//*[@id=\"product-photo\"]/div[1]/a/img")).size() > 0) {
            WebElement image = driveChrome.findElement(By.xpath("//*[@id=\"product-photo\"]/div[1]/a/img"));
            smallImageUrl = image.getAttribute("src");
        }
        // find datasheet URL
        if(driveChrome.findElements(By.className("lnkDatasheet")).size() > 0) {
            WebElement datasheet = driveChrome.findElement(By.className("lnkDatasheet"));
            datasheetUrl = datasheet.getAttribute("href");
        }
        // find component name
        if(driveChrome.findElements(By.tagName("h1")).size() > 0) {
            WebElement nameElement = driveChrome.findElement(By.tagName("h1"));
            name = nameElement.getText();
        }
        List<WebElement> tableRow = driveChrome.findElement(By.xpath("//*[@id=\"product-attribute-table\"]/tbody")).findElements(By.tagName("tr"));
        temp = new String[tableRow.size()];
        temp[0] = category;
        temp[1] = smallImageUrl;
        temp[2] = datasheetUrl;
        temp[3] = name ;
        System.out.println(0 + " : " + category);
        System.out.println(1 + " : " + smallImageUrl);
        System.out.println(2 + " : " + datasheetUrl);
        System.out.println(3 + " : " + name);
        for (int t = 3; t < tableRow.size()-1; t++) {
            System.out.println(t+1 + " : " + tableRow.get(t).getText());
            temp[t+1] = tableRow.get(t).getText();
        }
        return temp;
    }

    public static String imageURL(WebElement image) {
        String smallImage = image.getAttribute("src");
        System.out.println("smallImageURL : " +smallImage);
        return smallImage;
    }

    public static String datasheetURL(WebDriver driveChrome) {
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
}
