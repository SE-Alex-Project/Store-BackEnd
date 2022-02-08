package Software.storeBackEnd.storeBackEnd.authentication;

import Software.storeBackEnd.authentication.TokenManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenManagerTest {

    @Test
    void getUser() {
        TokenManager tm = new TokenManager();
        String token1 = tm.generateToken("test@employee.com");
        assertEquals(tm.getUser(token1), "test@employee.com");

        String token2 = tm.generateToken("test@ay7aga.com");
        assertEquals(tm.getUser(token2), "test@ay7aga.com");

        assertThrows(RuntimeException.class, () -> {
            tm.getUser("ay7aga");
        });
    }


    @Test
    void removeUser() {
        TokenManager tm = new TokenManager();
        String token1 = tm.generateToken("test@employee.com");
        assertEquals(tm.getUser(token1), "test@employee.com");

        String token2 = tm.generateToken("test@ay7aga.com");
        assertEquals(tm.getUser(token2), "test@ay7aga.com");

        tm.removeUser(token2);
        assertThrows(RuntimeException.class, () -> {
            tm.getUser(token2);//user 2 removed
        });
        assertEquals(tm.getUser(token1), "test@employee.com");//user1 is not removed

    }
}