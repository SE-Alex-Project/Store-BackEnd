package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.database.EmployeeDatabase;
import Software.storeBackEnd.entities.Employee;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin
@RestController
@RequestMapping("/Manager")
public class ManagerController {

    EmployeeDatabase employeeDatabase = new EmployeeDatabase();
    /*{
        "email":user email
        "firstName": user first name
        "lastName": user last name
        "password": user hashed password
        "store":"storeId"
    }*/
    @PostMapping("/add")
    public String addEmployee(@RequestBody JSONObject employee) throws SQLException {
        if(!Authentication.isEmployeeEmail(employee.getAsString("email"))){
            Employee E = new Employee(employee);
            employeeDatabase.insertEmployee(E);
        }
        return "This Email Have an Account!!!";
    }


}
