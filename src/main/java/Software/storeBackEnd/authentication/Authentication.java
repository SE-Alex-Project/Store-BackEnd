package Software.storeBackEnd.authentication;

import Software.storeBackEnd.database.Database;
import Software.storeBackEnd.entities.UserType;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication {
    private final static Database dataBase = Database.getInstance();
    private static TokenManager tokenManager = TokenManager.getInstance();

    public static boolean isCustomer(String name, String password) throws SQLException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT fname from Customer WHERE email = '" + name + "' AND passW = '" + password + "'");
        return resultSet.next();
    }

    public static boolean isEmployee(String name, String password) throws SQLException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT fname from Employee WHERE email = '" + name + "' AND passW = '" + password + "'");
        return resultSet.next();
    }

    public static boolean isManager(String name, String password) throws SQLException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT email from Manager WHERE email = '" + name + "' AND passW = '" + password + "'");
        return resultSet.next();
    }

    public static boolean isCustomerEmail(String email) throws SQLException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT email from Customer WHERE email = '" + email + "';");
        return resultSet.next();
    }

    public static boolean isEmployeeEmail(String email) throws SQLException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT email from Employee WHERE email = '" + email + "';");
        return resultSet.next();
    }

    public static boolean isStore(String StoreID) throws SQLException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT storeId from Store WHERE storeId = '" + StoreID + "'");
        return resultSet.next();
    }

    public static UserType getUserType(String email) {
        if (email.contains("@employee"))
            return UserType.Employee;
        else if (email.contains("@manager"))
            return UserType.Manager;
        else
            return UserType.Customer;
    }

    public static UserType tokenUserType(String token) {
        String email = tokenManager.getUser(token);
        if (email == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User Not Signed In\nSign In first\n");
        return getUserType(email);
    }

}
