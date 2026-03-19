/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

/**
 *
 * @author THIS PC
 */
import Service.Service;
import java.util.Arrays;
import java.util.List;

public class MainBai1 {
    public static void main(String[] args) {
        // Tạo danh sách 10 tên [cite: 14]
        List<String> names = Arrays.asList("Anh", "Truong", "Quang", "Khoa", "Tuấn", "Hoàng", "Huy", "Phương", "Khải", "Minh");
        
        Service sv= new Service();
        System.out.println("--- Danh sách tên dài > 5 ---");
        sv.LocTenDai(names);
    }
}