package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.TokenManager;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.springframework.web.bind.annotation.*;

import Software.storeBackEnd.database.CustomerDatabase;
import Software.storeBackEnd.entities.Cart;
import Software.storeBackEnd.entities.ProductQuantity;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController{


	private final CustomerDatabase customerDataBase = new CustomerDatabase();
    TokenManager tokenManager = TokenManager.getInstance();

	/*
	 * token
	 */
	@PostMapping("/buy")
    public String buyCart(@RequestBody String token) throws Exception {
      String email = tokenManager.getUser(token);
      String cartId = customerDataBase.getCart(email);
      Cart cart = customerDataBase.getProductInCart(cartId,email);
      return customerDataBase.buyCart(cart);
    }
	
	/*add To Cart json format
    {
    "token":token,
    "product": product_id
    }
     */
    @PostMapping("/addToCart")
    public void addToCart(@RequestBody JSONObject addToCartJson) throws Exception{
    	String email = tokenManager.getUser(addToCartJson.getAsString("token"));
        String cartId = customerDataBase.getCart(email);
        String product_id = addToCartJson.getAsString("product_id");
        customerDataBase.addToCart(Integer.parseInt(product_id), Integer.parseInt(cartId));
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
	@PostMapping("/modify")
    public String modify(@RequestBody JSONObject modifyJson) throws Exception{
    	String email = tokenManager.getUser(modifyJson.getAsString("token"));
        String cartId = customerDataBase.getCart(email);
        ArrayList<LinkedHashMap> products = (ArrayList<LinkedHashMap>) modifyJson.get("products");
        ArrayList<ProductQuantity> cart = new ArrayList<ProductQuantity>(); 
        for(int i = 0 ; i < products.size() ; i++ ) {
        	LinkedHashMap ob = (LinkedHashMap) products.get(i);
        	
        	ProductQuantity p = new ProductQuantity( Integer.parseInt( (String)ob.getOrDefault("product", 0) ),
        			Integer.parseInt( (String) ob.getOrDefault("quantity", 0) ) );
        	cart.add(p);
        }
        return customerDataBase.modify(Integer.parseInt(cartId), cart);
    }

  

}
