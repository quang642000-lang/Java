package model;
import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L; 
    
    private String id;
    private String name;
    private double gpa;

    // Constructor không tham số
    public Student() {
    }

    // Constructor có tham số
    public Student(String id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    // Getters và Setters
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

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ID='" + id + '\'' +
                ", Name='" + name + '\'' +
                ", GPA=" + gpa +
                '}';
    }
}