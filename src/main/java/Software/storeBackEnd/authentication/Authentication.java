package Software.storeBackEnd.authentication;


import java.util.HashMap;

public class Authentication {

    //user Token ---> user Email
    HashMap<String,String> ActiveUsers;
    protected Authentication(){
        ActiveUsers = new HashMap<>();
    }
//    private static Authentication instance = null;
//    private Authentication(){
//        ActiveUsers = new HashMap<>();
//    }
//
//    public static Authentication getInstance(){
//        if (instance == null)
//            instance = new Authentication();
//        return instance;
//    }

    public String getUser(String UserToken){
        if (ActiveUsers.containsKey(UserToken))
            return ActiveUsers.get(UserToken);
        return null;
    }


}
