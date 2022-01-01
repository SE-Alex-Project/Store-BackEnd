package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.TokenManager;
import Software.storeBackEnd.database.UserDatabase;
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
    "userType": {c,e,m}
    "email":user email,
    "password": user hashed password
    }
     */

    @PostMapping("/logIn")
    public String logIn(@RequestBody JSONObject logInJson) throws SQLException {
        String password = (String) logInJson.get("password");
        password = password.hashCode() + "";
        boolean exist = switch (logInJson.getAsString("userType")) {
            case "c" -> userDataBase.isCustomer(logInJson.getAsString("email"), password);
            case "e" -> userDataBase.isEmployee(logInJson.getAsString("email"), password);
        };
        if (exist)
            return tokenManager.generateToken((String) logInJson.get("email"));
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
        boolean exist = userDataBase.existEmail(signUpJson.getAsString("email"));
        if (!exist) {
            //create cart
            int id = userDataBase.createCart();
            //create user
            userDataBase.insertUser((String) signUpJson.get("email"), (String) signUpJson.get("firstName"),
                    (String) signUpJson.get("lastName"), password);
            //update cart
            userDataBase.updateCart((String) signUpJson.get("email"), id);
            return tokenManager.generateToken((String) signUpJson.get("email"));
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
