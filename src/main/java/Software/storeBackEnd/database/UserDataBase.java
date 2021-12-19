package Software.storeBackEnd.database;

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

    public void modifyUserinfo() throws SQLException {
        dataBase.stmt.executeQuery("");
    }
}
