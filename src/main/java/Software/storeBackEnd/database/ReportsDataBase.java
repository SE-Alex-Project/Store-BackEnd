package Software.storeBackEnd.database;

import Software.storeBackEnd.parser.ReportsParser;
import net.minidev.json.JSONArray;
import net.minidev.json.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportsDataBase {
    private final Database dataBase;

    public ReportsDataBase() {
        dataBase = Database.getInstance();
    }

    public JSONArray topCustomersLast3M() throws SQLException, ParseException {
        JSONArray top10 = new JSONArray();
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT userEmail,SUM(price * quantity) AS totalPrice\n" +
                "FROM Cart NATURAL JOIN ProductInCart NATURAL JOIN Product\n" +
                "WHERE buyDate >= DATE_ADD(NOW(),INTERVAL-90 DAY)\n" +
                "GROUP BY userEmail\n" +
                "ORDER BY totalPrice DESC LIMIT 10;");
        while (resultSet.next())
            top10.add(ReportsParser.parseTopCustomer(resultSet));
        return top10;
    }

    public JSONArray topSalesLast3M() throws SQLException, ParseException {
        JSONArray top10 = new JSONArray();
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT P.productName ,P.price ,SUM(quantity) AS totalSales\n" +
                "FROM Cart NATURAL JOIN ProductInCart NATURAL JOIN Product AS P\n" +
                "WHERE buyDate >= DATE_ADD(NOW(),INTERVAL-90 DAY)\n" +
                "GROUP BY P.productId\n" +
                "ORDER BY totalSales DESC LIMIT 10;");
        while (resultSet.next())
            top10.add(ReportsParser.parseTopSale(resultSet));
        return top10;
    }
}
