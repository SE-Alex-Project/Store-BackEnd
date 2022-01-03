package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.TokenManager;
import Software.storeBackEnd.database.CustomerDatabase;
import Software.storeBackEnd.entities.Cart;
import Software.storeBackEnd.entities.ProductQuantity;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController{


    /*@GetMapping("/rse")
    public String withResponseStatusException() {
        try {
            throw new RuntimeException("Error Occurred");
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "HTTP Status will be NOT FOUND (CODE 404)\n");
        }
    }*/

    private final CustomerDatabase customerDataBase = new CustomerDatabase();
    TokenManager tokenManager = TokenManager.getInstance();

	/*
	 * token
	 */
	@PostMapping("/buy")
    public String buyCart(@RequestBody String token) {
        try {
            String email = tokenManager.getUser(token);
            if (email == null)
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User Not Signed In\nSign In first\n");
            String cartId = customerDataBase.getCart(email);
            Cart cart = customerDataBase.getProductInCart(cartId,email);
            return customerDataBase.buyCart(cart);
        }catch (SQLException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error While Fetch Data From DataBase\n");
        }
    }
	
	/*add To Cart json format
    {
    "token":token,
    "product": product_id
    }
     */
    @PostMapping("/addToCart")
    public void addToCart(@RequestBody JSONObject addToCartJson){
        try {
            String email = tokenManager.getUser(addToCartJson.getAsString("token"));
            if (email == null)
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User Not Signed In\nSign In first\n");
            String cartId = customerDataBase.getCart(email);
            String product_id = addToCartJson.getAsString("product");
            customerDataBase.addToCart(Integer.parseInt(product_id), Integer.parseInt(cartId));
        }catch (SQLException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error While Fetch Data From DataBase\n");
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
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/modifyCart")
    public String modify(@RequestBody JSONObject modifyJson){
        try{
            String email = tokenManager.getUser(modifyJson.getAsString("token"));
            if (email == null)
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User Not Signed In\nSign In first\n");
            String cartId = customerDataBase.getCart(email);
            ArrayList<LinkedHashMap> products = (ArrayList<LinkedHashMap>) modifyJson.get("products");
            ArrayList<ProductQuantity> cart = new ArrayList<>();
            for (LinkedHashMap ob : products) {
                ProductQuantity p = new ProductQuantity(Integer.parseInt((String) ob.getOrDefault("product", 0)),
                        Integer.parseInt((String) ob.getOrDefault("quantity", 0)));
                cart.add(p);
            }
            return customerDataBase.modify(Integer.parseInt(cartId), cart);
        }catch (SQLException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error While Fetch Data From DataBase\n");
        }
    }
}
