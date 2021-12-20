package Software.storeBackEnd.authentication;


import java.util.HashMap;
import java.util.UUID;

public class Authentication {

    //user Token ---> user Email
    HashMap<String,String> ActiveUsers;
    protected Authentication(){
        ActiveUsers = new HashMap<>();
    }

    public String getUser(String UserToken){
        if (ActiveUsers.containsKey(UserToken))
            return ActiveUsers.get(UserToken);
        return null;
    }
    
    public String generateToken(String email) {
    	String token = UUID.randomUUID().toString().toUpperCase() 
                + " | " 
                + System.currentTimeMillis();
    	ActiveUsers.put(token, email);
    	return token;
    }


}
