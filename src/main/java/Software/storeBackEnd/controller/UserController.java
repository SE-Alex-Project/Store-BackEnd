package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.authentication.TokenManager;
import Software.storeBackEnd.database.UserDatabase;
import Software.storeBackEnd.entities.UserType;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.LinkedHashMap;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserDatabase userDataBase = new UserDatabase();
    TokenManager tokenManager = TokenManager.getInstance();

    /*log in json format
    {
    "email":user email,
    "password": user hashed password
    }
     */

    @PostMapping("/logIn")
    public String logIn(@RequestBody JSONObject logInJson) throws SQLException {
        String password = (String) logInJson.get("password");
        password = password.hashCode() + "";
        UserType userType = Authentication.getUserType(logInJson.getAsString("email"));
        boolean exist;
        switch (userType) {
            case Customer :
            	exist = Authentication.isCustomer(logInJson.getAsString("email"), password);
            	break;
            case Employee : 
            	exist = Authentication.isEmployee(logInJson.getAsString("email"), password);
            	break;
            default : 
            	exist = false;
        }
        if (exist)
            return tokenManager.generateToken(logInJson.getAsString("email"));
        return "Can't do this operation.";
    }


    /*sign up json format
    {
    "email":user email
    "firstName": user first name
    "lastName": user last name
    "password": user hashed password
    }
     */
    @PostMapping("/signUp")
    public String signUp(@RequestBody JSONObject signUpJson) throws SQLException {
        String password = (String) signUpJson.get("password");
        password = password.hashCode() + "";
        boolean exist = Authentication.isCustomerEmail(signUpJson.getAsString("email"));
        if (!exist) {
            //create cart
            int id = userDataBase.createCart();
            //create user
            userDataBase.insertUser((String) signUpJson.get("email"), (String) signUpJson.get("firstName"),
                    (String) signUpJson.get("lastName"), password,id);
            //update cart
            userDataBase.updateCart((String) signUpJson.get("email"), id);
            return tokenManager.generateToken(signUpJson.getAsString("email"));
        }
        return "Email is signed up before !!!";
    }


    @PostMapping("/logOut")
    public void logOut(@RequestBody String userToken) {
        tokenManager.removeUser(userToken);
    }


    /*
    modify info json format
    {
    "id": user token
    "data": {
        "password": new password
        "firstName": user name
        "lastName": user last name
        }
    }
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/modifyInfo")
    public String modifyInfo(@RequestBody JSONObject modifyJson) {
        String userEmail = tokenManager.getUser(modifyJson.getAsString("id"));
        if (userEmail == null)
            return "Invalid Operation Log In Again";
        userDataBase.modifyUserinfo(userEmail, (LinkedHashMap) modifyJson.get("data"));
        return "valid";
    }


    /*
    return json object same as signup object
     */
    @GetMapping("/info")
    public JSONObject userInfo(@RequestBody String userToken) {
        String userEmail = tokenManager.getUser(userToken);
        if (userEmail == null)
            return null;
        return userDataBase.getUserInfo(userEmail);
    }

}
