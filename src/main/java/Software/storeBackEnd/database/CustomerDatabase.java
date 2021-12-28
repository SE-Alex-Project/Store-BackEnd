package Software.storeBackEnd.database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Software.storeBackEnd.entities.Cart;
import Software.storeBackEnd.entities.ProductQuantity;

public class CustomerDatabase {

	private final DataBase dataBase;

    public CustomerDatabase() {
        dataBase = DataBase.getInstance();
    }
    
    
    public String getCart(String email) throws SQLException {
    	ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT cartId FROM Customer WHERE email = '" + email + "'");
        resultSet.next();
        String cartId = resultSet.getString("cartId");
    	return cartId;
    }
    
    public Cart getProductInCart(String cart_id,String email) throws SQLException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT * FROM CartProducts WHERE cartId = '" + cart_id + "'");
       Cart c = new Cart();
       c.setId(Integer.parseInt(cart_id));
       c.setEmail(email);
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
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        updateBuyTime(cart.getId(), formatter.format(date));
        int newCart = createCartByEmail(cart.getEmail());
        updateCustomerCart(newCart, cart.getEmail());
        return "Operation Done";
    }
    
    private void updateBuyTime(int id,String date) throws SQLException {
    	dataBase.getStatement().execute("update cart set buyDate='"+date+"' where cartId='"+id+"';");
    }
    
    private int createCartByEmail(String email) throws SQLException {
    	dataBase.getStatement().execute("INSERT INTO cart(userEmail) values ('"+email+"');");
    	ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT cartId FROM cart ORDER BY cartID DESC LIMIT 1;");
        resultSet.next();
        int id = Integer.parseInt(resultSet.getString("cartID"));
        return id;
    }
    
    private void updateCustomerCart(int id,String email) throws SQLException {
    	dataBase.getStatement().execute("update Customer set cartId = '"+id+"' where email ='"+email+"';");
    }
    
    public void addToCart(int product_id,int cart_id) throws SQLException {
    	dataBase.getStatement().execute("INSERT INTO CartProducts(cartId,productId,quantity) values ('"+cart_id+"','"+product_id+"','1') ;");
    }

    
}
