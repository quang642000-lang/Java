package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connect {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=lab6;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "P6t4q29!";

    // Phương thức kết nối
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            e.printStackTrace();
        }
        return conn;
    }
}