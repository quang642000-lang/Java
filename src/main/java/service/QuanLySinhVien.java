package service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import model.Student;

public class QuanLySinhVien {
    // Để đường dẫn file ở đây dùng chung cho cả đọc và ghi luôn
    private String path = "sinhvien.txt";

    // --- HÀM GHI FILE ---
    // Nhận vào nguyên cái danh sách và đẩy hết xuống file
    public void ghiFile(ArrayList<Student> list) {
        try {
            // Mở luồng kết nối tới file trên ổ cứng
            FileOutputStream fos = new FileOutputStream(path);
            // Dùng luồng Object để ghi dạng nhị phân (nguyên cục data)
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            // Hàm thần thánh: ném nguyên cái list vào là tự động ghi hết, khỏi cần vòng lặp for
            oos.writeObject(list);
            
            // Xong việc phải đóng luồng nhé, đi thi mà quên cái này thầy cô hay bắt bẻ trừ điểm lắm
            oos.close();
            fos.close();
            System.out.println("Ghi file thanh cong!");
        } catch (Exception e) {
            // Mẹo: Bắt Exception (cha) luôn cho nhanh, lười bắt từng lỗi FileNotFound hay IOException =))
            System.out.println("Loi ghi file: " + e.getMessage());
        }
    }

    // --- HÀM ĐỌC FILE ---
    public ArrayList<Student> docFile() {
        ArrayList<Student> list = new ArrayList<>();
        try {
            // Mở luồng để đọc file lên RAM
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            // Cực kỳ quan trọng: readObject() lấy lên một Object chung chung, 
            // nên phải Ép Kiểu (Casting) về đúng cấu trúc ArrayList<Student> của mình
            list = (ArrayList<Student>) ois.readObject();
            
            // Nhớ đóng luồng như hàm trên
            ois.close();
            fis.close();
            System.out.println("Doc file thanh cong!");
        } catch (Exception e) {
            System.out.println("Loi doc file: " + e.getMessage());
        }
        return list; // Trả về danh sách vừa khôi phục được
    }
}
