package service;
import model.Student;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private List<Student> students;
    private static final String FILE_NAME = "students.dat";

    public StudentService() {
        this.students = new ArrayList<>();
    }

    // Phương thức thêm sinh viên vào danh sách
    public void addStudent(Student student) {
        students.add(student);
        System.out.println("Đã thêm sinh viên thành công!");
    }

    // Phương thức hiển thị danh sách
    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("Danh sách sinh viên đang trống!");
            return;
        }
        System.out.println("--- DANH SÁCH SINH VIÊN ---");
        for (Student st : students) {
            System.out.println(st.toString());
        }
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
            System.out.println("Lưu dữ liệu thành công!");
        } catch (IOException e) {
            System.out.println("Lỗi lưu file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("File dữ liệu chưa tồn tại, sẽ tạo mới sau.");
            return;
        }

        if (file.length() == 0) {
            System.out.println("File dữ liệu đang rỗng.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                students = (List<Student>) obj;
                System.out.println("Tải dữ liệu thành công!");
            }
        } catch (Exception e) {
            System.out.println("Lỗi đọc file: " + e.getMessage());
        }
    }
}
