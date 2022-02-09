package Software.storeBackEnd.storeBackEnd.controller;

import Software.storeBackEnd.authentication.TokenManager;
import Software.storeBackEnd.controller.ManagerController;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagerControllerTest {

    @Test
    void addEmployee() throws ParseException {
        TokenManager tm = new TokenManager();
        String mToken = tm.generateToken("ahmed@manager.com");
        JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "                \"token\" : \"" + mToken + "\",\n" +
                "               \"email\":\"testt@employee.test\",\n" +
                "                \"firstName\": \"test\",\n" +
                "                \"lastName\": \"test\",\n" +
                "                \"password\": \"test11111\",\n" +
                "                \"store\":\"1\",\n" +
                "                \"erole\":\"tester\",\n" +
                "                \"salary\":\"100\"\n" +
                "        }");
        var mc = new ManagerController();
        ResponseEntity<String> s = mc.addEmployee(ob1);
        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Token , Log In First").toString(), s.toString());

        JSONObject ob2 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "                \"token\" : \"token\",\n" +
                "            \"email\":\"testt@employee.test\",\n" +
                "                \"firstName\": \"test\",\n" +
                "                \"lastName\": \"test\",\n" +
                "                \"password\": \"test\",\n" +
                "                \"store\":\"1\",\n" +
                "                \"erole\":\"tester\",\n" +
                "                \"salary\":\"100\"\n" +
                "        }");
        var mc2 = new ManagerController();
        s = mc2.addEmployee(ob2);
        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password format").toString(), s.toString());

    }
}