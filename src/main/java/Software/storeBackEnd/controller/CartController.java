package Software.storeBackEnd.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.database.CartDatabase;
import Software.storeBackEnd.entities.Cart;

@CrossOrigin
@RestController
@RequestMapping("/cart")
class CartController extends Authentication{
	
	private final CartDatabase cartDataBase = new CartDatabase();

	@PostMapping("/buy")
    public String buyCart(String token) throws Exception {
      String email = getUser(token);
      String cartId = cartDataBase.getCart(email);
      Cart cart = cartDataBase.getProductInCart(cartId);
      return cartDataBase.buyCart(cart);
    }
}
