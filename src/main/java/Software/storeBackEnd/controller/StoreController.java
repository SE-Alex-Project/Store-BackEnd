package Software.storeBackEnd.controller;

import Software.storeBackEnd.database.StoreDatabase;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/store")
public class StoreController {

    private final StoreDatabase storeDataBase = new StoreDatabase();

    /*store json format
   {
   "name": "name",
   "location":"location"
   }
    */

    @PostMapping("/add")
    public String addStore(@RequestBody JSONObject store){
        storeDataBase.add(store.getAsString("name"),store.getAsString("location"));
        return "true";
    }
}
