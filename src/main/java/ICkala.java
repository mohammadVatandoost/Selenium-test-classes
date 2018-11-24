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
//             tantalumCapacitorsDataProcessing();

//            getCapacitorsCeramic(driveChrome);
//            ceramicCapacitorsDataProcessing();

//            getCapacitorsAluminiumElectrolyticSMD(driveChrome);
//            aluminiumElectrolyticSMDDataProcessing();

//            getCapacitorsAluminiumElectrolyticRADIAL(driveChrome);
//            aluminiumElectrolyticRADIALDataProcessing();

//            getResistorsSurfaceMount(driveChrome);
//            resistorsSurfaceMountDataProcessing();

            getResistorsDipThrough(driveChrome);
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
            String[] productNames = new String[names.size()-2]; // 2 element does not exist
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

    static void aluminiumElectrolyticSMDDataProcessing() {
        try {
            System.out.println("aluminiumElectrolyticSMDDataProcessing");
            // read javan data
            FileInputStream fis = new FileInputStream(GlobalData.ICKala_CAPACITORS_AluminiumElectrolyticSMD);
            ObjectInputStream ois = new ObjectInputStream(fis);
            String[] productNames = (String[]) ois.readObject();
            ois.close();
            PrintWriter pw = new PrintWriter(new File("commonCapAluminiumElectrolyticSMD.csv"));
            PrintWriter pw2 = new PrintWriter(new File("Capacitors_AluminiumElectrolyticSMD_Capacitors.csv"));
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            System.out.println(productNames.length);
            for(int i=1;i< productNames.length;i++) {
                System.out.println(productNames[i]);
                if(productNames[i] != null) {
                    sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");
                    sb.append(getdecodeCName(productNames[i],"cap"));sb.append(",");sb.append("0");sb.append(",");sb.append("0");sb.append(","); // name,  quantity available, (price)(buy),
                    sb.append("Semtech Electronics");sb.append(","); //manufacturer
                    sb.append("CAP ALUM "+ getdecodeCName(productNames[i],"cap") +" "+getdecodeCName(productNames[i],"voltage")+" 20% SMD");sb.append(","); // , description,
                    sb.append("Cut Tape (CT)");sb.append(",");sb.append(" - ");sb.append(","); // packing series
                    sb.append("Active");sb.append(","); //  part Status
                    sb.append("10");sb.append(","); // minimum quantity
                    sb.append("8");sb.append(","); // Category
                    sb.append(Integer.toString(i+1));sb.append(","); // row number in category
                    sb.append('\n');
                    sb2.append(getdecodeCName(productNames[i],"cap"));sb2.append(","); // Capacity
                    sb2.append("20%");sb2.append(","); // Tolerance
                    sb2.append(getdecodeCName(productNames[i],"voltage"));sb2.append(","); // Voltage - Rated
                    sb2.append("-");sb2.append(","); // ESR (Equivalent Series Resistance)
                    sb2.append("-");sb2.append(","); // Lifetime @ Temp
                    sb2.append("-55°C ~ 100°C");sb2.append(","); // Operating Temperature
                    sb2.append("Polar");sb2.append(","); // Polarization
                    sb2.append("-");sb2.append(","); // Ratings
                    sb2.append("General Purpose");sb2.append(","); // Applications
                    sb2.append("-");sb2.append(","); // Ripple Current @ Low Frequency
                    sb2.append("-");sb2.append(","); // Ripple Current @ High Frequency
                    sb2.append("-");sb2.append(","); // Impedance
                    sb2.append("-");sb2.append(","); // Lead Spacing
                    sb2.append("-");sb2.append(","); // Size / Dimension
                    sb2.append("-");sb2.append(","); // Height - Seated (Max)
                    sb2.append("-");sb2.append(","); // Surface Mount Land Size
                    sb2.append("Surface Mount");sb2.append(","); // Mounting Type
                    sb2.append("Radial, Can - SMD");sb2.append(","); // Package / Case
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

    static void getCapacitorsAluminiumElectrolyticRADIAL(WebDriver driveChrome) {
        driveChrome.get("https://www.ickala.com/186-aluminum-electrolytic-capacitor?id_category=186&n=67");
        if(driveChrome.findElements(By.className("product-name")).size()>0)
        {
            List<WebElement> names = driveChrome.findElements(By.className("product-name"));
            String[] productNames = new String[names.size()];
            for(int i=0;i< productNames.length;i++) {
                productNames[i] = names.get(i).getText();
                System.out.println(i+" : " + productNames[i]);
            }
            try {
                // store All Data
                FileOutputStream partNameFile = new FileOutputStream("ICKala_Capacitors_AluminiumElectrolyticRADIAL.json");
                ObjectOutputStream oosFound = new ObjectOutputStream(partNameFile);
                oosFound.writeObject(productNames);
                oosFound.close();
            } catch (Exception e) {
                System.err.print(e.getMessage());
            }
        }
    }

    static void aluminiumElectrolyticRADIALDataProcessing() {
        try {
            System.out.println("aluminiumElectrolyticSMDDataProcessing");
            // read javan data
            FileInputStream fis = new FileInputStream(GlobalData.ICKala_CAPACITORS_AluminiumElectrolyticRADIAL);
            ObjectInputStream ois = new ObjectInputStream(fis);
            String[] productNames = (String[]) ois.readObject();
            ois.close();
            PrintWriter pw = new PrintWriter(new File("commonCapAluminiumElectrolyticRADIAL.csv"));
            PrintWriter pw2 = new PrintWriter(new File("Capacitors_AluminiumElectrolyticRADIAL_Capacitors.csv"));
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            System.out.println(productNames.length);
            for(int i=1;i< productNames.length;i++) {
                System.out.println(productNames[i]);
                if(productNames[i] != null) {
                    sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");
                    sb.append(getdecodeCName(productNames[i],"cap"));sb.append(",");sb.append("0");sb.append(",");sb.append("0");sb.append(","); // name,  quantity available, (price)(buy),
                    sb.append("CHENG ");sb.append(","); //manufacturer
                    sb.append("CAP ALUM "+ getdecodeCName(productNames[i],"cap") +" "+getdecodeCName(productNames[i],"voltage")+" 20% RADIAL");sb.append(","); // , description,
                    sb.append("Cut Tape (CT)");sb.append(",");sb.append(" - ");sb.append(","); // packing series
                    sb.append("Active");sb.append(","); //  part Status
                    sb.append("10");sb.append(","); // minimum quantity
                    sb.append("8");sb.append(","); // Category
                    sb.append(Integer.toString(i+1));sb.append(","); // row number in category
                    sb.append('\n');
                    sb2.append(getdecodeCName(productNames[i],"cap"));sb2.append(","); // Capacity
                    sb2.append("20%");sb2.append(","); // Tolerance
                    sb2.append(getdecodeCName(productNames[i],"voltage"));sb2.append(","); // Voltage - Rated
                    sb2.append("-");sb2.append(","); // ESR (Equivalent Series Resistance)
                    sb2.append("-");sb2.append(","); // Lifetime @ Temp
                    sb2.append("-55°C ~ 100°C");sb2.append(","); // Operating Temperature
                    sb2.append("Polar");sb2.append(","); // Polarization
                    sb2.append("-");sb2.append(","); // Ratings
                    sb2.append("General Purpose");sb2.append(","); // Applications
                    sb2.append("-");sb2.append(","); // Ripple Current @ Low Frequency
                    sb2.append("-");sb2.append(","); // Ripple Current @ High Frequency
                    sb2.append("-");sb2.append(","); // Impedance
                    sb2.append("-");sb2.append(","); // Lead Spacing
                    sb2.append("-");sb2.append(","); // Size / Dimension
                    sb2.append("-");sb2.append(","); // Height - Seated (Max)
                    sb2.append("-");sb2.append(","); // Surface Mount Land Size
                    sb2.append("Surface Mount");sb2.append(","); // Mounting Type
                    sb2.append("Radial, Can");sb2.append(","); // Package / Case
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

    static void getResistorsSurfaceMount(WebDriver driveChrome) {
        driveChrome.get("https://www.ickala.com/280-resistor-smd?id_category=280&n=1462");
        if(driveChrome.findElements(By.className("product-name")).size()>0)
        {
            List<WebElement> names = driveChrome.findElements(By.className("product-name"));
            String[] productNames = new String[names.size()-6];
            for(int i=0;i< productNames.length;i++) {
                productNames[i] = names.get(i).getText();
                System.out.println(i+" : " + productNames[i]);
            }
            try {
                // store All Data
                FileOutputStream partNameFile = new FileOutputStream("ICKala_Resistors_SurfaceMount.json");
                ObjectOutputStream oosFound = new ObjectOutputStream(partNameFile);
                oosFound.writeObject(productNames);
                oosFound.close();
            } catch (Exception e) {
                System.err.print(e.getMessage());
            }
        }
    }

    static void resistorsSurfaceMountDataProcessing() {
        try {
            System.out.println("resistorsSurfaceMountDataProcessing");
            // read javan data
            FileInputStream fis = new FileInputStream(GlobalData.ICKala_RESISTORS_SURFACEMOUNT);
            ObjectInputStream ois = new ObjectInputStream(fis);
            String[] productNames = (String[]) ois.readObject();
            ois.close();
            PrintWriter pw = new PrintWriter(new File("commonResistorsSurfaceMount.csv"));
            PrintWriter pw2 = new PrintWriter(new File("Resistors_SurfaceMount.csv"));
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            System.out.println(productNames.length);
            for(int i=0;i< 1440;i++) {
                System.out.println(productNames[i]);
                if(productNames[i] != null) {
                    sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");
                    sb.append("R"+getdecodeRName(productNames[i],"res"));sb.append(",");sb.append("0");sb.append(",");sb.append("0");sb.append(","); // name,  quantity available, (price)(buy),
                    sb.append("Hottech Industrial Co ");sb.append(","); //manufacturer
                    sb.append("RES SMD "+ getdecodeCName(productNames[i],"res") +" OHM "+getdecodeRName(productNames[i],"tolerance")+" "+getdecodeRName(productNames[i],"power")+" "+getdecodeRName(productNames[i],"package"));sb.append(","); // , description,
                    sb.append("Tape & Reel (TR)");sb.append(",");sb.append("-");sb.append(","); // packing series
                    sb.append("Active");sb.append(","); //  part Status
                    sb.append("50");sb.append(","); // minimum quantity
                    sb.append("204");sb.append(","); // Category
                    sb.append(Integer.toString(i+1));sb.append(","); // row number in category
                    sb.append('\n');
                    sb2.append(getdecodeRName(productNames[i],"res")+"Ohms");sb2.append(","); // Resistors value
                    sb2.append(getdecodeRName(productNames[i],"tolerance"));sb2.append(","); // Tolerance
                    sb2.append(getdecodeRName(productNames[i],"power"));sb2.append(","); // Power
                    sb2.append("Thick Film");sb2.append(","); // Composition
                    sb2.append("Automotive AEC-Q200");sb2.append(","); // Features
                    sb2.append("-");sb2.append(","); // Temperature Coefficient
                    sb2.append("-20°C ~ 125°C");sb2.append(","); // Operating Temperature
                    sb2.append(getdecodeRName(productNames[i],"packageCase"));sb2.append(","); // Package / Case
                    sb2.append(getdecodeRName(productNames[i],"package"));sb2.append(","); // Supplier Device Package
                    sb2.append(getdecodeRName(productNames[i],"sizeDimension"));sb2.append(","); // Size / Dimension
                    sb2.append("-");sb2.append(","); // Height - Seated (Max)
                    sb2.append("2");sb2.append(","); // Number of Terminations
                    sb2.append("-");sb2.append(","); // Failure Rate
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

    static String getdecodeRName(String temp, String featrure) {
        StringBuilder sb = new StringBuilder();
        int flag = 0;
        for (int i = 0; i < temp.length(); i++) {
            if(featrure.equals("res")) {
                if(temp.charAt(i) == 'R') {
                    flag = 1 ;
                } else if(temp.charAt(i) == ' ') { break;} else if(flag ==1) {sb.append(temp.charAt(i));}
            } else if(featrure.equals("package")) {
                if(temp.charAt(i) == ' ' && flag == 0) {
                    flag = 1 ;
                } else if(temp.charAt(i) == ' ') {break;} else if(flag ==1) {sb.append(temp.charAt(i));}
            } else if(featrure.equals("tolerance")) {
                if(temp.charAt(i) == ' ' && flag == 0) {
                    flag = 1 ;
                } else if(temp.charAt(i) == ' ' && flag == 1) {
                    sb.append(temp.charAt(i));
                }
            } else if(featrure.equals("power")) {
                String packagSize = getdecodeRName(temp, "package");
                if(packagSize.equals("0201")) {
                    return "1/20W";
                } else if(packagSize.equals("0402")) {
                    return "1/16W";
                } else if(packagSize.equals("0603")) {
                    return "1/10W";
                } else if(packagSize.equals("0805")) {
                    return "1/8W";
                } else if(packagSize.equals("1206")) {
                    return "1/4W";
                } else if(packagSize.equals("1210")) {
                    return "1/2W";
                } else if(packagSize.equals("2010")) {
                    return "3/4W";
                } else if(packagSize.equals("2512")) {
                    return "1W";
                }
            } else if(featrure.equals("sizeDimension")) {
                String packagSize = getdecodeRName(temp, "package");
                if(packagSize.equals("0201")) {
                    return "0.024 L x 0.012 W (0.60mm x 0.30mm)";
                } else if(packagSize.equals("0402")) {
                    return "0.039 L x 0.020 W (1.00mm x 0.50mm)";
                } else if(packagSize.equals("0603")) {
                    return "0.063 L x 0.031 W (1.60mm x 0.80mm)";
                } else if(packagSize.equals("0805")) {
                    return "0.079 L x 0.049 W (2.00mm x 1.25mm)";
                } else if(packagSize.equals("1206")) {
                    return "0.126 L x 0.063 W (3.20mm x 1.60mm)";
                } else if(packagSize.equals("1210")) {
                    return "0.126 L x 0.098 W (3.20mm x 2.50mm)";
                } else if(packagSize.equals("2010")) {
                    return "0.197 L x 0.098 W (5.00mm x 2.50mm)";
                } else if(packagSize.equals("2512")) {
                    return "0.248 L x 0.126 W (6.30mm x 3.20mm)";
                }
            }  else if(featrure.equals("packageCase")) {
                String packagSize = getdecodeRName(temp, "package");
                if(packagSize.equals("0201")) {
                    return "0201 (0603 Metric)";
                } else if(packagSize.equals("0402")) {
                    return "0402 (1005 Metric)";
                } else if(packagSize.equals("0603")) {
                    return "0603 (1608 Metric)";
                } else if(packagSize.equals("0805")) {
                    return "0805 (2012 Metric)";
                } else if(packagSize.equals("1206")) {
                    return "1206 (3216 Metric)";
                } else if(packagSize.equals("1210")) {
                    return "1210 (3225 Metric)";
                } else if(packagSize.equals("2010")) {
                    return "2010 (5025 Metric)";
                } else if(packagSize.equals("2512")) {
                    return "2512 (6432 Metric)";
                }
            }
        }
        return sb.toString();
    }

    static void getResistorsDipThrough(WebDriver driveChrome) {
        driveChrome.get("https://www.ickala.com/347-resistor-dip?id_category=347&n=1115");
        if(driveChrome.findElements(By.className("product-name")).size()>0)
        {
            List<WebElement> names = driveChrome.findElements(By.className("product-name"));
            String[] productNames = new String[names.size()];
            for(int i=0;i< productNames.length;i++) {
                productNames[i] = names.get(i).getText();
                System.out.println(i+" : " + productNames[i]);
            }
            try {
                // store All Data
                FileOutputStream partNameFile = new FileOutputStream("ICKala_Resistors_DIPThrough.json");
                ObjectOutputStream oosFound = new ObjectOutputStream(partNameFile);
                oosFound.writeObject(productNames);
                oosFound.close();
            } catch (Exception e) {
                System.err.print(e.getMessage());
            }
        }
    }

    static String getdecodeRDipName(String temp, String featrure) {
        StringBuilder sb = new StringBuilder();
        int flag = 0;
        for (int i = 0; i < temp.length(); i++) {
            if(featrure.equals("res")) {
                if(temp.charAt(i) == 'R') {
                    flag = 1 ;
                } else if(temp.charAt(i) == '-') { break;} else if(flag ==1) {sb.append(temp.charAt(i));}
            } else if(featrure.equals("tolerance")) {
                if(temp.charAt(i) == '-' && flag == 0) {
                    flag = 1 ;
                } else if(temp.charAt(i) == '-' && flag == 1) {
                    sb.append(temp.charAt(i));
                }
            } else if(featrure.equals("power")) {
                if (temp.charAt(i) == 'W') {
                    flag = 1;
                } else if (temp.charAt(i) == '-') {
                    sb.append('W');break;
                } else if (flag == 1) {
                    sb.append(temp.charAt(i));
                }
            } else if(featrure.equals("powerKasri")) {
                String power = getdecodeRDipName(temp, "power");
                if(power.equals("0.25W")) {
                    return "1/8W";
                } else if(power.equals("0.5W")) {
                    return "1/2W";
                } else if(power.equals("0.125W")) {
                    return "1/8W";
                } else if(power.equals("1W")) {
                    return "1W";
                } else if(power.equals("2W")) {
                    return "2W";
                }
            } else if(featrure.equals("name")) {

            }
        }
        return sb.toString();
    }

    static void resistorsThroughHoleDataProcessing() {
        try {
            System.out.println("resistorsDipThroughDataProcessing");
            // read javan data
            FileInputStream fis = new FileInputStream(GlobalData.ICKala_RESISTORS_THROUGHHOLE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            String[] productNames = (String[]) ois.readObject();
            ois.close();
            PrintWriter pw = new PrintWriter(new File("commonResistorThroughHole.csv"));
            PrintWriter pw2 = new PrintWriter(new File("Resistors_ThroughHole.csv"));
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            System.out.println(productNames.length);
            for(int i=0;i< productNames.length;i++) {
                System.out.println(productNames[i]);
                if(productNames[i] != null) {
                    sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");
                    sb.append("R"+getdecodeRName(productNames[i],"res"));sb.append(",");sb.append("0");sb.append(",");sb.append("0");sb.append(","); // name,  quantity available, (price)(buy),
                    sb.append("Hottech Industrial Co ");sb.append(","); //manufacturer
                    sb.append("RES SMD "+ getdecodeCName(productNames[i],"res") +" OHM "+getdecodeRName(productNames[i],"tolerance")+" "+getdecodeRName(productNames[i],"power")+" "+getdecodeRName(productNames[i],"package"));sb.append(","); // , description,
                    sb.append("Tape & Reel (TR)");sb.append(",");sb.append("-");sb.append(","); // packing series
                    sb.append("Active");sb.append(","); //  part Status
                    sb.append("50");sb.append(","); // minimum quantity
                    sb.append("204");sb.append(","); // Category
                    sb.append(Integer.toString(i+1));sb.append(","); // row number in category
                    sb.append('\n');
                    sb2.append(getdecodeRName(productNames[i],"res")+"Ohms");sb2.append(","); // Resistors value
                    sb2.append(getdecodeRName(productNames[i],"tolerance"));sb2.append(","); // Tolerance
                    sb2.append(getdecodeRName(productNames[i],"power"));sb2.append(","); // Power
                    sb2.append("Thick Film");sb2.append(","); // Composition
                    sb2.append("Automotive AEC-Q200");sb2.append(","); // Features
                    sb2.append("-");sb2.append(","); // Temperature Coefficient
                    sb2.append("-20°C ~ 125°C");sb2.append(","); // Operating Temperature
                    sb2.append(getdecodeRName(productNames[i],"packageCase"));sb2.append(","); // Package / Case
                    sb2.append(getdecodeRName(productNames[i],"package"));sb2.append(","); // Supplier Device Package
                    sb2.append(getdecodeRName(productNames[i],"sizeDimension"));sb2.append(","); // Size / Dimension
                    sb2.append("-");sb2.append(","); // Height - Seated (Max)
                    sb2.append("2");sb2.append(","); // Number of Terminations
                    sb2.append("-");sb2.append(","); // Failure Rate
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
}
