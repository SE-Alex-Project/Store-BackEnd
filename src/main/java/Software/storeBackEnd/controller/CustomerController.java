package Software.storeBackEnd.controller;

import net.minidev.json.JSONArray;
import org.springframework.web.bind.annotation.*;

import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.database.CustomerDatabase;
import Software.storeBackEnd.entities.Cart;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController extends Authentication{


	private final CustomerDatabase customerDataBase = new CustomerDatabase();

	@PostMapping("/buy")
    public String buyCart(@RequestBody String token) throws Exception {
      String email = getUser(token);
      String cartId = customerDataBase.getCart(email);
      Cart cart = customerDataBase.getProductInCart(cartId,email);
      return customerDataBase.buyCart(cart);
    }
	
	
    @PostMapping("/addToCart")
    public void addToCart(@RequestBody JSONArray products_id){
    }

  

}
