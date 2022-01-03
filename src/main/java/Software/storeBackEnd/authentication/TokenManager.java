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
        Active = new HashMap<>();
        Thread t = new Thread(() -> {
            synchronized (this) {
                while (true) {
                    try {
                        this.wait(24 * 60 * 60 * 1000);
                        clear();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    private void clear() {
        Active.forEach((token, userToken) -> {
            if (!userToken.isValid())
                Active.remove(token);
        });
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
