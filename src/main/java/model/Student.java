package model;

import java.io.Serializable;

// Nhớ implements Serializable nha mọi người, không có là lúc ghi file bị lỗi NotSerializableException liền
public class Student implements Serializable {
    private String id;
    private String name;
    private String password; 

    // Constructor rỗng (Bắt buộc phải có theo chuẩn Java)
    public Student() {
    }

    // Constructor có tham số để tạo data cho lẹ
    public Student(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    // --- Khúc này Insert Code -> Getter & Setter tự động cho nhanh nha ---
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Ghi đè hàm toString để tí in ra màn hình cho gọn, đỡ phải .get() từng thuộc tính
    @Override
    public String toString() {
        return "Ma SV: " + id + " | Ten: " + name + " | Pass: " + password;
    }
}
