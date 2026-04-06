package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    // Tách thông tin kết nối ra đây để dễ dàng bảo mật/quản lý sau này
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyNhaTroAssignment2;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "...";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}