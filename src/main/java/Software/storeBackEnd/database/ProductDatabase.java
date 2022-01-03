package Software.storeBackEnd.database;

import Software.storeBackEnd.entities.Product;
import Software.storeBackEnd.parser.ProductParser;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ProductDatabase {

    private final Database dataBase;

    public ProductDatabase() {
        dataBase = Database.getInstance();
    }


    public void addProduct(Product p) throws SQLException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String d = formatter.format(date);
        dataBase.getStatement().execute("INSERT INTO Product(categoryName,price,descripe,productName,addedBy,added_date) VALUES ('" + p.getCategory()
                + "','" + p.getPrice() + "','" + p.getDescription() + "','" + p.getName() + "','" + p.getAddedBy() + "','" + d + "');");

        ResultSet s = dataBase.getStatement().executeQuery("SELECT LAST_INSERT_ID();");
        s.next();
        int productId = s.getInt(1);
        s.close();
        addProductStores(productId, p.getStores());
        addProductImages(productId, p.getImagesURL());
    }

    private void addProductStores(int productID, ArrayList<Product.productStore> productStores) throws SQLException {
        for (Product.productStore p : productStores) {
            dataBase.getStatement().execute("INSERT INTO ProductInStore VALUES ('" + productID
                    + "','" + p.getStoreID() + "','" + p.getQuantity() + "');");
        }
    }

    private void addProductImages(int productID, ArrayList<String> productImages) throws SQLException {
        System.out.println(productImages.toString());
        for (String s : productImages)
            dataBase.getStatement().execute("INSERT INTO ProductImage VALUES ('" + productID + "','" + s + "');");

    }

    public JSONObject getProduct(String product_id) throws SQLException, ParseException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT * FROM Product WHERE productId = '" + product_id + "'");
        resultSet.next();
        JSONObject product = ProductParser.parseProduct(resultSet);
        resultSet.close();
        product.put("stores", getProductStores(product_id));
        product.put("images", getProductImages(product_id));
        return product;
    }

    private JSONArray getProductStores(String product_id) throws SQLException, ParseException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT storeId,storeName,quantity FROM (ProductInStore NATURAL JOIN Store) WHERE productId = '" + product_id + "'");
        return ProductParser.parseProductInStore(resultSet);
    }

    private JSONArray getProductImages(String product_id) throws SQLException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT * FROM ProductImage WHERE productId = '" + product_id + "'");
        return ProductParser.parseProductImage(resultSet);
    }

    public JSONArray getCategories() throws SQLException {
        JSONArray array = new JSONArray();
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT * FROM Category;");
        while (resultSet.next()) {
            array.add(resultSet.getString("categoryName"));
        }
        resultSet.close();
        return array;
    }

    public void addCategory(String categoryName) throws SQLException {
        dataBase.getStatement().execute("INSERT INTO Category VALUES('" + categoryName + "');");
    }


    //select * from table order by id asc limit 50 offset 0; -- Returns rows 1-50

    public JSONArray getList(int page) throws SQLException, ParseException {
        final String queryCheck = "SELECT * FROM Product limit 50 offset " + ((page - 1) * 50) + ";";
        return getProducts(queryCheck);
    }

    public JSONArray getListByCategory(int page, String category) throws SQLException, ParseException {
        final String queryCheck = "SELECT * FROM Product WHERE categoryName = '" + category + "' limit 50 offset " + ((page - 1) * 50) + ";";
        return getProducts(queryCheck);
    }

    private JSONArray getProducts(String Query) throws SQLException, ParseException {
        ResultSet resultSet;
        JSONArray array = new JSONArray();
        JSONObject product;
        resultSet = dataBase.getStatement().executeQuery(Query);
        while (resultSet.next()) {
            product = ProductParser.parseProduct(resultSet);
            product.put("stores", getProductStores(product.getAsString("id")));
            product.put("images", getProductImages(product.getAsString("id")));
            array.add(product);
        }
        resultSet.close();
        return array;
    }

    public void deleteProduct(String product_id) throws SQLException {
        dataBase.getStatement().execute("DELETE FROM Product WHERE productId = '" + product_id + "'");
    }

}
