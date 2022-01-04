package Software.storeBackEnd.storeBackEnd.controller;

import Software.storeBackEnd.authentication.TokenManager;
import Software.storeBackEnd.controller.ManagerController;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;
import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import static org.junit.jupiter.api.Assertions.*;

class ManagerControllerTest {

     @Test
    void addEmployee() throws ParseException {
        TokenManager tm = new TokenManager();
        String mToken = tm.generateToken("test@manager.com");
        JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "                \"token\" : \""+mToken+"\",\n" +
//                "                \"token\" : \"token\",\n" +
                "            \"email\":\"testt@employee.test\",\n" +
                "                \"firstName\": \"test\",\n" +
                "                \"lastName\": \"test\",\n" +
                "                \"password\": \"test\",\n" +
                "                \"store\":\"1\"\n" +
                "        }");
        var mc = new ManagerController();
         Throwable e = null;
         try {
             mc.addEmployee(ob1);
         } catch (Throwable ex) {
             e = ex;
         }
         assertTrue(e instanceof org.springframework.web.server.ResponseStatusException);

         JSONObject ob2 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                 "                \"token\" : \"token\",\n" +
                 "            \"email\":\"testt@employee.test\",\n" +
                 "                \"firstName\": \"test\",\n" +
                 "                \"lastName\": \"test\",\n" +
                 "                \"password\": \"test\",\n" +
                 "                \"store\":\"1\"\n" +
                 "        }");
         var mc2 = new ManagerController();
         Throwable e2 = null;
         try {
             mc2.addEmployee(ob2);
         } catch (Throwable ex) {
             e2 = ex;
         }
         assertTrue(e instanceof org.springframework.web.server.ResponseStatusException);

     }
}