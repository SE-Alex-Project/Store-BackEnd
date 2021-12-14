package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.Authentication;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController extends Authentication {

    /*log in json format
    {
    "email":user email,
    "password": user hashed password
    }
     */

    @PostMapping("/logIn")
    public String logIn(@RequestBody JSONObject logInJson){
        System.out.println(logInJson.get("password"));
        return "true";
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
        return "valid";
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
    @PostMapping("/modifyInfo")
    public String modifyInfo(@RequestBody JSONObject signUpJson){
        return "valid";
    }



    /*
    return json object same as signup object
     */
    @GetMapping("/info")
    public JSONObject userInfo(@RequestBody String userToken){
        return null;
    }


}