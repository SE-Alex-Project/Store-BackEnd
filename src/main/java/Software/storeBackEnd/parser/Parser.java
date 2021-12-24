package Software.storeBackEnd.parser;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

public class Parser {

    /*
     {
   "name": "name",
   "price":"12.5",
   "category" : "product category",
   "description" : "hello products",
   "stores": ["1","2"],
   "images": ["product image 1 (main image)", "product image 2" , "product image 3"]
   }
     */

    public static JSONObject parseProduct(ResultSet resultSet) throws SQLException, ParseException {
        return (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"id\":" + resultSet.getString("productId") + ",\"name\":"
                + resultSet.getString("productName") + ",\"price\":" + resultSet.getString("price") + ",\"category\":" +
                resultSet.getString("categoryName") + ",\"description\":" + resultSet.getString("descripe") + "}");
    }

    public static JSONArray parseProductInStore(ResultSet resultSet) throws SQLException {
        JSONArray stores = new JSONArray();
        while (resultSet.next()) {
            stores.add(resultSet.getString("storeId"));
            stores.add(resultSet.getString("quantity"));
        }
        resultSet.close();
        return stores;
    }

    public static JSONArray parseProductImage(ResultSet resultSet) throws SQLException {
        JSONArray images = new JSONArray();
        while (resultSet.next()) {
            images.add(resultSet.getString("URL"));
        }
        resultSet.close();
        return images;
    }

}
