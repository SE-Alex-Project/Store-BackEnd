package Software.storeBackEnd.database;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import Software.storeBackEnd.entities.UserType;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

public class UserDatabase {

    private final Database dataBase;

    public UserDatabase() {
        dataBase = Database.getInstance();
    }

    public void insertUser(String email, String fName, String lName, String password, int id) throws SQLException {
        final String queryCheck = "INSERT INTO Customer(email,passW,fName,lName,cartId) VALUES ('" + email
                + "','" + password + "','" + fName + "','" + lName + "','" + id + "');";
        dataBase.getStatement().execute(queryCheck);
    }

    @SuppressWarnings("rawtypes")
    public void modifyUserinfo(String UserEmail, LinkedHashMap data) throws SQLException {
        dataBase.getStatement().execute("UPDATE Customer SET fname = '" + data.get("firstName") +
                "' ,lname = '" + data.get("lastName") + "' WHERE email = '" + UserEmail + "'");
    }


    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public JSONObject getUserInfo(String UserEmail) throws SQLException, ParseException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT * FROM Customer WHERE email = '" + UserEmail + "';");
        resultSet.next();
        return (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"firstName\":'" + resultSet.getString("fName") + "',\"lastName\":'"
                + resultSet.getString("lName") + "',\"email\":'" + resultSet.getString("email") + "',\"password\":'" + resultSet.getString("passW") + "'}");
    }

    public int createCart() throws SQLException {
        dataBase.getStatement().execute("INSERT INTO Cart(userEmail) values (null);");
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT cartId FROM Cart ORDER BY cartID DESC LIMIT 1;");
        resultSet.next();
        return Integer.parseInt(resultSet.getString("cartID"));
    }

    public void updateCart(String email, int id) throws SQLException {
        dataBase.getStatement().execute("update Cart set userEmail='" + email + "' where cartId='" + id + "';");
    }
    
    public String deleteAccount(String email,UserType user) throws SQLException {
    	if (user == UserType.Manager) {
    		dataBase.getStatement().execute("DELETE Manager where email='" + email + "';");
    	}else if (user == UserType.Employee) {
    		dataBase.getStatement().execute("DELETE Employee where email='" + email + "';");
    	}else if (user == UserType.Customer) {
    		dataBase.getStatement().execute("DELETE Customer where email='" + email + "';");
    	}
    	return "Logged Out";
    	
    } 
    
    public int numberOfManagers() throws SQLException {
    	ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT COUNT(*) FROM Manager");
    	resultSet.next();
        return Integer.parseInt(resultSet.getString("COUNT(*)"));
    }
}
