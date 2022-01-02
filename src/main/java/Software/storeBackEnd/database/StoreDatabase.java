package Software.storeBackEnd.database;

import Software.storeBackEnd.parser.StoreParser;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreDatabase {

    private final Database dataBase;

    public StoreDatabase() {
        dataBase = Database.getInstance();
    }

    public void add(String name, String location) {
        try {
            dataBase.getStatement().execute("INSERT INTO Store(storeName,location) VALUES ('" + name
                    + "','" + location + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public JSONObject get(String Store_id) throws SQLException, ParseException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT * FROM Store WHERE storeId ='"+Store_id+"';");
        resultSet.next();
        return StoreParser.parseStore(resultSet);
    }

    public JSONArray getList() throws SQLException, ParseException {
        ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT * FROM Store;");
        JSONArray array = new JSONArray();
        while (resultSet.next()){
            array.add(StoreParser.parseStore(resultSet));
        }
        return array;
    }
}
