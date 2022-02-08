package Software.storeBackEnd.parser;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

public class UserParser {
    public static JSONObject parseUserCart(ResultSet resultSet) throws SQLException, ParseException {
        return (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"cartId\":'" + resultSet.getString("cartId") +
                "',\"userEmail\":'" + resultSet.getString("userEmail") + "',\"buyDate\":'" + resultSet.getString("buyDate") + "'}");
    }
}
