package Software.StoreBackEnd.Controller;

import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class userAPI {

    /*sign in json format
    {
    "email":user email,
    "password": user hashed password
    }
     */
    @PostMapping("/logIn")
    public String logIn(@RequestBody JSONObject signInJson){
        System.out.println(signInJson.get("password"));
        return "true";
    }

    @PostMapping("/signUp")
    public String signUp(@RequestBody JSONObject signInJson){
        return "valid";
    }




}
