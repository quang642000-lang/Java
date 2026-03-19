/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab4;

/**
 *
 * @author PC
 */
import java.util.ArrayList;

public class EmployeeService {
    private ArrayList<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee e) throws DuplicateEmployeeException, InvalidSalaryException {
        for (Employee emp : employees) {
            if (emp.getId().equals(e.getId())) {
                throw new DuplicateEmployeeException("ID nhân viên đã tồn tại!");
            }
        }
        if (e.getSalary() < 0) {
            throw new InvalidSalaryException("Lương không được âm!");
        }
        employees.add(e);
    }

    public Employee findById(String id) {
        for (Employee e : employees) {
            if (e.getId().equals(id)) return e;
        }
        return null;
    }

    public void showEmployees() {
        for (Employee e : employees) {
            e.inThongTin();
        }
    }
}

