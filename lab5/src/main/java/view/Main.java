package view;
import java.util.Scanner;
import model.Student;
import service.StudentService;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentService service = new StudentService();

        service.loadData();

        int choice = -1;
        while (choice != 0) {
            System.out.println("\n===== QUẢN LÝ SINH VIÊN =====");
            System.out.println("1. Thêm sinh viên mới");
            System.out.println("2. Hiển thị danh sách");
            System.out.println("3. Lưu dữ liệu hiện tại");
            System.out.println("0. Lưu dữ liệu và Thoát");
            System.out.print("Mời bạn chọn chức năng: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Nhập ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Nhập Tên: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhập Điểm GPA: ");
                    double gpa = 0;
                    try {
                        gpa = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("GPA phải là một số! Gán mặc định bằng 0.0");
                    }
                    
                    Student st = new Student(id, name, gpa);
                    service.addStudent(st);
                    break;
                case 2:
                    service.displayStudents();
                    break;
                case 3:
                    service.saveData();
                    break;
                case 0:
                    System.out.println("Đang lưu dữ liệu và thoát...");
                    service.saveData();
                    break;
                default:
                    System.out.println("Chức năng không tồn tại!");
            }
        }
        scanner.close();
    }
}