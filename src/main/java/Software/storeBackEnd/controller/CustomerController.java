package Software.storeBackEnd.controller;

import net.minidev.json.JSONObject;

import org.springframework.web.bind.annotation.*;

import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.database.CustomerDatabase;
import Software.storeBackEnd.entities.Cart;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController extends Authentication{


	private final CustomerDatabase customerDataBase = new CustomerDatabase();

	/*
	 * token
	 */
	@PostMapping("/buy")
    public String buyCart(@RequestBody String token) throws Exception {
      String email = getUser(token);
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
    	String email = getUser(addToCartJson.getAsString("token"));
        String cartId = customerDataBase.getCart(email);
        String product_id = addToCartJson.getAsString("product_id");
        customerDataBase.addToCart(Integer.parseInt(product_id), Integer.parseInt(cartId));
    }

  

}
