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
        dataBase.execute("INSERT INTO Employee VALUES ('" + E.getEmail()
                + "','" + E.getFirstname() + "','" + E.getLastname() + "','" + E.getStoreId() + "','" + E.getHashedPassword() + "');");
    }
    
    public JSONArray getEmployees() throws SQLException, ParseException {
        JSONArray array = new JSONArray();
        JSONObject Emp;
        ResultSet resultSet = dataBase.executeQuery("SELECT * FROM Employee;");
        while (resultSet.next()) {
        	Emp = EmployeeParser.parseEmployee(resultSet);
            array.add(Emp);
        }
        resultSet.close();
        return array;
    }
    
    public JSONObject getEmployee(String email)throws SQLException, ParseException {
    	ResultSet resultSet = dataBase.executeQuery("SELECT * FROM Employee WHERE email = '" + email + "'");
        resultSet.next();
    	return EmployeeParser.parseEmployee(resultSet);
    }
    
    public void modifyEmployee(Employee emp) throws SQLException {
    	dataBase.execute("UPDATE Employee SET fName = '"+ emp.getFirstname() +"', "
    			+ "lName = '"+emp.getLastname()+"', storeId = '"+emp.getStoreId()+"' "
    					+ " WHERE email = '"+emp.getEmail()+"';");
    }
}
