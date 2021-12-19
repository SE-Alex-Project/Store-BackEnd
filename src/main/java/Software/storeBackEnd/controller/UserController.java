package Software.storeBackEnd.controller;
import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.database.UserDataBase;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController extends Authentication {

    private final UserDataBase userDataBase = new UserDataBase();


    /*log in json format
    {
    "email":user email,
    "password": user hashed password
    }
     */

    @PostMapping("/logIn")
    public String logIn(@RequestBody JSONObject logInJson){
        boolean exist = userDataBase.existUser((String)logInJson.get("email"), (String) logInJson.get("password"));
        if(exist) {
        	return generateToken((String)logInJson.get("email"));
        }
        return "Can't do this operation.";
    }


    /*sign up json format
    {
    "email":user email
    "first-name": user first name
    "last-name": user last name
    "password": user hashed password
    }
     */

    @PostMapping("/signUp")
    public String signUp(@RequestBody JSONObject signUpJson){
    	userDataBase.insertUser((String)signUpJson.get("email"), (String)signUpJson.get("first-name"),
    			(String)signUpJson.get("last-name"), (String)signUpJson.get("password"));
    	return generateToken((String)signUpJson.get("email"));
    }




    @PostMapping("/logOut")
    public void logOut (@RequestBody String userToken){

    }


    /*
    modify info json format
    {
    "id": user token
    "data": {
        "password": new password
        "first-name": user name
        "last-name": user last name
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
    public JSONObject userInfo(@RequestBody String userToken) throws ParseException {
        String userEmail = getUser(userToken);
        if (userEmail == null)
            return null;
            //return (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"email:\"Invalid Operation Log In Again}");
        return userDataBase.getUserInfo(userEmail);
    }

}
