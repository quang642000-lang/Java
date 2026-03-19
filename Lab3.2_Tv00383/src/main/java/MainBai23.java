/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author THIS PC
 */
import Model.Employee;
import Service.EmployeeService;
import java.util.*;

public class MainBai23 {
    public static void main(String[] args) {
        List<Employee> ds = Arrays.asList(
            new Employee("E1", "An", 16000000), new Employee("E2", "Bình", 12000000),
            new Employee("E3", "Anh", 20000000), new Employee("E4", "Cường", 8000000),
            new Employee("E5", "Dũng", 18000000), new Employee("E6", "Hoa", 15000000),
            new Employee("E7", "Lan", 14000000), new Employee("E8", "Mai", 25000000)
        );
        EmployeeService service = new EmployeeService();
        
        System.out.println("--- NV Lương Cao ---");
        service.FilterHighSalary(ds).forEach(System.out::println);
        System.out.println("\n--- Thống kê ---");
        service.CalculateStats(ds);
    }
}
