package Software.storeBackEnd.authentication;


import java.util.HashMap;
import java.util.UUID;

public class TokenManager {
    //generated Token ---> user Email and token validation
    private final HashMap<String, UserToken> Active;
//    private final HashMap<UserType,HashMap<String,UserToken>> Tokens;

    private static TokenManager instance = null;

    public static TokenManager getInstance() {
        if (instance == null)
            instance = new TokenManager();
        return instance;
    }

    private TokenManager() {
//        Tokens = new HashMap<>();
//        Tokens.put(UserType.Customer,new HashMap<>());
//        Tokens.put(UserType.Employee,new HashMap<>());
//        Tokens.put(UserType.Manager,new HashMap<>());
        Active = new HashMap<>();
    }


    public String getUser(String token) {
//        HashMap<String,UserToken> Active = Tokens.get(userType);
        if (Active.containsKey(token)) {
            UserToken user = Active.get(token);
            if (user.isValid()) {
                user.setLastUsed();
                return user.userEmail;
            } else
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
//        Tokens.get(UserType.Customer).remove(UserToken);
//        Tokens.get(UserType.Employee).remove(UserToken);
//        Tokens.get(UserType.Manager).remove(UserToken);
        Active.remove(UserToken);
    }


    public static class UserToken {
        String userEmail;
        long lastUsed;

        public UserToken(String email) {
            userEmail = email;
            setLastUsed();
        }

        public void setLastUsed() {
            lastUsed = System.currentTimeMillis();
        }

        public boolean isValid() {
            return System.currentTimeMillis() - lastUsed < (24 * 60 * 60 * 1000);
        }
    }
}
