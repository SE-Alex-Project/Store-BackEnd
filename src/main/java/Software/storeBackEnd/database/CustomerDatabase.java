package Software.storeBackEnd.database;

import Software.storeBackEnd.entities.Cart;
import Software.storeBackEnd.entities.ProductQuantity;
import Software.storeBackEnd.parser.ProductParser;
import net.minidev.json.JSONArray;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CustomerDatabase {

    private final Database dataBase;

    public CustomerDatabase() {
        dataBase = Database.getInstance();
    }


    public String getCart(String email) throws SQLException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT cartId FROM Customer WHERE email = '" + email + "'");
        resultSet.next();
        return resultSet.getString("cartId");
    }

    public Cart getProductInCart(String cart_id, String email) throws SQLException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT * FROM ProductInCart WHERE cartId = '" + cart_id + "'");
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

    public ResponseEntity<String> buyCart(Cart cart) throws SQLException {
        ArrayList<ProductQuantity> a = cart.getProducts();
        for (ProductQuantity p : a) {
            int id = p.getProduct_id();
            int q = p.getQuantity();
            ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT quantity FROM ProductInStore WHERE productId = '" + id + "' AND storeId = '1' ;");
            resultSet.next();
            int quantity = Integer.parseInt(resultSet.getString("quantity"));
            if (q <= quantity) {
                q = quantity - q;
                p.setQuantity(q);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't do this Operation because Database Updated");
            }
        }
        for (ProductQuantity p : a) {
            int id = p.getProduct_id();
            int q = p.getQuantity();
            dataBase.getStatement().execute("UPDATE ProductInStore SET quantity = '" + q + "' WHERE productId = '" + id + "' AND storeId = '1' ;");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        updateBuyTime(cart.getId(), formatter.format(date));
        int newCart = createCartByEmail(cart.getEmail());
        updateCustomerCart(newCart, cart.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    private void updateBuyTime(int id, String date) throws SQLException {
        dataBase.getStatement().execute("update cart set buyDate='" + date + "' where cartId='" + id + "';");
    }

    private int createCartByEmail(String email) throws SQLException {
        dataBase.getStatement().execute("INSERT INTO cart(userEmail) values ('" + email + "');");
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT cartId FROM cart ORDER BY cartID DESC LIMIT 1;");
        resultSet.next();
        return Integer.parseInt(resultSet.getString("cartID"));
    }

    private void updateCustomerCart(int id, String email) throws SQLException {
        dataBase.getStatement().execute("update Customer set cartId = '" + id + "' where email ='" + email + "';");
    }

    public void addToCart(int product_id, int cart_id) throws SQLException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT quantity FROM ProductInCart WHERE productId = '" + product_id + "' AND cartId = '" + cart_id + "' ;");
        if (resultSet.next()) {
            int quantity = Integer.parseInt(resultSet.getString("quantity"));
            quantity++;
            dataBase.getStatement().execute("UPDATE ProductInCart set quantity = '" + quantity + "' where cartId ='" + cart_id + "' AND productId = '" + product_id + "';");
        } else {
            dataBase.getStatement().execute("INSERT INTO ProductInCart(cartId,productId,quantity) values ('" + cart_id + "','" + product_id + "','1') ;");
        }
    }

    public void modify(int cart_id, ArrayList<ProductQuantity> cart) throws SQLException {
        dataBase.getStatement().execute("DELETE FROM ProductInCart WHERE cartId ='" + cart_id + "';");
        for (ProductQuantity p : cart) {
            dataBase.getStatement().execute("INSERT INTO ProductInCart(cartId,productId,quantity) values ('" + cart_id + "','" + p.getProduct_id() + "','" + p.getQuantity() + "') ;");
        }
    }

    public JSONArray getCartInfo(Cart cart) throws SQLException, ParseException {
        JSONArray array = new JSONArray();
        int cart_id = cart.getId();
        ArrayList<ProductQuantity> a = cart.getProducts();
        for (ProductQuantity p : a) {
            ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT c.productId,p.price,p.productName,c.quantity FROM ( ProductInCart AS c JOIN Product AS p ON c.productId=p.productId)"
                    + " WHERE cartId='" + cart_id + "' AND c.productId='" + p.getProduct_id() + "';");
            if (resultSet.next())
                array.add(ProductParser.parseProductInCart(resultSet));
        }
        return array;
    }
}
