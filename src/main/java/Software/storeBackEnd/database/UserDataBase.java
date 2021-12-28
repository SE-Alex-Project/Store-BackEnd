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

    public boolean existUser(String name, String password) {
        final String queryCheck = "SELECT fname from Customer WHERE email = '" + name + "' AND passW = '" + password + "'";
        ResultSet resultSet;
        try {
            resultSet = dataBase.getStatement().executeQuery(queryCheck);

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("false");
        return false;
    }


    public boolean existEmail(String email) {
        final String queryCheck = "SELECT email from Customer WHERE email = '" + email + "';";
        ResultSet resultSet;
        try {
            resultSet = dataBase.getStatement().executeQuery(queryCheck);

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void insertUser(String email, String fName, String lName, String password) {
        final String queryCheck = "INSERT INTO Customer(email,passW,fName,lName) VALUES ('" + email
                + "','" + password + "','" + fName + "','" + lName + "');";
        try {
            System.out.println(dataBase.getStatement().execute(queryCheck));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("rawtypes")
    public void modifyUserinfo(String UserEmail, LinkedHashMap data) {
        try {
            dataBase.getStatement().execute("UPDATE Customer SET passW = '" + data.get("password") + "' ,fname = '" + data.get("firstName") +
                    "' ,lname = '" + data.get("lastName") + "' WHERE email = '" + UserEmail + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getUserInfo(String UserEmail) {
        try {
            ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT * FROM Customer WHERE email = '" + UserEmail + "';");
            resultSet.next();
            return (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"firstName\":" + resultSet.getString("fName") + ",\"lastName\":"
                    + resultSet.getString("lName") + ",\"email\":" + resultSet.getString("email") + ",\"password\":" + resultSet.getString("passW") + "}");
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int createCart() throws SQLException {
    	dataBase.getStatement().execute("INSERT INTO cart(userEmail) values (null);");
    	ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT cartId FROM cart ORDER BY cartID DESC LIMIT 1;");
        resultSet.next();
        int id = Integer.parseInt(resultSet.getString("cartID"));
        return id;
    }
    
    public void updateCart(String email,int id) throws SQLException {
    	dataBase.getStatement().execute("update cart set userEmail='"+email+"' where cartId='"+id+"';");
    }
}
