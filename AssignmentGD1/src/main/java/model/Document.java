package model;

import exception.DuplicateIdException;
import java.util.ArrayList;
import java.util.List;

public class Document {
    // Các thuộc tính cơ bản của tài liệu
    private String id;
    private String title;
    private String author;
    private String category;
    
    // ĐÁP ỨNG YÊU CẦU: "Quan hệ 1-N bằng List" (1 Tài liệu có N Bản sao)
    private List<Copy> copies;

    // Constructor (Hàm khởi tạo)
    public Document(String id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        // Khởi tạo danh sách ArrayList rỗng để chứa bản sao
        this.copies = new ArrayList<>();
    }

    // Các hàm Getter / Setter cơ bản
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public List<Copy> getCopies() { return copies; }

    // ==========================================
    // CÁC HÀM XỬ LÝ BẢN SAO BÊN TRONG TÀI LIỆU
    // ==========================================

    // Thêm bản sao (Có ném ra lỗi tự định nghĩa nếu trùng mã)
    public void addCopy(Copy copy) throws DuplicateIdException {
        // Dùng vòng for cơ bản duyệt qua từng bản sao để kiểm tra trùng lặp
        for (int i = 0; i < copies.size(); i++) {
            Copy c = copies.get(i);
            // So sánh mã không phân biệt hoa thường (equalsIgnoreCase)
            if (c.getId().equalsIgnoreCase(copy.getId())) {
                // ĐÁP ỨNG YÊU CẦU: "Kiểm tra trùng mã bằng Exception"
                throw new DuplicateIdException("Mã bản sao '" + copy.getId() + "' đã tồn tại!");
            }
        }
        // Nếu không trùng thì thêm vào danh sách
        copies.add(copy);
    }

    // Cập nhật trạng thái của 1 bản sao dựa vào mã
    public boolean updateCopyStatus(String copyId, CopyStatus newStatus) {
        for (int i = 0; i < copies.size(); i++) {
            if (copies.get(i).getId().equalsIgnoreCase(copyId)) {
                // Đổi trạng thái cũ thành trạng thái mới
                copies.get(i).setStatus(newStatus);
                return true; // Trả về true báo hiệu cập nhật thành công
            }
        }
        return false; // Không tìm thấy mã
    }

    // Xóa bản sao dựa vào mã
    public boolean removeCopy(String copyId) {
        for (int i = 0; i < copies.size(); i++) {
            if (copies.get(i).getId().equalsIgnoreCase(copyId)) {
                // Dùng hàm remove của ArrayList cơ bản (Không dùng code nâng cao removeIf)
                copies.remove(i);
                return true; // Đã xóa thành công
            }
        }
        return false; // Không tìm thấy mã để xóa
    }

    // Hàm xuất thông tin đối tượng ra chuỗi để in ra màn hình
    @Override
    public String toString() {
        return "Tài liệu [Mã TL: " + id + ", Tiêu đề: " + title + 
               ", Tác giả: " + author + ", Thể loại: " + category + 
               ", Số bản sao đang có: " + copies.size() + "]";
    }
}