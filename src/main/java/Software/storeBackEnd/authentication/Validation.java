package Software.storeBackEnd.authentication;

import Software.storeBackEnd.entities.UserType;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Validation {
    public static void validate_token(String token) {
        if (token.length() != 52 || token == null)
            throw new RuntimeException("Invalid token");
    }

    public static void validate_name(String name) {
        if (name.length() < 1 || name == null)
            throw new RuntimeException("Invalid name format");
    }

    public static void validate_password(String pass) {
        if (pass == null || pass.length() < 8 || pass.length() > 20 || pass.contains("','"))
            throw new RuntimeException("Invalid password format");
    }

    public static void validate_email(String email) {
        if (email == null || email.length() < 8 || email.length() > 40 || email.contains(",") || email.contains(" ") || !email.contains(".") || !email.contains("@"))
            throw new RuntimeException("Invalid email format");
    }

    public static void validate_store(String store) {
        validate_value(store);
        if (store == null)
            throw new RuntimeException("Invalid store");

    }


    public static void validate_value(String number) {
        if (number == null)
            throw new RuntimeException("invalid value");
        for (int i = 0; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(i))) {
                throw new RuntimeException("invalid value");
            }
        }
    }

    public static void validate_price(String price) {
        if (price == null)
            throw new RuntimeException("invalid price");
        try {
            double d = Double.parseDouble(price);
        } catch (NumberFormatException nfe) {
            throw new RuntimeException("invalid price");
        }
    }


   /* {
        "id":"product_id",
            "name":"product_name",
            "quantity":"quantity",
            "price":"price"
    }  */

    public static void validate_product0(JSONObject product) {
        validate_value(product.getAsString("id"));
        validate_name(product.getAsString("name"));
        validate_value(product.getAsString("quantity"));
        validate_price(product.getAsString("price"));
    }

    /*add To Cart json format
{
"token":token,
"product": product_id
}
 */
    public static void validate_addToCart(JSONObject obj) {
        validate_token(obj.getAsString("token"));
        validate_value(obj.getAsString("product"));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void validate_modifyCart(JSONObject obj) {
        validate_token(obj.getAsString("token"));
        ArrayList<LinkedHashMap> products = (ArrayList<LinkedHashMap>) obj.get("products");
        for (LinkedHashMap ob : products) {
            validate_value((String) ob.getOrDefault("product", 0));
            validate_value((String) ob.getOrDefault("quantity", 0));
        }
    }

    /*{
        "token" : "token",
        "email":"user email",
        "firstName": "user first name",
        "lastName": "user last name",
        "password": "user hashed password",
        "store":"1",
        "erole":role,
        "salary":salary
    }*/
    public static void validate_employee(JSONObject employee) {
        validate_email(employee.getAsString("email"));
        if (Authentication.getUserType(employee.getAsString("email")) != UserType.Employee)
            throw new RuntimeException("Invalid employee Email");
        validate_name(employee.getAsString("firstName"));
        validate_name(employee.getAsString("lastName"));
        validate_password(employee.getAsString("password"));
        validate_store(employee.getAsString("store"));
        validate_name(employee.getAsString("erole"));
        validate_price(employee.getAsString("salary"));
    }

    public static void validate_getEmployee(JSONObject employee) {
        validate_email(employee.getAsString("email"));
        if (Authentication.getUserType(employee.getAsString("email")) == UserType.Customer)
            throw new RuntimeException("InValid Email");
        validate_token(employee.getAsString("token"));
        if (Authentication.tokenUserType(employee.getAsString("token")) != UserType.Manager)
            throw new RuntimeException("Un Authorized Access");
    }

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
    public static void validate_product1(JSONObject product) {
        validate_token(product.getAsString("addedBy"));
        validate_name(product.getAsString("name"));
        validate_price(product.getAsString("price"));
        validate_name(product.getAsString("category"));
        validate_name(product.getAsString("description"));
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
    public static void validate_product2(JSONObject product) {
        validate_value(product.getAsString("id"));
        validate_product1(product);
    }

    /*
 {
 "page" : "1",
 "category" : "category name"
 }
  */
    public static void validate_page(JSONObject product) {
        validate_value(product.getAsString("page"));
        validate_name(product.getAsString("category"));
    }

    /*store json format
  {
  "name": "name",
  "location":"location"
  }
   */
    public static void validate_addstore(JSONObject store) {
        validate_name(store.getAsString("name"));
        validate_name(store.getAsString("location"));
        validate_token(store.getAsString("token"));
        if (Authentication.tokenUserType(store.getAsString("token")) == UserType.Customer)
            throw new RuntimeException("UnAuthorized Access");
    }

    /*
     * log in json format {
     *  "email":user email,
     * "password": user hashed password }
     */
    public static void validate_login(JSONObject obj) {
        validate_email(obj.getAsString("email"));
        validate_password(obj.getAsString("password"));
    }

    /*
     * sign up json format { "email":user email
     * "firstName": user first name
     * "lastName": user last name
     *  "password": user hashed password }
     */
    public static void validate_signup(JSONObject obj) {
        validate_email(obj.getAsString("email"));
        validate_name(obj.getAsString("firstName"));
        validate_name(obj.getAsString("lastName"));
        validate_password(obj.getAsString("password"));
    }

    /*
     * modify info json format {
     *  "id": user token
     * "data": { "password": new password
     * "firstName": user name "lastName": user last name } }
     */
    public static void validate_modify(JSONObject obj) {
        validate_token(obj.getAsString("id"));
        JSONObject data = (JSONObject) obj.get("data");
        validate_password(data.getAsString("password"));
        validate_name(data.getAsString("firstName"));
        validate_name(data.getAsString("lastName"));
    }

}