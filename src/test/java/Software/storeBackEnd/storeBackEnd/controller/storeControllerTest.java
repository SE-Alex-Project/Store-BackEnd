package Software.storeBackEnd.storeBackEnd.controller;

import Software.storeBackEnd.controller.StoreController;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

class storeControllerTest {

    @Test
    void addStore() throws ParseException {
        JSONObject store = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\n" +
                "        \"name\":\"testt\",\n" +
                "            \"location\":\"test\"\n" +
                "    }");
        StoreController sc = new StoreController();
//        assertEquals(sc.addStore(store),"true");
    }
}