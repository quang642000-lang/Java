package main;

import service.QuanLySanPham;
import java.util.Scanner;

// ĐÁP ỨNG Y10: Hiển thị menu chương trình
public class Main {

    // Hàm main là điểm bắt đầu (entry point) của mọi ứng dụng Java
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Khởi tạo đối tượng Service để quản lý nghiệp vụ
        QuanLySanPham service = new QuanLySanPham();

        // Dùng vòng lặp vô hạn (while true) để Menu luôn được hiển thị lại sau khi chạy xong 1 chức năng
        while (true) {
            System.out.println("\n===== MENU QUẢN LÝ SẢN PHẨM =====");
            System.out.println("1. Nhập danh sách sản phẩm (Y1, Y5)");
            System.out.println("2. Hiển thị thông tin sản phẩm (Y2)");
            System.out.println("3. Cập nhật thông tin sản phẩm (Y4)");
            System.out.println("4. Tìm kiếm sản phẩm (Y6)");
            System.out.println("5. Xóa sản phẩm trong danh sách (Y7)");
            System.out.println("6. Sắp xếp danh sách theo tên (Y8)");
            System.out.println("0. Thoát chương trình");
            System.out.println("=================================");
            System.out.print("=> Chọn chức năng (0-6): ");

            int chon = -1;

            // Khối try-catch dùng để "bẫy lỗi" (Exception Handling)
            // Nếu người dùng cố tình nhập chữ (VD: 'abc') thay vì số, chương trình sẽ không bị sập (crash)
            try {
                // Dùng ép kiểu thay vì nextInt() để khắc phục triệt để lỗi "trôi lệnh Scanner"
                chon = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập số!");
                continue; // Bỏ qua đoạn switch bên dưới, quay lại đầu vòng lặp while (hiển thị lại menu)
            }

            // Switch-case dùng để rẽ nhánh chức năng dựa trên số đã chọn
            switch (chon) {
                case 1:
                    service.nhap(sc);
                    break;
                case 2:
                    service.xuat();
                    break;
                case 3:
                    service.capNhat(sc);
                    break;
                case 4:
                    service.timKiem(sc);
                    break;
                case 5:
                    service.xoa(sc);
                    break;
                case 6:
                    service.sapXep();
                    break;
                case 0:
                    System.out.println("Đã thoát chương trình!");
                    System.exit(0); // Lệnh tắt hoàn toàn ứng dụng Java
                default:
                    System.out.println("Chức năng không tồn tại, vui lòng chọn lại!");
                    break;
            }
        }
    }
}
