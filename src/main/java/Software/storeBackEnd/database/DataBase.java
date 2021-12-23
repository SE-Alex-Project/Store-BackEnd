package Software.storeBackEnd.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBase {

    Statement stmt;
    private static DataBase instance = null;
    private DataBase(){
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/storeDB","Store","12345");
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection con= DriverManager.getConnection(
//                    "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11460629","sql11460629","5vZvcuYbs9");
            stmt = con.createStatement();
//            ResultSet st = stmt.executeQuery("SELECT * FROM Product");
////            stmt.execute("INSERT INTO Product VALUES (12,'WATCh', 4.02 , 'ahmed watch')");
//            //System.out.println(st.getInt(0));
//            System.out.println(st.next());
//            System.out.println(st.getString(4));

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public static DataBase getInstance(){
        if (instance==null)
            instance = new DataBase();
        return instance;
    }

}
