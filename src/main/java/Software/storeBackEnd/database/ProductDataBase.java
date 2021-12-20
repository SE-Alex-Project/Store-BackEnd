package Software.storeBackEnd.database;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

public class ProductDataBase {

    private final DataBase dataBase;
    public ProductDataBase() {
        dataBase = DataBase.getInstance();
    }
    
    public void addProduct(String name ,String price,String category,String quantity){
    	final String queryCheck = "INSERT INTO PRODUCT(category-name,quantity,price,name) VALUES ('"+category
    			+"','"+quantity+"','"+price+"','"+name+"');";
		try {
			dataBase.stmt.executeQuery(queryCheck);
		} catch (SQLException e) {
			e.printStackTrace();
		}

    }

    public JSONObject getProduct(String product_id){
        try {
            ResultSet resultSet = dataBase.stmt.executeQuery("SELECT name , price , category FROM PRODUCT WHERE product_id = '" + product_id +"'");
            return (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"name\":" +resultSet.getString("name") + ",\"price\":"
                    +resultSet.getString("price")+",\"category-name\":"+ resultSet.getString("category")+"}");
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //select * from table order by id asc limit 50 offset 0; -- Returns rows 1-50

    public JSONArray getlist(int page) {
    	final String queryCheck = "SELECT * FROM PRODUCT limit 50 offset "+((page-1)*50)+";";
    	JSONArray array = new JSONArray();
    	ResultSet resultSet;
		try {
			resultSet = dataBase.stmt.executeQuery(queryCheck);
	            while(resultSet.next())
	            {
	                array.add(  (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse("{\"name\":" +resultSet.getString("name") + ",\"price\":"
                    +resultSet.getString("price")+",\"category-name\":"+ resultSet.getString("category")+"}")   );

	            }
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }

    public void deleteProduct(String product_id){
        try {
            dataBase.stmt.executeQuery("DELETE FROM PRODUCT WHERE product_id = '" + product_id +"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
