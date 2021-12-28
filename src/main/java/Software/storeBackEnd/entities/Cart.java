package Software.storeBackEnd.entities;

import java.util.ArrayList;
import java.util.Arrays;

public class Cart {
    ArrayList<Integer> products;
    public Cart(){
        products = new ArrayList<>();
    }
    public void addProduct (Integer... p){
        products.addAll(Arrays.asList(p));
    }
}
