/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import java.util.List;

/**
 *
 * @author THIS PC
 */

public class Service {
    // In danh sách: dùng forEach thay cho vòng for [cite: 15]
    public void InDanhSach(List<String> ds) {
        ds.forEach(ten -> System.out.println("Tên: " + ten));
    }

    // Lọc tên dài > 5 ký tự [cite: 16]
    public void LocTenDai(List<String> ds) {
        ds.stream()
          .filter(ten -> ten.length() > 5) 
          .forEach(System.out::println);
    }

    // Sắp xếp A-Z [cite: 17]
    public void SapXepAZ(List<String> ds) {
        ds.stream().sorted().forEach(System.out::println);
    }
}
