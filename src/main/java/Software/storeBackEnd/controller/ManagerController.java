package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.authentication.TokenManager;
import Software.storeBackEnd.database.EmployeeDatabase;
import Software.storeBackEnd.entities.Employee;
import Software.storeBackEnd.entities.UserType;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> addEmployee(@RequestBody JSONObject employee) {
        try {
            UserType user = Authentication.getUserType(tokenManager.getUser(employee.getAsString("token")));
            if (user == UserType.Manager) {
                if (Authentication.isEmployeeEmail(employee.getAsString("email")))
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This Email Have an Account!!!\n");
                Employee E = new Employee(employee);
                employeeDatabase.insertEmployee(E);
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Owner Access\n");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error While Fetch Data From DataBase\n");
        }
    }

}
