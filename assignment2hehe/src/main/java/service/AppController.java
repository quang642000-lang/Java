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
    // Lưu trữ dữ liệu trên RAM (ArrayList) để thao tác (tìm kiếm, sắp xếp) nhanh hơn thay vì gọi DB liên tục
    private List<NguoiDung> listNguoiDung = new ArrayList<>();
    private List<NhaTro> listNhaTro = new ArrayList<>();
    private final String FILE_NAME = "data_backup.dat"; // Tên file dùng để backup dữ liệu

    /**
     * Tải toàn bộ dữ liệu từ SQL Server lên ArrayList khi khởi động chương trình.
     */
    public void loadDataFromDB() {
        listNguoiDung.clear();
        listNhaTro.clear();
        
        // Cú pháp try-with-resources: Tự động đóng Connection (đóng kết nối) sau khi dùng xong để giải phóng bộ nhớ
        try (Connection conn = Connect.getConnection()) {
            
            // 1. Tải bảng NGUOIDUNG
            Statement stmtND = conn.createStatement();
            ResultSet rsND = stmtND.executeQuery("SELECT * FROM NGUOIDUNG"); // Thực thi câu lệnh SELECT
            while (rsND.next()) { // Duyệt từng dòng kết quả trả về từ DB
                // Lấy dữ liệu từng cột nạp vào object NguoiDung
                listNguoiDung.add(new NguoiDung(rsND.getString("MaND"), rsND.getString("TenND"), 
                        rsND.getString("GioiTinh"), rsND.getString("DienThoai"), 
                        rsND.getString("DiaChi"), rsND.getString("Quan"), rsND.getString("Email")));
            }

            // 2. Tải bảng NHATRO
            Statement stmtNT = conn.createStatement();
            ResultSet rsNT = stmtNT.executeQuery("SELECT * FROM NHATRO");
            while (rsNT.next()) {
                // Ép kiểu (Convert) từ chuỗi String trong CSDL sang kiểu Enum của Java
                LoaiNhaTro loaiNha = LoaiNhaTro.fromMa(rsNT.getString("MaLoai"));
                TrangThaiNhaTro trangThai = TrangThaiNhaTro.fromDBString(rsNT.getString("TrangThai"));

                // Xử lý ngày tháng từ dạng java.sql.Date sang java.time.LocalDate (chuẩn mới của Java 8+)
                java.sql.Date sqlDate = rsNT.getDate("NgayDang");
                LocalDate ld = (sqlDate != null) ? sqlDate.toLocalDate() : LocalDate.now();

                // Tạo đối tượng NhaTro
                NhaTro nt = new NhaTro(rsNT.getString("MaNhaTro"), loaiNha, 
                        rsNT.getDouble("DienTich"), rsNT.getDouble("GiaPhong"), rsNT.getString("DiaChi"), 
                        rsNT.getString("Quan"), rsNT.getString("MoTa"), trangThai,
                        ld, rsNT.getString("NguoiLienHe"));
                listNhaTro.add(nt);
                
                // 3. Ghép nối dữ liệu: Thêm nhà trọ này vào danh sách nhà trọ của người dùng tương ứng (quan hệ 1-Nhiều)
                for (NguoiDung nd : listNguoiDung) {
                    if (nd.getId().equals(nt.getNguoiLienHeId())) {
                        nd.addNhaTro(nt);
                        break; // Tìm thấy chủ nhà thì dừng vòng lặp này
                    }
                }
            }
            System.out.println("Đã đồng bộ dữ liệu thành công từ Database!");
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối DB: " + e.getMessage());
        }
    }

    /**
     * Thêm người dùng mới vào DB và cập nhật List.
     */
    public void themNguoiDung(NguoiDung nd) throws DuplicateIdException {
        // Kiểm tra xem ID có bị trùng với list hiện tại không (Dùng Stream API)
        if (listNguoiDung.stream().anyMatch(u -> u.getId().equalsIgnoreCase(nd.getId()))) {
            throw new DuplicateIdException("Mã người dùng đã tồn tại trong hệ thống!");
        }
        
        // Câu lệnh INSERT dùng Parameterized Query (?) để chống tấn công SQL Injection
        String sql = "INSERT INTO NGUOIDUNG VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Connect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Gán giá trị vào các dấu hỏi chấm (?)
            pstmt.setString(1, nd.getId()); 
            pstmt.setString(2, nd.getTen());
            pstmt.setString(3, nd.getGioiTinh()); 
            pstmt.setString(4, nd.getSdt());
            pstmt.setString(5, nd.getDiaChi()); 
            pstmt.setString(6, nd.getQuan());
            pstmt.setString(7, nd.getEmail());
            
            pstmt.executeUpdate(); // Chạy lệnh INSERT xuống DB
            listNguoiDung.add(nd); // Thành công thì thêm vào danh sách trên RAM
            System.out.println("Thêm người dùng thành công!");
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    /**
     * Xóa người dùng. Lưu ý: Cần dùng Transaction (Giao dịch) để xóa nhà trọ trước rồi mới xóa người.
     */
    public void xoaNguoiDung(String maND) {
        String sqlNhaTro = "DELETE FROM NHATRO WHERE NguoiLienHe = ?";
        String sqlNguoiDung = "DELETE FROM NGUOIDUNG WHERE MaND = ?";
        
        try (Connection conn = Connect.getConnection()) {
            // TẮT CHẾ ĐỘ AUTO-COMMIT: Bắt buộc phải thực hiện xong tất cả mới lưu, nếu lỗi giữa chừng thì hủy bỏ toàn bộ
            conn.setAutoCommit(false); 
            
            try (PreparedStatement pst1 = conn.prepareStatement(sqlNhaTro);
                 PreparedStatement pst2 = conn.prepareStatement(sqlNguoiDung)) {
                
                // Bước 1: Xóa toàn bộ nhà trọ do người này đăng
                pst1.setString(1, maND); 
                pst1.executeUpdate();
                
                // Bước 2: Xóa người dùng này
                pst2.setString(1, maND); 
                int row = pst2.executeUpdate(); // row trả về số dòng bị xóa
                
                if (row > 0) {
                    conn.commit(); // NẾU CẢ 2 BƯỚC THÀNH CÔNG -> Xác nhận lưu thay đổi vào DB (Commit)
                    
                    // Xóa luôn khỏi ArrayList trên RAM
                    listNhaTro.removeIf(nt -> nt.getNguoiLienHeId().equalsIgnoreCase(maND));
                    listNguoiDung.removeIf(nd -> nd.getId().equalsIgnoreCase(maND));
                    System.out.println("Đã xóa người dùng và các nhà trọ liên quan thành công!");
                } else {
                    System.out.println("Không tìm thấy mã người dùng này!");
                }
            } catch (SQLException ex) {
                conn.rollback(); // NẾU CÓ LỖI XẢY RA -> Hoàn tác lại mọi thứ (Rollback), bảo toàn dữ liệu
                System.out.println("Lỗi trong quá trình xóa, đã tự động Rollback: " + ex.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    public void hienThiDanhSachNguoiDung() {
        if(listNguoiDung.isEmpty()) System.out.println("Danh sách trống!");
        for (NguoiDung nd : listNguoiDung) {
            System.out.println(nd); // Gọi ngầm hàm toString() của NguoiDung
            if (!nd.getDanhSachNhaTroDaDang().isEmpty()) {
                System.out.println("   -> Các nhà trọ đã đăng:");
                nd.getDanhSachNhaTroDaDang().forEach(nt -> System.out.println("      " + nt)); // Dùng forEach của Java 8
            }
        }
    }

    public void themNhaTro(NhaTro nt) throws DuplicateIdException {
        if (listNhaTro.stream().anyMatch(t -> t.getId().equalsIgnoreCase(nt.getId()))) {
            throw new DuplicateIdException("Mã nhà trọ đã tồn tại!");
        }
        
        // Kiểm tra xem ID người đăng có tồn tại trong hệ thống chưa
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
            pstmt.setString(2, nt.getLoaiNha().getMaLoai()); // Lấy mã Enum (Ví dụ: "CH")
            pstmt.setDouble(3, nt.getDienTich()); 
            pstmt.setDouble(4, nt.getGiaPhong());
            pstmt.setString(5, nt.getDiaChi()); 
            pstmt.setString(6, nt.getQuan());
            pstmt.setString(7, nt.getMoTa()); 
            pstmt.setDate(8, java.sql.Date.valueOf(nt.getNgayDang())); // Convert LocalDate sang java.sql.Date
            pstmt.setString(9, nt.getNguoiLienHeId());
            pstmt.setString(10, nt.getTrangThai().name());
            
            pstmt.executeUpdate();
            listNhaTro.add(nt);
            user.addNhaTro(nt); // Cập nhật luôn vào danh sách của chủ nhà
            System.out.println("Thêm nhà trọ thành công!");
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    /**
     * Xóa nhà trọ theo mã. Xóa dưới Database và dọn dẹp dữ liệu trên RAM.
     */
    public void xoaNhaTro(String maNhaTro) {
        String sql = "DELETE FROM NHATRO WHERE MaNhaTro = ?";
        
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, maNhaTro);
            int row = pstmt.executeUpdate(); // Thực thi lệnh xóa dưới Database
            
            if (row > 0) {
                // 1. Xóa nhà trọ này khỏi danh sách tổng trên RAM
                listNhaTro.removeIf(nt -> nt.getId().equalsIgnoreCase(maNhaTro));
                
                // 2. Xóa nhà trọ này khỏi danh sách riêng của người đã đăng nó
                for (NguoiDung nd : listNguoiDung) {
                    nd.getDanhSachNhaTroDaDang().removeIf(nt -> nt.getId().equalsIgnoreCase(maNhaTro));
                }
                
                System.out.println("Đã xóa nhà trọ thành công!");
            } else {
                System.out.println("Không tìm thấy mã nhà trọ này!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL khi xóa nhà trọ: " + e.getMessage());
        }
    }

    /**
     * Cập nhật trạng thái hàng loạt cho các phòng trọ dựa theo Quận
     */
    public void capNhatTrangThaiTheoQuan(String quan, TrangThaiNhaTro trangThaiMoi) {
        String sql = "UPDATE NHATRO SET TrangThai = ? WHERE Quan = ?";
        
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, trangThaiMoi.name());
            pstmt.setString(2, quan);
            
            int rows = pstmt.executeUpdate(); // Thực thi lệnh UPDATE nhiều dòng dưới Database
            
            if (rows > 0) {
                // Cập nhật lại dữ liệu trên RAM (ArrayList) để đồng bộ ngay lập tức
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

    /**
     * Cập nhật trạng thái cho một phòng trọ cụ thể dựa theo ID (Mã nhà trọ)
     */
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

    /**
     * Dùng SQL JOIN để kết hợp dữ liệu từ nhiều bảng (điểm nhấn môn Cơ sở dữ liệu).
     */
    public void xemNhaTroKemThongTinGhep() {
        // Nối bảng NHATRO với NGUOIDUNG (lấy Tên Người Dùng) và LOAINHA (lấy Tên Loại Nhà)
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
                // In định dạng: %s (String), %,.0f (Số thực có dấu phẩy ngăn cách hàng nghìn)
                System.out.printf("Mã NT: %s | Loại: %s | Giá: %,.0f | Quận: %s | Trạng thái: %s | Người đăng: %s\n",
                        rs.getString("MaNhaTro"), rs.getString("TenLoai"), 
                        rs.getDouble("GiaPhong"), rs.getString("Quan"), tt.getMoTa(), rs.getString("TenND"));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi CSDL: " + e.getMessage());
        }
    }

    /**
     * Dùng Stream API để tìm kiếm và sắp xếp cực nhanh trên RAM.
     */
    public void timKiemVaPhanTrang(String quanTimKiem) {
        // Tạo một luồng (stream) từ danh sách nhà trọ
        List<NhaTro> ketQua = listNhaTro.stream()
                .filter(nt -> nt.getQuan().equalsIgnoreCase(quanTimKiem)) // Lọc (filter) các nhà trọ có quận khớp với từ khóa
                .sorted(Comparator.comparing(NhaTro::getGiaPhong))        // Sắp xếp (sort) theo giá phòng tăng dần
                .collect(Collectors.toList());                            // Gom kết quả lại thành 1 list mới

        if (ketQua.isEmpty()) {
            System.out.println("\n--- KẾT QUẢ TÌM KIẾM ---");
            System.out.println("Không tìm thấy nhà trọ nào ở khu vực: " + quanTimKiem); 
            return;
        }

        // === THUẬT TOÁN PHÂN TRANG (PAGINATION) ===
        java.util.Scanner sc = new java.util.Scanner(System.in);
        int pageSize = 2; // Số lượng kết quả hiển thị trên 1 trang (VD: mỗi trang hiện 2 phòng)
        int totalPages = (int) Math.ceil((double) ketQua.size() / pageSize);
        int currentPage = 1;

        while (true) {
            System.out.println("\n--- KẾT QUẢ TÌM KIẾM (Trang " + currentPage + "/" + totalPages + ") ---");
            
            // Tính toán vị trí bắt đầu và kết thúc của mảng để in ra trang hiện tại
            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, ketQua.size());
            
            for (int i = start; i < end; i++) {
                System.out.println(ketQua.get(i));
            }
            
            if (totalPages == 1) break; // Nếu chỉ có 1 trang thì in xong kết thúc luôn
            
            System.out.print("\nĐiều hướng: [N]ext (Trang sau) | [P]rev (Trang trước) | [E]xit (Thoát): ");
            String choice = sc.nextLine().trim().toUpperCase();
            
            if (choice.equals("N")) {
                if (currentPage < totalPages) currentPage++;
                else System.out.println(">> Đã ở trang cuối!");
            } else if (choice.equals("P")) {
                if (currentPage > 1) currentPage--;
                else System.out.println(">> Đã ở trang đầu!");
            } else if (choice.equals("E") || choice.equals("0")) {
                break; // Thoát vòng lặp phân trang
            } else {
                System.out.println(">> Lựa chọn không hợp lệ! Vui lòng nhập N, P hoặc E.");
            }
        }
    }

    /**
     * Sao lưu dữ liệu Object vào file nhị phân.
     */
    public void backupToFile() {
        // Dùng ObjectOutputStream để ghi thẳng các Object NguoiDung và NhaTro (yêu cầu implements Serializable ở lớp Model)
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(listNguoiDung);
            oos.writeObject(listNhaTro);
            System.out.println("Đã sao lưu toàn bộ dữ liệu thành công vào file " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Lỗi ghi file: " + e.getMessage());
        }
    }
}