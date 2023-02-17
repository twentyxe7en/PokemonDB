
package pokemondb;

import java.sql.Connection;
import java.sql.SQLException;

public class Connect {
    private final String SERVER = "localhost:3306";
    private final String DB = "pokemon";
    private final String USER = "root";
    private final String PASS = "";
    private static Connect instance = null;
    private Connection con;

    public Connect() {
    }
    
    public static Connect getInstance() {
        if (instance == null) {
            instance = new Connect();
        }
        return instance;
    }
    
    public void connectDB() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = java.sql.DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DB, USER, PASS);
    }
    
    public void reconnect() throws Exception {
        con = java.sql.DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DB, USER, PASS);
    }
    
    public Connection openConnection() {
        return con;
    }
    
    public void closeConnection() throws SQLException {
        con.close();
    }
}
