import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohammad on 10/18/2018.
 */
public class CategoryInfo {
    public String thirdCategory;
    public String firstCategory;
    public String secondCategory;
    public int counter;
    List<List<String>> productList;

    CategoryInfo() {
        firstCategory = ""; secondCategory = ""; thirdCategory = ""; counter = 0 ;
        productList = new ArrayList<List<String>>(); // number is not important
    }

    public void addProduct(String[] temp) {
        if(temp != null) {
            productList.add(new ArrayList<String>());
            for (int i = 1; i < temp.length; i++) {  // 0 is category name
                productList.get(counter).add(temp[i]);
            }
            counter++;
        }
    }
}
