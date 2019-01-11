import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NameConversion {
    public static void main(String[] args) {
        try {

            System.out.println("Start");


//            getCapacitorsTantalum(driveChrome);
//            tantalumCapacitorsDataProcessing();

//            getCapacitorsCeramic(driveChrome);
//            ceramicCapacitorsDataProcessing();

//            getCapacitorsAluminiumElectrolyticSMD(driveChrome);
//            aluminiumElectrolyticSMDDataProcessing();

//            getCapacitorsAluminiumElectrolyticRADIAL(driveChrome);
//            aluminiumElectrolyticRADIALDataProcessing();

//            getResistorsSurfaceMount(driveChrome);
//            resistorsSurfaceMountDataProcessing();

//            getResistorsDipThrough(driveChrome);
            resistorsThroughHoleDataProcessing();
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
            PrintWriter pw = new PrintWriter(new File("Capacitors_Ceramic_CapacitorsName.csv"));
            StringBuilder sb = new StringBuilder();
            System.out.println(productNames.length);
            for(int i=1;i< productNames.length;i++) {
                System.out.println(productNames[i]);
                if(productNames[i] != null) {
                    sb.append(getdecodeCName(productNames[i],"cap"));sb.append(",");sb.append(productNames[i]);
                    sb.append('\n');
                }
            }
            pw.write(sb.toString());
            pw.close();
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
        } else if(cap.contains(".") && cap.length()==3) {
            cap = cap.replace(".","")+"5K";
        } else if(cap.length()==1) {
            cap = cap+"05K";
        } else if(cap.length()==2) {
            cap = cap+"6K";
        } else if(cap.length()==3) {
            cap = cap.replace("0","")+"7K";
        }
//        System.out.println("cap:"+cap);
        String V = "0"+getdecodeCName(name,"voltage").replace("V","");
        if( V.equals("06.3") ) { V = "006"; cap = cap.replace("K","M"); }
        if( V.equals("02.5") ) { V = "002";  }
//        System.out.println("V:"+V);
        String packageT = getdecodeCName(name,"packageT");
//        System.out.println("packageT:"+packageT);
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
            PrintWriter pw = new PrintWriter(new File("commonCapTANTALUMName.csv"));
            StringBuilder sb = new StringBuilder();
            System.out.println(productNames.length);
            for(int i=0;i< productNames.length;i++) {
                System.out.println(productNames[i]);
                System.out.println(makeCapacitorsTantalumName(productNames[i]));
                if(productNames[i] != null) {
                    sb.append(makeCapacitorsTantalumName(productNames[i]));sb.append(",");sb.append(productNames[i]);
                    sb.append('\n');

                }
            }
            pw.write(sb.toString());
            pw.close();
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
            StringBuilder sb = new StringBuilder();
            System.out.println(productNames.length);
            for(int i=1;i< productNames.length;i++) {
                System.out.println(productNames[i]);
                if(productNames[i] != null) {
                    sb.append(getdecodeCName(productNames[i],"cap"));sb.append(",");sb.append(productNames[i]);
                    sb.append('\n');

                }
            }
            pw.write(sb.toString());
            pw.close();
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
            PrintWriter pw = new PrintWriter(new File("commonCapAluminiumElectrolyticRADIALName.csv"));
            StringBuilder sb = new StringBuilder();
            System.out.println(productNames.length);
            for(int i=1;i< productNames.length;i++) {
                System.out.println(productNames[i]);
                if(productNames[i] != null) {
                    sb.append(getdecodeCName(productNames[i],"cap"));sb.append(",");sb.append(productNames[i]);
                    sb.append('\n');
                }
            }
            pw.write(sb.toString());
            pw.close();
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
            PrintWriter pw = new PrintWriter(new File("commonResistorsSurfaceMountName.csv"));
            StringBuilder sb = new StringBuilder();
            System.out.println(productNames.length);
            for(int i=0;i< 1440;i++) {
                System.out.println(productNames[i]);
                if(productNames[i] != null) {
                    sb.append("R"+getdecodeRName(productNames[i],"res"));sb.append(",");sb.append(productNames[i]);
                    sb.append('\n');
                }
            }
            pw.write(sb.toString());
            pw.close();
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
                    flag = 2 ;
                } else if(flag == 2) {
                    sb.append(temp.charAt(i));
                }
            } else if(featrure.equals("power")) {
                if (temp.charAt(i) == 'W') {
                    flag = 1;
                } else if (temp.charAt(i) == '-' && flag == 1) {
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
                String name = "CF"+getdecodeRDipName(temp, "powerKasri").replace("/","").replace("W","");
                name = name + "JT";
                if(getdecodeRDipName(temp,"res").contains("K") && getdecodeRDipName(temp,"res").contains(".")) {
                    name = name + getdecodeRDipName(temp,"res").replace("K","").replace(".","K");
                } else if( getdecodeRDipName(temp,"res").contains("K") ) {
                    name = name + getdecodeRDipName(temp,"res");
                } else if( getdecodeRDipName(temp,"res").contains("M") && getdecodeRDipName(temp,"res").contains(".") ) {
                    name = name + getdecodeRDipName(temp,"res").replace("M","").replace(".","M");
                } else if( getdecodeRDipName(temp,"res").contains("M") ) {
                    name = name + getdecodeRDipName(temp,"res");
                } else {
                    name = name + getdecodeRDipName(temp,"res").replace(".","R");
                }
                return name;
            } else if(featrure.equals("sizeDimension")) {
                String power = getdecodeRDipName(temp, "power");
                if(power.equals("0.25W")) {
                    return "0.091 Dia x 0.236 L (2.30mm x 6.00mm)";
                } else if(power.equals("0.5W")) {
                    return "0.091 Dia x 0.256 L (2.30mm x 6.50mm)";
                } else if(power.equals("0.125W")) {
                    return "0.067 Dia x 0.130 L (1.70mm x 3.30mm)";
                } else if(power.equals("1W")) {
                    return "0.098 Dia x 0.256 L (2.50mm x 6.50mm)";
                } else if(power.equals("2W")) {
                    return "0.154 Dia x 0.394 L (3.90mm x 10.00mm)";
                }
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
            StringBuilder sb = new StringBuilder();
            System.out.println(productNames.length);
            for(int i=0;i< productNames.length;i++) {
                System.out.println(i+" : " +productNames[i]);
                if(productNames[i] != null && (!productNames[i].contains("RN70"))) {
                    sb.append(getdecodeRDipName(productNames[i], "name"));sb.append(",");sb.append(productNames[i]);
                    sb.append('\n');
                }
            }
            pw.write(sb.toString());
            pw.close();
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

    static void getResistorsDipThrough5W(WebDriver driveChrome) {
        driveChrome.get("https://www.ickala.com/349-%D9%85%D9%82%D8%A7%D9%88%D9%85%D8%AA-%D8%A2%D8%AC%D8%B1%DB%8C-5-%D9%88%D8%A7%D8%AA?id_category=349&n=106");
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
                FileOutputStream partNameFile = new FileOutputStream("ICKala_Resistors_DIPThrough5W.json");
                ObjectOutputStream oosFound = new ObjectOutputStream(partNameFile);
                oosFound.writeObject(productNames);
                oosFound.close();
            } catch (Exception e) {
                System.err.print(e.getMessage());
            }
        }
    }
}
