package Software.storeBackEnd.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreDataBase {

    private final DataBase dataBase;
    public StoreDataBase() {
        dataBase = DataBase.getInstance();
    }

    public void add(String name,String location){
        try {
            dataBase.stmt.execute("INSERT INTO Store(storeName,location) VALUES ('"+name
                    +"','"+location+"');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isExist(String StoreID) {
        try {
            ResultSet resultSet = dataBase.stmt.executeQuery("SELECT * from Store WHERE storeId = '"+StoreID+"'");
            if(resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}