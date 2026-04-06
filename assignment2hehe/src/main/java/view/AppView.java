package view;

import service.AppController;
import model.*;

import java.time.LocalDate;
import java.util.Scanner;

public class AppView {
    private Scanner scanner;
    private AppController controller; // View gọi Controller để xử lý, không tự tính toán logic ở đây

    public AppView() {
        this.scanner = new Scanner(System.in);
        this.controller = new AppController();
    }

    /**
     * Hàm bắt đầu chương trình, in ra menu chính.
     */
    public void start() {
        controller.loadDataFromDB(); // Nạp dữ liệu một lần duy nhất lúc khởi động ứng dụng
        
        // Vòng lặp vô hạn để menu luôn hiện lại sau khi thực hiện xong 1 chức năng
        while (true) {
            System.out.println("\n===== HỆ THỐNG QUẢN LÝ NHÀ TRỌ =====");
            System.out.println("1. Quản lý Người dùng");
            System.out.println("2. Quản lý Nhà trọ");
            System.out.println("3. Tìm kiếm nhà trọ");
            System.out.println("4. Sao lưu File");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": menuNguoiDung(); break; // Gọi sang menu con
                case "2": menuNhaTro(); break;    // Gọi sang menu con
                case "3":
                    System.out.print("Nhập tên Quận cần tìm (VD: Cầu Giấy): ");
                    controller.timKiemVaPhanTrang(scanner.nextLine());
                    break;
                case "4": 
                    controller.backupToFile(); 
                    break;
                case "0": 
                    System.out.println("Cảm ơn bạn đã sử dụng phần mềm!");
                    System.exit(0); // Tắt chương trình hoàn toàn
                default: 
                    System.out.println("Nhập sai, vui lòng chọn từ 0 đến 4!");
            }
        }
    }

    private void menuNguoiDung() {
        while (true) {
            System.out.println("\n--- MENU NGƯỜI DÙNG ---");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm người dùng");
            System.out.println("3. Xóa người dùng");
            System.out.println("0. Quay lại Menu chính");
            String c = scanner.nextLine();
            switch (c) {
                case "1": controller.hienThiDanhSachNguoiDung(); break;
                case "2":
                    // Dùng try-catch để bắt lỗi (Exception) từ DuplicateIdException do Controller ném ra
                    try {
                        System.out.print("ID (Mã ND): "); String id = scanner.nextLine();
                        System.out.print("Tên ND: "); String ten = scanner.nextLine();
                        System.out.print("Giới tính: "); String gioiTinh = scanner.nextLine();
                        System.out.print("SĐT: "); String sdt = scanner.nextLine();
                        System.out.print("Địa chỉ: "); String diaChi = scanner.nextLine();
                        System.out.print("Quận: "); String quan = scanner.nextLine();
                        System.out.print("Email: "); String email = scanner.nextLine();
                        
                        // Đóng gói dữ liệu vào Object NguoiDung và ném sang Controller xử lý
                        controller.themNguoiDung(new NguoiDung(id, ten, gioiTinh, sdt, diaChi, quan, email));
                    } catch (Exception e) { 
                        System.out.println("Lỗi khi thêm: " + e.getMessage()); 
                    }
                    break;
                case "3":
                    System.out.print("Nhập ID người dùng cần xóa: ");
                    controller.xoaNguoiDung(scanner.nextLine());
                    break;
                case "0": 
                    return; // Lệnh return sẽ thoát khỏi hàm menuNguoiDung() và tự động quay về vòng lặp start()
            }
        }
    }

    private void menuNhaTro() {
        while (true) {
            System.out.println("\n--- MENU NHÀ TRỌ ---");
            System.out.println("1. Xem chi tiết nhà trọ (SQL JOIN)");
            System.out.println("2. Thêm nhà trọ");
            System.out.println("3. Xóa nhà trọ");
            System.out.println("4. Cập nhật trạng thái phòng trọ");
            System.out.println("0. Quay lại Menu chính");
            String c = scanner.nextLine();
            switch (c) {
                case "1": controller.xemNhaTroKemThongTinGhep(); break;
                case "2":
                    try {
                        System.out.print("ID Nhà trọ: "); String id = scanner.nextLine();
                        System.out.print("Mã Loại (CH/NR/PT): "); 
                        LoaiNhaTro loai = LoaiNhaTro.fromMa(scanner.nextLine()); // Sử dụng Enum để dịch mã
                        
                        // Parse số thực, nếu nhập chữ sẽ văng lỗi NumberFormatException và bị block try-catch bắt lại
                        System.out.print("Diện tích (m2): "); double dt = Double.parseDouble(scanner.nextLine());
                        System.out.print("Giá phòng (VND): "); double gia = Double.parseDouble(scanner.nextLine());
                        
                        System.out.print("Quận: "); String quan = scanner.nextLine();
                        System.out.print("ID Người đăng (Phải tồn tại trong DB): "); String idND = scanner.nextLine();
                        System.out.print("Trạng thái (1: Trống, 2: Đang ở, 3: Đang sửa): ");
                        TrangThaiNhaTro tt = TrangThaiNhaTro.fromLuaChon(scanner.nextLine());
                        
                        // Khởi tạo và đẩy sang controller
                        controller.themNhaTro(new NhaTro(id, loai, dt, gia, "Mặc định", quan, "Mô tả", tt, LocalDate.now(), idND));
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: Diện tích và Giá phòng phải nhập số!");
                    } catch (Exception e) { 
                        System.out.println("Lỗi: " + e.getMessage()); 
                    }
                    break;
                case "3":
                    System.out.print("Nhập ID nhà trọ cần xóa: ");
                    controller.xoaNhaTro(scanner.nextLine());
                    break;
                case "4":
                    System.out.println("   1. Cập nhật 1 phòng cụ thể (theo ID)");
                    System.out.println("   2. Cập nhật đồng loạt (theo Quận)");
                    System.out.print("   Chọn chức năng: ");
                    String chonSua = scanner.nextLine();
                    
                    if (chonSua.equals("1")) {
                        System.out.print("Nhập ID nhà trọ cần cập nhật: ");
                        String idUpdate = scanner.nextLine();
                        System.out.print("Chọn trạng thái mới (1: Trống, 2: Đang ở, 3: Đang sửa): ");
                        TrangThaiNhaTro ttMoiId = TrangThaiNhaTro.fromLuaChon(scanner.nextLine());
                        controller.capNhatTrangThaiTheoId(idUpdate, ttMoiId);
                    } else if (chonSua.equals("2")) {
                        System.out.print("Nhập tên Quận muốn cập nhật trạng thái hàng loạt: ");
                        String updateQuan = scanner.nextLine();
                        System.out.print("Chọn trạng thái mới (1: Trống, 2: Đang ở, 3: Đang sửa): ");
                        TrangThaiNhaTro ttMoiQuan = TrangThaiNhaTro.fromLuaChon(scanner.nextLine());
                        controller.capNhatTrangThaiTheoQuan(updateQuan, ttMoiQuan);
                    } else {
                        System.out.println("Lựa chọn không hợp lệ!");
                    }
                    break;
                case "0": 
                    return;
            }
        }
    }
}