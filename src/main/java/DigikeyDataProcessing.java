import com.google.common.base.CaseFormat;

import java.io.*;
import java.util.*;

/**
 * Created by Mohammad on 10/16/2018.
 */
public class DigikeyDataProcessing {
    public static void main(String[] args) {
        String[][] thirdAllParts = getFileData(GlobalData.THIRD_SEARCH_FOUNDED);
        String[][] firstAllParts = getFileData(GlobalData.FIRST_SEARCH_FOUNDED);
        String[][] allParts = concat2DArray(firstAllParts,thirdAllParts);
        System.out.println("allParts.length : " + allParts.length);
//        try {
//          // store All IC Part
//          FileOutputStream partNameFile = new FileOutputStream("AllICParts.json");
//          ObjectOutputStream oos = new ObjectOutputStream(partNameFile);
//          oos.writeObject(allParts);
//          oos.close();
//        } catch (Exception e) {
//            System.err.print(e.getMessage());
//        }

        Set<String> allPartsCategorySet = sortCategory(allParts);
        System.out.println("allPartsCategorySet.size() : " + allPartsCategorySet.size());
        String[] allPartsCategory = allPartsCategorySet.toArray(new String[allPartsCategorySet.size()]);
        System.out.println("allPartsCategory.length : " + allPartsCategory.length);
        Arrays.sort(allPartsCategory);

        CategoryInfo[] categoryInfo = new CategoryInfo[allPartsCategory.length];
        for(int i=0;i<allPartsCategory.length;i++) {
//            System.out.println("allPartsCategory "+ i + " : "+ allPartsCategory[i]);
            categoryInfo[i] = checkCategory(allPartsCategory[i]);
//new CategoryInfo();
//            categoryInfo[i].firstCategory = allPartsCategory[i];
        }

        int counter2 = 0;
        for(int i=0;i<allParts.length;i++) {
            int flag = 0 ;String categoryTemp = "";
            for(int j =0;j<categoryInfo.length;j++) {
                if( checkEqualityCategories(allParts[i][0],categoryInfo[j])) {
                    categoryInfo[j].addProduct(allParts[i]);
                    flag++;
                    if(flag ==1 ){categoryTemp = categoryInfo[j].firstCategory+" * "+categoryInfo[j].secondCategory+" * "+categoryInfo[j].thirdCategory;}
                    if(flag == 2) {
//                        System.out.println("two category : "+allParts[i][4]);
//                        System.out.println(allParts[i][0]);
//                        System.out.println(categoryTemp);
//                        System.out.println(categoryInfo[j].firstCategory+" * "+categoryInfo[j].secondCategory+" * "+categoryInfo[j].thirdCategory);
                    }
                }
            }
            if(flag == 0) {counter2++;
//            System.out.println("Not Found : "+allParts[i][4]);System.out.println("category : "+allParts[i][0]);
            }
        }
//        System.out.println("*************counter2 : "+counter2);
//        findProductCategory("TPS73633DBVT",categoryInfo);
//        storeCategoryToCSV(categoryInfo);
        int counter = 0;
        for(int i =0;i<categoryInfo.length;i++) {
            System.out.println("categoryInfo "+ i + " : "+ categoryInfo[i].firstCategory + " * "+ categoryInfo[i].secondCategory + " * "
                    + categoryInfo[i].thirdCategory + " * " + categoryInfo[i].counter + " * " + categoryInfo[i].productList.size());
//            counter = counter + categoryInfo[i].productList.size();
//
//            storeProductsToCSV(categoryInfo[i]);
        }
//        System.out.println("counter : "+counter);
//        test(categoryInfo[13]); // from 11
//        storeProductsToCSV(categoryInfo[12]);
        storeProductsToCSV(categoryInfo[57]); // 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 58 59 60 61 62 64 65 66 67 71 72
        // 73 74 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100 101 102 103 104 105 106 107 108 109 110 111
        // 112 113 114 115 116 117 118 119 120 121 122 123 124 125 126 127 128 129 130 131 132 133 134 135 136 137 138 139 140 141 142
        // 143 144 145 146 147 148 149 150 151 152 153 154 155 156 157 158 159 160 161 162 163 164 165 166 167 168 169 170 171 172
        // 173 174
        // later 63 68 69 70
        // not 37
        // check 57
//        storeAllCommonProductDataToCSV(categoryInfo);
//        Set<String> thirdSearchCategory = sortCategory(thirdAllParts);
//        Set<String> secondSearchCategory = sortCategory(firstAllParts);
//        System.out.println("secondSearch");
//        System.out.println(secondSearchCategory);
//        System.out.println("thirdSearch");
//        System.out.println(thirdSearchCategory);
//        String[] secondSearchCategoryArray = secondSearchCategory.toArray(new String[secondSearchCategory.size()]);
//        String[] thirdSearchCategoryArray = thirdSearchCategory.toArray(new String[thirdSearchCategory.size()]);
//        ArrayList<String> list = new ArrayList<String>();
//        for(int i=0;i<secondSearchCategoryArray.length;i++) {
//            secondSearchCategoryArray[i] = secondSearchCategoryArray[i].replace("Product Index > ","");
//            secondSearchCategoryArray[i] = secondSearchCategoryArray[i].replace(" > ","_");
//            secondSearchCategoryArray[i] = covertCamelCaseToUnderScore(secondSearchCategoryArray[i]);
//            list.add(secondSearchCategoryArray[i]);
////            System.out.println("secondSearchCategoryArray "+ i + " : "+ secondSearchCategoryArray[i]);
//        }
//        for(int i=0;i<thirdSearchCategoryArray.length;i++) {
//            thirdSearchCategoryArray[i] = thirdSearchCategoryArray[i].replace("Product Index > ","");
//            thirdSearchCategoryArray[i] = thirdSearchCategoryArray[i].replace(" > ","_");
//            thirdSearchCategoryArray[i] = covertCamelCaseToUnderScore(thirdSearchCategoryArray[i]);
////            System.out.println("thirdSearchCategoryArray "+ i + " : "+ thirdSearchCategoryArray[i]);
//            list.add(thirdSearchCategoryArray[i]);
//        }
//        Set<String> tempSet = new LinkedHashSet<String>(list);
//        String[] allCategory = tempSet.toArray(new String[tempSet.size()]);
//        Arrays.sort(allCategory);
//        for(int i=0;i<allCategory.length;i++) {
//            System.out.println("allCategory "+ i + " : "+ allCategory[i]);
//        }
//        System.out.println(covertCamelCaseToUnderScore("Circuit ProtectionTVS - Diodes"));
//        if(Character.isLowerCase('T')) {System.out.println("is lowerCase");} else {System.out.println("is UpperCase");}

        try {
//            for(int i=0;i<1;i++) {
//                for (int j = 0; j < firstAllParts[i].length; j++) {
//                    System.out.println("firstAllParts "+i + " , " +j+" : "+ firstAllParts[i][j]);
//                }
//                for (int j = 0; j < thirdAllParts[i].length; j++) {
//                    System.out.println("thirdAllParts "+ i + " , " +j+" : "+ thirdAllParts[i][j]);
//                }
//            }
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

    static CategoryInfo checkCategory(String category) {
        CategoryInfo categoryInfo = new CategoryInfo();
//        categoryInfo.firstCategory = category;
        if(category.contains("Circuit Protection")) {
            categoryInfo.firstCategory = "Circuit Protection";
            category = category.replace("Circuit Protection_","");
            categoryInfo.secondCategory = category;

        } else if(category.contains("Cables, Wires")) {
            categoryInfo.firstCategory = "Cables, Wires";
            category = category.replace("Cables, Wires - ","");
            categoryInfo.secondCategory = category;

        } else if(category.contains("Capacitors")) {
            categoryInfo.firstCategory = "Capacitors";
            category = category.replace("Capacitors_","");
            categoryInfo.secondCategory = category;

        } else if(category.contains("Connectors, Interconnects")) {
            categoryInfo.firstCategory = "Connectors, Interconnects";
            category = category.replace("Connectors, Interconnects_","");
            if(category.contains("Rectangular Connectors")) {
                categoryInfo.secondCategory = "Rectangular Connectors";
                category = category.replace("Rectangular Connectors - ", "");
                categoryInfo.thirdCategory = category;
            } else if(category.contains("Terminal Blocks")) {
                categoryInfo.secondCategory = "Terminal Blocks";
                category = category.replace("Terminal Blocks - ", "");
                categoryInfo.thirdCategory = category;
            } else if(category.contains("Terminals")) {
                categoryInfo.secondCategory = "Terminals";
                category = category.replace("Terminals - ", "");
                categoryInfo.thirdCategory = category;
            } else {
                categoryInfo.secondCategory = category;
            }

        } else if(category.contains("Development Boards, Kits, Programmers")) {
            categoryInfo.firstCategory = "Development Boards, Kits, Programmers";
            category = category.replace("Development Boards, Kits, Programmers_","");
            if(category.contains("Evaluation Boards")) {
                categoryInfo.secondCategory = "Evaluation Boards";
                category = category.replace("Evaluation Boards - ", "");
                categoryInfo.thirdCategory = category;
            } else {
                categoryInfo.secondCategory = category;
            }

        } else if(category.contains("Crystals, Oscillators, Resonators")) {
            categoryInfo.firstCategory = "Crystals, Oscillators, Resonators";
            category = category.replace("Crystals, Oscillators, Resonators_","");
            categoryInfo.secondCategory = category;

        } else if(category.contains("Discrete Semiconductor")) {
            categoryInfo.firstCategory = "Discrete Semiconductor";
            category = category.replace("Discrete Semiconductor ","");
            if(category.contains("Products_Diodes")) {
                categoryInfo.secondCategory = "Products_Diodes";
                category = category.replace("Products_Diodes - ", "");
                categoryInfo.thirdCategory = category;
            } else if(category.contains("Products_Thyristors")) {
                categoryInfo.secondCategory = "Products_Thyristors";
                category = category.replace("Products_Thyristors - ", "");
                categoryInfo.thirdCategory = category;
            } else if(category.contains("Products_Transistors")) {
                categoryInfo.secondCategory = "Products_Transistors";
                category = category.replace("Products_Transistors - ", "");
                categoryInfo.thirdCategory = category;
            } else {
                categoryInfo.secondCategory = category;
            }

        } else if(category.contains("Integrated Circuits (ICs)")) {
            categoryInfo.firstCategory = "Integrated Circuits (ICs)";
            category = category.replace("Integrated Circuits (ICs)","");
            if(category.contains("Clock/Timing")) {
                categoryInfo.secondCategory = "Clock/Timing";
                category = category.replace("Clock/Timing - ", "");
                categoryInfo.thirdCategory = category;
            } else if(category.contains("Data Acquisition")) {
                categoryInfo.secondCategory = "Data Acquisition";
                category = category.replace("Data Acquisition - ", "");
                categoryInfo.thirdCategory = category;
            } else if(category.contains("Embedded")) {
                categoryInfo.secondCategory = "Embedded";
                category = category.replace("Embedded - ", "");
                categoryInfo.thirdCategory = category;
            } else if(category.contains("Interface")) {
                categoryInfo.secondCategory = "Interface";
                category = category.replace("Interface - ", "");
                categoryInfo.thirdCategory = category;
            } else if(category.contains("Linear") && (!category.contains("PMIC"))) {
                categoryInfo.secondCategory = "Linear";
                category = category.replace("Linear - ", "");
                categoryInfo.thirdCategory = category;
            } else if(category.contains("Logic")) {
                categoryInfo.secondCategory = "Logic";
                category = category.replace("Logic - ", "");
                categoryInfo.thirdCategory = category;
            } else if(category.contains("PMIC")) {
                categoryInfo.secondCategory = "PMIC";
                category = category.replace("PMIC - ", "");
                categoryInfo.thirdCategory = category;
            } else if(category.contains("Memory")) {
                categoryInfo.secondCategory = "Memory";
                category = category.replace("Memory - ", "");
                categoryInfo.thirdCategory = category;
            } else {
                categoryInfo.secondCategory = category;
            }

        } else if(category.contains("Filters")) {
            categoryInfo.firstCategory = "Filters";
            category = category.replace("Filters_","");
            categoryInfo.secondCategory = category;

        } else if(category.contains("Isolators")) {
            categoryInfo.firstCategory = "Isolators";
            category = category.replace("Isolators_","");
            if(category.contains("Optoisolators")) {
                categoryInfo.secondCategory = "Optoisolators";
                category = category.replace("Optoisolators - ", "");
                categoryInfo.thirdCategory = category;
            } else if(category.contains("Isolators")) {
                categoryInfo.secondCategory = "Isolators";
                category = category.replace("Isolators - ", "");
                categoryInfo.thirdCategory = category;
            } else {
                categoryInfo.secondCategory = category;
            }

        } else if(category.contains("Optoelectronics")) {
            categoryInfo.firstCategory = "Optoelectronics";
            category = category.replace("Optoelectronics_","");
            categoryInfo.secondCategory = category;

        } else if(category.contains("Potentiometers, Variable Resistors")) {
            categoryInfo.firstCategory = "Potentiometers, Variable Resistors";
            category = category.replace("Potentiometers, Variable Resistors_","");
            categoryInfo.secondCategory = category ;

        } else if(category.contains("Power Supplies - Board Mount")) {
            categoryInfo.firstCategory = "Power Supplies - Board Mount";
            category = category.replace("Power Supplies - Board Mount_","");
            categoryInfo.secondCategory = category;

        } else if(category.contains("Power Supplies - External/Internal (Off_-Board)")) {
            categoryInfo.firstCategory = "Power Supplies - External/Internal (Off-Board)";
            category = category.replace("Power Supplies - External/Internal (Off_-Board)_","");
            categoryInfo.secondCategory = category;

        } else if(category.contains("RF/IF and RFID")) {
            categoryInfo.firstCategory = "RF/IF and RFID";
            category = category.replace("RF/IF and RFID","");
            if(category.contains("RF ") && (!category.contains("RF Access"))) {
                categoryInfo.secondCategory = "RF";
                category = category.replace("RF ", "");
                categoryInfo.thirdCategory = category;
            } else {
                categoryInfo.secondCategory = category;
            }

        } else if(category.contains("Resistors")) {
            categoryInfo.firstCategory = "Resistors";
            category = category.replace("Resistors_","");
            categoryInfo.secondCategory = category;

        } else if(category.contains("Sensors, Transducers")) {
            categoryInfo.firstCategory = "Sensors, Transducers";
            category = category.replace("Sensors, Transducers_","");
            if(category.contains("Optical Sensors ")) {
                categoryInfo.secondCategory = "Optical Sensors";
                category = category.replace("Optical Sensors -  ", "");
                categoryInfo.thirdCategory = category;
            } else if(category.contains("Temperature Sensors")) {
                categoryInfo.secondCategory = "Temperature Sensors";
                category = category.replace("Temperature Sensors - ","");
                categoryInfo.thirdCategory = category;

            } else {
                categoryInfo.secondCategory = category;
            }

        } else if(category.contains("Boxes, Enclosures, Racks")) {
            categoryInfo.firstCategory = "Boxes, Enclosures, Racks";
            category = category.replace("Boxes, Enclosures, Racks_","");
            categoryInfo.secondCategory = category ;

        } else if(category.contains("Test and Measurement")) {
            categoryInfo.firstCategory = "Test and Measurement";
            category = category.replace("Test and Measurement_","");
            categoryInfo.secondCategory = category ;
        } else if(category.contains("Tools")) {
            categoryInfo.firstCategory = "Tools";
            category = category.replace("Tools_","");
            categoryInfo.secondCategory = category;

        } else {
            categoryInfo.firstCategory = category ;
        }

        return categoryInfo;
    }

    static String[][] concat2DArray(String[][] array1, String[][] array2) {
        List<List<String>> listOfLists = new ArrayList<List<String>>(); // number is not important
        int dataCounter = 0 ;
        for(int i=0;i<array1.length;i++) {
            if(array1[i] != null) {
                listOfLists.add(new ArrayList<String>());
                for(int j=0;j<array1[i].length;j++) {
                    if(j==0) {
                        listOfLists.get(dataCounter).add(normalizeCategory(array1[i][j]));
                    } else {
                        listOfLists.get(dataCounter).add(array1[i][j]);
                    }
                }
                dataCounter++;
            }
        }
        for(int i=0;i<array2.length;i++) {
            if(array2[i] != null) {
                listOfLists.add(new ArrayList<String>());
                for(int j=0;j<array2[i].length;j++) {
                    if(j==0) {
                        listOfLists.get(dataCounter).add(normalizeCategory(array2[i][j]));
                    } else {
                        listOfLists.get(dataCounter).add(array2[i][j]);
                    }
                }
                dataCounter++;
            }
        }
        System.out.println("listOfLists.size() : " + listOfLists.size());
        System.out.println("dataCounter : " + dataCounter);
        String[][] temp = new String[listOfLists.size()][];
        for(int i=0;i<listOfLists.size();i++) {
               temp[i] = new String[listOfLists.get(i).size()];
               for (int j = 0; j < listOfLists.get(i).size(); j++) {
                   try {
                     temp[i][j] = listOfLists.get(i).get(j);
                   } catch (Exception e) {
//                       System.err.println("err : " + i + " , "+j+" : "+listOfLists.get(i).get(j));
//                       System.err.print(e.getMessage());
                   }
               }
        }

        return temp;
    }

    static String normalizeCategory(String temp) {
        temp = temp.replace("Product Index > ","");
        temp = temp.replace(" > ","_");
        temp = covertCamelCaseToUnderScore(temp);
        temp = temp.replace("RFID_","RFID ");
        temp = temp.replace("RFIDAttenuators","RFID Attenuators");
        temp = temp.replace("RFIDRFID,","RFID RFID,");
        temp = temp.replace("RFIDRF","RFID RF ");
        temp = temp.replace("RFIDRF","RFID RF ");
        temp = temp.replace("(ICs)_","(ICs)");
        temp = temp.replace("(ICs)","(ICs) ");
        temp = temp.replace("RF  ","RF ");
        return temp;
    }

    static String covertCamelCaseToUnderScore(String temp) {
        String buf = "" ;
        for (int i = 0; i < temp.length()-1; i++){
            char a = temp.charAt(i);
            char b = temp.charAt(i+1);
            if( Character.isLowerCase(a) && (!Character.isLowerCase(b)) && (a != ' ') && (b != ' ') && (a != ',') && (b != ',') && (a != '_') && (b != '_') && (a != ')') && (b != ')') && (a != '/') && (b != '/') ) {
                buf = buf+a+'_'+b;i++;
//                System.out.println("Buf if"+i+" : "+buf);
            } else {
                buf = buf+a;
//                System.out.println("Buf "+i+" : "+buf);
            }
        }
        buf = buf + temp.charAt(temp.length()-1);
        return buf;
    }

    static Set<String> sortCategory(String[][] parts) {
        ArrayList<String> list = new ArrayList<String>();
        System.out.println("parts length: "+ parts.length);
        for(int i=0;i<parts.length;i++) {
            if(parts[i] != null) {
                if (parts[i][0] != null) {
//                    System.out.println("parts " + i + " : " + parts[i][0]);
                    list.add(parts[i][0]);
                } else {
//                    System.out.println("parts j is null");
                }
            } else {
//                System.out.println("parts is null");
            }
        }
        Set<String> set = new LinkedHashSet<String>(list);
        return set;
    }

    static String[][] getFileData(String directory) {
        String[][] AllParts = new String[20000][];
        int dataCounter = 0;int nullDataCounter = 0;
        // get all files name in  directory
        final File folder = new File(directory);
        try {
         for (final File file : folder.listFiles()) {
//             System.out.println(directory+"\\"+file.getName());
             // read every file in loop
            FileInputStream fis = new FileInputStream(directory+"\\"+file.getName());
            ObjectInputStream ois = new ObjectInputStream(fis);
            String[][] partNameFile = (String[][]) ois.readObject();
            ois.close();
            // push all the data in one 2dArray
            for(int i=0;i<partNameFile.length;i++) {
                if(partNameFile[i] != null) {
                    AllParts[dataCounter] = new String[partNameFile[i].length];
                    for (int j = 0; j < partNameFile[i].length; j++) {AllParts[dataCounter][j] = partNameFile[i][j];}
                    dataCounter++;
                } else {
                    nullDataCounter++;
                }
            }
         }
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
        System.out.println("dataCounter :"+dataCounter);
        System.out.println("nullDataCounter :"+nullDataCounter);
        return AllParts;
    }

    static void test(CategoryInfo categoryInfo) {
        System.out.println("test : "+ categoryInfo.firstCategory+ " * "+ categoryInfo.secondCategory+" * "+ categoryInfo.thirdCategory);
        for(int i=0; i< categoryInfo.productList.size();i++) {
            for (int j=0;j<categoryInfo.productList.get(i).size();j++) {
                if(categoryInfo.productList.get(i).get(j) != null) {
                    System.out.println(i + " , " + j + " : " + categoryInfo.productList.get(i).get(j));
                } else {
                    System.out.println(i+" , "+j+" : "+"this is null");
                }
            }
        }
    }

    static void findProductCategory(String temp, CategoryInfo[] categoryInfo) {
        System.out.println("************** findProductCategory *************");
        for(int j =0;j<categoryInfo.length;j++) {
            for(int i=0 ; i < categoryInfo[j].productList.size() ; i++ ){
                if (categoryInfo[j].productList.get(i).get(3).equals(temp) || categoryInfo[j].productList.get(i).get(4).equals(temp)) {
                    System.out.println("find categoryInfo of " + temp + " : " + categoryInfo[j].firstCategory + " , " + categoryInfo[j].secondCategory + " , "
                            + categoryInfo[j].thirdCategory);
                } else {
//                    System.out.println(j+" , "+i+ " : "+categoryInfo[j].productList.get(i).get(3));
                }
            }
        }
    }

    static void storeProductsToCSV(CategoryInfo temp) {
        try {
            int counters = 0 ;
            String fileNmae = temp.firstCategory+"_"+temp.secondCategory +"_"+temp.thirdCategory;
            fileNmae = fileNmae.replaceAll("\\(","");fileNmae = fileNmae.replaceAll("\\)","");
            fileNmae = fileNmae.replaceAll("(ICs)","ICs");fileNmae.replaceAll(" - ","_");fileNmae = fileNmae.replaceAll(" ","_");
            fileNmae = fileNmae.replaceAll("\\\\","_");
            fileNmae = fileNmae.replaceAll("/","_");
            fileNmae = fileNmae.replaceAll("__","_");fileNmae = fileNmae.replaceAll(",","");
            fileNmae = fileNmae.replaceAll("_-_","_");
            System.out.println("storeProductsToCSV , "+fileNmae);
          PrintWriter pw = new PrintWriter(new File(GlobalData.CSV_RESULT+fileNmae+".csv"));
            StringBuilder sb = new StringBuilder();
            for (int i=0;i<temp.productList.size();i++) { //
                int dataType = 0 ;
                if(temp.productList.get(i).get(3).contains("Manufacturer")) { dataType = 1 ;}
                for (int j=1;j<temp.productList.get(i).size();j++) {
                    System.out.println(i+" , "+j+" : "+temp.productList.get(i).get(j));
                    if(temp.productList.get(i).get(j) != null) {
                        if (dataType == 1) {
                            if ((j > 6)) {
                                String tempBuf = temp.productList.get(i).get(j).replaceAll(",", "*").replaceAll("\\r|\\n", "#");
                                sb.append(correctFeatures(tempBuf));
                                sb.append(",");
//                       System.out.println("name : "+i+" , "+j+" : "+temp.productList.get(i).get(4));
                            }

                        } else {
                            if ((j > 11)) {
                                String tempBuf = temp.productList.get(i).get(j).replaceAll(",", "*").replaceAll("\\r|\\n", "#");
                                sb.append(correctFeatures(tempBuf));
                                sb.append(",");
//                       System.out.println("name : "+i+" , "+j+" : "+temp.productList.get(i).get(3));
                            }
                        }
                    } else {counters++;}
                }
                sb.append('\n');
            }
            System.out.println("null counters is "+counters);
            pw.write(sb.toString());
            pw.close();
            System.out.println("storeProductsToCSV : "+temp.firstCategory+"_"+temp.secondCategory +"_"+temp.thirdCategory);
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

    static void storeAllCommonProductDataToCSV(CategoryInfo[] categoryInfos) {
        try {
            int counters = 0 ;
            //  for test
            int flag = 0;String[][] desProduct = getFileData(GlobalData.DES_FOUNDED);
            System.out.println("storeAllCommonProductDataToCSV");
            System.out.println("desProduct length : "+ desProduct.length);
            PrintWriter pw = new PrintWriter(new File("common.csv"));
            StringBuilder sb = new StringBuilder();
            for (int t=0;t<categoryInfos.length;t++) {
                CategoryInfo temp = categoryInfos[t];
                for (int i=0;i<temp.productList.size();i++) {
                    int dataType = 0 ;
                    if(temp.productList.get(i).get(3).contains("Manufacturer")) { dataType = 1 ;}
                    for (int j = 0; j < temp.productList.get(i).size(); j++) {
                       if(temp.productList.get(i).get(j) != null) {
                        if(j==0) {sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");} // image dataSheet Footprint
                        if(dataType == 1) {
                            if(j==3) {
                                sb.append(temp.productList.get(i).get(2).replace(",", "*"));sb.append(",");sb.append("0");sb.append(",");sb.append("0");sb.append(","); // name,  quantity available, (price)(buy),
                                sb.append(temp.productList.get(i).get(3).replace("Manufacturer ","").replace(",", "*"));sb.append(","); //manufacturer
//                                if(!= null)
                                sb.append(findDes(desProduct,temp.productList.get(i).get(2)).replaceAll(",", "*"));sb.append(","); // , description,
//                                noDesProduct[counters] = temp.productList.get(i).get(2);
//                                counters++;
                            }
                            if(j==5) {
                                sb.append(temp.productList.get(i).get(5).replace("Packaging ","").replace(",", "*").replaceAll("\\r|\\n", "#"));sb.append(",");sb.append(temp.productList.get(i).get(4).replace("Series ","").replace(",", "*"));sb.append(","); // packing series
                            }
                            if(j==6) {
                                sb.append(temp.productList.get(i).get(6).replace("Part Status ",""));sb.append(","); //  part Status
                            }
                        } else {
                            if(j==3) {
                                sb.append(temp.productList.get(i).get(3).replace(",", "*"));sb.append(",");sb.append("0");sb.append(",");sb.append("0");sb.append(","); // name,  quantity available, (price)(buy),
                            }
                            if(j==4) {
                                sb.append(temp.productList.get(i).get(4).replaceAll(",", "*"));sb.append(","); //  manufacturer, ,
                            }
                            if(j==5) {
                                sb.append(temp.productList.get(i).get(5).replaceAll(",", "*"));sb.append(","); // description
                            }
                            if(j==9) {
                                sb.append(temp.productList.get(i).get(9).replaceAll("\\r|\\n", "#").replace(",", "*"));sb.append(","); // packing
                            }
                            if(j==10) {
                                sb.append(temp.productList.get(i).get(10).replace(",", "*"));sb.append(","); // series
                            }
                            if(j==11) {
                                sb.append(temp.productList.get(i).get(11));sb.append(","); // , part Status
                            }
                        }
                       } else {
                           if(j==0) {
                               sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");sb.append(" *");sb.append(",");// image dataSheet Footprint
                           } else {
                               System.out.println("is null  j: "+j);
                           }
                       }

                    }
                    sb.append(Integer.toString(t+1));sb.append(",");sb.append(Integer.toString(i+1));sb.append(",");sb.append('\n');
                }
            }
            System.out.println("counters is "+counters);
            pw.write(sb.toString());
            pw.close();

            // store no des product
//            FileOutputStream partNameFile = new FileOutputStream("noDesProduct.json");
//            ObjectOutputStream oos = new ObjectOutputStream(partNameFile);
//            oos.writeObject(noDesProduct);
//            oos.close();
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

    static void storeCategoryToCSV(CategoryInfo[] temp) {
        try {
            PrintWriter pw = new PrintWriter(new File(GlobalData.CSV_RESULT+"Categories.csv"));
            StringBuilder sb = new StringBuilder();
            for (int i=0;i<temp.length;i++) {
                sb.append(temp[i].firstCategory.replace(",","_")+"*"+
                        temp[i].secondCategory.replace(",","_")+"*"+temp[i].thirdCategory.replace(",","_"));
//                sb.append(",");
//                sb.append(temp[i].secondCategory);sb.append(",");
//                sb.append(temp[i].thirdCategory);
                sb.append('\n');
            }
            pw.write(sb.toString());
            pw.close();
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }

    static String removeMultipleCharFromString(String temp, int first, int second) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < first; i++) {
            sb.append(temp.charAt(i));
        }
        for (int i = second; i < temp.length(); i++) {
            sb.append(temp.charAt(i));
        }
        return sb.toString();
    }

    static boolean checkEqualityCategories(String temp, CategoryInfo categoryInfo) {
        String removeFirstCategory = removeMultipleCharFromString(temp,0,categoryInfo.firstCategory.length());
//        String removeSecondCategory = removeMultipleCharFromString(removeFirstCategory,0,5);
        return (
                temp.contains(categoryInfo.firstCategory) &&
                        removeFirstCategory.contains(categoryInfo.secondCategory) && removeFirstCategory.contains(categoryInfo.thirdCategory) &&
                        (temp.length() - (categoryInfo.firstCategory+" "+categoryInfo.secondCategory+" "+categoryInfo.thirdCategory).length()) < 5
                );
    }

    static String findDes(String[][] temp ,String name) {
        System.out.println("findDes");
        if(temp != null) {
            if (name != null) {
                for (int i = 0; i < temp.length; i++) {
                    if (temp[i] != null) {
                        if (temp[i][0] != null) {
                            if (temp[i][0].equals(name)) {
                                if (temp[i][1] != null) {
                                    return temp[i][1];
                                } else {
//                                    System.out.println("temp[i][1] is null");
                                    break;
                                }

                            }
                        } else {
//                            System.out.println("temp[i][0] is null");
                        }
                    } else {
//                        System.out.println("temp[i] is null");
                    }
                }
            } else {
//                System.out.println("name is null");
            }
        } else {System.out.println("temp is null");}
        return " ";
    }

    static String correctFeatures(String temp) {
        String tempBuff = temp;
        tempBuff = tempBuff.replace("Diode Configuration ","");
        tempBuff = tempBuff.replace("Diode Type ","");
        tempBuff = tempBuff.replace("Voltage - DC Reverse (Vr) (Max) ","");
        tempBuff = tempBuff.replace("Current - Average Rectified (Io) (per Diode) ","");
        tempBuff = tempBuff.replace("Voltage - Forward (Vf) (Max) @ If ","");
        tempBuff = tempBuff.replace("Speed ","");
        tempBuff = tempBuff.replace("Reverse Recovery Time (trr) ","");
        tempBuff = tempBuff.replace("Current - Reverse Leakage @ Vr ","");
        tempBuff = tempBuff.replace("Operating Temperature - Junction ","");
        tempBuff = tempBuff.replace("Mounting Type ","");
        tempBuff = tempBuff.replace("Package / Case ","");
        tempBuff = tempBuff.replace("Supplier Device Package ","");
        tempBuff = tempBuff.replace("Manufacturer ","");
        tempBuff = tempBuff.replace("Series ","");
        tempBuff = tempBuff.replace("Packaging ","");
        tempBuff = tempBuff.replace("Part Status ","");
        tempBuff = tempBuff.replace("Diode Type ","");
        tempBuff = tempBuff.replace("Voltage - Peak Reverse (Max) ","");
        tempBuff = tempBuff.replace("Capacitance @ Vr* F ","");
        tempBuff = tempBuff.replace("Resistance @ If* F ","");
        tempBuff = tempBuff.replace("Power Dissipation (Max) ","");
        tempBuff = tempBuff.replace("Operating Temperature ","");
        tempBuff = tempBuff.replace("Package / Case ","");
        tempBuff = tempBuff.replace("Current - Max ","");
        tempBuff = tempBuff.replace("Current - Average Rectified (Io) ","");
        tempBuff = tempBuff.replace("Capacitance Ratio ","");
        tempBuff = tempBuff.replace("Capacitance Ratio Condition ","");
        tempBuff = tempBuff.replace("Voltage - Zener (Nom) (Vz) ","");
        tempBuff = tempBuff.replace("Tolerance ","");
        tempBuff = tempBuff.replace("Power - Max ","");
        tempBuff = tempBuff.replace("Impedance (Max) (Zzt) ","");
        tempBuff = tempBuff.replace("Capacitance Ratio Condition ","");
        tempBuff = tempBuff.replace("Configuration ","");
//        tempBuff = tempBuff.replace("Current ","");
        tempBuff = tempBuff.replace("Voltage - Isolation ","");
        tempBuff = tempBuff.replace("Voltage - Breakover ","");
        tempBuff = tempBuff.replace("Current - Breakover ","");
        tempBuff = tempBuff.replace("Current - Peak Output ","");
//        tempBuff = tempBuff.replace("Voltage ","");
        tempBuff = tempBuff.replace("Voltage - Off State ","");
        tempBuff = tempBuff.replace("Voltage - Gate Trigger (Vgt) (Max) ","");
        tempBuff = tempBuff.replace("Current - Gate Trigger (Igt) (Max) ","");
        tempBuff = tempBuff.replace("Voltage - On State (Vtm) (Max) ","");
        tempBuff = tempBuff.replace("Current - On State (It (AV)) (Max) ","");
        tempBuff = tempBuff.replace("Current - On State (It (RMS)) (Max) ","");
        tempBuff = tempBuff.replace("Current - Hold (Ih) (Max) ","");
        tempBuff = tempBuff.replace("Current - Off State (Max) ","");
        tempBuff = tempBuff.replace("SCR Type ","");
        tempBuff = tempBuff.replace("Current - Non Rep. Surge 50* 60Hz (Itsm) ","");
//        tempBuff = tempBuff.replace("SCR Type ","");
        tempBuff = tempBuff.replace("Transistor Type ","");
        tempBuff = tempBuff.replace("Current - Collector (Ic) (Max) ","");
        tempBuff = tempBuff.replace("Voltage - Collector Emitter Breakdown (Max) ","");
        tempBuff = tempBuff.replace("Vce Saturation (Max) @ Ib* Ic ","");
        tempBuff = tempBuff.replace("Current - Collector Cutoff (Max) ","");
        tempBuff = tempBuff.replace("DC Current Gain (hFE) (Min) @ Ic* Vce ","");
        tempBuff = tempBuff.replace("Frequency - Transition ","");
        tempBuff = tempBuff.replace("Resistor - Base (R1) ","");
        tempBuff = tempBuff.replace("Resistor - Emitter Base (R2) ","");
        tempBuff = tempBuff.replace("Noise Figure (dB Typ @ f) ","");
        tempBuff = tempBuff.replace("Gain ","");
        tempBuff = tempBuff.replace("FET Type ","");
        tempBuff = tempBuff.replace("FET Feature ","");
        tempBuff = tempBuff.replace("Drain to Source Voltage (Vdss) ","");
//        tempBuff = tempBuff.replace("Current - Continuous Drain (Id) @ 25Â°C ","");
        tempBuff = tempBuff.replace("Rds On (Max) @ Id* Vgs ","");
        tempBuff = tempBuff.replace("Vgs(th) (Max) @ Id ","");
        tempBuff = tempBuff.replace("Gate Charge (Qg) (Max) @ Vgs ","");
        tempBuff = tempBuff.replace("Input Capacitance (Ciss) (Max) @ Vds ","");
        tempBuff = tempBuff.replace("Frequency ","");
        tempBuff = tempBuff.replace("Voltage - Test ","");
        tempBuff = tempBuff.replace("Current Rating ","");
        tempBuff = tempBuff.replace("Noise Figure ","");
        tempBuff = tempBuff.replace("Current - Test ","");
        tempBuff = tempBuff.replace("Power - Output ","");
        tempBuff = tempBuff.replace("Voltage - Rated ","");
        tempBuff = tempBuff.replace("Drive Voltage (Max Rds On* Min Rds On) ","");
        tempBuff = tempBuff.replace("Vgs (Max) ","");
        tempBuff = tempBuff.replace("Technology ","");
        tempBuff = tempBuff.replace("Vce(on) (Max) @ Vge* Ic ","");
        tempBuff = tempBuff.replace("Input Capacitance (Cies) @ Vce ","");
        tempBuff = tempBuff.replace("NTC Thermistor ","");
        tempBuff = tempBuff.replace("Input Type ","");
        tempBuff = tempBuff.replace("IGBT Type ","");
        tempBuff = tempBuff.replace("Voltage - Collector Emitter Breakdown (Max) ","");
        tempBuff = tempBuff.replace("Current - Collector Pulsed (Icm) ","");
//        tempBuff = tempBuff.replace("Current - Collector Pulsed (Icm) ","");
        tempBuff = tempBuff.replace("Vce(on) (Max) @ Vge* Ic ","");
        tempBuff = tempBuff.replace("Power - Max ","");
        tempBuff = tempBuff.replace("Switching Energy ","");
        tempBuff = tempBuff.replace("Gate Charge ","");
        tempBuff = tempBuff.replace("Test Condition ","");
        tempBuff = tempBuff.replace("Reverse Recovery Time (trr) ","");
        tempBuff = tempBuff.replace("Operating Temperature ","");
        tempBuff = tempBuff.replace("Mounting Type ","");
        tempBuff = tempBuff.replace("Package / Case ","");
        tempBuff = tempBuff.replace("Supplier Device Package ","");
        tempBuff = tempBuff.replace("Voltage - Breakdown (V(BR)GSS) ","");
        tempBuff = tempBuff.replace("Current - Drain (Idss) @ Vds (Vgs=0) ","");
        tempBuff = tempBuff.replace("Voltage - Cutoff (VGS off) @ Id ","");
        tempBuff = tempBuff.replace("Voltage - Cutoff (VGS off) @ Id ","");
        tempBuff = tempBuff.replace("Resistance - RDS(On) ","");
        tempBuff = tempBuff.replace("Type Low Pass ","Low Pass");
        tempBuff = tempBuff.replace("Filter Order ","");
        tempBuff = tempBuff.replace("Number of Channels ","");
        tempBuff = tempBuff.replace("Center / Cutoff ","");
        tempBuff = tempBuff.replace("Attenuation Value ","");
        tempBuff = tempBuff.replace("Resistance - Channel (Ohms) ","");
        tempBuff = tempBuff.replace("Values ","");
        tempBuff = tempBuff.replace("Filter Type ","");
        tempBuff = tempBuff.replace("Ripple ","");
        tempBuff = tempBuff.replace("Insertion Loss ","");
        tempBuff = tempBuff.replace("Size / Dimension ","");
        tempBuff = tempBuff.replace("Main Purpose ","");
//        tempBuff = tempBuff.replace("Output ","");
        tempBuff = tempBuff.replace("Number of Circuits ","");
        tempBuff = tempBuff.replace("Ratio - Input:Output ","");
        tempBuff = tempBuff.replace("Differential - Input:Output ","");
        tempBuff = tempBuff.replace("- Max ","");
        tempBuff = tempBuff.replace("Voltage - Supply ","");
        tempBuff = tempBuff.replace("Number of Circuits ","");
        tempBuff = tempBuff.replace("Ratio - Input:Output ","");
        tempBuff = tempBuff.replace("Differential - Input:Output ","");
        tempBuff = tempBuff.replace("Divider/Multiplier ","");
        tempBuff = tempBuff.replace("Memory Size ","");
        tempBuff = tempBuff.replace("Time Format ","");
        tempBuff = tempBuff.replace("Date Format ","");
        tempBuff = tempBuff.replace("Voltage - Supply* Battery ","");
        tempBuff = tempBuff.replace("Current - Timekeeping (Max) ","");
        tempBuff = tempBuff.replace("Resolution (Bits) ","");
        tempBuff = tempBuff.replace("Sampling Rate (Per Second) ","");
        tempBuff = tempBuff.replace("Data Interface ","");
        tempBuff = tempBuff.replace("Number of Logic Elements/Cells ","");
        tempBuff = tempBuff.replace("Voltage Supply Source ","");
        tempBuff = tempBuff.replace("Number of Bits ","");
        tempBuff = tempBuff.replace("Power (Watts) ","");
        tempBuff = tempBuff.replace("Voltage - Supply* Analog ","");
        tempBuff = tempBuff.replace("Voltage - Supply* Digital ","");
        tempBuff = tempBuff.replace("Number of Inputs ","");
        tempBuff = tempBuff.replace("Ratio - S/H:ADC ","");
        tempBuff = tempBuff.replace("Number of A/D Converters ","");
        tempBuff = tempBuff.replace("Architecture ","");
        tempBuff = tempBuff.replace("Reference ","");
        tempBuff = tempBuff.replace("Number of Taps ","");
        tempBuff = tempBuff.replace("Resistance (Ohms) ","");
//        tempBuff = tempBuff.replace("Interface ","");
        tempBuff = tempBuff.replace("Temperature Coefficient (Typ) ","");
        tempBuff = tempBuff.replace("Resistance - Wiper (Ohms) (Typ) ","");
        tempBuff = tempBuff.replace("Number of D/A Converters ","");
        tempBuff = tempBuff.replace("Settling Time ","");
        tempBuff = tempBuff.replace("Differential Output ","");
        tempBuff = tempBuff.replace("INL/DNL (LSB) ","");
        tempBuff = tempBuff.replace("Current - Supply ","");
        tempBuff = tempBuff.replace("Programmable In System Programmable","In System Programmable");
        tempBuff = tempBuff.replace("Delay Time tpd(1) Max ","");
        tempBuff = tempBuff.replace("Voltage Supply - Internal ","");
        tempBuff = tempBuff.replace("Number of Logic Elements/Blocks ","");
        tempBuff = tempBuff.replace("Number of Macrocells ","");
        tempBuff = tempBuff.replace("Number of Gates ","");
        tempBuff = tempBuff.replace("Number of I/O ","");
        tempBuff = tempBuff.replace("Clock Rate ","");
        tempBuff = tempBuff.replace("Non-Volatile Memory ","");
        tempBuff = tempBuff.replace("On-Chip RAM ","");
        tempBuff = tempBuff.replace("Voltage - I/O ","");
        tempBuff = tempBuff.replace("Voltage - Core ","");
        tempBuff = tempBuff.replace("Total RAM Bits ","");
        tempBuff = tempBuff.replace("Core Processor ","");
        tempBuff = tempBuff.replace("Core Size ","");
        tempBuff = tempBuff.replace("Connectivity ","");
        tempBuff = tempBuff.replace("Peripherals ","");
        tempBuff = tempBuff.replace("EEPROM Size ","");
        tempBuff = tempBuff.replace("Program Memory Type ","");
        tempBuff = tempBuff.replace("Voltage - Input ","");
        tempBuff = tempBuff.replace("RAM Size ","");
        tempBuff = tempBuff.replace("(Vcc/Vdd) ","");
        tempBuff = tempBuff.replace("Data Converters ","");
        tempBuff = tempBuff.replace("Oscillator Type ","");
        tempBuff = tempBuff.replace("Number of Cores/Bus Width ","");
        tempBuff = tempBuff.replace("Co-Processors/DSP ","");
        tempBuff = tempBuff.replace("RAM Controllers ","");
        tempBuff = tempBuff.replace("Graphics Acceleration ","");
        tempBuff = tempBuff.replace("Display & Interface Controllers ","");
        tempBuff = tempBuff.replace("Ethernet ","");
        tempBuff = tempBuff.replace("Security Features ","");
        tempBuff = tempBuff.replace("Additional Interfaces ","");
        tempBuff = tempBuff.replace("Programmable Type PAL","PAL");
        tempBuff = tempBuff.replace("Architecture ","");
        tempBuff = tempBuff.replace("Primary Attributes ","");
        tempBuff = tempBuff.replace("Flash Size ","");
        tempBuff = tempBuff.replace("Switch Circuit ","");
        tempBuff = tempBuff.replace("Multiplexer/Demultiplexer Circuit ","");
        tempBuff = tempBuff.replace("Number of Circuits ","");
        tempBuff = tempBuff.replace("On-State Resistance (Max) ","");
        tempBuff = tempBuff.replace("Voltage - Supply* Single (V+) ","");
        tempBuff = tempBuff.replace("Switch Time (Ton* Toff) (Max) ","");
        tempBuff = tempBuff.replace("Switch Time (Ton* Toff) (Max) ","");
        tempBuff = tempBuff.replace("Charge Injection ","");
        tempBuff = tempBuff.replace("Channel Capacitance (CS(off)* CD(off)) ","");
        tempBuff = tempBuff.replace("Current - Leakage (IS(off)) (Max) ","");
        tempBuff = tempBuff.replace("Crosstalk ","");
        tempBuff = tempBuff.replace("-3db Bandwidth ","");
        tempBuff = tempBuff.replace("Number of ADCs / DACs ","");
        tempBuff = tempBuff.replace("Sigma Delta ","");
        tempBuff = tempBuff.replace("Dynamic Range* ADCs / DACs (db) Typ ","");
        tempBuff = tempBuff.replace("Number of Drivers/Receivers ","");
        tempBuff = tempBuff.replace("Receiver Hysteresis ","");
        tempBuff = tempBuff.replace("Data Rate ","");
        tempBuff = tempBuff.replace("- Cutoff or Center ","");
        tempBuff = tempBuff.replace("Number of Filters ","");
        tempBuff = tempBuff.replace("Interrupt Output ","");
        tempBuff = tempBuff.replace("Output Type ","");
        tempBuff = tempBuff.replace("Current - Output Source/Sink ","");
        tempBuff = tempBuff.replace("Data Format ","");
        tempBuff = tempBuff.replace("Baud Rates ","");
        tempBuff = tempBuff.replace("Data Rate (Max) ","");
        tempBuff = tempBuff.replace("Signal Conditioning ","");
        tempBuff = tempBuff.replace("Capacitance - Input ","");
        tempBuff = tempBuff.replace("Delay Time ","");
        tempBuff = tempBuff.replace("With Auto Flow Control ","");
        tempBuff = tempBuff.replace("With IrDA Encoder/Decoder ","");
        tempBuff = tempBuff.replace("With False Start Bit Detection ","");
        tempBuff = tempBuff.replace("With Modem Control ","");
        tempBuff = tempBuff.replace("Max Output Power x Channels @ Load ","");
        tempBuff = tempBuff.replace("Slew Rate ","");
        tempBuff = tempBuff.replace("Bandwidth Product ","");
        tempBuff = tempBuff.replace("Current - Input Bias ","");
        tempBuff = tempBuff.replace("Offset ","");
        tempBuff = tempBuff.replace("Current - Output / Channel ","");
        tempBuff = tempBuff.replace("Amplifier Type ","");
        tempBuff = tempBuff.replace("Number of Bits/Stages ","");
        tempBuff = tempBuff.replace("Number of Elements ","");
        tempBuff = tempBuff.replace("Current - Quiescent (Max) ","");
        tempBuff = tempBuff.replace("Propagation Delay (Max) ","");
        tempBuff = tempBuff.replace("Current - Output (Typ) ","");
        tempBuff = tempBuff.replace("Hysteresis ","");
        tempBuff = tempBuff.replace("Logic Type ","");
        tempBuff = tempBuff.replace("per Element ","");
        tempBuff = tempBuff.replace("Current - Output High* Low ","");
        tempBuff = tempBuff.replace("Max Propagation Delay @ V* Max CL ","");
        tempBuff = tempBuff.replace("Current - Quiescent (Iq) ","");
        tempBuff = tempBuff.replace("Output Function ","");
        tempBuff = tempBuff.replace("Count Rate ","");
        tempBuff = tempBuff.replace("Trigger Type ","");
        tempBuff = tempBuff.replace("Access Time ","");
        tempBuff = tempBuff.replace("Expansion Type ","");
        tempBuff = tempBuff.replace("Programmable Flags Support ","");
        tempBuff = tempBuff.replace("Retransmit Capability ","");
        tempBuff = tempBuff.replace("FWFT Support ","");
        tempBuff = tempBuff.replace("Independent Circuits ","");
        tempBuff = tempBuff.replace("- Propagation ","");
        tempBuff = tempBuff.replace("Schmitt Trigger Input ","");
        tempBuff = tempBuff.replace("Propagation Delay ","");
        tempBuff = tempBuff.replace("Translator Type ","");
        tempBuff = tempBuff.replace("Channel Type ","");
        tempBuff = tempBuff.replace("Channels per Circuit ","");
        tempBuff = tempBuff.replace("Input Signal ","");
        tempBuff = tempBuff.replace("Output Signal ","");
        tempBuff = tempBuff.replace("Memory Format ","");
        tempBuff = tempBuff.replace("Write Cycle Time - Word* Page ","");
        tempBuff = tempBuff.replace("Memory Type ","");
        tempBuff = tempBuff.replace("Memory Interface ","");
        tempBuff = tempBuff.replace("Internal Switch(s) ","");
        tempBuff = tempBuff.replace("Voltage - Breakdown ","");
        tempBuff = tempBuff.replace("Voltage - Start Up ","");
        tempBuff = tempBuff.replace("Duty Cycle ","");
        tempBuff = tempBuff.replace("- Switching ","");
        tempBuff = tempBuff.replace("Fault Protection ","");
        tempBuff = tempBuff.replace("Control Features ","");
        tempBuff = tempBuff.replace("Battery Chemistry ","");
        tempBuff = tempBuff.replace("Number of Cells ","");
        tempBuff = tempBuff.replace("Current - Charging ","");
        tempBuff = tempBuff.replace("Programmable Features ","");
        tempBuff = tempBuff.replace("Battery Pack Voltage ","");
        tempBuff = tempBuff.replace("Display Type ","");
        tempBuff = tempBuff.replace("Digits or Characters ","");
        tempBuff = tempBuff.replace("Input Impedance ","");
        tempBuff = tempBuff.replace("Measurement Error ","");
        tempBuff = tempBuff.replace("Meter Type ","");
        tempBuff = tempBuff.replace("Voltage - Load ","");
        tempBuff = tempBuff.replace("Load Type ","");
        tempBuff = tempBuff.replace("Rds On (Typ) ","");
        tempBuff = tempBuff.replace("Number of Drivers ","");
        tempBuff = tempBuff.replace("Gate Type ","");
        tempBuff = tempBuff.replace("Logic Voltage - VIL* VIH ","");
        tempBuff = tempBuff.replace("(Source* Sink) ","");
        tempBuff = tempBuff.replace("Rise / Fall Time (Typ) ","");
        tempBuff = tempBuff.replace("Motor Type - Stepper ","");
        tempBuff = tempBuff.replace("Motor Type - AC* DC ","");
        tempBuff = tempBuff.replace("Step Resolution ","");
        tempBuff = tempBuff.replace("Current - Output ","");
        tempBuff = tempBuff.replace("Current - Startup ","");
        tempBuff = tempBuff.replace("Number of Voltages Monitored ","");
        tempBuff = tempBuff.replace("Reset Timeout ","");
        tempBuff = tempBuff.replace("Voltage - Threshold ","");
        tempBuff = tempBuff.replace("Output Alarm ","");
        tempBuff = tempBuff.replace("Output Fan ","");
        tempBuff = tempBuff.replace("Sensor Type ","");
        tempBuff = tempBuff.replace("Sensing Temperature ","");
        tempBuff = tempBuff.replace("Number of Outputs ","");
        tempBuff = tempBuff.replace("Output Phases ","");
        tempBuff = tempBuff.replace("Synchronous Rectifier ","");
        tempBuff = tempBuff.replace("Clock Sync ","");
        tempBuff = tempBuff.replace("Number of Regulators ","");
        tempBuff = tempBuff.replace("Voltage - Output (Min/Fixed) ","");
        tempBuff = tempBuff.replace("Voltage - Output (Max) ","");
        tempBuff = tempBuff.replace("Voltage Dropout (Max)  ","");
        tempBuff = tempBuff.replace("Voltage Dropout (Max) ","");
        tempBuff = tempBuff.replace("Protection Features ","");
        tempBuff = tempBuff.replace("Voltage - Forward (Vf) (Typ) ","");
        tempBuff = tempBuff.replace("Current - DC Forward (If) (Max) ","");
        tempBuff = tempBuff.replace("Common Mode Transient Immunity (Min) ","");
        tempBuff = tempBuff.replace("Current - DC Forward (If) (Max) ","");
        tempBuff = tempBuff.replace("tpLH / tpHL (Max) ","");
        tempBuff = tempBuff.replace("Pulse Width Distortion (Max) ","");
        tempBuff = tempBuff.replace("Inputs - Side 1/Side 2 ","");
        tempBuff = tempBuff.replace("Current Transfer Ratio (Min) ","");
        tempBuff = tempBuff.replace("Current Transfer Ratio (Max) ","");
        tempBuff = tempBuff.replace("Turn On / Turn Off Time (Typ) ","");
        tempBuff = tempBuff.replace("Zero Crossing Circuit ","");
        tempBuff = tempBuff.replace("Static dV/dt (Min) ","");
        tempBuff = tempBuff.replace("Current - LED Trigger (Ift) (Max) ","");
        tempBuff = tempBuff.replace("Current - Hold (Ih) ","");
        tempBuff = tempBuff.replace("Turn On Time ","");
        tempBuff = tempBuff.replace("Gain ","");
        tempBuff = tempBuff.replace("Gain ","");
//        tempBuff = tempBuff.replace("Gain ","");Current - Startup

        return tempBuff;
    }
}
