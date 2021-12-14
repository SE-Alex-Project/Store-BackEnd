package Software.storeBackEnd.entities;

import java.util.ArrayList;
import java.util.Arrays;

public class Cart {
    ArrayList<Product> products;
    public Cart(){
        products = new ArrayList<>();
    }
    public void addProduct (Product... p){
        products.addAll(Arrays.asList(p));
    }
}
