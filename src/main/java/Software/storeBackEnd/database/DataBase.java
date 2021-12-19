package Software.storeBackEnd.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    Statement stmt;
    private static DataBase instance = null;
    private DataBase(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sonoo","root","root");
            //here sonoo is database name, root is username and password
            stmt = con.createStatement();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public DataBase getInstance(){
        if (instance==null)
            instance = new DataBase();
        return instance;
    }
    
    public boolean existUser(String name,String password) {
    	final String queryCheck = "SELECT count(*) from messages WHERE msgid = ?";
    	ResultSet resultSet;
		try {
			resultSet = stmt.executeQuery(queryCheck);
			if(resultSet.next()) {
	    	    int count = resultSet.getInt(1);
	    	    if(count > 0) {
	    	    	return true;
	    	    }
	    	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }

    





}
