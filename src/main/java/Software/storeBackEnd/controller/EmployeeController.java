package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.TokenManager;
import Software.storeBackEnd.database.EmployeeDatabase;
import Software.storeBackEnd.entities.Employee;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin
@RestController
@RequestMapping("/Employee")
public class EmployeeController {

    EmployeeDatabase employeeDatabase = new EmployeeDatabase();
    TokenManager employeeTokenManager = new TokenManager();
    /*{
        "email":user email
        "firstName": user first name
        "lastName": user last name
        "password": user hashed password
        "store":"storeId"
    }*/
    @PostMapping("/add")
    public String addEmployee(@RequestBody JSONObject employee) throws SQLException {
        if(!employeeDatabase.isExist(employee.getAsString("email"))){
            Employee E = new Employee(employee);
            employeeDatabase.insertEmployee(E);
        }
        return "This Email Have an Account!!!";
    }

//    @PostMapping("/logIn")
//    public String logIn(@RequestBody JSONObject logInJson){
//        String password = (String)logInJson.get("password");
//        password = password.hashCode()+"";
//        boolean exist = userDataBase.existUser((String)logInJson.get("email"), password);
//        if(exist) {
//            return userTokenManager.generateToken((String)logInJson.get("email"));
//        }
//        return "Can't do this operation.";
//    }

}
