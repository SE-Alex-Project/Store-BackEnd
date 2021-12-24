package Software.storeBackEnd.controller;

import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

	@Test

    void get_add_Product() throws ParseException {
		JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"id\":123,\"name\":adf}");
        ProductController pc = new ProductController();
        System.out.println(pc.getProduct("1"));

	    assertEquals(pc.getProduct("1"),ob1);
	 

    }

}