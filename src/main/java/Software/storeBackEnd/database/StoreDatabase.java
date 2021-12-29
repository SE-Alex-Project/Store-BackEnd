package Software.storeBackEnd.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreDatabase {

    private final Database dataBase;
    public StoreDatabase() {
        dataBase = Database.getInstance();
    }

    public void add(String name,String location){
        try {
            dataBase.getStatement().execute("INSERT INTO Store(storeName,location) VALUES ('"+name
                    +"','"+location+"');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isExist(String StoreID) {
        try {
            ResultSet resultSet = dataBase.getStatement().executeQuery("SELECT * from Store WHERE storeId = '"+StoreID+"'");
            if(resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
