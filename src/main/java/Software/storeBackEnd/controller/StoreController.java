package Software.storeBackEnd.controller;

import Software.storeBackEnd.database.StoreDatabase;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
    public void addStore(@RequestBody JSONObject store) {
        try {
            storeDataBase.add(store.getAsString("name"), store.getAsString("location"));
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error While Fetch Data From DataBase\n");
        }
    }

    @PostMapping("/get_list")
    public JSONArray getStoreList() {
        try {
            return storeDataBase.getList();
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error While Fetch Data From DataBase\n");
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error IN Parsing JsonObject\n");
        }
    }

    @PostMapping("/get")
    public JSONObject getStore(@RequestBody String store_id) {
        try {
            return storeDataBase.get(store_id);
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error While Fetch Data From DataBase\n");
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error IN Parsing JsonObject\n");
        }
    }

}
