package service;

import connect.connect;
import model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    public List<Student> getAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = connect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Student s = new Student();
                s.setStudentId(rs.getInt("student_id"));
                s.setStudentName(rs.getString("student_name"));
                s.setGender(rs.getString("gender"));
                s.setGpa(rs.getDouble("gpa"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public boolean add(Student s) {
        String sql = "INSERT INTO student(student_id, student_name, gender, gpa) VALUES(?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, s.getStudentId());
            ps.setString(2, s.getStudentName());
            ps.setString(3, s.getGender());
            ps.setDouble(4, s.getGpa());
            
            int rowObtain = ps.executeUpdate();
            if (rowObtain > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean update(Student s) {
        String sql = "UPDATE student SET student_name=?, gender=?, gpa=? WHERE student_id=?";
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, s.getStudentName());
            ps.setString(2, s.getGender());
            ps.setDouble(3, s.getGpa());
            ps.setInt(4, s.getStudentId());
            
            int rowObtain = ps.executeUpdate();
            if (rowObtain > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM student WHERE student_id=?";
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            int rowObtain = ps.executeUpdate();
            if (rowObtain > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}