package Software.storeBackEnd.database;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

public class ProductDataBase {

    private final DataBase dataBase;
    public ProductDataBase() {
        dataBase = DataBase.getInstance();
    }

    public JSONObject getProduct(String product_id){
        try {
            ResultSet resultSet = dataBase.stmt.executeQuery("SELECT name , price , category FROM PRODUCT WHERE product_id = '" + product_id +"'");
            return (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"name\":" +resultSet.getString("name") + ",\"price\":"
                    +resultSet.getString("price")+",\"category\":"+ resultSet.getString("category")+"}");
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteProduct(String product_id){
        try {
            dataBase.stmt.executeQuery("DELETE FROM PRODUCT WHERE product_id = '" + product_id +"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
