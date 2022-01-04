package Software.storeBackEnd.controller;

import Software.storeBackEnd.database.StoreDatabase;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;


@RestController
@CrossOrigin
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
    public ResponseEntity<String> addStore(@RequestBody JSONObject store) {
        try {
            storeDataBase.add(store.getAsString("name"), store.getAsString("location"));
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error While Fetch Data From DataBase\n"+e.getMessage());
        }
    }

    @PostMapping("/get_list")
    public ResponseEntity<?> getStoreList() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(storeDataBase.getList());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error While Fetch Data From DataBase\n"+e.getMessage());
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error IN Parsing JsonObject\n"+e.getMessage());
        }
    }

    @PostMapping("/get")
    public ResponseEntity<?> getStore(@RequestBody String store_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(storeDataBase.get(store_id));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error While Fetch Data From DataBase\n"+e.getMessage());
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error IN Parsing JsonObject\n"+e.getMessage());
        }
    }

}
