package Software.storeBackEnd.storeBackEnd.controller;

import Software.storeBackEnd.controller.UserController;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserControllerTest {


    @Test
    void Sign_UP() throws Exception {
        UserController uc = new UserController();
        JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "            \"email\":\"test@test.test\",\n" +
                "                \"firstName\": \"test\",\n" +
                "                \"lastName\": \"test\",\n" +
                "                \"password\":\"test111111111\"\n" +
                "        }");
        JSONObject ob2 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "            \"email\":\"youssef@employee.com\",\n" +
                "                \"firstName\": \"test2\",\n" +
                "                \"lastName\": \"test2\",\n" +
                "                \"password\":\"test111111111\"\n" +
                "        }");

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("This Email Have an Account!!!\nLog In Instead"),uc.signUp(ob1));

        assertEquals(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Not an customer account.\n"),uc.signUp(ob2));
    }


    @Test
    void Log_In() throws ParseException, SQLException {
        UserController uc = new UserController();
        JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "        \"email\":\"test@test.test\",\n" +
                "            \"password\":\"8820915566360241\"\n" +
                "    }");
         JSONObject ob2 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "        \"email\":\"test@test.test\",\n" +
                "            \"password\":\"8820ac6360241\"\n" +
                "    }");
         JSONObject ob3 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "        \"email\":\"ay7agatest@test.test\",\n" +
                "            \"password\":\"8820ac6360241\"\n" +
                "    }");

        assertEquals( ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Wrong email or Password\n"),uc.logIn(ob2)); //wrong password case
        assertEquals( ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Wrong email or Password\n"),uc.logIn(ob3)); //wrong e-mail case
    }

    @Test
    void UserInfo() throws ParseException, SQLException {
        UserController uc = new UserController();
        JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "        \"email\":\"test@test.test\",\n" +
                "            \"password\":\"12345678\"\n" +
                "    }");
        JSONObject ob2 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "            \"email\":\"testt@test.test\",\n" +
                "                \"firstName\": \"test\",\n" +
                "                \"lastName\": \"test\",\n" +
                "                \"password\":3556498\n" +
                "        }");
          var tk = uc.logIn(ob1);
          assertEquals(ResponseEntity.status(HttpStatus.OK).body("{\"firstName\":\"test\",\"lastName\":\"test\",\"password\":\"-1861353340\",\"email\":\"test@test.test\"}").toString(),uc.userInfo(tk.getBody()).toString());

    }

}

