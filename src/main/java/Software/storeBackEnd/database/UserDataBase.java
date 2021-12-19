package Software.storeBackEnd.database;

import net.minidev.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public void modifyUserinfo(String UserEmail, JSONObject data) {
        System.out.println(data.getAsString("last-name"));
//        try {
//            dataBase.stmt.executeQuery("UPDATE CUSTOMER SET ");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
