package Software.storeBackEnd.database;

import Software.storeBackEnd.entities.UserType;
import Software.storeBackEnd.parser.UserParser;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class UserDatabase {

    private final Database dataBase;

    public UserDatabase() {
        dataBase = Database.getInstance();
    }

    public void insertUser(String email, String fName, String lName, String password, int id) throws SQLException {
        final String queryCheck = "INSERT INTO Customer(email,passW,fName,lName,cartId) VALUES ('" + email
                + "','" + password + "','" + fName + "','" + lName + "','" + id + "');";
        dataBase.execute(queryCheck);
    }

    @SuppressWarnings("rawtypes")
    public void modifyUserinfo(String UserEmail, LinkedHashMap data) throws SQLException {
        dataBase.execute("UPDATE Customer SET fname = '" + data.get("firstName") +
                "' ,lname = '" + data.get("lastName") + "' WHERE email = '" + UserEmail + "'");
    }


    public JSONObject getUserInfo(String UserEmail) throws SQLException, ParseException {
        ResultSet resultSet = dataBase.executeQuery("SELECT * FROM Customer WHERE email = '" + UserEmail + "';");
        resultSet.next();
        return UserParser.parseUser(resultSet);
    }

    public int createCart() throws SQLException {
        dataBase.execute("INSERT INTO Cart(userEmail) values (null);");
        ResultSet resultSet = dataBase.executeQuery("SELECT cartId FROM Cart ORDER BY cartID DESC LIMIT 1;");
        resultSet.next();
        return Integer.parseInt(resultSet.getString("cartID"));
    }

    public void updateCart(String email, int id) throws SQLException {
        dataBase.execute("update Cart set userEmail='" + email + "' where cartId='" + id + "';");
    }
    
    public String deleteAccount(String email,UserType user) throws SQLException {
    	System.out.println(email+" "+user);
        if (user == UserType.Employee || user == UserType.Manager) {
    		dataBase.execute("DELETE From Employee where email='" + email + "'");
    	}else if (user == UserType.Customer) {
    		dataBase.execute("DELETE From Customer where email='" + email + "'");
    	}
    	return "Logged Out";
    	
    } 
    
    public int numberOfManagers() throws SQLException {
    	ResultSet resultSet = dataBase.executeQuery("SELECT COUNT(*) FROM Employee WHERE email LIKE %@manager.com%");
    	resultSet.next();
        return Integer.parseInt(resultSet.getString("COUNT(*)"));
    }

    public JSONArray UserCart(String Email) throws SQLException, ParseException {
        JSONArray Carts = new JSONArray();
        ResultSet resultSet = dataBase.executeQuery("SELECT * FROM Cart WHERE buyDate IS NOT NULL AND userEmail = '"+Email+"';");
        while (resultSet.next())
            Carts.add(UserParser.parseUserCart(resultSet));
        return Carts;
    }
}
