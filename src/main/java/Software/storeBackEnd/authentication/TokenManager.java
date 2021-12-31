package Software.storeBackEnd.authentication;

import java.util.HashMap;
import java.util.UUID;

public class TokenManager {
    //user Token ---> user Email and token validation
    HashMap<String,UserToken> Active;
    public TokenManager(){
        Active = new HashMap<>();
    }
    public String getUser(String token){
        if (Active.containsKey(token)){
            UserToken user = Active.get(token);
            if (user.isValid()){
                user.setLastUsed();
                return user.userEmail;
            }
            else
                Active.remove(token);
        }
        return null;
    }

    public String generateToken(String email) {
        String token = UUID.randomUUID().toString().toUpperCase()
                + " | "
                + System.currentTimeMillis();
        Active.put(token, new UserToken(email));
        return token;
    }

    public void removeUser(String UserToken) {
        if (Active.containsKey(UserToken))
            Active.remove(UserToken);
    }


    public class UserToken{
        String userEmail;
        long lastUsed;
        public UserToken(String email){
            userEmail = email;
            setLastUsed();
        }
        public void setLastUsed(){
            lastUsed = System.currentTimeMillis();
        }
        public boolean isValid(){
            if (System.currentTimeMillis() - lastUsed < (24*60*60*1000))
                return true;
            return false;
        }
    }
}
