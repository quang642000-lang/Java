import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 1. Lớp Employee (Model)
class Employee {
    private String id;
    private String name;
    private double salary;

    public Employee(String id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Mã: " + id + ", Tên: " + name + ", Lương: " + salary;
    }
}

// 2. Lớp EmployeeService (Service)
class EmployeeService {
    // Sử dụng Collection để lưu trữ danh sách nhân viên
    private List<Employee> list = new ArrayList<>();

    // Thêm một nhân viên vào danh sách
    public void addEmployee(Employee emp) {
        list.add(emp);
    }

    // Hiển thị danh sách nhân viên
    public void displayEmployees() {
        for (Employee emp : list) {
            System.out.println(emp.toString());
        }
    }

    // Tìm nhân viên theo mã
    public Employee findById(String id) {
        for (Employee emp : list) {
            if (emp.getId().equals(id)) {
                return emp;
            }
        }
        return null;
    }

    // Cập nhật lương nhân viên theo mã
    public void updateSalary(String id, double newSalary) {
        Employee emp = findById(id);
        if (emp != null) {
            emp.setSalary(newSalary);
        }
    }
}

// 3. Chương trình chính (Main)
public class bai3 {
    public static void main(String[] args) {
        // Khởi tạo EmployeeService
        EmployeeService service = new EmployeeService();
        Scanner scanner = new Scanner(System.in);
        int choice;

        // Tạo 1 menu lặp lại
        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Them nhan vien");
            System.out.println("2. Hien thi danh sach");
            System.out.println("3. Tim nhan vien theo ma");
            System.out.println("4. Cap nhat luong theo ma");
            System.out.println("0. Thoat");
            System.out.print("Moi chon chuc nang: ");
            choice = Integer.parseInt(scanner.nextLine());

            // Gọi các chức năng tương ứng để thao tác
            switch (choice) {
                case 1:
                    System.out.print("Nhap ma NV: ");
                    String id = scanner.nextLine();
                    System.out.print("Nhap ten NV: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhap luong: ");
                    double salary = Double.parseDouble(scanner.nextLine());
                    service.addEmployee(new Employee(id, name, salary));
                    break;
                case 2:
                    System.out.println("Danh sach nhan vien:");
                    service.displayEmployees();
                    break;
                case 3:
                    System.out.print("Nhap ma NV can tim: ");
                    String searchId = scanner.nextLine();
                    Employee emp = service.findById(searchId);
                    if (emp != null) {
                        System.out.println(emp.toString());
                    } else {
                        System.out.println("Khong tim thay nhan vien.");
                    }
                    break;
                case 4:
                    System.out.print("Nhap ma NV can cap nhat luong: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Nhap muc luong moi: ");
                    double newSalary = Double.parseDouble(scanner.nextLine());
                    service.updateSalary(updateId, newSalary);
                    System.out.println("Da cap nhat.");
                    break;
                case 0:
                    System.out.println("Ket thuc chuong trinh.");
                    break;
                default:
                    System.out.println("Chon sai, vui long chon lai.");
                    break;
            }
        } while (choice != 0);
    }
}