package service;

import connect.connect;
import model.Tree;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class TreeService {

    public List<Tree> getAll() {
        List<Tree> list = new ArrayList<>();
        String sql = "SELECT * FROM tree";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = connect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Tree t = new Tree();
                t.setNodeId(rs.getInt("node_id"));
                t.setNodeName(rs.getString("node_name"));
                
                int parentId = rs.getInt("parent_id");
                if (rs.wasNull()) {
                    t.setParentId(null);
                } else {
                    t.setParentId(parentId);
                }
                
                t.setLevel(rs.getInt("level"));
                list.add(t);
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

    public boolean add(Tree t) {
        String sql = "INSERT INTO tree(node_id, node_name, parent_id, level) VALUES(?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, t.getNodeId());
            ps.setString(2, t.getNodeName());
            if (t.getParentId() == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, t.getParentId());
            }
            ps.setInt(4, t.getLevel());
            
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

    public boolean update(Tree t) {
        String sql = "UPDATE tree SET node_name=?, parent_id=?, level=? WHERE node_id=?";
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, t.getNodeName());
            if (t.getParentId() == null) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, t.getParentId());
            }
            ps.setInt(3, t.getLevel());
            ps.setInt(4, t.getNodeId());
            
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
        String sql = "DELETE FROM tree WHERE node_id=?";
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