package Software.storeBackEnd.storeBackEnd.database;

import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.database.StoreDatabase;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class storeDataBaseTest {


    @Test
    void isExist() throws SQLException {
        StoreDatabase sdb = new StoreDatabase();
        sdb.add("ax", "1 pol street");
        sdb.add("vu", "1 egy street");
        sdb.add("bx", "1 sud street");
        sdb.add("er", "1 nor street");

        assertEquals(Authentication.isStore("412"),false);

        }
}