/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.lab4;

/**
 *
 * @author PC
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentService studentService = new StudentService();
        EmployeeService employeeService = new EmployeeService();
        GenericManager<Student> studentManager = new GenericManager<>();
        GenericManager<Employee> employeeManager = new GenericManager<>();

        while (true) {
            System.out.println("\n===== MENU CHÍNH =====");
            System.out.println("1. Quản lý Sinh viên");
            System.out.println("2. Quản lý Nhân viên");
            System.out.println("3. Test GenericManager");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    try {
                        System.out.print("Nhập ID SV: ");
                        String id = sc.nextLine();
                        System.out.print("Nhập tên SV: ");
                        String name = sc.nextLine();
                        System.out.print("Nhập GPA: ");
                        double gpa = sc.nextDouble();
                        sc.nextLine();
                        Student s = new Student(id, name, gpa);
                        studentService.addStudent(s);
                        studentManager.add(s);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Lỗi: " + e.getMessage());
                    }
                    studentService.showStudents();
                }
                case 2 -> {
                    try {
                        System.out.print("Nhập ID NV: ");
                        String id = sc.nextLine();
                        System.out.print("Nhập tên NV: ");
                        String name = sc.nextLine();
                        System.out.print("Nhập lương: ");
                        double salary = sc.nextDouble();
                        sc.nextLine();
                        Employee e = new Employee(id, name, salary);
                        employeeService.addEmployee(e);
                        employeeManager.add(e);
                    } catch (DuplicateEmployeeException | InvalidSalaryException ex) {
                        System.out.println("Lỗi: " + ex.getMessage());
                    }
                    employeeService.showEmployees();
                }
                case 3 -> {
                    try {
                        System.out.println("First Student: " + studentManager.getFirst());
                        System.out.println("Last Student: " + studentManager.getLast());
                    } catch (EmptyListException e) {
                        System.out.println("Lỗi: " + e.getMessage());
                    }
                }
                case 0 -> {
                    System.out.println("Thoát chương trình.");
                    return;
                }
                default -> System.out.println("Chọn sai, vui lòng nhập lại!");
            }
        }
    }
}