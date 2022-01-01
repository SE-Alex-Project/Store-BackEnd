package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.authentication.TokenManager;
import Software.storeBackEnd.database.EmployeeDatabase;
import Software.storeBackEnd.entities.Employee;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin
@RestController
@RequestMapping("/manager")
public class ManagerController {

    EmployeeDatabase employeeDatabase = new EmployeeDatabase();
    TokenManager tokenManager = TokenManager.getInstance();
    /*{
        "token" : "token",
        "email":"user email",
        "firstName": "user first name",
        "lastName": "user last name",
        "password": "user hashed password",
        "store":"1"
    }*/
    @PostMapping("/addEmployee")
    public String addEmployee(@RequestBody JSONObject employee) throws SQLException {
        //UserType user = Authentication.getUserType(tokenManager.getUser(employee.getAsString("token")));
        //if (user == UserType.Manager){
            if (!Authentication.isEmployeeEmail(employee.getAsString("email"))) {
                Employee E = new Employee(employee);
                employeeDatabase.insertEmployee(E);
                return "OK";
            }
            return "This Email Have an Account!!!";
        //}
//        return "Invalid Owner Access";
    }


}
