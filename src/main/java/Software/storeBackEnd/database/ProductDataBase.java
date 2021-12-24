package Software.storeBackEnd.database;

import Software.storeBackEnd.entities.Product;
import Software.storeBackEnd.parser.Parser;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

public class ProductDataBase {

    private final DataBase dataBase;

    public ProductDataBase() {
        dataBase = DataBase.getInstance();
    }


    public void addProduct(Product p) {
        try {
            dataBase.stmt.execute("INSERT INTO Product(categoryName,price,descripe,productName) VALUES ('" + p.getCategory()
                    + "','" + p.getPrice() + "','" + p.getDescription() + "','" + p.getName() + "');");

            ResultSet s = dataBase.stmt.executeQuery("SELECT LAST_INSERT_ID();");
            s.next();
            int productId = s.getInt(1);
            addProductStores(productId, p.getStores());
            addProductImages(productId, p.getImagesURL());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addProductStores(int productID, ArrayList<Product.productStore> productStores) throws SQLException {
        for (Product.productStore p : productStores) {
            dataBase.stmt.execute("INSERT INTO ProductInStore VALUES ('" + productID
                    + "','" + p.getStoreID() + "','" + p.getQuantity() + "');");
        }

    }

    private void addProductImages(int productID, ArrayList<String> productImages) throws SQLException {
        System.out.println(productImages.toString());
        for (String s : productImages) {
            System.out.println(s);
            dataBase.stmt.execute("INSERT INTO ProductImage VALUES ('" + productID + "','" + s + "');");
        }
    }

    public JSONObject getProduct(String product_id) {
        try {
            ResultSet resultSet = dataBase.stmt.executeQuery("SELECT * FROM Product WHERE productId = '" + product_id + "'");
            resultSet.next();
            JSONObject product = Parser.parseProduct(resultSet);
            product.put("stores", getProductStores(product_id));
            product.put("images", getProductImages(product_id));
            return product;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONArray getProductStores(String product_id) throws SQLException {
        ResultSet resultSet = dataBase.stmt.executeQuery("SELECT * FROM ProductInStore WHERE productId = '" + product_id + "'");
        return Parser.parseProductInStore(resultSet);
    }

    private JSONArray getProductImages(String product_id) throws SQLException {
        ResultSet resultSet = dataBase.stmt.executeQuery("SELECT * FROM ProductImage WHERE productId = '" + product_id + "'");
        return Parser.parseProductImage(resultSet);
    }
    
    public JSONArray getCategories()
    {
    	final String queryCheck = "SELECT DISTINCT categoryName FROM Product;";
        JSONArray array = new JSONArray();
        try {
            ResultSet resultSet = dataBase.stmt.executeQuery(queryCheck);
            while (resultSet.next()) {
                array.add(resultSet.getString("categoryName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }
    //select * from table order by id asc limit 50 offset 0; -- Returns rows 1-50

    public JSONArray getlist(int page) {
        final String queryCheck = "SELECT * FROM Product limit 50 offset " + ((page - 1) * 50) + ";";
        JSONArray array = new JSONArray();
        try {
            ResultSet resultSet = dataBase.stmt.executeQuery(queryCheck);
            while (resultSet.next()) {
                array.add(Parser.parseProduct(resultSet));
            }
            JSONObject product;
            for (Object j : array) {
                product = (JSONObject) j;
                product.put("stores", getProductStores(product.getAsString("id")));
                product.put("images", getProductImages(product.getAsString("id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public JSONArray getListByCategory(int page, String category) {
        final String queryCheck = "SELECT * FROM Product WHERE categoryName = '" + category + "' limit 50 offset " + ((page - 1) * 50) + ";";
        JSONArray array = new JSONArray();
        ResultSet resultSet;
        try {
            resultSet = dataBase.stmt.executeQuery(queryCheck);
            while (resultSet.next()) {
                array.add(Parser.parseProduct(resultSet));
            }
            JSONObject product;
            for (Object j : array) {
                product = (JSONObject) j;
                product.put("stores", getProductStores(product.getAsString("id")));
                product.put("images", getProductImages(product.getAsString("id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public void deleteProduct(String product_id) {
        try {
            dataBase.stmt.execute("DELETE FROM Product WHERE productId = '" + product_id + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
