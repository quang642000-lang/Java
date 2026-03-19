/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author THIS PC
 */
public class Student {
    private String name;
    private StudentType type;
    private double gpa;

    public Student(String name, String cuong, StudentType type, double gpa) {
        this.name = name; this.type = type; this.gpa = gpa;
    }
    public String getName() { return name; }
    public StudentType getType() { return type; }
    public double getGpa() { return gpa; }
    @Override
    public String toString() { return name + " (" + type + ") - GPA: " + gpa; }
}