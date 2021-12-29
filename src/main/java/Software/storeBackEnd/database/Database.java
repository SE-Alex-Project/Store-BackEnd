package Software.storeBackEnd.database;

import java.sql.*;

public class Database {

    private Connection con;
    private static Database instance = null;

    private Database() {
        try {
//            Connection con= DriverManager.getConnection(
//                    "jdbc:mysql://127.0.0.1:3306/storeDB","Store","12345");
            Class.forName("com.mysql.jdbc.Driver");
          //  con = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11460629", "sql11460629", "5vZvcuYbs9");
            con= DriverManager.getConnection(
                 "jdbc:mysql://127.0.0.1:3306/storeDB","Store","12345");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    public Statement getStatement() throws SQLException {
        return con.createStatement();
    }


}