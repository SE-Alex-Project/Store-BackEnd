package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.Validation;
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
   "token": token
   "name": "name",
   "location":"location"
   }
    */
    @PostMapping("/add")
    public ResponseEntity<String> addStore(@RequestBody JSONObject store) {
        try {// validate Employee or Manager
            Validation.validate_addstore(store);
            storeDataBase.add(store.getAsString("name"), store.getAsString("location"));
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/get_list")
    public ResponseEntity<?> getStoreList() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(storeDataBase.getList());
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (ParseException e) {
            return Controller.ParserEx(e);
        }
    }

    @PostMapping("/get")
    public ResponseEntity<?> getStore(@RequestBody String store_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(storeDataBase.get(store_id));
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (ParseException e) {
            return Controller.ParserEx(e);
        }
    }

}
