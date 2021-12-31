package Software.storeBackEnd.authentication;

public class Authentication {

    TokenManager userTokenManager;
    protected Authentication(){
        userTokenManager = new TokenManager();
    }
    

    public String getUser(String UserToken){
        return userTokenManager.getUser(UserToken);
    }
    
    public String generateToken(String email) {
    	return userTokenManager.generateToken(email);
    }


}
