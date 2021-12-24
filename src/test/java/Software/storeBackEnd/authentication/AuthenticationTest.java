package Software.storeBackEnd.authentication;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthenticationTest {

    @Test
    void testUser() {
        Authentication auth = new Authentication();
        String token1 = auth.generateToken("asdf@qwe.com");
        String token2 = auth.generateToken("abc@qwe.com");
        String token3 =auth.generateToken("cde@qwe.com");
        String token4 =auth.generateToken("asw@qwe.com");
         assertEquals  ( token1 ,auth.getUser(token1) );
         assertEquals  ( token2 ,auth.getUser(token2) );
         assertEquals  ( token3 ,auth.getUser(token3) );
         assertEquals  ( token4 ,auth.getUser(token4) );
         assertEquals  ( null ,auth.getUser("12345") );


    }
}