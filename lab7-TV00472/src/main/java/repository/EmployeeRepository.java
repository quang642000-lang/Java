package repository;
import connect.DBConnect;
import entity.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    public List<Employee> getAll() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employee";

        try {
            Connection con = DBConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("salary")
                ));
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insert(Employee e) {
        String sql = "INSERT INTO employee(name, salary) VALUES (?, ?)";

        try {
            Connection con = DBConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, e.getName());
            ps.setDouble(2, e.getSalary());

            int kq = ps.executeUpdate();

            if (kq > 0) {
                System.out.println("Them thanh cong");
            } else {
                System.out.println("Them that bai");
            }

            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update(Employee e) {
        String sql = "UPDATE employee SET name = ?, salary = ? WHERE id = ?";

        try {
            Connection con = DBConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, e.getName());
            ps.setDouble(2, e.getSalary());
            ps.setInt(3, e.getId());

            int kq = ps.executeUpdate();

            if (kq > 0) {
                System.out.println("Update thanh cong");
            } else {
                System.out.println("Update that bai");
            }

            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM employee WHERE id = ?";

        try {
            Connection con = DBConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int kq = ps.executeUpdate();

            if (kq > 0) {
                System.out.println("Xoa thanh cong");
            } else {
                System.out.println("Xoa that bai");
            }

            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // SEARCH (Bài 4)
    public Employee findById(int id) {
        String sql = "SELECT * FROM employee WHERE id = ?";
        Employee emp = null;

        try {
            Connection con = DBConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                emp = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("salary")
                );
                System.out.println("Tim thay nhan vien");
            } else {
                System.out.println("Khong tim thay nhan vien");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emp;
    }
}
