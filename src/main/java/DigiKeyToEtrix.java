import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DigiKeyToEtrix {
    public static void main(String[] args) {

    }

    public static void getFromDigikey(String url) {
        System.setProperty("webdriver.chrome.driver", "E://Tutorial//Selenium//chromedriver.exe");
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
            for(WebElement ele : elements){
                category = category + ele.getText();
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
