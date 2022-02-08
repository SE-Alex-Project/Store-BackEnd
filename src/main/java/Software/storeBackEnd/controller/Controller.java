package Software.storeBackEnd.controller;

import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

public class Controller {

    public static ResponseEntity<String> SqlEx(SQLException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error While Fetch Data From DataBase\n"+e.getMessage());
    }
    public static ResponseEntity<String> ParserEx(ParseException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error IN Parsing JsonObject\n"+e.getMessage());
    }
}
