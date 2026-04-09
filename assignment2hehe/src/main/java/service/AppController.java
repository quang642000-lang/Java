package service;

import connect.Connect;
import model.*;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppController {
    // Lưu trữ dữ liệu trên RAM (ArrayList) để thao tác (tìm kiếm, sắp xếp) nhanh hơn thay vì gọi DB liên tục
    private List<NguoiDung> listNguoiDung = new ArrayList<>();
    private List<NhaTro> listNhaTro = new ArrayList<>();
    private final String FILE_NAME = "data_backup.dat"; // Tên file dùng để backup dữ liệu
    
    // Map lưu trữ đánh giá theo mã nhà trọ (Áp dụng Enum TrangThaiDanhGia)
    private Map<String, List<TrangThaiDanhGia>> lichSuDanhGia = new HashMap<>();

    /**
     * Tải toàn bộ dữ liệu từ SQL Server lên ArrayList khi khởi động chương trình.
     */
    public void loadDataFromDB() {
        listNguoiDung.clear();
        listNhaTro.clear();
        lichSuDanhGia.clear();
        
        try (Connection conn = Connect.getConnection()) {
            // 1. Tải bảng NGUOIDUNG
            Statement stmtND = conn.createStatement();
            ResultSet rsND = stmtND.executeQuery("SELECT * FROM NGUOIDUNG");
            while (rsND.next()) {
                listNguoiDung.add(new NguoiDung(rsND.getString("MaND"), rsND.getString("TenND"), 
                        rsND.getString("GioiTinh"), rsND.getString("DienThoai"), 
                        rsND.getString("DiaChi"), rsND.getString("Quan"), rsND.getString("Email")));
            }

            // 2. Tải bảng NHATRO
            Statement stmtNT = conn.createStatement();
            ResultSet rsNT = stmtNT.executeQuery("SELECT * FROM NHATRO");
            while (rsNT.next()) {
                LoaiNhaTro loaiNha = LoaiNhaTro.fromMa(rsNT.getString("MaLoai"));
                TrangThaiNhaTro trangThai = TrangThaiNhaTro.fromDBString(rsNT.getString("TrangThai"));

                java.sql.Date sqlDate = rsNT.getDate("NgayDang");
                LocalDate ld = (sqlDate != null) ? sqlDate.toLocalDate() : LocalDate.now();

                NhaTro nt = new NhaTro(rsNT.getString("MaNhaTro"), loaiNha, 
                        rsNT.getDouble("DienTich"), rsNT.getDouble("GiaPhong"), rsNT.getString("DiaChi"), 
                        rsNT.getString("Quan"), rsNT.getString("MoTa"), trangThai,
                        ld, rsNT.getString("NguoiLienHe"));
                listNhaTro.add(nt);
                
                // Ghép nối dữ liệu
                for (NguoiDung nd : listNguoiDung) {
                    if (nd.getId().equals(nt.getNguoiLienHeId())) {
                        nd.addNhaTro(nt);
                        break;
                    }
                }
            }

            // 3. Tải bảng DANHGIA từ SQL
            Statement stmtDG = conn.createStatement();
            ResultSet rsDG = stmtDG.executeQuery("SELECT * FROM DANHGIA");
            while (rsDG.next()) {
                String maNhaTro = rsDG.getString("MaNhaTro");
                String tt = rsDG.getString("TrangThai");
                TrangThaiDanhGia danhGiaEnum = TrangThaiDanhGia.valueOf(tt); // Chuyển từ String -> Enum
                
                lichSuDanhGia.putIfAbsent(maNhaTro, new ArrayList<>());
                lichSuDanhGia.get(maNhaTro).add(danhGiaEnum);
            }

            System.out.println("Đã đồng bộ dữ liệu thành công từ Database!");
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối DB: " + e.getMessage());
        }
    }

    // ==================== QUẢN LÝ NGƯỜI DÙNG ====================

    public void themNguoiDung(NguoiDung nd) throws DuplicateIdException {
        if (listNguoiDung.stream().anyMatch(u -> u.getId().equalsIgnoreCase(nd.getId()))) {
            throw new DuplicateIdException("Mã người dùng đã tồn tại trong hệ thống!");
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
        String sqlDanhGia = "DELETE FROM DANHGIA WHERE MaNhaTro IN (SELECT MaNhaTro FROM NHATRO WHERE NguoiLienHe = ?)";
        String sqlNhaTro = "DELETE FROM NHATRO WHERE NguoiLienHe = ?";
        String sqlNguoiDung = "DELETE FROM NGUOIDUNG WHERE MaND = ?";
        
        try (Connection conn = Connect.getConnection()) {
            conn.setAutoCommit(false); // Bật Transaction
            
            try (PreparedStatement pst0 = conn.prepareStatement(sqlDanhGia);
                 PreparedStatement pst1 = conn.prepareStatement(sqlNhaTro);
                 PreparedStatement pst2 = conn.prepareStatement(sqlNguoiDung)) {
                
                // Phải xóa các đánh giá của các nhà trọ do người này đăng trước
                pst0.setString(1, maND);
                pst0.executeUpdate();

                // Xóa các nhà trọ
                pst1.setString(1, maND); 
                pst1.executeUpdate();
                
                // Cuối cùng xóa người dùng
                pst2.setString(1, maND); 
                int row = pst2.executeUpdate();
                
                if (row > 0) {
                    conn.commit();
                    
                    // Lọc và dọn dẹp lịch sử đánh giá trên RAM của các nhà trọ vừa bị xóa
                    List<String> nhaTroBixoa = listNhaTro.stream()
                        .filter(nt -> nt.getNguoiLienHeId().equalsIgnoreCase(maND))
                        .map(NhaTro::getId)
                        .collect(Collectors.toList());
                    for (String ma : nhaTroBixoa) {
                        lichSuDanhGia.remove(ma);
                    }
                    
                    listNhaTro.removeIf(nt -> nt.getNguoiLienHeId().equalsIgnoreCase(maND));
                    listNguoiDung.removeIf(nd -> nd.getId().equalsIgnoreCase(maND));
                    
                    System.out.println("Đã xóa người dùng và các nhà trọ liên quan thành công!");
                } else {
                    System.out.println("Không tìm thấy mã người dùng này!");
                }
            } catch (SQLException ex) {
                conn.rollback();
                System.out.println("Lỗi trong quá trình xóa, đã tự động Rollback: " + ex.getMessage());
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

    public void timKiemNguoiDungTheoTen(String ten) {
        List<NguoiDung> ketQua = listNguoiDung.stream()
                .filter(nd -> nd.getTen().toLowerCase().contains(ten.toLowerCase()))
                .collect(Collectors.toList());
                
        if (ketQua.isEmpty()) {
            System.out.println("Không tìm thấy người dùng nào có tên chứa từ khóa: '" + ten + "'");
        } else {
            System.out.println("\n--- KẾT QUẢ LỌC NGƯỜI DÙNG THEO TÊN ---");
            ketQua.forEach(nd -> {
                System.out.println(nd);
                if (!nd.getDanhSachNhaTroDaDang().isEmpty()) {
                    System.out.println("   -> Các nhà trọ đã đăng:");
                    nd.getDanhSachNhaTroDaDang().forEach(nt -> System.out.println("      " + nt));
                }
            });
        }
    }

    // ==================== QUẢN LÝ NHÀ TRỌ ====================

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

    public void xoaNhaTro(String maNhaTro) {
        String sqlDanhGia = "DELETE FROM DANHGIA WHERE MaNhaTro = ?";
        String sqlNhaTro = "DELETE FROM NHATRO WHERE MaNhaTro = ?";
        
        try (Connection conn = Connect.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement pst1 = conn.prepareStatement(sqlDanhGia);
                 PreparedStatement pst2 = conn.prepareStatement(sqlNhaTro)) {
                
                // Xóa đánh giá của nhà trọ trước
                pst1.setString(1, maNhaTro);
                pst1.executeUpdate();
                
                // Rồi mới xóa nhà trọ
                pst2.setString(1, maNhaTro);
                int row = pst2.executeUpdate();
                
                if (row > 0) {
                    conn.commit();
                    listNhaTro.removeIf(nt -> nt.getId().equalsIgnoreCase(maNhaTro));
                    for (NguoiDung nd : listNguoiDung) {
                        nd.getDanhSachNhaTroDaDang().removeIf(nt -> nt.getId().equalsIgnoreCase(maNhaTro));
                    }
                    
                    lichSuDanhGia.remove(maNhaTro); // Xóa trên RAM
                    System.out.println("Đã xóa nhà trọ và các đánh giá liên quan thành công!");
                } else {
                    System.out.println("Không tìm thấy mã nhà trọ này!");
                }
            } catch (SQLException ex) {
                conn.rollback();
                System.out.println("Lỗi trong quá trình xóa nhà trọ: " + ex.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL khi xóa nhà trọ: " + e.getMessage());
        }
    }

    public void capNhatTrangThaiTheoQuan(String quan, TrangThaiNhaTro trangThaiMoi) {
        String sql = "UPDATE NHATRO SET TrangThai = ? WHERE Quan = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt =prepareStatement(conn, sql)) {
            pstmt.setString(1, trangThaiMoi.name());
            pstmt.setString(2, quan);
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                for (NhaTro nt : listNhaTro) {
                    if (nt.getQuan().equalsIgnoreCase(quan)) {
                        nt.setTrangThai(trangThaiMoi);
                    }
                }
                System.out.println("Đã cập nhật thành công trạng thái mới cho " + rows + " nhà trọ tại quận " + quan + "!");
            } else {
                System.out.println("Không tìm thấy nhà trọ nào tại quận " + quan + " để cập nhật.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL khi cập nhật trạng thái: " + e.getMessage());
        }
    }
    
    private PreparedStatement prepareStatement(Connection conn, String sql) throws SQLException{
        return conn.prepareStatement(sql);
    }

    public void capNhatTrangThaiTheoId(String maNhaTro, TrangThaiNhaTro trangThaiMoi) {
        String sql = "UPDATE NHATRO SET TrangThai = ? WHERE MaNhaTro = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, trangThaiMoi.name());
            pstmt.setString(2, maNhaTro);
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                for (NhaTro nt : listNhaTro) {
                    if (nt.getId().equalsIgnoreCase(maNhaTro)) {
                        nt.setTrangThai(trangThaiMoi);
                        break;
                    }
                }
                System.out.println("Đã cập nhật thành công trạng thái mới cho nhà trọ: " + maNhaTro);
            } else {
                System.out.println("Không tìm thấy nhà trọ nào có mã " + maNhaTro + " để cập nhật.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL khi cập nhật trạng thái: " + e.getMessage());
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
            System.out.println("\n--- CHI TIẾT NHÀ TRỌ (LẤY TỪ SQL JOIN) ---");
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

    // ==================== TÌM KIẾM, PHÂN TRANG & BACKUP ====================

    public void timKiemVaPhanTrang(String quanTimKiem) {
        List<NhaTro> ketQua = listNhaTro.stream()
                .filter(nt -> nt.getQuan().equalsIgnoreCase(quanTimKiem))
                .sorted(Comparator.comparing(NhaTro::getGiaPhong))
                .collect(Collectors.toList());

        if (ketQua.isEmpty()) {
            System.out.println("\n--- KẾT QUẢ TÌM KIẾM ---");
            System.out.println("Không tìm thấy nhà trọ nào ở khu vực: " + quanTimKiem); 
            return;
        }

        java.util.Scanner sc = new java.util.Scanner(System.in);
        int pageSize = 2; 
        int totalPages = (int) Math.ceil((double) ketQua.size() / pageSize);
        int currentPage = 1;

        while (true) {
            System.out.println("\n--- KẾT QUẢ TÌM KIẾM (Trang " + currentPage + "/" + totalPages + ") ---");
            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, ketQua.size());
            
            for (int i = start; i < end; i++) {
                System.out.println(ketQua.get(i));
            }
            
            if (totalPages == 1) break; 
            
            System.out.print("\nĐiều hướng: [N]ext (Trang sau) | [P]rev (Trang trước) | [E]xit (Thoát): ");
            String choice = sc.nextLine().trim().toUpperCase();
            
            if (choice.equals("N")) {
                if (currentPage < totalPages) currentPage++;
                else System.out.println(">> Đã ở trang cuối!");
            } else if (choice.equals("P")) {
                if (currentPage > 1) currentPage--;
                else System.out.println(">> Đã ở trang đầu!");
            } else if (choice.equals("E") || choice.equals("0")) {
                break;
            } else {
                System.out.println(">> Lựa chọn không hợp lệ! Vui lòng nhập N, P hoặc E.");
            }
        }
    }

    public void backupToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(listNguoiDung);
            oos.writeObject(listNhaTro);
            oos.writeObject(lichSuDanhGia); 
            System.out.println("Đã sao lưu toàn bộ dữ liệu thành công vào file " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Lỗi ghi file: " + e.getMessage());
        }
    }

    // ==================== ĐÁNH GIÁ (LIKE/DISLIKE) ====================

    public void danhGiaNhaTro(String maNhaTro, TrangThaiDanhGia danhGia) {
        boolean tonTai = listNhaTro.stream().anyMatch(nt -> nt.getId().equalsIgnoreCase(maNhaTro));
        if (!tonTai) {
            System.out.println("Lỗi: Không tìm thấy nhà trọ có mã " + maNhaTro);
            return;
        }

        String sql = "INSERT INTO DANHGIA (MaNhaTro, TrangThai) VALUES (?, ?)";
        try (Connection conn = Connect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maNhaTro);
            pstmt.setString(2, danhGia.name());
            pstmt.executeUpdate();
            
            // Cập nhật lên RAM sau khi lưu DB thành công
            lichSuDanhGia.putIfAbsent(maNhaTro, new ArrayList<>());
            lichSuDanhGia.get(maNhaTro).add(danhGia);
            
            System.out.println("Đã lưu đánh giá [" + danhGia + "] cho phòng trọ " + maNhaTro + " vào CSDL!");
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL khi ghi nhận đánh giá: " + e.getMessage());
        }
    }
    
    public void xemThongKeDanhGia(String maNhaTro) {
        List<TrangThaiDanhGia> list = lichSuDanhGia.getOrDefault(maNhaTro, new ArrayList<>());
        if (list.isEmpty()) {
            System.out.println("Phòng trọ " + maNhaTro + " hiện chưa có đánh giá nào.");
            return;
        }
        
        long likes = list.stream().filter(dg -> dg == TrangThaiDanhGia.LIKE).count();
        long dislikes = list.stream().filter(dg -> dg == TrangThaiDanhGia.DISLIKE).count();
        
        System.out.println("\n--- THỐNG KÊ ĐÁNH GIÁ (Mã phòng: " + maNhaTro + ") ---");
        System.out.println("👍 Lượt LIKE: " + likes);
        System.out.println("👎 Lượt DISLIKE: " + dislikes);
    }

    public void xemTatCaThongKeDanhGia() {
        if (lichSuDanhGia.isEmpty()) {
            System.out.println("Hệ thống hiện chưa có bất kỳ đánh giá nào.");
            return;
        }
        
        System.out.println("\n--- THỐNG KÊ ĐÁNH GIÁ HÀNG LOẠT ---");
        for (String maNhaTro : lichSuDanhGia.keySet()) {
            List<TrangThaiDanhGia> list = lichSuDanhGia.get(maNhaTro);
            long likes = list.stream().filter(dg -> dg == TrangThaiDanhGia.LIKE).count();
            long dislikes = list.stream().filter(dg -> dg == TrangThaiDanhGia.DISLIKE).count();
            
            System.out.printf("Phòng [%s] - 👍 LIKE: %d | 👎 DISLIKE: %d\n", maNhaTro, likes, dislikes);
        }
    }
}