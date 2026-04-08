package view;

import entity.Employee;
import repository.EmployeeRepository;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmployeeRepository repo = new EmployeeRepository();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n1. Lấy danh sách nhân viên");
            System.out.println("2. Thêm nhân viên");
            System.out.println("3. Cập nhật nhân viên");
            System.out.println("4. Xóa nhân viên");
            System.out.println("5. Tìm kiếm nhân viên theo ID");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    List<Employee> list = repo.getAll();
                    if (list.isEmpty()) {
                        System.out.println("Danh sách trống!");
                    } else {
                        for (Employee e : list) {
                            System.out.println(e);
                        }
                    }
                    break;

                case 2:
                    System.out.print("Nhập ID: ");
                    int idAdd = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nhập Tên: ");
                    String nameAdd = scanner.nextLine();
                    System.out.print("Nhập Lương: ");
                    float salaryAdd = Float.parseFloat(scanner.nextLine());
                    
                    Employee newEmp = new Employee(idAdd, nameAdd, salaryAdd);
                    if (repo.add(newEmp)) {
                        System.out.println("Thêm nhân viên thành công!");
                    } else {
                        System.out.println("Thêm nhân viên thất bại!");
                    }
                    break;

                case 3:
                    System.out.print("Nhập ID nhân viên cần cập nhật: ");
                    int idUpdate = Integer.parseInt(scanner.nextLine());
                    
                    if (repo.findById(idUpdate) == null) {
                        System.out.println("Không tìm thấy nhân viên!");
                        break;
                    }
                    
                    System.out.print("Nhập Tên mới: ");
                    String nameUpdate = scanner.nextLine();
                    System.out.print("Nhập Lương mới: ");
                    float salaryUpdate = Float.parseFloat(scanner.nextLine());
                    
                    Employee updateEmp = new Employee(idUpdate, nameUpdate, salaryUpdate);
                    if (repo.update(updateEmp)) {
                        System.out.println("Cập nhật thành công!");
                    } else {
                        System.out.println("Cập nhật thất bại!");
                    }
                    break;

                case 4:
                    System.out.print("Nhập ID nhân viên cần xóa: ");
                    int idDelete = Integer.parseInt(scanner.nextLine());
                    if (repo.delete(idDelete)) {
                        System.out.println("Xóa thành công!");
                    } else {
                        System.out.println("Xóa thất bại!");
                    }
                    break;

                case 5:
                    System.out.print("Nhập ID nhân viên cần tìm: ");
                    int idSearch = Integer.parseInt(scanner.nextLine());
                    
                    Employee emp = repo.findById(idSearch);
                    if (emp != null) {
                        System.out.println("Tìm kiếm thành công: " + emp);
                    } else {
                        System.out.println("Tìm kiếm thất bại!");
                    }
                    break;

                case 0:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!!!");
            }
        } while (choice != 0);
        
        scanner.close();
    }
}