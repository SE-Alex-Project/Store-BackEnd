package Software.storeBackEnd.storeBackEnd.database;

import Software.storeBackEnd.database.UserDataBase;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import static org.junit.jupiter.api.Assertions.*;

class UserDataBaseTest {

    @Test
    void existUser() {
        UserDataBase ud = new UserDataBase();
        ud.insertUser("test@test.com","ftest","ltest","pass");
        ud.insertUser("test2@test.com","ftest2","ltest","pass");
        ud.insertUser("test3@test.com","ftest3","ltest","pass");

        assertTrue(ud.existUser("test@test.com", "pass"));
        assertTrue(ud.existUser("test2@test.com", "pass"));
        assertTrue(ud.existUser("test3@test.com", "pass"));
        assertFalse(ud.existUser("tt", "rr"));
    }

    @Test
    void existEmail() {
        UserDataBase ud = new UserDataBase();
        ud.insertUser("test@test.com","ftest","ltest","pass");
        ud.insertUser("test2@test.com","ftest2","ltest","pass");
        ud.insertUser("test3@test.com","ftest3","ltest","pass");

        assertTrue(ud.existEmail("test@test.com"));
        assertTrue(ud.existEmail("test2@test.com"));
        assertTrue(ud.existEmail("test3@test.com"));
        assertFalse(ud.existEmail("tt"));
    }

    @Test
    void getUserInfo() throws ParseException {
        UserDataBase ud = new UserDataBase();
        ud.insertUser("test@test.com", "ftest", "ltest", "pass");
        ud.insertUser("test2@test.com", "ftest2", "ltest", "pass");

        JSONObject ob1 =    (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"firstName\":" + "ftest" + ",\"lastName\":"
                + "ltest" + ",\"email\":" + "test@test.com" + ",\"password\":" + "pass" + "}");


        assertEquals(ud.getUserInfo("test@test.com"),ob1);
        assertNull(ud.getUserInfo("sdcsdg"));
    }
}