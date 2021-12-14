package Software.storeBackEnd.entities;

import java.util.ArrayList;

public class Product {
    double price;
    String name;
    ArrayList<Byte[]> images = new ArrayList<Byte[]>();
    
    public void addImage(Byte[] photo) {
    	images.add(photo);
    }
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
