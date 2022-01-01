package Software.storeBackEnd.database;

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
}
