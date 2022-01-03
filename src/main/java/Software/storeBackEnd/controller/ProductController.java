package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.authentication.TokenManager;
import Software.storeBackEnd.database.ProductDatabase;
import Software.storeBackEnd.entities.Product;
import Software.storeBackEnd.entities.UserType;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

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
    public void addProduct(@RequestBody JSONObject product) {
        try {
            String userMail = token.getUser(product.getAsString("addedBy"));
            UserType userType = Authentication.getUserType(userMail);
            product.put("addedBy", userMail);
            switch (userType) {
                case Employee, Manager: {
                    Product p = new Product(product);
                    productDataBase.addProduct(p);
                }
                default:
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Access\n");
            }
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error While Fetch Data From DataBase\n");
        }
    }

    /*
    return product json object
     */
    @GetMapping("/get")
    public ResponseEntity<?> getProduct(@RequestParam("pId") String product_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productDataBase.getProduct(product_id));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error While Fetch Data From DataBase\n");
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error IN Parsing JsonObject\n");
        }
    }

    /*
        {"product" : [product 1 , product 2 , product 3]}
     */
    @PostMapping("/product_list")
    public ResponseEntity<?> getProductList(@RequestBody int page) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productDataBase.getList(page));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error While Fetch Data From DataBase\n");
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error IN Parsing JsonObject\n");
        }
    }


    /*
    {
    "page" : "1",
    "category" : "category name"
    }
     */
    @PostMapping("/product_list_category")
    public ResponseEntity<?> getCategoryList(@RequestBody JSONObject productCategory) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productDataBase.getListByCategory(Integer.parseInt(productCategory.getAsString("page")), productCategory.getAsString("category")));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error While Fetch Data From DataBase\n");
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error IN Parsing JsonObject\n");
        }

    }

    @PostMapping("/categories")
    public ResponseEntity<?> getCategories() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productDataBase.getCategories());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error While Fetch Data From DataBase\n");
        }
    }

    /*
    {
    "token":"",
    "categories" : ["","",""]
    }
     */
    @PostMapping("/add_category")
    public void addCategories(@RequestBody JSONObject categories) {
        try {
            UserType userType = Authentication.tokenUserType(categories.getAsString("token"));
            switch (userType) {
                case Employee, Manager -> {
                    ArrayList<String> categoryNames = (ArrayList<String>) categories.get("categories");
                    for (String s : categoryNames) {
                        productDataBase.addCategory(s.toLowerCase(Locale.ROOT));
                    }
                }
            }
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error While Fetch Data From DataBase\n");
        }
    }


    /*
     * {"product_id":[id1 ,id2 ,id3,.....]}
     * */
    @SuppressWarnings("unchecked")
    @DeleteMapping("/delete")
    public void deleteProduct(@RequestBody JSONObject product_ids) {
        try {
            ArrayList<String> products = (ArrayList<String>) product_ids.get("product_id");
            for (String s : products)
                productDataBase.deleteProduct(s);
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error While Fetch Data From DataBase\n");
        }
    }
}
