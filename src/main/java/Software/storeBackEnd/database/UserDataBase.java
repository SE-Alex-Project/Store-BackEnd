package Software.storeBackEnd.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class UserDataBase {

    private final DataBase dataBase;
    public UserDataBase() {
        dataBase = DataBase.getInstance();
    }

    public boolean existUser(String name,String password) {
    	final String queryCheck = "SELECT userName from Customer WHERE email = '"+name+"' AND password = '"+password+"';";
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
    public void insertUser (String email , String fname , String lname , String password) {
    	final String queryCheck = "INSERT INTO Customer(email,password,fname,lname) VALUES ('"+email
    			+"','"+password+"','"+fname+"','"+lname+"');";
		try {
			dataBase.stmt.executeQuery(queryCheck);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }

    @SuppressWarnings("rawtypes")
	public void modifyUserinfo(String UserEmail, LinkedHashMap data) {
        System.out.println(data.get("last-name"));
//        try {
//            dataBase.stmt.executeQuery("UPDATE CUSTOMER SET ");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
