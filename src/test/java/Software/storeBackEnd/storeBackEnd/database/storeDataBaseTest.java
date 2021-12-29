package Software.storeBackEnd.storeBackEnd.database;

import Software.storeBackEnd.database.StoreDatabase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class storeDataBaseTest {


    @Test
    void isExist(){
        StoreDatabase sdb = new StoreDatabase();
        sdb.add("ax", "1 pol street");
        sdb.add("vu", "1 egy street");
        sdb.add("bx", "1 sud street");
        sdb.add("er", "1 nor street");

        assertEquals(sdb.isExist("412"),false);

        }
}