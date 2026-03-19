/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

/**
 *
 * @author THIS PC
 */
import Model.Student;
import Model.StudentType;
import java.util.*;
import java.util.stream.Collectors;

public class StudentService {

    public List<Student> filterInternational(List<Student> list) {
        return list.stream()
                   .filter(s -> s.getType() == StudentType.INTERNATIONAL)
                   .filter(s -> s.getGpa() >= 3.2)
                   .collect(Collectors.toList());
    }


    public List<Student> getTop3Gpa(List<Student> list) {
        return list.stream()
                   .sorted(Comparator.comparingDouble(Student::getGpa).reversed())
                   .limit(3)
                   .collect(Collectors.toList());
    }


    public Map<StudentType, Long> countByType(List<Student> list) {
        return list.stream()
                   .collect(Collectors.groupingBy(Student::getType, Collectors.counting()));
    }


    public Map<StudentType, Double> averageGpaByType(List<Student> list) {
        return list.stream()
                   .collect(Collectors.groupingBy(Student::getType, 
                            Collectors.averagingDouble(Student::getGpa)));
    }
}