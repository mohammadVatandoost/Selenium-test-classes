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

//            getCapacitorsTantalum(driveChrome);
              tantalumCapacitorsDataProcessing();

//            getCapacitorsCeramic(driveChrome);
            ceramicCapacitorsDataProcessing();
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

    static void ceramicCapacitorsDataProcessing() {
        try {
            System.out.println("ceramicCapacitorsDataProcessing");
            // read javan data
            FileInputStream fis = new FileInputStream(GlobalData.ICKala_CAPACITORS_CERAMIC);
            ObjectInputStream ois = new ObjectInputStream(fis);
            String[] productNames = (String[]) ois.readObject();
            ois.close();
            PrintWriter pw = new PrintWriter(new File("commonCap.csv"));
            PrintWriter pw2 = new PrintWriter(new File("Capacitors_Ceramic_Capacitors.csv"));
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            System.out.println(productNames.length);
            for(int i=1;i< productNames.length;i++) {
                System.out.println(productNames[i]);
                if(productNames[i] != null) {
                    sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");
                    sb.append(getdecodeCName(productNames[i],"cap"));sb.append(",");sb.append("0");sb.append(",");sb.append("0");sb.append(","); // name,  quantity available, (price)(buy),
                    sb.append("Samsung Electronics");sb.append(","); //manufacturer
                    sb.append("CAP CER"+ getdecodeCName(productNames[i],"cap") +" "+getdecodeCName(productNames[i],"voltage")+" X7R "+getdecodeCName(productNames[i],"package"));sb.append(","); // , description,
                    sb.append("Cut Tape (CT)");sb.append(",");sb.append(" - ");sb.append(","); // packing series
                    sb.append("Active");sb.append(","); //  part Status
                    sb.append("50");sb.append(","); // minimum quantity
                    sb.append("236");sb.append(","); // Category
                    sb.append(Integer.toString(i+1));sb.append(","); // row number in category
                    sb.append('\n');
                    sb2.append(getdecodeCName(productNames[i],"cap"));sb2.append(","); // Capacity
                    sb2.append("10%");sb2.append(","); // Tolerance
                    sb2.append(getdecodeCName(productNames[i],"voltage"));sb2.append(","); // Voltage - Rated
                    sb2.append("X7R");sb2.append(","); // Temperature Coefficient
                    sb2.append("-55°C ~ 100°C");sb2.append(","); // Operating Temperature
                    sb2.append(" - ");sb2.append(","); // Features
                    sb2.append(" - ");sb2.append(","); // Ratings
                    sb2.append("General Purpose");sb2.append(","); // Applications
                    sb2.append(" - ");sb2.append(","); // Failure Rate
                    sb2.append("Surface Mount, MLCC");sb2.append(","); // Mounting Type
                    sb2.append(getdecodeCName(productNames[i],"package"));sb2.append(","); // Package / Case
                    if(getdecodeCName(productNames[i],"package").equals("0402")) {
                        sb2.append("0.039 L x 0.020 W (1.00mm x 0.50mm)");sb2.append(","); // Size / Dimension
                    } else if(getdecodeCName(productNames[i],"package").equals("0603")) {
                        sb2.append("0.064 L x 0.035 W (1.63mm x 0.89mm)");sb2.append(","); // Size / Dimension
                    } else if(getdecodeCName(productNames[i],"package").equals("0805")) {
                        sb2.append("0.079 L x 0.049 W (2.00mm x 1.25mm)");sb2.append(","); // Size / Dimension
                    } else if(getdecodeCName(productNames[i],"package").equals("1206")) {
                        sb2.append("0.126 L x 0.063 W (3.20mm x 1.60mm)");sb2.append(","); // Size / Dimension
                    } else if(getdecodeCName(productNames[i],"package").equals("1210")) {
                        sb2.append("0.126 L x 0.098 W (3.20mm x 2.50mm)");sb2.append(","); // Size / Dimension
                    }
                    sb2.append(" - ");sb2.append(","); // Height - Seated (Max)
                    sb2.append(" - ");sb2.append(","); // Thickness (Max)
                    sb2.append(" - ");sb2.append(","); // Lead Spacing
                    sb2.append(" - ");sb2.append(","); // Lead Style
                    sb2.append('\n');
                }
            }
            pw.write(sb.toString());
            pw.close();
            pw2.write(sb2.toString());
            pw2.close();
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

    static String getdecodeCName(String temp, String featrure) {
        StringBuilder sb = new StringBuilder();
        int flag = 0;
        for (int i = 0; i < temp.length(); i++) {
            if(featrure.equals("cap")) {
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
            } else if(featrure.equals("packageT")) {
                sb.append(temp.charAt(temp.length()-1));break;
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

    static void getCapacitorsTantalum(WebDriver driveChrome) {
        driveChrome.get("https://www.ickala.com/190-tantalium-smd-capacitor?id_category=190&n=186");
        if(driveChrome.findElements(By.className("product-name")).size()>0)
        {
            List<WebElement> names = driveChrome.findElements(By.className("product-name"));
            String[] productNames = new String[names.size()-8]; // 6 element does not exist
            for(int i=0;i< productNames.length;i++) {
                productNames[i] = names.get(i).getText();
                System.out.println(i+" : " + productNames[i]);
            }
            try {
                // store All Data
                FileOutputStream partNameFile = new FileOutputStream("ICKala_Capacitors_Tantalum.json");
                ObjectOutputStream oosFound = new ObjectOutputStream(partNameFile);
                oosFound.writeObject(productNames);
                oosFound.close();
            } catch (Exception e) {
                System.err.print(e.getMessage());
            }
        }
    }

    static String  makeCapacitorsTantalumName(String name) {
        String cap = getdecodeCName(name,"cap");
        cap = cap.replace("uF","");
        if(cap.contains(".") && cap.length()>3) {
            cap = cap.replace(".","")+"5K";
        } else if(cap.length()==1) {
           cap = cap+"05K";
        } else if(cap.length()==2) {
            cap = cap+"6K";
        } else if(cap.length()==3) {
            cap = cap.replace("0","")+"7K";
        }
        String V = getdecodeCName(name,"voltage").replace("V","");
        String packageT = getdecodeCName(name,"packageT");
        return "TAJ"+packageT+cap+V+"RNJ";
    }

    static void tantalumCapacitorsDataProcessing() {
        try {
            System.out.println("tantalumCapacitorsDataProcessing");
            // read javan data
            FileInputStream fis = new FileInputStream(GlobalData.ICKala_CAPACITORS_TANTALUM);
            ObjectInputStream ois = new ObjectInputStream(fis);
            String[] productNames = (String[]) ois.readObject();
            ois.close();
            PrintWriter pw = new PrintWriter(new File("commonCapTANTALUM.csv"));
            PrintWriter pw2 = new PrintWriter(new File("Capacitors_Tantalum_Capacitors.csv"));
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            System.out.println(productNames.length);
            for(int i=0;i< productNames.length;i++) {
                System.out.println(productNames[i]);
                if(productNames[i] != null) {
                    sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");
                    sb.append(makeCapacitorsTantalumName("name"));sb.append(",");sb.append("0");sb.append(",");sb.append("0");sb.append(","); // name,  quantity available, (price)(buy),
                    sb.append("AVX Corporation");sb.append(","); //manufacturer
                    sb.append("CAP TANT "+ getdecodeCName(productNames[i],"cap") +" 20% "+getdecodeCName(productNames[i],"voltage")+"  "+getdecodeCName(productNames[i],"packageT"));sb.append(","); // , description,
                    sb.append("Cut Tape (CT)");sb.append(",");sb.append("TAJ");sb.append(","); // packing series
                    sb.append("Active");sb.append(","); //  part Status
                    sb.append("10");sb.append(","); // minimum quantity
                    sb.append("232");sb.append(","); // Category
                    sb.append(Integer.toString(i+1));sb.append(","); // row number in category
                    sb.append('\n');
                    sb2.append(getdecodeCName(productNames[i],"cap"));sb2.append(","); // Capacity
                    sb2.append("20%");sb2.append(","); // Tolerance
                    sb2.append(getdecodeCName(productNames[i],"voltage"));sb2.append(","); // Voltage - Rated
                    sb2.append("Molded");sb2.append(","); // Type
                    sb2.append("-");sb2.append(","); // ESR (Equivalent Series Resistance)
                    sb2.append("-55°C ~ 125°C");sb2.append(","); // Operating Temperature
                    sb2.append("Surface Mount");sb2.append(","); // Mounting Type
                    sb2.append("-");sb2.append(","); // Package / Case
                    sb2.append("-");sb2.append(","); // Size / Dimension
                    sb2.append(" - ");sb2.append(","); // Height - Seated (Max)
                    sb2.append(" - ");sb2.append(","); // Lead Spacing
                    sb2.append(getdecodeCName(productNames[i],"packageT"));sb2.append(","); // Manufacturer Size Code
                    sb2.append("-");sb2.append(","); // Ratings
                    sb2.append("General Purpose");sb2.append(","); // features
                    sb2.append(" - ");sb2.append(","); // Failure Rate
                    sb2.append('\n');
                }
            }
            pw.write(sb.toString());
            pw.close();
            pw2.write(sb2.toString());
            pw2.close();
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

    static void getCapacitorsAluminiumElectrolyticSMD(WebDriver driveChrome) {
        driveChrome.get("https://www.ickala.com/187-Aluminium-Electrolytic-Capacitor-SMD?id_category=187&n=59");
        if(driveChrome.findElements(By.className("product-name")).size()>0)
        {
            List<WebElement> names = driveChrome.findElements(By.className("product-name"));
            String[] productNames = new String[names.size()-2]; // 6 element does not exist
            for(int i=0;i< productNames.length;i++) {
                productNames[i] = names.get(i).getText();
                System.out.println(i+" : " + productNames[i]);
            }
            try {
                // store All Data
                FileOutputStream partNameFile = new FileOutputStream("ICKala_Capacitors_AluminiumElectrolyticSMD.json");
                ObjectOutputStream oosFound = new ObjectOutputStream(partNameFile);
                oosFound.writeObject(productNames);
                oosFound.close();
            } catch (Exception e) {
                System.err.print(e.getMessage());
            }
        }
    }
}
