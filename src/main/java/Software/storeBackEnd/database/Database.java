package Software.storeBackEnd.database;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private Connection con;
    private static Database instance = null;

    public static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    private Database() {
        connect();
    }

    private void connect() {
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11462667", "sql11462667", "UccaRWmjdb");
//            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/storeDB", "Store", "12345");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Failed To connect to database\n");
        }
    }


    public Statement getStatement() throws SQLException {
        if (!con.isValid(10000)) {
            System.out.println("reconnect Database");
            connect();
        }
        return con.createStatement();
    }


}
