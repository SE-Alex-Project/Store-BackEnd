package Software.storeBackEnd.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import Software.storeBackEnd.parser.EmployeeParser;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;

public class ReportsDataBase {
	
	private final Database dataBase;

    public ReportsDataBase() {
        dataBase = Database.getInstance();
    }

    public void topCustomersLast3M(){

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
        	data = EmployeeParser.parseEmployee(resultSet);
            array.add(data);
        }
        resultSet.close();
        return array;

    }
}
