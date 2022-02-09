package Software.storeBackEnd.storeBackEnd.authentication;

import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.controller.UserController;
import Software.storeBackEnd.entities.UserType;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {

    @Test
    void isCustomer() throws SQLException {
        assertTrue(Authentication.isCustomer("test@test.test", "-1861353340"));
        assertFalse(Authentication.isCustomer("software@manager.com", "-1113294952"));
        assertFalse(Authentication.isCustomer("test@employee.com", "-1113294952"));

    }

    @Test
    void isEmployee()throws SQLException {
        assertTrue(Authentication.isEmployee("test@employee.com", "-1861353340"));
        assertFalse(Authentication.isEmployee("test@customer.com", "-1113294952"));
        assertFalse(Authentication.isEmployee("test@ay7aga.com", "-1113294952"));
    }

   @Test
    void isManager() throws SQLException {
        assertFalse(Authentication.isManager("test@employee.com", "-1113294952"));
        assertTrue(Authentication.isManager("software@manager.com", "-1861353340"));
        assertFalse(Authentication.isManager("test@customer.com", "-1113294952"));
        assertFalse(Authentication.isManager("test@ay7aga.com", "-1113294952"));
    }

    @Test
    void isCustomerEmail() throws SQLException {
        assertFalse(Authentication.isCustomerEmail("software@manager.com"));
        assertFalse(Authentication.isCustomerEmail("software@employee.com"));
        assertTrue(Authentication.isCustomerEmail("test@test.test"));

    }

    @Test
    void isEmployeeEmail() throws SQLException {
        assertTrue(Authentication.isEmployeeEmail("test@employee.com"));
        assertFalse(Authentication.isEmployeeEmail("software@manager.com"));
        assertFalse(Authentication.isCustomerEmail("software@ay7aga.com"));
    }

    @Test
    void isStore() throws SQLException {
        assertTrue(Authentication.isStore("1"));
        assertTrue(Authentication.isStore("2"));
        assertFalse(Authentication.isStore("1234"));
    }

    @Test
    void getUserType() {
        assertEquals(Authentication.getUserType("test@employee.com"), UserType.Employee );
        assertEquals(Authentication.getUserType("test@manager.com"),UserType.Manager );
        assertEquals(Authentication.getUserType("test@customer.com"),UserType.Customer );
        assertEquals(Authentication.getUserType("test@ay7aga.com"),UserType.Customer);
    }
    @Test
    void tokenUserType() throws ParseException {
        var user = new UserController();
        JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "            \"email\": \"test@test.test\",\n" +
                "             \"password\": \"12345678\"\n" +
                "        }");
        JSONObject ob2 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "            \"email\": \"test@employee.com\",\n" +
                "             \"password\": \"12345678\"\n" +
                "        }");
        JSONObject ob3 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "            \"email\": \"software@manager.com\",\n" +
                "             \"password\": \"12345678\"\n" +
                "        }");
        var t1 = user.logIn(ob1) ;
        assertEquals(Authentication.tokenUserType(t1.getBody()),UserType.Customer);
        var t2 = user.logIn(ob2) ;
        assertEquals(Authentication.tokenUserType(t2.getBody()),UserType.Employee);
        var t3 = user.logIn(ob3) ;
        assertEquals(Authentication.tokenUserType(t3.getBody()),UserType.Manager);

        Throwable e2 = null;
        try {
            Authentication.tokenUserType("ay7aga");
        } catch (Throwable ex) {
            e2 = ex;
        }
        assertTrue(e2 instanceof RuntimeException);

    }
}
