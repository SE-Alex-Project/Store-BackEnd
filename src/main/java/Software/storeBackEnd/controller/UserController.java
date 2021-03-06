package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.authentication.TokenManager;
import Software.storeBackEnd.authentication.Validation;
import Software.storeBackEnd.database.UserDatabase;
import Software.storeBackEnd.entities.UserType;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.LinkedHashMap;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserDatabase userDataBase = new UserDatabase();
    TokenManager tokenManager = TokenManager.getInstance();

    /*
     * log in json format { "email":user email, "password": user password }
     */
    @PostMapping("/logIn")
    public ResponseEntity<String> logIn(@RequestBody JSONObject logInJson) {
        try {
            Validation.validate_login(logInJson);
            String password = (String) logInJson.get("password");
            password = password.hashCode() + "";
            UserType userType = Authentication.getUserType(logInJson.getAsString("email"));
            boolean exist = false;
            switch (userType) {
                case Customer: {
                    exist = Authentication.isCustomer(logInJson.getAsString("email"), password);
                    break;
                }
                case Employee: {
                    exist = Authentication.isEmployee(logInJson.getAsString("email"), password);
                    break;
                }
                case Manager: {
                    exist = Authentication.isManager(logInJson.getAsString("email"), password);
                    break;
                }
            }
            if (!exist)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Wrong email or Password\n");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(tokenManager.generateToken(logInJson.getAsString("email")));
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /*
     * sign up json format { "email":user email "firstName": user first name
     * "lastName": user last name "password": user hashed password }
     */
    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody JSONObject signUpJson) {
        try {
            Validation.validate_signup(signUpJson);
            String password = (String) signUpJson.get("password");
            password = password.hashCode() + "";
            if(Authentication.getUserType(signUpJson.getAsString("email")) != UserType.Customer) {
            	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Not an customer account.\n");
            }
            boolean exist = Authentication.isCustomerEmail(signUpJson.getAsString("email"));
            if (exist)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("This Email Have an Account!!!\nLog In Instead");
            // create cart
            int id = userDataBase.createCart();
            // create user
            userDataBase.insertUser(signUpJson.getAsString("email"), signUpJson.getAsString("firstName"),
                    signUpJson.getAsString("lastName"), password, id);
            // update cart
            userDataBase.updateCart(signUpJson.getAsString("email"), id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(tokenManager.generateToken(signUpJson.getAsString("email")));
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/logOut")
    public void logOut(@RequestBody String userToken) {
        tokenManager.removeUser(userToken);
    }

    /*
     * modify info json format { "id": user token "data": { "password": new password
     * "firstName": user name "lastName": user last name } }
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/modifyInfo")
    public ResponseEntity<String> modifyInfo(@RequestBody JSONObject modifyJson) {
        try {

            String userEmail = tokenManager.getUser(modifyJson.getAsString("id"));
            if (userEmail == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Not Signed In\nSign In first\n");
            userDataBase.modifyUserinfo(userEmail, (LinkedHashMap) modifyJson.get("data"));
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * return json object same as signup object
     */
    @PostMapping("/info")
    public ResponseEntity<?> userInfo(@RequestBody String userToken) {
        try {
            String userEmail = tokenManager.getUser(userToken);
            if (userEmail == null)
            	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Not Signed In\nSign In first\n");
            return ResponseEntity.status(HttpStatus.OK).body(userDataBase.getUserInfo(userEmail));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Controller.SqlEx(e);
        } catch (ParseException e) {
            return Controller.ParserEx(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    /*
     * return logged out As String
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deleteMyAccount(@RequestBody String userToken) {
        try {
            String userEmail = tokenManager.getUser(userToken);
            if (userEmail == null)
            	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Not Signed In\nSign In first\n");
            UserType user = Authentication.tokenUserType(userToken);
            if (user == UserType.Manager) {
                int n = userDataBase.numberOfManagers();
                if (n == 1) {
                	return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can't delete last Manager\n");
                }
            }
            String temp = userDataBase.deleteAccount(userEmail,user);
            logOut(userToken);
            return ResponseEntity.status(HttpStatus.OK).body(temp);
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } 
    }
    
    /*
     * {
     * "token":userToken
     * "email":deleted email
     */
    @PostMapping("/removeAccount")
    public ResponseEntity<?> removeAccount(@RequestBody JSONObject removeJson) {
    	try {
	    	String userToken = removeJson.getAsString("token");
	    	String email = tokenManager.getUser(userToken);
	    	String deleted = removeJson.getAsString("email");
	    	if(email.equalsIgnoreCase(deleted)) {
	    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can't remove yourself.\n");
	    	}
	    	UserType user = Authentication.tokenUserType(userToken);
	    	UserType d = Authentication.getUserType(deleted);
	    	if(user == UserType.Customer) {
	    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("this account can't delete any account.\n");
	    	}else if(user == UserType.Employee) {
	    		if(d != UserType.Customer) {
	    			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can't delete this account\n");
	    		}   		
	    	}
	    	userDataBase.deleteAccount(deleted, d);
	    	return ResponseEntity.status(HttpStatus.OK).body(null);
    	} catch (SQLException e) {
            return Controller.SqlEx(e);
        } 
    }

    @PostMapping("/userCart_token")
    public ResponseEntity<?> userCart_token(@RequestBody String userToken) {
            return userCart_Email(tokenManager.getUser(userToken));
    }

    @PostMapping("/userCart_Email")
    public ResponseEntity<?> userCart_Email(@RequestBody String userEmail) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userDataBase.UserCart(userEmail));
        } catch (SQLException e) {
            return Controller.SqlEx(e);
        } catch (ParseException e) {
            return Controller.ParserEx(e);
        }
    }
    
}
