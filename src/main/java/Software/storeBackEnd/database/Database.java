package Software.storeBackEnd.database;

import java.sql.*;

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
            con = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11471189", "sql11471189", "8fKZmzeBHX");
//            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/storeDB", "SAMPLE", "12345");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
//            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Failed To connect to database\n");
        }
    }


    private Statement getStatement() throws SQLException {
        if (!con.isValid(10000)) {
            System.out.println("reconnect Database");
            connect();
        }
        return con.createStatement();
    }

    public ResultSet executeQuery(String Query) throws SQLException {
        return getStatement().executeQuery(Query);
    }

    public void execute(String Query) throws SQLException {
        getStatement().execute(Query);
    }
}
