package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.authentication.Validation;
import Software.storeBackEnd.database.EmployeeDatabase;
import Software.storeBackEnd.database.ReportsDataBase;
import Software.storeBackEnd.entities.Employee;
import Software.storeBackEnd.entities.UserType;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin
@RestController
@RequestMapping("/manager")
public class ManagerController {

    EmployeeDatabase employeeDatabase = new EmployeeDatabase();
    ReportsDataBase reportsDataBase = new ReportsDataBase();

    /*{
        "token" : "token",
        "email":"user email",
        "firstName": "user first name",
        "lastName": "user last name",
        "password": "user hashed password",
        "store":"1",
        "erole":role,
        "salary":salary
    }*/
    @PostMapping("/addEmployee")//validate_employee
    public ResponseEntity<String> addEmployee(@RequestBody JSONObject employee) {
        try {
            Validation.validate_employee(employee);
            UserType user = Authentication.tokenUserType(employee.getAsString("token"));
            if (user == UserType.Manager) {
                if (Authentication.isEmployeeEmail(employee.getAsString("email")))
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This Email Have an Account!!!\n");
                Employee E = new Employee(employee);
                employeeDatabase.insertEmployee(E);
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Owner Access\n");
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/getEmployees")
    public ResponseEntity<?> getEmployees(@RequestBody String token) {
        try {
            UserType user = Authentication.tokenUserType(token);
            if (user != UserType.Manager)
                throw new RuntimeException("Can't get Employees from not manager account\n");
            return ResponseEntity.status(HttpStatus.OK).body(employeeDatabase.getEmployees());
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (ParseException e) {
            return Controller.ParserEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    /*
     * {
     * "token": token
     * "email":emp-email
     * "fName":emp-fname
     * "lName":emp-lname
     * "storeId":id
     * }
     */

    @PostMapping("/getEmployeeInfo")
    public ResponseEntity<?> getEmployeeInfo(@RequestBody JSONObject getEmp) {
        try {
            Validation.validate_getEmployee(getEmp);
            return ResponseEntity.status(HttpStatus.OK).body(employeeDatabase.getEmployee(getEmp.getAsString("email")));
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (ParseException e) {
            return Controller.ParserEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }


    @PostMapping("/modifyEmployee")
    public ResponseEntity<?> modifyEmployee(@RequestBody JSONObject employee) {
        try {
            Validation.validate_employee(employee);
            Employee emp = new Employee(employee);
            employeeDatabase.modifyEmployee(emp);
            return ResponseEntity.status(HttpStatus.OK).body("Employee updated");
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/top10Customers")
    public ResponseEntity<?> topCustomers() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reportsDataBase.topCustomersLast3M());
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (ParseException e) {
            return Controller.ParserEx(e);
        }
    }


    @GetMapping("/totalSales")
    public ResponseEntity<?> totalSales() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reportsDataBase.totalSales());
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (ParseException e) {
            return Controller.ParserEx(e);
        }
    }

    @GetMapping("/top10Sales")
    public ResponseEntity<?> topSales() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reportsDataBase.topSalesLast3M());
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (ParseException e) {
            return Controller.ParserEx(e);
        }
    }

}
