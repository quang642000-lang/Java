/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package lab4;

/**
 *
 * @author PC
 */
public class Student {
    private String id;
    private String name;
    private double gpa;

    public Student(String id, String name, double gpa) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID không được rỗng!");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Tên không được rỗng!");
        }
        if (gpa < 0 || gpa > 4) {
            throw new IllegalArgumentException("GPA phải nằm trong khoảng 0 - 4!");
        }
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "Student{id='" + id + "', name='" + name + "', gpa=" + gpa + "}";
    }

    public void inThongTin() {
        System.out.println(this);
    }
}
