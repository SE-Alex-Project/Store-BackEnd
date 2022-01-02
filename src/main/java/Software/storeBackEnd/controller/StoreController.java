package Software.storeBackEnd.controller;

import Software.storeBackEnd.database.StoreDatabase;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;


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
    public String addStore(@RequestBody JSONObject store) {
        storeDataBase.add(store.getAsString("name"), store.getAsString("location"));
        return "true";
    }

    @PostMapping("/get_list")
    public JSONArray getStoreList() throws SQLException, ParseException {
        return storeDataBase.getList();
    }

    @PostMapping("/get")
    public JSONObject getStore(@RequestBody String store_id) throws SQLException, ParseException {
        return storeDataBase.get(store_id);
    }

}
