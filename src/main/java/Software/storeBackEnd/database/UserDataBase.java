package Software.storeBackEnd.database;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

public class UserDataBase {

    private final DataBase dataBase;
    public UserDataBase() {
        dataBase = DataBase.getInstance();
    }

    public boolean existUser(String name,String password) {
    	final String queryCheck = "SELECT userName from Customer WHERE email = '"+name+"' AND password = '"+password+"'";
    	ResultSet resultSet;
		try {
			resultSet = dataBase.stmt.executeQuery(queryCheck);
			
			if(resultSet.getFetchSize() > 0) {
	    	   return true;
	    	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }

    public void modifyUserinfo(String UserEmail, LinkedHashMap data) {
        try {
            dataBase.stmt.executeQuery("UPDATE CUSTOMER SET password = '"+data.get("password")+ "' ,fname = '"+data.get("first-name")+
					"' ,lname = '"+ data.get("last-name") + "' WHERE email = '" + UserEmail+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public JSONObject getUserInfo(String UserEmail){
		try {
			ResultSet resultSet = dataBase.stmt.executeQuery("SELECT fname , lname , email FROM CUSTOMER WHERE email = '" + UserEmail+"'");
			return (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"first-name\":" +resultSet.getString("fname") + ",\"last-name\":"
					+resultSet.getString("lname")+",\"email\":"+ resultSet.getString("email")+"}");
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
