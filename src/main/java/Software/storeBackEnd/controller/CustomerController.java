package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.TokenManager;
import Software.storeBackEnd.authentication.Validation;
import Software.storeBackEnd.database.CustomerDatabase;
import Software.storeBackEnd.entities.Cart;
import Software.storeBackEnd.entities.ProductQuantity;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerDatabase customerDataBase = new CustomerDatabase();
    TokenManager tokenManager = TokenManager.getInstance();

    /*
     * token
     */
    @PostMapping("/buy")
    public ResponseEntity<String> buyCart(@RequestBody String token) {
        try {
            Validation.validate_token(token);
            String email = tokenManager.getUser(token);
            String cartId = customerDataBase.getCart(email);
            Cart cart = customerDataBase.getProductInCart(cartId, email);
            return customerDataBase.buyCart(cart);
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    /*
     [
        {
            "id":"product_id",
            "name":"product_name",
            "quantity":"quantity",
            "price":"price"
        }
        ,
        {
            "id":"product_id",
            "name":"product_name",
            "quantity":"quantity",
            "price":"price"
        }
      ]
     */
    @PostMapping("/getCart")
    public ResponseEntity<?> getCart(@RequestBody String token) { //validate product 0;
        try {
            Validation.validate_token(token);
            String email = tokenManager.getUser(token);
            String cartId = customerDataBase.getCart(email);
            Cart cart = customerDataBase.getProductInCart(cartId, email);
            JSONArray array = customerDataBase.getCartInfo(cart);
            return ResponseEntity.status(HttpStatus.OK).body(array);
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (ParseException e) {
            return Controller.ParserEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    /*add To Cart json format
    {
    "token":token,
    "product": product_id
    }
     */
    @PostMapping("/addToCart")//validate_addToCart
    public ResponseEntity<String> addToCart(@RequestBody JSONObject addToCartJson) {///check product quantity first in store 1
        try {
            Validation.validate_addToCart(addToCartJson);
            String email = tokenManager.getUser(addToCartJson.getAsString("token"));
            String cartId = customerDataBase.getCart(email);
            String product_id = addToCartJson.getAsString("product");
            return customerDataBase.addToCart(Integer.parseInt(product_id), Integer.parseInt(cartId));
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    /*
      {
      "token": token of user   ,
      "products":[
      	{
       	  "product": product_id,
       	  "quantity": quantity
      	}
      	,
      	{
       	  "product": product_id,
       	  "quantity": quantity
      	}
      	]
      }
     */

    @SuppressWarnings({"unchecked", "rawtypes"})
    @PostMapping("/modifyCart")
    public ResponseEntity<String> modify(@RequestBody JSONObject modifyJson) {///check product quantity first in store 1
        try {
            Validation.validate_modifyCart(modifyJson);
            String email = tokenManager.getUser(modifyJson.getAsString("token"));
            String cartId = customerDataBase.getCart(email);
            ArrayList<LinkedHashMap> products = (ArrayList<LinkedHashMap>) modifyJson.get("products");
            ArrayList<ProductQuantity> cart = new ArrayList<>();
            for (LinkedHashMap ob : products) {
                ProductQuantity p = new ProductQuantity(
                        Integer.parseInt((String) ob.getOrDefault("product", 0)),
                        Integer.parseInt((String) ob.getOrDefault("quantity", 0)));
                cart.add(p);
            }
            return customerDataBase.modify(Integer.parseInt(cartId), cart);
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
