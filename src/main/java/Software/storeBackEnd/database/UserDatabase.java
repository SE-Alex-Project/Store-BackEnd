package Software.storeBackEnd.database;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

public class UserDatabase {

    private final Database dataBase;

    public UserDatabase() {
        dataBase = Database.getInstance();
    }

    public void insertUser(String email, String fName, String lName, String password, int id) {
        final String queryCheck = "INSERT INTO Customer(email,passW,fName,lName,cartId) VALUES ('" + email
                + "','" + password + "','" + fName + "','" + lName + "','" + id + "');";
        try {
            System.out.println(dataBase.getStatement().execute(queryCheck));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("rawtypes")
    public void modifyUserinfo(String UserEmail, LinkedHashMap data) throws SQLException {
        dataBase.getStatement().execute("UPDATE Customer SET passW = '" + data.get("password") + "' ,fname = '" + data.get("firstName") +
                "' ,lname = '" + data.get("lastName") + "' WHERE email = '" + UserEmail + "'");
    }

    public JSONObject getUserInfo(String UserEmail) throws SQLException, ParseException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT * FROM Customer WHERE email = '" + UserEmail + "';");
        resultSet.next();
        return (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"firstName\":" + resultSet.getString("fName") + ",\"lastName\":"
                + resultSet.getString("lName") + ",\"email\":" + resultSet.getString("email") + ",\"password\":" + resultSet.getString("passW") + "}");
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
}
