import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Created by Mohammad on 9/28/2018.
 */
public class PrintFiles {
    public static void main(String[] args) {
        try {
            // define variable
            int dataCounter = 0;
            String[] AllParts = new String[14000];
            // get all files name in  directory
            final File folder = new File(GlobalData.SECOND_SEARCH_FOUNDED);
            System.out.println("Start");


            for (final File file : folder.listFiles()) {
                System.out.println(file.getName());
                // read every file in loop
                FileInputStream fis = new FileInputStream(GlobalData.SECOND_SEARCH_FOUNDED +"\\"+ file.getName());
                ObjectInputStream ois = new ObjectInputStream(fis);
                String[][] partNameFile = (String[][]) ois.readObject();
                ois.close();
                // push all the data in one 2dArray
                for (int i = 0; i < partNameFile.length; i++) {
                    if (partNameFile[i] != null) {
                        for (int j = 0; j < partNameFile[i].length; j++) {
                            if (partNameFile[i][j] != null) {
                                System.out.println(i + " , " + j + "  : " + partNameFile[i][j]);
                            }
                        }
//                        AllParts[dataCounter] = partNameFile[i];
                        dataCounter++;
                    }
                }
            }
            System.out.println("All parts number : " + dataCounter);
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }
}

