package Software.storeBackEnd.storeBackEnd.entities;

import Software.storeBackEnd.entities.Product;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @Test
    void testProductStore() {
        /* Testing productStore Class */
//        Product.productStore store1 = new Product.productStore(122 , 2);
//        Product.productStore store2 = new Product.productStore(133 , 3);
//        Product.productStore store3 = new Product.productStore(144 , 4);
//
//        assertEquals(store1.toString(),"productStore{StoreID=122, Quantity=2}");
//        assertEquals(store2.toString(),"productStore{StoreID=133, Quantity=3}");
//        assertEquals(store3.toString(),"productStore{StoreID=144, Quantity=4}");
     

       
    }
    @Test
    void testProduct() throws ParseException {
                /* Testing Product Class */

        JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/shop.orange.eg\\/content\\/images\\/thumbs\\/0002724_iphone-12-pro.jpeg\",\"https:\\/\\/shop.orange.eg\\/content\\/images\\/thumbs\\/0002726_iphone-12-pro.jpeg\"],\"price\":23.5,\"stores\":[\"1\",\"200\"],\"name\":\"Iphone 12 pro\",\"description\":\"Body Weight:189 g (6.67 oz)\\nDisplay Size:6.1 inches\\nDisplay type: Super Retina XDR OLED\",\"id\":1,\"category\":\"Smart Phone\"}");
        JSONObject ob2 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/mob4g.com\\/wp-content\\/uploads\\/2019\\/02\\/20190217_185342-600x600.jpg\",\"https:\\/\\/www.vapulus.com\\/ar\\/wp-content\\/uploads\\/2018\\/08\\/Oppo-F9-Pro-1-696x435.jpg\"],\"price\":5.5,\"stores\":[\"1\",\"200\"],\"name\":\"oppo f9 pro\",\"description\":\"Body Weight:189 g (6.67 oz)\\nDisplay Size:6.1 inches\\nDisplay type: Super Retina XDR OLED\",\"id\":2,\"category\":\"Smart Phone\"}");

        //Product.productStore store1 = new Product.productStore(1 , 200);
        ArrayList<String> imgs1 = new ArrayList<>();
                imgs1.add("https://shop.orange.eg/content/images/thumbs/0002724_iphone-12-pro.jpeg");
                imgs1.add("https://shop.orange.eg/content/images/thumbs/0002726_iphone-12-pro.jpeg");
        ArrayList<Product.productStore> stores = new ArrayList<>();
        //stores.add(store1);
        Product p1 =new Product(ob1);
        assertEquals("Iphone 12 pro",p1.getName());
        assertEquals(23.5,p1.getPrice());
        assertEquals("Smart Phone",p1.getCategory());
        assertEquals("Body Weight:189 g (6.67 oz)\nDisplay Size:6.1 inches\nDisplay type: Super Retina XDR OLED",p1.getDescription());
        assertEquals(imgs1,p1.getImagesURL());

        assertEquals(stores.toString(),p1.getStores().toString());

        ArrayList<String> imgs2 = new ArrayList<>();
        imgs2.add("https://mob4g.com/wp-content/uploads/2019/02/20190217_185342-600x600.jpg");
        imgs2.add("https://www.vapulus.com/ar/wp-content/uploads/2018/08/Oppo-F9-Pro-1-696x435.jpg");
        Product p2 =new Product(ob2);
        assertEquals("oppo f9 pro",p2.getName());
        assertEquals(5.5,p2.getPrice());
        assertEquals("Smart Phone",p2.getCategory());
        assertEquals("Body Weight:189 g (6.67 oz)\n"+"Display Size:6.1 inches\n"+"Display type: Super Retina XDR OLED",p1.getDescription());
        assertEquals(imgs2,p2.getImagesURL());
        assertEquals(stores.toString(),p2.getStores().toString());

        p1.setName("prd");
        p1.setPrice(22);
        p1.setCategory("cat1");
        p1.setDescription("desc");

        assertEquals("prd",p1.getName());
        assertEquals(22,p1.getPrice());
        assertEquals("cat1",p1.getCategory());
        assertEquals("desc",p1.getDescription());


        assertEquals("Product{" +
                "price=" + 22.0 +
                ", name='" + "prd" + '\'' +
                ", category='" + "cat1" + '\'' +
                ", description='" + "desc" + '\'' +
                ", imagesURL=" + imgs1 +
                ", stores=" + stores +
                '}',p1.toString());

    }

  
}