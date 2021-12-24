package Software.storeBackEnd.controller;

import org.junit.jupiter.api.Test;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

	@Test

    void get_add_Product() {
		JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"id\":123,\"name\":adf}");
		
		JSONObject ob2 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"id\":456,\"name\":asf}");
        
        JSONObject ob3 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"id\":789,\"name\":asl}");
        
        ProductController pc = new ProductController();

	    pc.addProduct(ob1);
	    pc.addProduct(ob2);

	    assertEquals(pc.getProduct(123),ob1);
	    assertEquals(pc.getProduct(456),ob2);
	    assertEquals(pc.getProduct(789),null);
	    assertEquals(pc.getProduct(111),null);

    }

    @Test
    void getProductList() {
    }

    @Test
    void getCategoryList() {
    }
}