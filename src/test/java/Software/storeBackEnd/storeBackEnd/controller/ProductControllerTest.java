package Software.storeBackEnd.storeBackEnd.controller;

import Software.storeBackEnd.controller.ProductController;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductControllerTest {

	@Test

    void get_Product() throws ParseException {
        ProductController pc = new ProductController();
		JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/shop.orange.eg\\/content\\/images\\/thumbs\\/0002724_iphone-12-pro.jpeg\",\"https:\\/\\/shop.orange.eg\\/content\\/images\\/thumbs\\/0002726_iphone-12-pro.jpeg\"],\"price\":23.5,\"stores\":[\"1\",\"200\"],\"name\":\"Iphone 12 pro\",\"description\":\"Body Weight:189 g (6.67 oz)\\nDisplay Size:6.1 inches\\nDisplay type: Super Retina XDR OLED\",\"id\":1,\"category\":\"Smart Phone\"}");
        JSONObject ob2 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/mob4g.com\\/wp-content\\/uploads\\/2019\\/02\\/20190217_185342-600x600.jpg\",\"https:\\/\\/www.vapulus.com\\/ar\\/wp-content\\/uploads\\/2018\\/08\\/Oppo-F9-Pro-1-696x435.jpg\"],\"price\":5.5,\"stores\":[\"1\",\"200\"],\"name\":\"oppo f9 pro\",\"description\":\"Body Weight:189 g (6.67 oz)\\nDisplay Size:6.1 inches\\nDisplay type: Super Retina XDR OLED\",\"id\":2,\"category\":\"Smart Phone\"}");
	    assertEquals(pc.getProduct("1"),ob1);
        assertEquals(pc.getProduct("2"),ob2);
    }

}