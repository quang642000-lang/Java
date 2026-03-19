/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab4;

/**
 *
 * @author PC
 */
public class Employee {
    private String id;
    private String name;
    private double salary;

    public Employee(String id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public String getId() { return id; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return "Employee{id='" + id + "', name='" + name + "', salary=" + salary + "}";
    }

    public void inThongTin() {
        System.out.println(this);
    }
}

