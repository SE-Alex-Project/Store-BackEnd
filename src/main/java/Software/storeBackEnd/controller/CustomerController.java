package Software.storeBackEnd.controller;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController {


    @PostMapping("/addToCart")
    public void addToCart(@RequestBody JSONArray products_id){
    }

    @PostMapping("/buy")
    public void buy(@RequestBody String user_id){
    }

}
