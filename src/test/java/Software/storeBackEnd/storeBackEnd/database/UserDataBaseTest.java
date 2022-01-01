package Software.storeBackEnd.storeBackEnd.database;

import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.database.UserDatabase;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import static org.junit.jupiter.api.Assertions.*;

class UserDataBaseTest {

    @Test
    void existUser() throws SQLException {
        UserDatabase ud = new UserDatabase();
        ud.insertUser("test@test.com","ftest","ltest","pass");
        ud.insertUser("test2@test.com","ftest2","ltest","pass");
        ud.insertUser("test3@test.com","ftest3","ltest","pass");

        assertTrue(Authentication.isCustomer("test@test.com", "pass"));
        assertTrue(Authentication.isCustomer("test2@test.com", "pass"));
        assertTrue(Authentication.isCustomer("test3@test.com", "pass"));
        assertFalse(Authentication.isCustomer("tt", "rr"));
    }

    @Test
    void existEmail() throws SQLException {
        UserDatabase ud = new UserDatabase();
        ud.insertUser("test@test.com","ftest","ltest","pass");
        ud.insertUser("test2@test.com","ftest2","ltest","pass");
        ud.insertUser("test3@test.com","ftest3","ltest","pass");

        assertTrue(Authentication.isCustomerEmail("test@test.com"));
        assertTrue(Authentication.isCustomerEmail("test2@test.com"));
        assertTrue(Authentication.isCustomerEmail("test3@test.com"));
        assertFalse(Authentication.isCustomerEmail("tt"));
    }

    @Test
    void getUserInfo() throws ParseException {
        UserDatabase ud = new UserDatabase();
        ud.insertUser("test@test.com", "ftest", "ltest", "pass");
        ud.insertUser("test2@test.com", "ftest2", "ltest", "pass");

        JSONObject ob1 =    (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"firstName\":" + "ftest" + ",\"lastName\":"
                + "ltest" + ",\"email\":" + "test@test.com" + ",\"password\":" + "pass" + "}");


        assertEquals(ud.getUserInfo("test@test.com"),ob1);
        assertNull(ud.getUserInfo("sdcsdg"));
    }
}