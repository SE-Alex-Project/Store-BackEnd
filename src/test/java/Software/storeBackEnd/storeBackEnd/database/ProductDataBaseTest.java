package Software.storeBackEnd.storeBackEnd.database;

import Software.storeBackEnd.database.ProductDatabase;
import Software.storeBackEnd.entities.Product;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductdataBaseTest {


    @Test
    void getProduct() throws ParseException, SQLException {
        ProductDatabase p = new ProductDatabase();

        JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/shop.orange.eg\\/content\\/images\\/thumbs\\/0002724_iphone-12-pro.jpeg\",\"https:\\/\\/shop.orange.eg\\/content\\/images\\/thumbs\\/0002726_iphone-12-pro.jpeg\"],\"price\":23.5,\"stores\":[\"1\",\"200\"],\"name\":\"Iphone 12 pro\",\"description\":\"Body Weight:189 g (6.67 oz)\\nDisplay Size:6.1 inches\\nDisplay type: Super Retina XDR OLED\",\"id\":1,\"category\":\"Smart Phone\"}");
        JSONObject ob2 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/mob4g.com\\/wp-content\\/uploads\\/2019\\/02\\/20190217_185342-600x600.jpg\",\"https:\\/\\/www.vapulus.com\\/ar\\/wp-content\\/uploads\\/2018\\/08\\/Oppo-F9-Pro-1-696x435.jpg\"],\"price\":5.5,\"stores\":[\"1\",\"200\"],\"name\":\"oppo f9 pro\",\"description\":\"Body Weight:189 g (6.67 oz)\\nDisplay Size:6.1 inches\\nDisplay type: Super Retina XDR OLED\",\"id\":2,\"category\":\"Smart Phone\"}");

        Product p1 = new Product(ob1);
        p.addProduct(p1);

        Product p2 = new Product(ob2);
        p.addProduct(p2);

        assertEquals(p.getProduct("1"),ob1);
        assertEquals(p.getProduct("2"),ob2);
        assertNull(p.getProduct("789"));



    }

    /*@Test
    void getlist() throws ParseException, SQLException {*/


       /* {"images":["https:\/\/eg.jumia.is\/unsafe\/fit-in\/150x150\/filters:fill(white)\/product\/95\/041322\/3.jpg?0530","https:\/\/eg.jumia.is\/unsafe\/fit-in\/150x150\/filters:fill(white)\/product\/95\/041322\/4.jpg?0530","https:\/\/eg.jumia.is\/unsafe\/fit-in\/500x500\/filters:fill(white)\/product\/95\/041322\/1.jpg?0530","https:\/\/eg.jumia.is\/unsafe\/fit-in\/500x500\/filters:fill(white)\/product\/95\/041322\/5.jpg?0530"],"price":580.0,"stores":["1","2"],"name":"Standard GM014","description":"Standard Headphone Gaming ST Standard GM014","id":4,"category":"Headset"},
                   {"images":["https:\/\/eg.jumia.is\/unsafe\/fit-in\/500x500\/filters:fill(white)\/product\/26\/696112\/1.jpg?3946","https:\/\/eg.jumia.is\/unsafe\/fit-in\/500x500\/filters:fill(white)\/product\/26\/696112\/2.jpg?7495","https:\/\/eg.jumia.is\/unsafe\/fit-in\/500x500\/filters:fill(white)\/product\/26\/696112\/5.jpg?7495"],"price":198.0,"stores":["1","5"],"name":"GIGAHERTZ M9RGB","description":"GIGAHERTZ 7.1 RGB USB Surround Gaming Headset With Noise Cancelling Mic - M9RGB For PC\/Laptop","id":6,"category":"Headset"}]
 {"images":["https:\/\/eg.jumia.is\/unsafe\/fit-in\/150x150\/filters:fill(white)\/product\/95\/041322\/3.jpg?0530","https:\/\/eg.jumia.is\/unsafe\/fit-in\/150x150\/filters:fill(white)\/product\/95\/041322\/4.jpg?0530","https:\/\/eg.jumia.is\/unsafe\/fit-in\/500x500\/filters:fill(white)\/product\/95\/041322\/1.jpg?0530","https:\/\/eg.jumia.is\/unsafe\/fit-in\/500x500\/filters:fill(white)\/product\/95\/041322\/5.jpg?0530"],"price":580.0,"stores":["1","2"],"name":"Standard GM014","description":"Standard Headphone Gaming ST Standard GM014","id":4,"category":"Headset"},
*/
     /*   ProductDatabase db = new ProductDatabase();
        JSONObject ob0 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/43\\/890632\\/1.jpg?8665\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/43\\/890632\\/2.jpg?8665\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/43\\/890632\\/4.jpg?8665\"],\"price\":139.99,\"stores\":[\"1\",\"2\"],\"name\":\"Aula S602\",\"description\":\"Aula S602 - Computer Gaming Headset RGB - Surround Sound - Microphone - Black\",\"id\":3,\"category\":\"Headset\"}");
        JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/3.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/4.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/1.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/5.jpg?0530\"],\"price\":580.0,\"stores\":[\"1\",\"2\"],\"name\":\"Standard GM014\",\"description\":\"Standard Headphone Gaming ST Standard GM014\",\"id\":4,\"category\":\"Headset\"}");
        JSONObject ob2 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/26\\/696112\\/1.jpg?3946\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/26\\/696112\\/2.jpg?7495\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/26\\/696112\\/5.jpg?7495\"],\"price\":198.0,\"stores\":[\"1\",\"5\"],\"name\":\"GIGAHERTZ M9RGB\",\"description\":\"GIGAHERTZ 7.1 RGB USB Surround Gaming Headset With Noise Cancelling Mic - M9RGB For PC\\/Laptop\",\"id\":6,\"category\":\"Headset\"}");
        JSONObject ob3 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/3.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/4.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/1.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/5.jpg?0530\"],\"price\":580.0,\"stores\":[\"1\",\"2\"],\"name\":\"Standard GM014\",\"description\":\"Standard Headphone Gaming ST Standard GM014\",\"id\":29,\"category\":\"Headset\"}");
        JSONObject ob4 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/26\\/696112\\/1.jpg?3946\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/26\\/696112\\/2.jpg?7495\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/26\\/696112\\/5.jpg?7495\"],\"price\":198.0,\"stores\":[\"1\",\"5\"],\"name\":\"GIGAHERTZ M9RGB\",\"description\":\"GIGAHERTZ 7.1 RGB USB Surround Gaming Headset With Noise Cancelling Mic - M9RGB For PC\\/Laptop\",\"id\":30,\"category\":\"Headset\"}");
        JSONObject ob5 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/3.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/4.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/1.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/5.jpg?0530\"],\"price\":580.0,\"stores\":[\"1\",\"2\"],\"name\":\"Standard GM014\",\"description\":\"Standard Headphone Gaming ST Standard GM014\",\"id\":31,\"category\":\"Headset\"}");
        JSONObject ob6= (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/3.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/4.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/1.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/5.jpg?0530\"],\"price\":580.0,\"stores\":[\"1\",\"2\"],\"name\":\"Standard GM014\",\"description\":\"Standard Headphone Gaming ST Standard GM014\",\"id\":40,\"category\":\"Headset\"}");
        JSONObject ob7 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/26\\/696112\\/1.jpg?3946\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/26\\/696112\\/2.jpg?7495\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/26\\/696112\\/5.jpg?7495\"],\"price\":198.0,\"stores\":[\"1\",\"5\"],\"name\":\"GIGAHERTZ M9RGB\",\"description\":\"GIGAHERTZ 7.1 RGB USB Surround Gaming Headset With Noise Cancelling Mic - M9RGB For PC\\/Laptop\",\"id\":41,\"category\":\"Headset\"}");
        JSONObject ob8 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/3.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/4.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/1.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/5.jpg?0530\"],\"price\":580.0,\"stores\":[\"1\",\"2\"],\"name\":\"Standard GM014\",\"description\":\"Standard Headphone Gaming ST Standard GM014\",\"id\":42,\"category\":\"Headset\"}");
        JSONObject ob9 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/3.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/4.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/1.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/5.jpg?0530\"],\"price\":580.0,\"stores\":[\"1\",\"2\"],\"name\":\"Standard GM014\",\"description\":\"Standard Headphone Gaming ST Standard GM014\",\"id\":43,\"category\":\"Headset\"}");
        JSONObject ob10 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/26\\/696112\\/1.jpg?3946\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/26\\/696112\\/2.jpg?7495\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/26\\/696112\\/5.jpg?7495\"],\"price\":198.0,\"stores\":[\"1\",\"5\"],\"name\":\"GIGAHERTZ M9RGB\",\"description\":\"GIGAHERTZ 7.1 RGB USB Surround Gaming Headset With Noise Cancelling Mic - M9RGB For PC\\/Laptop\",\"id\":44,\"category\":\"Headset\"}");
        JSONObject ob11 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/3.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/4.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/1.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/5.jpg?0530\"],\"price\":580.0,\"stores\":[\"1\",\"2\"],\"name\":\"Standard GM014\",\"description\":\"Standard Headphone Gaming ST Standard GM014\",\"id\":45,\"category\":\"Headset\"}");
        JSONObject ob12 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/3.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/4.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/1.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/5.jpg?0530\"],\"price\":580.0,\"stores\":[\"1\",\"2\"],\"name\":\"Standard GM014\",\"description\":\"Standard Headphone Gaming ST Standard GM014\",\"id\":46,\"category\":\"Headset\"}");
        JSONObject ob13 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/26\\/696112\\/1.jpg?3946\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/26\\/696112\\/2.jpg?7495\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/26\\/696112\\/5.jpg?7495\"],\"price\":198.0,\"stores\":[\"1\",\"5\"],\"name\":\"GIGAHERTZ M9RGB\",\"description\":\"GIGAHERTZ 7.1 RGB USB Surround Gaming Headset With Noise Cancelling Mic - M9RGB For PC\\/Laptop\",\"id\":47,\"category\":\"Headset\"}");
        JSONObject ob14= (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/3.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/150x150\\/filters:fill(white)\\/product\\/95\\/041322\\/4.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/1.jpg?0530\",\"https:\\/\\/eg.jumia.is\\/unsafe\\/fit-in\\/500x500\\/filters:fill(white)\\/product\\/95\\/041322\\/5.jpg?0530\"],\"price\":580.0,\"stores\":[\"1\",\"2\"],\"name\":\"Standard GM014\",\"description\":\"Standard Headphone Gaming ST Standard GM014\",\"id\":48,\"category\":\"Headset\"}");


        Product p0 = new Product(ob0);
        db.addProduct(p0);

        Product p1 = new Product(ob1);
        db.addProduct(p1);

        Product p2 = new Product(ob2);
        db.addProduct(p2);

        Product p3 = new Product(ob3);
        db.addProduct(p3);

        Product p4 = new Product(ob4);
        p.addProduct(p4);

        Product p5 = new Product(ob5);
        p.addProduct(p5);

        Product p6 = new Product(ob6);
        p.addProduct(p6);

        Product p7 = new Product(ob7);
        p.addProduct(p7);

        Product p8 = new Product(ob8);
        p.addProduct(p8);

    Product p9 = new Product(ob9);
        p.addProduct(p9);

        Product p10 = new Product(ob10);
        p.addProduct(p10);

        Product p11 = new Product(ob11);
        p.addProduct(p11);

        Product p12 = new Product(ob12);
        p.addProduct(p12);

        Product p13 = new Product(ob13);
        p.addProduct(p13);

        Product p14 = new Product(ob14);
        p.addProduct(p14);

        for(int count = 50 ; count <100 ; count++) {

            db.deleteProduct("count");
        }
        JSONArray array1 = new JSONArray();

        array1.add(ob0);
        array1.add(ob1);
        array1.add(ob2);
        array1.add(ob3);
        array1.add(ob4);
        array1.add(ob5);
        array1.add(ob6);
        array1.add(ob7);
        array1.add(ob8);
        array1.add(ob9);
        array1.add(ob10);
        array1.add(ob11);
        array1.add(ob12);
        array1.add(ob13);
        array1.add(ob14);
        assertEquals(db.g(1,"Headset"),array1);


    }

    @Test
    void getListByCategory() throws ParseException {
        ProductDataBase p = new ProductDataBase();

        JSONObject ob1 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/shop.orange.eg\\/content\\/images\\/thumbs\\/0002724_iphone-12-pro.jpeg\",\"https:\\/\\/shop.orange.eg\\/content\\/images\\/thumbs\\/0002726_iphone-12-pro.jpeg\"],\"price\":23.5,\"stores\":[\"1\",\"200\"],\"name\":\"Iphone 12 pro\",\"description\":\"Body Weight:189 g (6.67 oz)\\nDisplay Size:6.1 inches\\nDisplay type: Super Retina XDR OLED\",\"id\":1,\"category\":\"Smart Phone\"}");
        JSONObject ob2 = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"images\":[\"https:\\/\\/mob4g.com\\/wp-content\\/uploads\\/2019\\/02\\/20190217_185342-600x600.jpg\",\"https:\\/\\/www.vapulus.com\\/ar\\/wp-content\\/uploads\\/2018\\/08\\/Oppo-F9-Pro-1-696x435.jpg\"],\"price\":5.5,\"stores\":[\"1\",\"200\"],\"name\":\"oppo f9 pro\",\"description\":\"Body Weight:189 g (6.67 oz)\\nDisplay Size:6.1 inches\\nDisplay type: Super Retina XDR OLED\",\"id\":2,\"category\":\"Smart Phone\"}");

        Product p1 = new Product(ob1);
        p.addProduct(p1);

        Product p2 = new Product(ob2);
        p.addProduct(p2);

        JSONArray array1 = new JSONArray();

        array1.add(ob1);
        array1.add(ob2);
        //array1.add(ob3);
        assertEquals(p.getProduct("1"),p1);
        assertEquals(p.getProduct("2"),p2);

     assertEquals(p.getListByCategory(,"Smart TV"),array2);

        p.deleteProduct("1");
        p.deleteProduct("2");
        assertEquals(p.getListByCategory(1,"Smart TV"),array1);
    }*/
}