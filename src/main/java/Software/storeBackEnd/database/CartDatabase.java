package Software.storeBackEnd.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Software.storeBackEnd.entities.Cart;
import Software.storeBackEnd.entities.ProductQuantity;

public class CartDatabase {

	private final DataBase dataBase;

    public CartDatabase() {
        dataBase = DataBase.getInstance();
    }
    
    
    public String getCart(String email) throws SQLException {
    	ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT cartId FROM Customer WHERE email = '" + email + "'");
        resultSet.next();
        String cartId = resultSet.getString("cartId");
    	return cartId;
    }
    
    public Cart getProductInCart(String cart_id) throws SQLException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT * FROM CartProducts WHERE cartId = '" + cart_id + "'");
       Cart c = new Cart();
       c.setId(Integer.parseInt(cart_id));
        while (resultSet.next()) {
        	String productId = resultSet.getString("productId");
        	String quantity = resultSet.getString("quantity");
            c.addProduct(Integer.parseInt(productId), Integer.parseInt(quantity));
        }
        resultSet.close();
        return c;
    }
    
    public String buyCart(Cart cart) throws Exception {
        ArrayList<ProductQuantity> a = cart.getProducts();
        for(ProductQuantity p : a) {
        	int id = p.getProduct_id();
        	int q = p.getQuantity();
        	ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT quantity FROM ProductInStore WHERE productId = '" + id + "' AND storeId = '1' ;");
        	resultSet.next();
        	int quantity = Integer.parseInt(resultSet.getString("quantity"));
        	if(q >= quantity) {
        		q = quantity-q;
        		p.setQuantity(q);
        	}else {
        		return "Can't do this Operation because Database Updated";
        	}
        }
        for(ProductQuantity p : a) {
        	int id = p.getProduct_id();
        	int q = p.getQuantity();
        	dataBase.getStatement().execute("UPDATE ProductInStore SET quantity = '"+q+"' WHERE productId = '" + id + "' AND storeId = '1' ;");
        }
        return "Operation Done";
    }

    
}
