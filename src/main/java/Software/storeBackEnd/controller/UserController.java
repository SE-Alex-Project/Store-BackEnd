package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.Authentication;
import Software.storeBackEnd.authentication.TokenManager;
import Software.storeBackEnd.database.UserDatabase;
import Software.storeBackEnd.entities.UserType;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.LinkedHashMap;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

	private final UserDatabase userDataBase = new UserDatabase();
	TokenManager tokenManager = TokenManager.getInstance();

	/*
	 * log in json format { "email":user email, "password": user hashed password }
	 */

	// @PostMapping("/logIn")
//    public String logIn(@RequestBody JSONObject logInJson) {
//        try {
//            String password = (String) logInJson.get("password");
//            password = password.hashCode() + "";
//            UserType userType = Authentication.getUserType(logInJson.getAsString("email"));
//            boolean exist = switch (userType) {
//                case Customer -> Authentication.isCustomer(logInJson.getAsString("email"), password);
//                case Employee -> Authentication.isEmployee(logInJson.getAsString("email"), password);
//                case Manager -> Authentication.isManager(logInJson.getAsString("email"), password);
//            };
//            if (!exist)
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Email Haven't an Account!!!\nSign Up Instead\n");
//            return tokenManager.generateToken(logInJson.getAsString("email"));
//        } catch (SQLException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error While Fetch Data From DataBase\n");
//        }
//    }
	@PostMapping("/logIn")
	public ResponseEntity<String> logIn(@RequestBody JSONObject logInJson) {
		try {
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
			;
			if (!exist)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("This Email Haven't an Account!!!\nSign Up Instead\n");
			return ResponseEntity.status(HttpStatus.OK)
					.body(tokenManager.generateToken(logInJson.getAsString("email")));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error While Fetch Data From DataBase\n");
		}
	}

	/*
	 * sign up json format { "email":user email "firstName": user first name
	 * "lastName": user last name "password": user hashed password }
	 */
	@PostMapping("/signUp")
	public ResponseEntity<String> signUp(@RequestBody JSONObject signUpJson) {
		try {
			String password = (String) signUpJson.get("password");
			password = password.hashCode() + "";
			boolean exist = Authentication.isCustomerEmail(signUpJson.getAsString("email"));
			if (exist)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"This Email Have an Account!!!\nLog In Instead");
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
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error While Fetch Data From DataBase\n");
		}
	}

	@PostMapping("/logOut")
	public ResponseEntity<String> logOut(@RequestBody String userToken) {
		tokenManager.removeUser(userToken);
		return ResponseEntity.status(HttpStatus.OK).body(null);
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
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error While Fetch Data From DataBase\n");
		}

	}

	/*
	 * return json object same as signup object
	 */
	@GetMapping("/info")
	public ResponseEntity<?> userInfo(@RequestBody String userToken) {
		try {
			String userEmail = tokenManager.getUser(userToken);
			if (userEmail == null)
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User Not Signed In\nSign In first\n");
			return ResponseEntity.status(HttpStatus.OK).body(userDataBase.getUserInfo(userEmail));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error While Fetch Data From DataBase\n");
		} catch (ParseException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error IN Parsing JsonObject\n");
		}
	}

}
