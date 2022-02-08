package Software.storeBackEnd.database;


import java.sql.ResultSet;
import java.sql.SQLException;

import Software.storeBackEnd.parser.EmployeeParser;
import Software.storeBackEnd.parser.ReportsParser;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;

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
    
    
    /*[
     * {
     * "productName":name,
     * "productId":id,
     * "quantity":quantity,
     * "price":price
     * }
     *]
     */
    
    public JSONArray totalSales(int page) throws SQLException, ParseException {
    	JSONArray array = new JSONArray();
        JSONObject data;
    	ResultSet resultSet = dataBase.getStatement().executeQuery("Select P.productName, P.productId , sum(quantity) as number , P.price\n" + 
    			"From ProductInCart NATURAL JOIN Product AS P NATURAL JOIN Cart\n" + 
    			"WHERE buyDate >= DATE_ADD(NOW(),INTERVAL-30 DAY)\n" + 
    			"Group by P.productId \r\n" + 
    			"LIMIT 50 OFFSET "+(page-1)*50 +";");
    	
        while (resultSet.next()) {
        	data = ReportsParser.parseSales(resultSet);
            array.add(data);
        }
        resultSet.close();
        return array;

    }
}
