package Software.storeBackEnd.parser;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

public class ReportsParser {
    public static JSONObject parseTopCustomer(ResultSet resultSet) throws SQLException, ParseException {
        return (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"userEmail\":'" + resultSet.getString("userEmail") + "',\"totalPrice\":'"
                + resultSet.getString("totalPrice") + "'}");
    }
    
    public static JSONObject parseSales(ResultSet resultSet) throws SQLException, ParseException {
        return (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"productName\":'" + resultSet.getString("productName") + "',\"productId\":'"
                + resultSet.getString("productId") + "',\"quantity\":'" + resultSet.getString("number") + "',\"price\":'" +
                resultSet.getString("price") + "'}");
    }
    public static JSONObject parseTopSale(ResultSet resultSet) throws SQLException, ParseException {
        return (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"productName\":'" + resultSet.getString("productName") + "',\"price\":'"
                + resultSet.getString("price") + "',\"totalSales\":'" + resultSet.getString("totalSales")+"'}");
    }
}
