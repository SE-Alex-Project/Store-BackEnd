package Software.storeBackEnd.authentication;

public class Authentication {

    TokenManager userTokenManager;
    protected Authentication(){
        userTokenManager = new TokenManager();
    }
    

    public String getUser(String UserToken){
        return userTokenManager.getUser(UserToken);
    }
    
    public void removeUser(String UserToken) {
    	if (ActiveUsers.containsKey(UserToken))
    		ActiveUsers.remove(UserToken);
    }
    
    public String generateToken(String email) {
    	return userTokenManager.generateToken(email);
    }


}
