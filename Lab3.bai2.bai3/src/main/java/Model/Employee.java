/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author THIS PC
 */
public class Employee {
    private String id, name;
    private double salary;

    public Employee(String id, String name, double salary) {
        this.id = id; this.name = name;
        this.salary = salary;
    }
    // Getter để Stream có thể lấy dữ liệu
    public String getName() 
    { return name; }
    public double getSalary() 
    { return salary; }
    @Override
    public String toString() { return id + " | " + name + " | " + salary; }
}