package Software.storeBackEnd.parser;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class ReportParser {
	
	public static JSONObject parseSales(ResultSet resultSet) throws SQLException, ParseException {
        return (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"productName\":'" + resultSet.getString("productName") + "',\"productId\":'"
                + resultSet.getString("productId") + "',\"quantity\":'" + resultSet.getString("number") + "',\"price\":'" +
                resultSet.getString("price") + "'}");
    }

}
