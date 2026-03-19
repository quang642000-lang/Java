/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

/**
 *
 * @author THIS PC
 */
import Model.Employee;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeService {
    public List<Employee> FilterHighSalary(List<Employee> list) {
        return list.stream().filter(e -> e.getSalary() >= 15000000).collect(Collectors.toList());
    }

    public void CalculateStats(List<Employee> list) {
        double tong = list.stream().mapToDouble(Employee::getSalary).sum();
        double tb = list.stream().mapToDouble(Employee::getSalary).average().orElse(0);
        Employee max = list.stream().max(Comparator.comparingDouble(Employee::getSalary)).get();
        
        System.out.printf("Tổng: %,.0f | TB: %,.0f | Cao nhất: %s\n", tong, tb, max.getName());
    }
}