package Software.storeBackEnd.authentication;

import Software.storeBackEnd.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication {
    private final static Database dataBase = Database.getInstance();

    public static boolean isCustomer(String name, String password) throws SQLException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT fname from Customer WHERE email = '" + name + "' AND passW = '" + password + "'");
        if (resultSet.next())
            return true;
        return false;
    }

    public static boolean isEmployee(String name, String password) throws SQLException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT fname from Employee WHERE email = '" + name + "' AND passW = '" + password + "'");
        if (resultSet.next())
            return true;
        return false;
    }


    public static boolean isCustomerEmail(String email) throws SQLException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT email from Customer WHERE email = '" + email + "';");
        if (resultSet.next())
            return true;
        return false;
    }
}
