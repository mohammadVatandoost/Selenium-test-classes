//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import com.google.gson.JsonArray;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
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
        String[] urls = {"https://www.digikey.com/product-detail/en/maxim-integrated/MAX2607EUT-T/MAX2607EUT-TTR-ND/1938023",
          "https://www.digikey.com/products/en?keywords=ATMEGA128L-8AI"
        };
        try {
            ArrayList<String> testArrayList = new ArrayList<String>();
            testArrayList.add("RF_IF_and_RFID");testArrayList.add("RF");testArrayList.add("Power_Dividers_Splitters");
            String content = readFile(GlobalData.Categories_Features, StandardCharsets.UTF_8);
            catergoriesJsonFormat = new JSONObject(content);
//            findCategoryFeatures(testArrayList);
//            temp.put("categories",JSONcontent);
//            JSONArray categories = (JSONArray)temp.get("categories");
//            System.out.println(temp.toString());
//            String[][] partNameFile = (JSONArray) ois.readObject();
//            ois.close();
            getFromDigikey(urls[1]);
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
//        getFromDigikey(urls[0]);
    }

     static JSONObject findCategoryFeatures(String categories) {
        JSONArray categoriesJsonArray = catergoriesJsonFormat.getJSONArray("categories");
        for (int i = 0; i < categoriesJsonArray.length(); i++) {
            JSONObject explrObject = categoriesJsonArray.getJSONObject(i);
            JSONArray category = explrObject.getJSONArray("category");
            String categoryText = category.getString(0);
            for (int j = 1; j < category.length(); j++) {
                categoryText = categoryText + "_" + category.getString(j);
            }
            if(categories.equals(categoryText)) {
//                    Iterator<String> keys = explrObject.keys();
//                    while(keys.hasNext()) {
//                        System.out.println("keys.hasNext()");
//                        String key = keys.next();
//                        if (explrObject.get(key) instanceof JSONArray) {
//                            System.out.println("explrObject.get(key) instanceof JSONObject");
////                            System.out.println(explrObject.getString(key));
//                        }
//                    }
                    System.out.println("founded");
                    return explrObject;
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
//               String[] data = getDataFromComponentPage(driveChrome);
                getDataFromComponentPage(driveChrome);
            } else {
                System.out.println("not founded" );
            }
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

    public static void getDataFromComponentPage(WebDriver driveChrome) {
        String[] temp;
        String category = "";
        String smallImageUrl = null;
        String datasheetUrl = null;
        String name = null;
        String description = null;
        int component_id, product_id;
        JSONObject categpriesFeatures;
        // find Category
        if(driveChrome.findElements(By.className("attributes-td-categories-link")).size() > 0) {
            List<WebElement> elements = driveChrome.findElements(By.className("attributes-td-categories-link"));
            ArrayList<String> categories = new ArrayList<String>();
            int counter = 0;
            for(WebElement ele : elements){
                if(counter == 0) {
                    category = category +ele.getText().replaceAll("/","_").replaceAll(" ","_");
                    counter++;
                } else {
                    category = category +"_"+ele.getText().replaceAll("/","_").replaceAll(" ","_");
                }
                categories.add(ele.getText().replaceAll("/","_").replaceAll(" ","_"));
            }
            category = category.replace("_-_","_").replace("(","").replace(")","");
            JSONObject commonsJSON = new JSONObject();
            JSONObject seperateJSON = new JSONObject();
            JSONObject sendJSON = new JSONObject();
            categpriesFeatures = findCategoryFeatures(category);
            if(categpriesFeatures != null) {
                component_id = categpriesFeatures.getInt("component_id");
                product_id = categpriesFeatures.getInt("product_id");
                commonsJSON.put("component_id",component_id);
                seperateJSON.put("product_id",product_id);
                // find image URL
                if(driveChrome.findElements(By.xpath("//*[@id=\"product-photo\"]/div[1]/a/img")).size() > 0) {
                    WebElement image = driveChrome.findElement(By.xpath("//*[@id=\"product-photo\"]/div[1]/a/img"));
                    smallImageUrl = image.getAttribute("src");
                }
                commonsJSON.put("ld_image",smallImageUrl);
                commonsJSON.put("hd_image","");
                // find datasheet URL
                if(driveChrome.findElements(By.className("lnkDatasheet")).size() > 0) {
                    WebElement datasheet = driveChrome.findElement(By.className("lnkDatasheet"));
                    datasheetUrl = datasheet.getAttribute("href");
                }
                commonsJSON.put("datasheet",datasheetUrl);
                // find component name
                if(driveChrome.findElements(By.tagName("h1")).size() > 0) {
                    WebElement nameElement = driveChrome.findElement(By.tagName("h1"));
                    name = nameElement.getText();
                }
                commonsJSON.put("footprint","");
                commonsJSON.put("manufacturer_part_number",name);
                commonsJSON.put("quantity_available",0);commonsJSON.put("unit_price",0);
                // find description
                if(driveChrome.findElements(By.xpath("//*[@id=\"product-overview\"]/tbody/tr[5]/td[1]")).size() > 0) {
                    WebElement nameElement = driveChrome.findElement(By.xpath("//*[@id=\"product-overview\"]/tbody/tr[5]/td[1]"));
                    description = nameElement.getText();
                }
                commonsJSON.put("description",description);
                commonsJSON.put("part_status","active");
                commonsJSON.put("original",1);
                // we crawl just ICs then minimum_quantity is 1
                commonsJSON.put("minimum_quantity",1);
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
                JSONArray categoryColumns = categpriesFeatures.getJSONArray("columns");
                for (int t = 3; t < tableRow.size()-1; t++) {
                    System.out.println(t+1 + " : " + tableRow.get(t).getText());
                    for(int k = 0 ; k< categoryColumns.length();k++) {
                       if(tableRow.get(t).getText().contains("Package / Case ")) {
                           seperateJSON.put("package_case",tableRow.get(t).getText().replace("Package / Case ",""));
                       } else if(tableRow.get(t).getText().contains("Series ")) {
                           commonsJSON.put("series",tableRow.get(t).getText().replace("Series ",""));
                       } else if(tableRow.get(t).getText().contains("Packaging ")) {
                           commonsJSON.put("packaging",tableRow.get(t).getText().replace("Packaging ",""));
                       } else if(tableRow.get(t).getText().contains("Manufacturer ")) {
                           commonsJSON.put("manufacturer",tableRow.get(t).getText().replace("Manufacturer ",""));
                       } else {
                           if (!(categoryColumns.get(k).equals("ld_image") || categoryColumns.get(k).equals("hd_image") ||
                                   categoryColumns.get(k).equals("footprint") || categoryColumns.get(k).equals("manufacturer_part_number") ||
                                   categoryColumns.get(k).equals("quantity_available") || categoryColumns.get(k).equals("unit_price") ||
                                   categoryColumns.get(k).equals("description") || categoryColumns.get(k).equals("minimum_quantity") ||
                                   categoryColumns.get(k).equals("part_status"))) {
                               String normalizeText = tableRow.get(t).getText();
                               String columnNameNormalize = splitUpperCaseFirstCharJoin((String) categoryColumns.get(k), "_", " ") + " ";
                               System.out.println( "columnNameNormalize : " + columnNameNormalize);
                               if(normalizeText.toLowerCase().contains(columnNameNormalize.toLowerCase())) {
                                   System.out.println( "Add to Json : " + (String) categoryColumns.get(k)+" : "+normalizeText.replace(columnNameNormalize,""));
                                   seperateJSON.put((String) categoryColumns.get(k),normalizeText.replace(columnNameNormalize,""));
                               }
                           }
                       }
                    }
                    temp[t+1] = tableRow.get(t).getText();
                }
                sendJSON.put("commons",commonsJSON);
                sendJSON.put("separate",seperateJSON);
                System.out.println("JSON to String: "+ sendJSON.toString());
                String url = "http://etrix.ir/api/add-parts";
                sendJSON(url, sendJSON.toString());
            } else  {
                System.out.println("category does not found: "+ category);
//                for (int t=0;t<categories.size();t++) {
//                    System.out.println(categories.get(t));
//                }
            }
        }
//        return temp;
    }

    public static void sendJSON(String urlRequest, String message) {
        String payload = message;
        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_FORM_URLENCODED);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(urlRequest);
        request.setEntity(entity);
        try {
            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());
        }catch (Exception ex) {

            //handle exception here

        } finally {
            //Deprecated
            //httpClient.getConnectionManager().shutdown();
        }
        //Deprecated
//HttpClient httpClient = new DefaultHttpClient();

//        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
//
//        try {
//
//            HttpPost request = new HttpPost(urlRequest);
//            StringEntity params =new StringEntity(message);
//            request.addHeader("content-type", "application/x-www-form-urlencoded");
//            request.setEntity(params);
//            HttpResponse response = httpClient.execute(request);
//
//            //handle response here...
//
//        }catch (Exception ex) {
//
//            //handle exception here
//
//        } finally {
//            //Deprecated
//            //httpClient.getConnectionManager().shutdown();
//        }
    }

    public static String splitUpperCaseFirstCharJoin(String name, String splitString, String joinString) {
        String[] parts = name.split(splitString);
        for(int i=0; i<parts.length;i++) {
            parts[i] =  parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1);
        }
        return String.join(joinString, parts);
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
