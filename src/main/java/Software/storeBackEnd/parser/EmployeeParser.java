package Software.storeBackEnd.parser;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class EmployeeParser {

	public static JSONObject parseEmployee(ResultSet resultSet) throws SQLException, ParseException {
        return (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"email\":'" + resultSet.getString("email") + "',\"fName\":'"
                + resultSet.getString("fName") + "',\"lName\":'" + resultSet.getString("lName")+ "',\"erole\":'" + resultSet.getString("erole")
                + "',\"storeId\":'" + resultSet.getString("storeId") + 
                "',\"salary\":'" + resultSet.getString("salary")+"'}");
    }
}
