package Software.storeBackEnd.database;

import Software.storeBackEnd.entities.Employee;
import Software.storeBackEnd.parser.EmployeeParser;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDatabase {
    private final Database dataBase;

    public EmployeeDatabase() {
        dataBase = Database.getInstance();
    }

    public void insertEmployee(Employee E) throws SQLException {
        dataBase.getStatement().execute("INSERT INTO Employee VALUES ('" + E.getEmail()
                + "','" + E.getFirstname() + "','" + E.getLastname() + "','" + E.getStoreId() + "','" + E.getHashedPassword() + "');");
    }
    
    public JSONArray getEmployees() throws SQLException {
        JSONArray array = new JSONArray();
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT * FROM Employee;");
        while (resultSet.next()) {
            array.add(resultSet.getString("email"));
        }
        resultSet.close();
        return array;
    }
    
    public JSONObject getEmployee(String email)throws SQLException, ParseException {
    	ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT * FROM Employee WHERE email = '" + email + "'");
        resultSet.next();
    	return EmployeeParser.parseEmployee(resultSet);
    }
}
