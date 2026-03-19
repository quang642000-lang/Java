import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 1. Enum StudentType
enum StudentType {
    REGULAR,
    PART_TIME,
    INTERNATIONAL
}

// 2. Lớp Student
class Student {
    private String id;
    private String name;
    private StudentType type;

    // Constructor đầy đủ tham số
    public Student(String id, String name, StudentType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    // Getter / Setter
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

    public StudentType getType() {
        return type;
    }

    public void setType(StudentType type) {
        this.type = type;
    }

    // Override toString() để hiển thị thông tin sinh viên
    @Override
    public String toString() {
        return "Mã SV: " + id + ", Tên: " + name + ", Loại: " + type;
    }
}

// 3. Chương trình chính (Main)
public class bai1 {
    public static void main(String[] args) {
        // Tạo List<Student> gồm ít nhất 6 sinh viên
        List<Student> list = new ArrayList<>();
        list.add(new Student("SV01", "Nguyen Van A", StudentType.REGULAR));
        list.add(new Student("SV02", "Tran Thi B", StudentType.PART_TIME));
        list.add(new Student("SV03", "Le Van C", StudentType.INTERNATIONAL));
        list.add(new Student("SV04", "Pham Van D", StudentType.REGULAR));
        list.add(new Student("SV05", "Vu Thi E", StudentType.PART_TIME));
        list.add(new Student("SV06", "Hoang Van F", StudentType.INTERNATIONAL));

        // Hiển thị danh sách toàn bộ sinh viên
        System.out.println("--- DANH SACH TOAN BO SINH VIEN ---");
        for (Student sv : list) {
            System.out.println(sv.toString());
        }

        // Hiển thị danh sách sinh viên theo từng loại
        System.out.println("\n--- DANH SACH SINH VIEN THEO TUNG LOAI ---");
        for (StudentType type : StudentType.values()) {
            System.out.println("Loai: " + type);
            for (Student sv : list) {
                if (sv.getType() == type) {
                    System.out.println(sv.toString());
                }
            }
        }

        // Đếm số lượng sinh viên của mỗi loại
        System.out.println("\n--- SO LUONG SINH VIEN MOI LOAI ---");
        Map<StudentType, Integer> map = new HashMap<>();
        
        for (Student sv : list) {
            StudentType type = sv.getType();
            if (map.containsKey(type)) {
                map.put(type, map.get(type) + 1);
            } else {
                map.put(type, 1);
            }
        }

        for (Map.Entry<StudentType, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}