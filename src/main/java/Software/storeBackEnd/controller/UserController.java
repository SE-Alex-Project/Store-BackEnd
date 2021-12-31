package Software.storeBackEnd.controller;
import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.database.UserDatabase;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.LinkedHashMap;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController extends Authentication {

    private final UserDatabase userDataBase = new UserDatabase();


    /*log in json format
    {
    "email":user email,
    "password": user hashed password
    }
     */

    @PostMapping("/logIn")
    public String logIn(@RequestBody JSONObject logInJson){
    	String password = (String)logInJson.get("password");
    	password = password.hashCode()+"";
        boolean exist = userDataBase.existUser((String)logInJson.get("email"), password);
        if(exist) {
        	return generateToken((String)logInJson.get("email"));
        }
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
    public String signUp(@RequestBody JSONObject signUpJson) throws SQLException{
    	String password = (String)signUpJson.get("password");
    	password = password.hashCode()+"";
    	boolean exist = userDataBase.existEmail(signUpJson.getAsString("email"));
        if(!exist) {
        	//create cart
        	int id = userDataBase.createCart();
        	//create user
        	userDataBase.insertUser((String)signUpJson.get("email"), (String)signUpJson.get("firstName"),
        			(String)signUpJson.get("lastName"), password);
        	//update cart
        	userDataBase.updateCart((String)signUpJson.get("email"), id);
        	return generateToken((String)signUpJson.get("email"));
        }
        return "Email is signed up before !!!";

    }




    @PostMapping("/logOut")
    public void logOut (@RequestBody String userToken){
    	removeUser(userToken);
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
    public String modifyInfo(@RequestBody JSONObject modifyJson){
        String userEmail = getUser(modifyJson.getAsString("id"));
        if (userEmail == null)
            return "Invalid Operation Log In Again";
        userDataBase.modifyUserinfo(userEmail, (LinkedHashMap) modifyJson.get("data"));
        return "valid";
    }



    /*
    return json object same as signup object
     */
    @GetMapping("/info")
    public JSONObject userInfo(@RequestBody String userToken){
        String userEmail = getUser(userToken);
        if (userEmail == null)
            return null;
            //return (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"email:\"Invalid Operation Log In Again}");
        return userDataBase.getUserInfo(userEmail);
    }

}
