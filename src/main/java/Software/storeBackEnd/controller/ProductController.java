package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.authentication.TokenManager;
import Software.storeBackEnd.database.ProductDatabase;
import Software.storeBackEnd.entities.Product;
import Software.storeBackEnd.entities.UserType;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

    ProductDatabase productDataBase = new ProductDatabase();
    TokenManager token = TokenManager.getInstance();

    /*product json format
   {
   "addedBy":"user token"
   "name": "name",
   "price":"12.5",
   "category" : "product category",
   "description" : "hello products",
   "stores": ["ID:1","2","2","4"],
   "images": ["product image 1 (main image)", "product image 2" , "product image 3"]
   }
    */
    @PostMapping("/add")
    public String addProduct(@RequestBody JSONObject product) throws SQLException {
        String userMail = token.getUser(product.getAsString("addedBy"));
        UserType userType = Authentication.getUserType(userMail);
        switch (userType){
            case Employee,Manager :{
                Product p = new Product(product);
                p.setAddedBy(userMail);
                productDataBase.addProduct(p);
                return "true";
            }
            default : return "Invalid Employee Access";
        }
    }

    /*
    return product json object
     */
    @GetMapping("/get")
    public JSONObject getProduct(@RequestParam("pId") String product_id) {
        return productDataBase.getProduct(product_id);
    }

    /*
        {"product" : [product 1 , product 2 , product 3]}
     */
    @PostMapping("/product_list")
    public JSONArray getProductList(@RequestBody int page) {
        return productDataBase.getList(page);
    }


    /*
    {
    "page" : "1",
    "category" : "category name"
    }
     */
    @PostMapping("/product_list_category")
    public JSONArray getCategoryList(@RequestBody JSONObject productCategory) {
        return productDataBase.getListByCategory(Integer.parseInt(productCategory.getAsString("page")), productCategory.getAsString("category"));
    }

    @PostMapping("/categories")
    public JSONArray getCategories() {
        return productDataBase.getCategories();

    }


    /*
     * {"product_id":[id1 ,id2 ,id3,.....]}
     * */
    @SuppressWarnings("unchecked")
    @DeleteMapping("/delete")
    public void deleteProduct(@RequestBody JSONObject product_ids) {
        ArrayList<String> products = (ArrayList<String>) product_ids.get("product_id");
        for (String s : products) {
            productDataBase.deleteProduct(s);
        }
    }


}
