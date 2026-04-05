package view;

import model.Student;
import model.Tree;
import service.StudentService;
import service.TreeService;

public class View {
    public static void main(String[] args) {
        StudentService studentRepo = new StudentService();
        TreeService treeRepo = new TreeService();

        System.out.println("=== QUẢN LÝ SINH VIÊN ===");
        // 1. Lấy và hiển thị toàn bộ sinh viên
        System.out.println("Danh sách sinh viên:");
        for (Student s : studentRepo.getAll()) {
            System.out.println(s);
        }
        // 2. Thêm mới một sinh viên
        System.out.println("\nThêm sinh viên ID 4...");
        Student newStudent = new Student(4, "Đinh Thị D", "Nữ", 3.9);
        if (studentRepo.add(newStudent)) {
            System.out.println("Thêm thành công!");
        }

        // 3. Cập nhật thông tin sinh viên
        System.out.println("\nCập nhật sinh viên ID 4 (GPA lên 4.0)...");
        newStudent.setGpa(4.0);
        if (studentRepo.update(newStudent)) {
            System.out.println("Cập nhật thành công!");
        }

        // 4. Xóa sinh viên
        System.out.println("\nXóa sinh viên ID 4...");
        if (studentRepo.delete(4)) {
            System.out.println("Xóa thành công!");
        }

        System.out.println("\n=== QUẢN LÝ TREE ===");
        System.out.println("Danh sách Tree Node:");
        for (Tree t : treeRepo.getAll()) {
            System.out.println(t);
        }
    }
}