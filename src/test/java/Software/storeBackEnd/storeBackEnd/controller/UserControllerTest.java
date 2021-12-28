package Software.storeBackEnd.storeBackEnd.controller;

import Software.storeBackEnd.controller.UserController;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class UserControllerTest {


    @Test
    void Sign_UP() throws ParseException {
        UserController uc = new UserController();
        JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "            \"email\":\"testt@test.test\",\n" +
                "                \"firstName\": \"test\",\n" +
                "                \"lastName\": \"test\",\n" +
                "                \"password\":\"test\"\n" +
                "        }");
        JSONObject ob2 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "            \"email\":\"testt2@test.test\",\n" +
                "                \"firstName\": \"test2\",\n" +
                "                \"lastName\": \"test2\",\n" +
                "                \"password\":\"test2\"\n" +
                "        }");
        JSONObject ob3 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "            \"email\":\"testt3@test.test\",\n" +
                "                \"firstName\": \"test3\",\n" +
                "                \"lastName\": \"test3\",\n" +
                "                \"password\":\"test3\"\n" +
                "        }");
        assertEquals("Email is signed up before !!!",uc.signUp(ob1));
    }

    @Test
    void Log_In() throws ParseException {
        UserController uc = new UserController();
        JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "        \"email\":\"testt@test.test\",\n" +
                "            \"password\":\"test\"\n" +
                "    }");
        assertNotEquals( "Can't do this operation.",uc.logIn(ob1));


        ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "        \"email\":\"test2@test.test\",\n" +
                "            \"password\":\"test\"\n" +
                "    }");
        assertEquals( "Can't do this operation.",uc.logIn(ob1));
    }

    @Test
    void UserInfo() throws ParseException {
        UserController uc = new UserController();
        JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "        \"email\":\"testt@test.test\",\n" +
                "            \"password\":\"test\"\n" +
                "    }");
        JSONObject ob2 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "            \"email\":\"testt@test.test\",\n" +
                "                \"firstName\": \"test\",\n" +
                "                \"lastName\": \"test\",\n" +
                "                \"password\":3556498\n" +
                "        }");
        assertEquals(ob2,uc.userInfo(uc.logIn(ob1)));
    }

}

