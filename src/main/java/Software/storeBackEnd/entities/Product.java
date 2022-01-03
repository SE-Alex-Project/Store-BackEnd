package Software.storeBackEnd.entities;


import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class Product {
    double price;
    String name, category, description, addedBy;
    ArrayList<String> imagesURL;
    ArrayList<productStore> stores;


    private Product() {
        imagesURL = new ArrayList<>();
        stores = new ArrayList<>();
    }

    public Product(JSONObject product) {
        this();
        this.setCategory(product.getAsString("category").toLowerCase(Locale.ROOT));
        this.setName(product.getAsString("name"));
        this.setPrice(Double.parseDouble(product.getAsString("price")));
        this.setDescription(product.getAsString("description"));
        ArrayList<String> images = (ArrayList<String>) product.get("images");
        for (String s : images) {
            this.AddImage(s);
        }
        ArrayList<String> stores = (ArrayList<String>) product.get("stores");
        System.out.println(stores);
        for (int i = 0; i < stores.size(); i += 2) {
            this.AddStore(Integer.parseInt(stores.get(i)), Integer.parseInt(stores.get(i + 1)));
        }
    }

    public static class productStore {
        int StoreID, Quantity;

        public productStore(int storeID, int Quantity) {
            this.StoreID = storeID;
            this.Quantity = Quantity;
        }

        public int getStoreID() {
            return StoreID;
        }

        public int getQuantity() {
            return Quantity;
        }

        @Override
        public String toString() {
            return "productStore{" +
                    "StoreID=" + StoreID +
                    ", Quantity=" + Quantity +
                    '}';
        }
    }


    public void AddStore(int StoreId, int Quantity) {
        stores.add(new productStore(StoreId, Quantity));
    }

    public void AddImage(String URL) {
        imagesURL.add(URL);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getImagesURL() {
        return imagesURL;
    }

    public ArrayList<productStore> getStores() {
        return stores;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    @Override
    public String toString() {
        return "Product{" +
                "price=" + price +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", imagesURL=" + imagesURL +
                ", stores=" + stores +
                '}';
    }
}
