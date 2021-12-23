package Software.storeBackEnd.parser;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

public class Parser {

    /*
     {
   "id" : "product-id"
   "name": "name",
   "price":"12.5",
   "category" : "product category",
   "description" : "hello products",
   "stores": ["1","2","2","4"],
   "images": ["product image 1 (main image)", "product image 2" , "product image 3"]
   }
     */


    public static JSONObject parseProduct(ResultSet resultSet){
        try {
            return (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"id\":" +resultSet.getString("productId") + ",\"name\":"
                    +resultSet.getString("productName")+ ",\"price\":" +resultSet.getString("price")+",\"category\":"+
                    resultSet.getString("categoryName")+ ",\"description\":" +resultSet.getString("descripe")+"}");
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
