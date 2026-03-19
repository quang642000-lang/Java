package View;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author THIS PC
 */
import Model.Student;
import Model.StudentType;
import Service.StudentService;
import java.util.*;

public class Mainbai45 {
    public static void main(String[] args) {
        List<Student> ds = Arrays.asList(
            new Student("S01", "Anh", StudentType.INTERNATIONAL, 3.5),
            new Student("S02", "Linh", StudentType.REGULAR, 3.8),
            new Student("S03", "Truong", StudentType.PART_TIME, 2.5),
            new Student("S04", "Dung", StudentType.INTERNATIONAL, 3.2),
            new Student("S05", "Khoa", StudentType.REGULAR, 3.9),
            new Student("S06", "Hai", StudentType.PART_TIME, 3.0),
            new Student("S07", "Quang", StudentType.INTERNATIONAL, 2.8),
            new Student("S08", "Thuong", StudentType.REGULAR, 3.1),
            new Student("S09", "Huy", StudentType.PART_TIME, 3.6),
            new Student("S10", "Xo hin", StudentType.INTERNATIONAL, 3.7)
        );

        StudentService service = new StudentService();

        System.out.println("---BÀI 4: LỌC & SẮP XẾP ---");
        System.out.println("SV International GPA >= 3.2:");
        service.filterInternational(ds).forEach(System.out::println);

        System.out.println("\nTop 3 GPA cao nhất:");
        service.getTop3Gpa(ds).forEach(System.out::println);

        System.out.println("\n---BÀI 5: THỐNG KÊ ---");
        System.out.println("Số lượng SV theo loại: " + service.countByType(ds));
        
        Map<StudentType, Double> avgMap = service.averageGpaByType(ds);
        System.out.println("GPA trung bình theo loại: " + avgMap);

        // Tìm loại có GPA cao nhất
        StudentType maxType = avgMap.entrySet().stream()
                                    .max(Map.Entry.comparingByValue())
                                    .get().getKey();
        System.out.println("Loại SV có GPA trung bình cao nhất: " + maxType);
    }
}