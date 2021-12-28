package Software.storeBackEnd.entities;

import java.util.ArrayList;

public class Cart {
	
	int id;
	String email;
    ArrayList<ProductQuantity> products;

    public Cart(){
        products = new ArrayList<>();
    }
	
	public ArrayList<ProductQuantity> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<ProductQuantity> products) {
		this.products = products;
	}
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void addProduct (int id,int quantity){
        products.add(new ProductQuantity(id, quantity));
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
