import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;

/**
 * Created by Mohammad on 4/25/2018.
 */
public class readFiles {
    public static void main(String[] args) {
        try {
            // read file for test
            FileInputStream fis = new FileInputStream("partName.json");
            ObjectInputStream ois = new ObjectInputStream(fis);
            String[] partNameFile = (String[]) ois.readObject();
            ois.close();
            // read file for test
            FileInputStream fis2 = new FileInputStream("partNameNotFoundFile.json");
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            String[] partNameNotFoundFile = (String[]) ois2.readObject();
            ois2.close();

            System.out.println("partNameFile : " + partNameFile.length);
            System.out.println("partNameNotFoundFile : " + partNameNotFoundFile.length);

//            // JsonArray parentJsonArray = new JsonArray();
//            JSONObject parentJsonArray = new JSONObject();
//            // loop through your elements
            for (int i=0; i<partNameFile.length; i++){
                System.out.println("i :"+ i);
                System.out.println("partNameFile :"+ partNameFile[i]);
                System.out.println("partNameNotFoundFile :"+ partNameNotFoundFile[i]);
            }
//
//
//            FileWriter file = new FileWriter("Category2.json");
//            file.write(parentJsonArray.toJSONString());
//            file.flush();
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }
}
