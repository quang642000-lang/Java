package view;

import java.util.ArrayList;
import model.Student;
import service.QuanLySinhVien;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo Service để mượn 2 cái hàm Đọc/Ghi
        QuanLySinhVien qlsv = new QuanLySinhVien();
        ArrayList<Student> danhSach = new ArrayList<>();

        // 1. Fake vài cái data bằng tay để test thử xem sao
        danhSach.add(new Student("SV01", "Nguyen Van A", "123456"));
        danhSach.add(new Student("SV02", "Tran Thi B", "abcdef"));

        System.out.println("--- DU LIEU BAN DAU (Lúc mới add bằng tay) ---");
        for (Student sv : danhSach) {
            System.out.println(sv.toString());
        }

        // 2. Chạy thử hàm Ghi
        System.out.println("\n--- THUC HIEN GHI FILE ---");
        // Lưu list có 2 người ở trên xuống ổ đĩa
        qlsv.ghiFile(danhSach);

        // 3. Chạy thử hàm Đọc
        System.out.println("\n--- THUC HIEN DOC FILE ---");
        // Lấy data từ file lên và gán vào một danh sách mới
        ArrayList<Student> danhSachDocLen = qlsv.docFile();
        
        // In ra check kết quả.
        for (Student sv : danhSachDocLen) {
            System.out.println(sv.toString());
        }
    }
}
