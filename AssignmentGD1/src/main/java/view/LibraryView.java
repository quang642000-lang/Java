package view;

import model.Document;
import model.Copy;
import model.CopyStatus;
import java.util.List;
import java.util.Scanner;

// Chữ V trong MVC (View) - Nơi duy nhất tương tác với người dùng
// TUYỆT ĐỐI KHÔNG chứa vòng lặp xử lý dữ liệu hay thêm/sửa/xóa ở đây.
public class LibraryView {
    // Khởi tạo Scanner một lần để dùng chung
    private Scanner scanner = new Scanner(System.in);

    // In menu và bẫy lỗi nhập chữ thay vì nhập số (tránh sập chương trình)
    public int showMainMenu() {
        System.out.println("\n===========================================");
        System.out.println("   HỆ THỐNG QUẢN LÝ THƯ VIỆN (MÔ HÌNH MSV) ");
        System.out.println("===========================================");
        System.out.println("1. Thêm tài liệu mới");
        System.out.println("2. Xem danh sách tài liệu");
        System.out.println("3. Sửa thông tin tài liệu");
        System.out.println("4. Xóa tài liệu");
        System.out.println("5. Quản lý Bản sao (Thêm/Sửa/Xóa/Xem)");
        System.out.println("0. Thoát chương trình");
        System.out.println("-------------------------------------------");
        System.out.print("Chọn chức năng (0-5): ");
        try {
            // Dùng trim() để xóa lỡ người dùng gõ thêm dấu cách trước số
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            return -1; // Trả về -1 để lọt vào case 'default' thông báo lỗi
        }
    }

    public int showCopyMenu(String docTitle) {
        System.out.println("\n--- Quản lý bản sao của: [" + docTitle + "] ---");
        System.out.println("1. Thêm bản sao");
        System.out.println("2. Xem danh sách bản sao");
        System.out.println("3. Cập nhật trạng thái bản sao");
        System.out.println("4. Xóa bản sao");
        System.out.println("0. Quay lại menu chính");
        System.out.println("-------------------------------------------");
        System.out.print("Chọn chức năng (0-4): ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            return -1;
        }
    }

    // Hàm tiện ích: In một tin nhắn ra màn hình (để service gọi)
    public void printMessage(String message) {
        System.out.println(">> " + message);
    }

    // Hàm tiện ích: In dòng nhắc nhở, ép người dùng nhập chữ (KHÔNG ĐƯỢC ĐỂ TRỐNG)
    public String getInput(String prompt) {
        String result = "";
        while (true) {
            System.out.print(prompt);
            result = scanner.nextLine().trim(); // Tự động xóa khoảng trắng thừa ở 2 đầu
            
            if (!result.isEmpty()) { // Kiểm tra nếu người dùng gõ chữ thật
                return result; // Trả về kết quả
            }
            
            // Nếu người dùng chỉ bấm Enter không, sẽ chạy xuống dòng này và lặp lại
            System.out.println(">> LỖI: Dữ liệu không được để trống! Vui lòng nhập lại.");
        }
    }

    // In ra danh sách các đối tượng Tài liệu
    public void displayDocuments(List<Document> docs) {
        if (docs.isEmpty()) {
            System.out.println(">> Hệ thống chưa có tài liệu nào.");
        } else {
            System.out.println("\n--- DANH SÁCH TÀI LIỆU ---");
            for (int i = 0; i < docs.size(); i++) {
                System.out.println("- " + docs.get(i).toString());
            }
            System.out.println("--------------------------");
            System.out.println(">> Tổng số tài liệu: " + docs.size());
        }
    }

    // In ra danh sách các đối tượng Bản sao
    public void displayCopies(List<Copy> copies) {
        if (copies.size() == 0) {
            System.out.println(">> Tài liệu này chưa có bản sao nào.");
        } else {
            System.out.println("\n--- DANH SÁCH BẢN SAO ---");
            for (int i = 0; i < copies.size(); i++) {
                // Đánh số thứ tự cho dễ nhìn
                System.out.println((i + 1) + ". " + copies.get(i).toString());
            }
            System.out.println("-------------------------");
        }
    }

    // Giao diện để chọn hằng số Enum một cách an toàn
    public CopyStatus selectStatusUI() {
        while (true) { // Vòng lặp ép người dùng phải chọn đúng số 1, 2 hoặc 3
            System.out.println("Chọn trạng thái: [1] Còn tốt | [2] Hư hỏng | [3] Mất");
            System.out.print("Mời bạn chọn (1-3): ");
            String input = scanner.nextLine().trim();
            if (input.equals("1")) return CopyStatus.CON_TOT;
            if (input.equals("2")) return CopyStatus.HU_HONG;
            if (input.equals("3")) return CopyStatus.MAT;
            System.out.println(">> LỖI: Lựa chọn không hợp lệ. Vui lòng nhập 1, 2 hoặc 3!");
        }
    }
}