package Software.storeBackEnd.storeBackEnd.controller;

import Software.storeBackEnd.controller.ProductController;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductControllerTest {

	@Test

    void get_Product() throws ParseException {
        ProductController pc = new ProductController();
		JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https://images-na.ssl-images-amazon.com/images/I/41xShlnTZTL._SX376_BO1,204,203,200_.jpg\"],\"price\":\"200.00\",\"stores\":[{\"quantity\":\"2\",\"name\":\"Online\",\"id\":\"1\"},{\"quantity\":\"11\",\"name\":\"Alex Store\",\"id\":\"2\"}],\"name\":\"Clean Code\",\"description\":\"Clean Code: A Handbook of Agile Software Craftsmanship 1st Edition\",\"id\":\"3\",\"category\":\"book\"}");
        JSONObject ob2 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/24\\/360512\\/1.jpg?4819\"],\"price\":\"350.00\",\"stores\":[{\"quantity\":\"9\",\"name\":\"Online\",\"id\":\"1\"},{\"quantity\":\"25\",\"name\":\"Alex Store\",\"id\":\"2\"},{\"quantity\":\"122\",\"name\":\"Cairo Store\",\"id\":\"3\"}],\"name\":\"Nokia 106\",\"description\":\"1.8-inch Dual SIM Mobile Phone - Dark Grey\",\"id\":\"5\",\"category\":\"mobile\"}");
	    assertEquals(pc.getProduct("3"),ResponseEntity.status(HttpStatus.OK).body(ob1));
        assertEquals(pc.getProduct("5"),ResponseEntity.status(HttpStatus.OK).body(ob2));
    }

}