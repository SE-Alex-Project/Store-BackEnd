package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.authentication.TokenManager;
import Software.storeBackEnd.database.EmployeeDatabase;
import Software.storeBackEnd.entities.Employee;
import Software.storeBackEnd.entities.UserType;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public void addEmployee(@RequestBody JSONObject employee) {
        try {
            UserType user = Authentication.getUserType(tokenManager.getUser(employee.getAsString("token")));
            switch (user) {
                case Manager -> {
                    if (Authentication.isEmployeeEmail(employee.getAsString("email")))
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Email Have an Account!!!\n");
                    Employee E = new Employee(employee);
                    employeeDatabase.insertEmployee(E);
                }
                default -> throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Owner Access\n");
            }
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error While Fetch Data From DataBase\n");
        }
    }

}
