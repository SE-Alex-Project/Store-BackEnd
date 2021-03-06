package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.authentication.TokenManager;
import Software.storeBackEnd.authentication.Validation;
import Software.storeBackEnd.database.ProductDatabase;
import Software.storeBackEnd.entities.Product;
import Software.storeBackEnd.entities.UserType;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> addProduct(@RequestBody JSONObject product) {
        try {
            Validation.validate_product1(product);
            String userMail = token.getUser(product.getAsString("addedBy"));
            UserType userType = Authentication.getUserType(userMail);
            product.put("addedBy", userMail);
            if (userType == UserType.Employee || userType == UserType.Manager) {
                Product p = new Product(product);
                return productDataBase.addProduct(p);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Access\n");
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /*product json format
    {
    "id":"product_id"
    "addedBy":"user token",
    "name": "name",
    "price":"12.5",
    "category" : "product category",
    "description" : "hello products",
    "stores": ["ID:1","2","2","4"],
    "images": ["product image 1 (main image)", "product image 2" , "product image 3"]
    }
     */
    @PostMapping("/edit")
    public ResponseEntity<String> editProduct(@RequestBody JSONObject product) {
        try {
            Validation.validate_product2(product);
            String userMail = token.getUser(product.getAsString("addedBy"));
            UserType userType = Authentication.getUserType(userMail);
            product.put("addedBy", userMail);
            int product_id = Integer.parseInt(product.getAsString("id"));
            if (userType == UserType.Employee || userType == UserType.Manager) {
                Product p = new Product(product);
                return productDataBase.editProduct(p, product_id);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Access\n");
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /*
    return product json object
     */
    @GetMapping("/get")
    public ResponseEntity<?> getProduct(@RequestParam("pId") String product_id) {
        try {
            Validation.validate_value(product_id);
            return ResponseEntity.status(HttpStatus.OK).body(productDataBase.getProduct(product_id));
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (ParseException e) {
            return Controller.ParserEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /*
        {"product" : [product 1 , product 2 , product 3]}
     */
    @PostMapping("/product_list")
    public ResponseEntity<?> getProductList(@RequestBody int page) {
        try {
            Validation.validate_value(page + "");
            return ResponseEntity.status(HttpStatus.OK).body(productDataBase.getList(page));
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (ParseException e) {
            return Controller.ParserEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
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
            Validation.validate_page(productCategory);
            return ResponseEntity.status(HttpStatus.OK).body(productDataBase.getListByCategory(Integer.parseInt(productCategory.getAsString("page")), productCategory.getAsString("category")));
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (ParseException e) {
            return Controller.ParserEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/categories")
    public ResponseEntity<?> getCategories() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productDataBase.getCategories());
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /*
    {
    "token":"",
    "categories" : ["","",""]
    }
     */
    @SuppressWarnings("unchecked")
    @PostMapping("/add_category")
    public ResponseEntity<String> addCategories(@RequestBody JSONObject categories) {
        try {
            UserType userType = Authentication.tokenUserType(categories.getAsString("token"));
            if (userType == UserType.Employee || userType == UserType.Manager) {
                ArrayList<String> categoryNames = (ArrayList<String>) categories.get("categories");
                for (String s : categoryNames)
                    productDataBase.addCategory(s.toLowerCase(Locale.ROOT));
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Access\n");
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/deleteCategory")
    public ResponseEntity<?> deleteStore(@RequestBody String categoryName) {
        try {
            productDataBase.deleteCategory(categoryName);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        }
    }

    /*
     * {"token","user token"
     * ,"product_id":[id1 ,id2 ,id3,.....]}
     * */
    @SuppressWarnings("unchecked")
    @PostMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestBody JSONObject product) {
        try {
            UserType userType = Authentication.tokenUserType(product.getAsString("token"));
            if (userType == UserType.Employee || userType == UserType.Manager) {
                ArrayList<String> products = (ArrayList<String>) product.get("product_id");
                for (String s : products)
                    productDataBase.deleteProduct(s);
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Access\n");
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        }
    }

    /*
    {
    "searchWord": word,
    "page":page
    }
     */
    @PostMapping("/search")
    public ResponseEntity<?> searchProduct(@RequestBody JSONObject search) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productDataBase.searchProduct(search.getAsString("searchWord"), (Integer) search.getAsNumber("page")));
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (ParseException e) {
            return Controller.ParserEx(e);
        }
    }
}
