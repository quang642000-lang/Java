package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static final String HOST = "localhost";
    private static final String PORT = "1433";
    private static final String DBNAME = "Lab6";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123";
    
    private static final String URL = "jdbc:sqlserver://" + HOST + ":" + PORT + 
                                      ";databaseName=" + DBNAME + 
                                      ";encrypt=true;trustServerCertificate=true;";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}