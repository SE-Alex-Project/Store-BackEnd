package Software.storeBackEnd.parser;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

public class StoreParser {

    /*
    {
   "id": "store_id",
   "name":"12.5",
   "location" : "product category",
   }
     */
    public static JSONObject parseStore(ResultSet resultSet) throws SQLException, ParseException {
        return (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"id\":'" + resultSet.getString("storeId") + "',\"name\":'"
                + resultSet.getString("storeName") + "',\"location\":'" + resultSet.getString("location") + "'}");
    }
}
