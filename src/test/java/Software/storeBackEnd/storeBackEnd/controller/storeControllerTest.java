package Software.storeBackEnd.storeBackEnd.controller;

import Software.storeBackEnd.authentication.TokenManager;
import Software.storeBackEnd.controller.StoreController;
import Software.storeBackEnd.controller.UserController;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class storeControllerTest {

    @Test
    void addStore() throws ParseException {
        TokenManager tm = new TokenManager();
        String mToken = tm.generateToken("ahmed@manager.com");
        UserController uc = new UserController();
        JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "        \"email\":\"ahmed@manager.com\",\n" +
                "            \"password\":\"12345678\"\n" +
                "    }");
        var tk = uc.logIn(ob1);
        JSONObject store = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "                \"token\" : \"" + tk.getBody() + "\",\n" +
                "        \"name\":\"testt\",\n" +
                "            \"location\":\"test\"\n" +
                "    }");
        StoreController sc = new StoreController();
        assertEquals(ResponseEntity.status(HttpStatus.OK).body(null).toString(), sc.addStore(store).toString());
    }
}