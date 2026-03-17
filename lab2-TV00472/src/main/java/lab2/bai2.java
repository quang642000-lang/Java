import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 1. Enum ProductCategory
enum ProductCategory {
    FOOD,
    ELECTRONIC,
    CLOTHING
}

// 2. Lớp Product
class Product {
    private String id;
    private String name;
    private double price;
    private ProductCategory category;

    // Constructor đầy đủ
    public Product(String id, String name, double price, ProductCategory category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    // Override toString()
    @Override
    public String toString() {
        return "Mã: " + id + ", Tên: " + name + ", Giá: " + price + ", Loại: " + category;
    }
}

// 3. Chương trình chính (Main)
public class bai2 {
    public static void main(String[] args) {
        // Bước 1: Khởi tạo dữ liệu
        // Tạo danh sách List<Product> gồm ít nhất 8 sản phẩm
        List<Product> list = new ArrayList<>();
        list.add(new Product("SP01", "Banh mi", 15000, ProductCategory.FOOD));
        list.add(new Product("SP02", "Sua", 10000, ProductCategory.FOOD));
        list.add(new Product("SP03", "Laptop", 15000000, ProductCategory.ELECTRONIC));
        list.add(new Product("SP04", "Dien thoai", 10000000, ProductCategory.ELECTRONIC));
        list.add(new Product("SP05", "Ao thun", 150000, ProductCategory.CLOTHING));
        list.add(new Product("SP06", "Quan jean", 250000, ProductCategory.CLOTHING));
        list.add(new Product("SP07", "Keo", 5000, ProductCategory.FOOD));
        list.add(new Product("SP08", "Tai nghe", 500000, ProductCategory.ELECTRONIC));

        // Danh sách sản phẩm
        System.out.println("--- DANH SACH SAN PHAM ---");
        for (Product p : list) {
            System.out.println(p.toString());
        }

        // Bước 2: Thống kê bằng Map
        // Sử dụng Map<ProductCategory, Integer> để đếm số lượng sản phẩm theo từng loại
        Map<ProductCategory, Integer> map = new HashMap<>();
        for (Product p : list) {
            ProductCategory cat = p.getCategory();
            if (map.containsKey(cat)) {
                map.put(cat, map.get(cat) + 1);
            } else {
                map.put(cat, 1);
            }
        }

        // 4. Kết quả cần hiển thị
        // Số lượng sản phẩm của mỗi loại (Duyệt Map bằng entrySet())
        System.out.println("\n--- THONG KE SO LUONG SAN PHAM ---");
        for (Map.Entry<ProductCategory, Integer> entry : map.entrySet()) {
            System.out.println("Loai: " + entry.getKey() + " - So luong: " + entry.getValue());
        }
    }
}