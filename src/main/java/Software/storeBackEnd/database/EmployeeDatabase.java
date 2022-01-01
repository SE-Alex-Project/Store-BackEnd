package Software.storeBackEnd.database;

import Software.storeBackEnd.entities.Employee;

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
}
