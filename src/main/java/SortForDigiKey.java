import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mohammad on 6/2/2018.
 */
public class SortForDigiKey {
    public static void main(String[] args) {
        try {
            // define variable
            int dataCounter = 0 ;
            int nullDataCounter = 0 ;
            String[][] AllParts = new String[50000][];
            // get all files name in  directory
            final File folder = new File("E:\\Tutorial\\Selenium\\Code\\Selenium\\DigiKey\\Founded");

            for (final File file : folder.listFiles()) {
                // read every file in loop
                FileInputStream fis = new FileInputStream("E:\\Tutorial\\Selenium\\Code\\Selenium\\DigiKey\\Founded\\"+file.getName());
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

            System.out.println("All parts number : " + dataCounter);
            System.out.println("nullDataCounter : " + nullDataCounter);
            // Normalize the category name
            for(int i=0;i<AllParts.length;i++) {
                if(AllParts[i] != null) {
//                    System.out.println(AllParts[i][0]);
                    AllParts[i][0] = AllParts[i][0].replace("Product Index > Integrated Circuits (ICs) > ", "");
                    AllParts[i][0] = AllParts[i][0].replace("Product Index > Integrated Circuits (ICs) > ", "");
                    AllParts[i][0] = AllParts[i][0].replace("Product Index > Discrete Semiconductor Products > ", "");
                    AllParts[i][0] = AllParts[i][0].replace(" - ", "_");
                    AllParts[i][0] = AllParts[i][0].replace(", ", "_");
                    AllParts[i][0] = AllParts[i][0].replace(" ", "_");
//                    System.out.println(i + " " + AllParts[i][0]);
                }
            }
            // make category List
            Set<String> categoryList = new HashSet<String>();
            int tempCounter = 0;
            int tempCounter2 = 0;
            for(int i=0;i<AllParts.length;i++) {
                if(AllParts[i] != null) {
                    if (categoryList.add(AllParts[i][0])) {
                        tempCounter++;
                    } else {
                        tempCounter2++;
                    }
                    if (AllParts[i][0] == null) {
                        System.out.println("tempCounter : " + tempCounter);
                        System.out.println("AllParts[i][1] : " + AllParts[i][1]);
                        System.out.println("AllParts[i][1] : " + AllParts[i][2]);
                        System.out.println("AllParts[i][1] : " + AllParts[i][3]);
                        System.out.println("AllParts[i][1] : " + AllParts[i][4]);
                        System.out.println("AllParts[i][1] : " + AllParts[i][5]);
                    }
                }
            }
            System.out.println("tempCounter :"+tempCounter);
            System.out.println("tempCounter2 :"+tempCounter2);
            // seperate each category and save each category in a JSON file
            String[] categoryArray = categoryList.toArray(new String[categoryList.size()]);
            System.out.println("categoryArray.length :"+categoryArray.length);
            for (int i=0;i<categoryArray.length;i++) {
                System.out.println(categoryArray[i]);
            }
            int testResult = 0;
            System.out.println("seperate");
            for(int i=0;i<categoryArray.length;i++) {
                String categoryBuff = categoryArray[i];
                System.out.println(i+" : "+categoryBuff);
                String[][] temp = new String[20000][];
                int counter = 0;
                int testCounter = 0;
//                System.out.println("AllParts.length : "+AllParts.length);
                for(int j=0;j<AllParts.length;j++) {
                    if(AllParts[j] != null) {
//                       System.out.println(j+" : "+AllParts[j][0]);
                        if (AllParts[j][0].equals(categoryBuff)) {
                            temp[counter] = new String[AllParts[j].length - 1];
//                           System.out.println("AllParts[j].length : "+AllParts[j].length);
                            for (int t = 0; t < AllParts[j].length - 1; t++) {
                                temp[counter][t] = AllParts[j][t + 1];
//                               System.out.println(t+" : "+temp[counter][t]);
                            }
                            counter++;
//                           System.out.println("j : "+j);
                        } else {
                            testCounter++;
                        }
                    }
                }
                testResult = testResult + counter ;
                System.out.println("testCounter : "+testCounter);


                // copy into the array without null
                String[][] bufferTemp = new String[counter][];
//                System.out.println("copy");
                String changeDir = categoryArray[i].replace("/", "_");
                changeDir = changeDir.replace(">", "5");
                PrintWriter pw = new PrintWriter(new File("E:\\Tutorial\\Selenium\\Code\\Selenium\\Result\\"+changeDir+".csv"));
                PrintWriter pw2 = new PrintWriter(new File("E:\\Tutorial\\Selenium\\Code\\Selenium\\Result\\"+changeDir+"2.csv"));
                StringBuilder sb = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                System.out.println(categoryArray[i]);
                for(int j=0;j<counter;j++) {
                    bufferTemp[j] = new String[temp[j].length];
                    if(temp[j].length < 12) {
                        for(int t=0;t<temp[j].length;t++) {
                            System.out.println(t+" : " +temp[j][t]);
                        }
                    }
                    if(temp[j].length > 12) {
                        for (int t = 0; t < 12; t++) {
                            String removeComma = temp[j][t];
                            if (removeComma != null) {
                                removeComma = removeComma.replace(",", "  ");
                                removeComma = removeComma.replace("\n", "  ");
                            }
//                        System.out.print("*******" +removeComma);

//                        System.out.print("*******" +removeComma);

                            sb.append(removeComma);
//                        System.out.print(temp[j][t]+"/");
                            if (t != 11) {
                                sb.append(',');
                            }
                            bufferTemp[j][t] = temp[j][t];
                        }
                        for (int t = 12; t < temp[j].length; t++) {
                            String removeComma = temp[j][t];
                            if (removeComma != null) {
                                removeComma = removeComma.replace(",", "  ");
                                removeComma = removeComma.replace("\n", "  ");
                            }
//                        System.out.print("*******" +removeComma);

//                        System.out.print("*******" +removeComma);

                            sb2.append(removeComma);
//                        System.out.print(temp[j][t]+"/");
                            if (t != temp[j].length - 1) {
                                sb2.append(',');
                            }
                            bufferTemp[j][t] = temp[j][t];
                        }
                        sb.append('\n');
                        sb2.append('\n');
                    }
//                    System.out.println(" ");
//                    System.out.println("---------------------------");
//                    System.out.println(" ");
                }

                pw.write(sb.toString());
                pw.close();
                pw2.write(sb2.toString());
                pw2.close();

//                System.out.println(i+" counter : "+counter);
//                System.out.println(categoryArray[i]+"  : "+counter);

                FileOutputStream partNameFile = new FileOutputStream("E:\\Tutorial\\Selenium\\Code\\Selenium\\Result\\"+changeDir+".json");
                ObjectOutputStream oos = new ObjectOutputStream(partNameFile);
                oos.writeObject(bufferTemp);
                oos.close();
            }
            System.out.println("done!");
            System.out.println("testResult : "+testResult);
            System.out.println("Finish");

        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }
}
