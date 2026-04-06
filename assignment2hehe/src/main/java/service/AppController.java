package service;

import connect.Connect;
import model.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AppController {
    private List<NguoiDung> listNguoiDung = new ArrayList<>();
    private List<NhaTro> listNhaTro = new ArrayList<>();
    private final String FILE_NAME = "data_backup.dat";

    public void loadDataFromDB() {
        listNguoiDung.clear();
        listNhaTro.clear();
        try (Connection conn = Connect.getConnection()) {
            Statement stmtND = conn.createStatement();
            ResultSet rsND = stmtND.executeQuery("SELECT * FROM NGUOIDUNG");
            while (rsND.next()) {
                listNguoiDung.add(new NguoiDung(rsND.getString("MaND"), rsND.getString("TenND"), 
                        rsND.getString("GioiTinh"), rsND.getString("DienThoai"), 
                        rsND.getString("DiaChi"), rsND.getString("Quan"), rsND.getString("Email")));
            }

            Statement stmtNT = conn.createStatement();
            ResultSet rsNT = stmtNT.executeQuery("SELECT * FROM NHATRO");
            while (rsNT.next()) {
                LoaiNhaTro loaiNha = LoaiNhaTro.fromMa(rsNT.getString("MaLoai"));
                TrangThaiNhaTro trangThai = TrangThaiNhaTro.TRONG; 
                try {
                    trangThai = TrangThaiNhaTro.fromDBString(rsNT.getString("TrangThai"));
                } catch (Exception ignore) {}

                java.sql.Date sqlDate = rsNT.getDate("NgayDang");
                LocalDate ld = (sqlDate != null) ? sqlDate.toLocalDate() : LocalDate.now();

                NhaTro nt = new NhaTro(rsNT.getString("MaNhaTro"), loaiNha, 
                        rsNT.getDouble("DienTich"), rsNT.getDouble("GiaPhong"), rsNT.getString("DiaChi"), 
                        rsNT.getString("Quan"), rsNT.getString("MoTa"), trangThai,
                        ld, rsNT.getString("NguoiLienHe"));
                listNhaTro.add(nt);
                
                for (NguoiDung nd : listNguoiDung) {
                    if (nd.getId().equals(nt.getNguoiLienHeId())) {
                        nd.addNhaTro(nt);
                        break;
                    }
                }
            }
            System.out.println("Đã đồng bộ dữ liệu thành công!");
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối DB: " + e.getMessage());
        }
    }

    public void themNguoiDung(NguoiDung nd) throws DuplicateIdException {
        if (listNguoiDung.stream().anyMatch(u -> u.getId().equalsIgnoreCase(nd.getId()))) {
            throw new DuplicateIdException("Mã người dùng đã tồn tại!");
        }
        String sql = "INSERT INTO NGUOIDUNG VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Connect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nd.getId()); 
            pstmt.setString(2, nd.getTen());
            pstmt.setString(3, nd.getGioiTinh()); 
            pstmt.setString(4, nd.getSdt());
            pstmt.setString(5, nd.getDiaChi()); 
            pstmt.setString(6, nd.getQuan());
            pstmt.setString(7, nd.getEmail());
            pstmt.executeUpdate();
            listNguoiDung.add(nd);
            System.out.println("Thêm người dùng thành công!");
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    public void xoaNguoiDung(String maND) {
        String sqlNhaTro = "DELETE FROM NHATRO WHERE NguoiLienHe = ?";
        String sqlNguoiDung = "DELETE FROM NGUOIDUNG WHERE MaND = ?";
        try (Connection conn = Connect.getConnection()) {
            conn.setAutoCommit(false); 
            try (PreparedStatement pst1 = conn.prepareStatement(sqlNhaTro);
                 PreparedStatement pst2 = conn.prepareStatement(sqlNguoiDung)) {
                pst1.setString(1, maND); pst1.executeUpdate();
                pst2.setString(1, maND); int row = pst2.executeUpdate();
                if (row > 0) {
                    conn.commit();
                    listNhaTro.removeIf(nt -> nt.getNguoiLienHeId().equalsIgnoreCase(maND));
                    listNguoiDung.removeIf(nd -> nd.getId().equalsIgnoreCase(maND));
                    System.out.println("Đã xóa thành công!");
                } else {
                    System.out.println("Không tìm thấy mã!");
                }
            } catch (SQLException ex) {
                conn.rollback();
                System.out.println("Lỗi, đã Rollback: " + ex.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    public void hienThiDanhSachNguoiDung() {
        if(listNguoiDung.isEmpty()) System.out.println("Danh sách trống!");
        for (NguoiDung nd : listNguoiDung) {
            System.out.println(nd);
            if (!nd.getDanhSachNhaTroDaDang().isEmpty()) {
                System.out.println("   -> Các nhà trọ đã đăng:");
                nd.getDanhSachNhaTroDaDang().forEach(nt -> System.out.println("      " + nt));
            }
        }
    }

    public void themNhaTro(NhaTro nt) throws DuplicateIdException {
        if (listNhaTro.stream().anyMatch(t -> t.getId().equalsIgnoreCase(nt.getId()))) {
            throw new DuplicateIdException("Mã nhà trọ đã tồn tại!");
        }
        NguoiDung user = listNguoiDung.stream()
                .filter(u -> u.getId().equalsIgnoreCase(nt.getNguoiLienHeId()))
                .findFirst()
                .orElse(null);
        if (user == null) {
            System.out.println("Lỗi: Mã người đăng không tồn tại!"); return;
        }

        String sql = "INSERT INTO NHATRO (MaNhaTro, MaLoai, DienTich, GiaPhong, DiaChi, Quan, MoTa, NgayDang, NguoiLienHe, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Connect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nt.getId()); 
            pstmt.setString(2, nt.getLoaiNha().getMaLoai());
            pstmt.setDouble(3, nt.getDienTich()); 
            pstmt.setDouble(4, nt.getGiaPhong());
            pstmt.setString(5, nt.getDiaChi()); 
            pstmt.setString(6, nt.getQuan());
            pstmt.setString(7, nt.getMoTa()); 
            pstmt.setDate(8, java.sql.Date.valueOf(nt.getNgayDang()));
            pstmt.setString(9, nt.getNguoiLienHeId());
            pstmt.setString(10, nt.getTrangThai().name());
            pstmt.executeUpdate();
            listNhaTro.add(nt);
            user.addNhaTro(nt);
            System.out.println("Thêm nhà trọ thành công!");
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    public void xemNhaTroKemThongTinGhep() {
        String sql = "SELECT nt.MaNhaTro, ln.TenLoai, nt.GiaPhong, nt.Quan, nt.TrangThai, nd.TenND " +
                     "FROM NHATRO nt " +
                     "JOIN NGUOIDUNG nd ON nt.NguoiLienHe = nd.MaND " +
                     "JOIN LOAINHA ln ON nt.MaLoai = ln.MaLoai";
        try (Connection conn = Connect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- CHI TIẾT NHÀ TRỌ (JOIN BẢNG) ---");
            while (rs.next()) {
                TrangThaiNhaTro tt = TrangThaiNhaTro.fromDBString(rs.getString("TrangThai"));
                System.out.printf("Mã NT: %s | Loại: %s | Giá: %,.0f | Quận: %s | Trạng thái: %s | Người đăng: %s\n",
                        rs.getString("MaNhaTro"), rs.getString("TenLoai"), 
                        rs.getDouble("GiaPhong"), rs.getString("Quan"), tt.getMoTa(), rs.getString("TenND"));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    public void timKiemVaPhanTrang(String quanTimKiem) {
        List<NhaTro> ketQua = listNhaTro.stream()
                .filter(nt -> nt.getQuan().equalsIgnoreCase(quanTimKiem))
                .sorted(Comparator.comparing(NhaTro::getGiaPhong))
                .collect(Collectors.toList());

        System.out.println("\n--- KẾT QUẢ TÌM KIẾM ---");
        if (ketQua.isEmpty()) {
            System.out.println("Không tìm thấy kết quả."); return;
        }
        ketQua.forEach(System.out::println);
    }

    public void backupToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(listNguoiDung);
            oos.writeObject(listNhaTro);
            System.out.println("Đã sao lưu thành công!");
        } catch (IOException e) {
            System.out.println("Lỗi ghi file: " + e.getMessage());
        }
    }
}