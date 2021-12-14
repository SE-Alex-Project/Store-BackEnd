package Software.StoreBackEnd.Controller;

import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class userAPI {

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
    "name": user name
    "password": user hashed password
    }
     */

    @PostMapping("/signUp")
    public String signUp(@RequestBody JSONObject signUpJson){
        return "valid";
    }



}
