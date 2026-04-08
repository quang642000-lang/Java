package connect;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
    public static String HOSTNAME = "localhost";
    public static String PORT = "1433";
    public static String DBNAME = "...";
    public static String USERNAME = "sa";
    public static String PASSWORD = "...";

    public static Connection getConnection() {
        String url = "jdbc:sqlserver://"
                + HOSTNAME + ":" + PORT + ";"
                + "databaseName=" + DBNAME + ";"
                + "encrypt=true;trustServerCertificate=true;";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println("Loi ket noi: " + e.getMessage());
        }
        return null;
    }
}