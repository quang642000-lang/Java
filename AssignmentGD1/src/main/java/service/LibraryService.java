package service;

import exception.DuplicateIdException;
import model.Copy;
import model.CopyStatus;
import model.Document;
import view.LibraryView;

import java.util.ArrayList;
import java.util.List;

// Chữ C trong MVC (Controller) - Nơi chứa tư duy và thuật toán của chương trình
public class LibraryService {
    // ĐÁP ỨNG YÊU CẦU: "Sử dụng Collection quản lý dữ liệu"
    // Dùng ArrayList rất cơ bản, không dùng code nâng cao
    private List<Document> documentList;
    
    // Gọi lớp View để in ra màn hình và lấy chữ người dùng nhập
    private LibraryView view;

    // Constructor khởi tạo
    public LibraryService(LibraryView view) {
        this.documentList = new ArrayList<>();
        this.view = view;
    }

    // Hàm Start: Chạy vòng lặp vô tận cho đến khi người dùng chọn số 0
    public void start() {
        int choice = -1;
        do {
            // Lấy lựa chọn từ menu của View
            choice = view.showMainMenu(); 
            
            // Switch-case cơ bản để điều hướng chức năng
            switch (choice) {
                case 1: addDocument(); break;
                case 2: view.displayDocuments(documentList); break;
                case 3: updateDocument(); break;
                case 4: deleteDocument(); break;
                case 5: manageCopies(); break;
                case 0: view.printMessage("Đã thoát chương trình!"); break;
                default: view.printMessage("Lựa chọn không hợp lệ!");
            }
        } while (choice != 0);
    }

    // ==========================================
    // LOGIC CHỨC NĂNG: QUẢN LÝ TÀI LIỆU (CRUD)
    // ==========================================

    private void addDocument() {
        // 1. Lấy thông tin người dùng gõ từ bàn phím thông qua View
        String id = view.getInput("Nhập mã tài liệu: ");
        String title = view.getInput("Nhập tiêu đề: ");
        String author = view.getInput("Nhập tác giả: ");
        String category = view.getInput("Nhập thể loại: ");

        // 2. Thuật toán kiểm tra trùng lặp (Code cơ bản bằng vòng lặp for)
        for (int i = 0; i < documentList.size(); i++) {
            if (documentList.get(i).getId().equalsIgnoreCase(id)) {
                view.printMessage("LỖI: Mã tài liệu '" + id + "' đã tồn tại!");
                return; // Kết thúc hàm luôn, không thêm mới nữa
            }
        }
        
        // 3. Nếu không trùng, tạo đối tượng mới và nạp vào danh sách
        documentList.add(new Document(id, title, author, category));
        view.printMessage("Thêm tài liệu thành công!");
    }

    // Hàm hỗ trợ: Tìm kiếm tài liệu theo mã ID (Tái sử dụng nhiều lần)
    private Document findDocument(String id) {
        for (int i = 0; i < documentList.size(); i++) {
            if (documentList.get(i).getId().equalsIgnoreCase(id)) {
                return documentList.get(i);
            }
        }
        return null; // Trả về null nếu tìm hết mà không thấy
    }

    private void updateDocument() {
        String id = view.getInput("Nhập mã tài liệu cần sửa: ");
        Document doc = findDocument(id); // Gọi hàm hỗ trợ ở trên
        
        if (doc == null) {
            view.printMessage("Không tìm thấy tài liệu với mã này!");
            return;
        }

        // Cập nhật bằng các hàm Setter
        doc.setTitle(view.getInput("Nhập tiêu đề mới: "));
        doc.setAuthor(view.getInput("Nhập tác giả mới: "));
        doc.setCategory(view.getInput("Nhập thể loại mới: "));
        view.printMessage("Cập nhật thành công!");
    }

    private void deleteDocument() {
        String id = view.getInput("Nhập mã tài liệu cần xóa: ");
        for (int i = 0; i < documentList.size(); i++) {
            if (documentList.get(i).getId().equalsIgnoreCase(id)) {
                documentList.remove(i); // Xóa theo vị trí index
                view.printMessage("Xóa thành công!");
                return;
            }
        }
        view.printMessage("Không tìm thấy tài liệu!");
    }

    // ==========================================
    // LOGIC CHỨC NĂNG: QUẢN LÝ BẢN SAO BÊN TRONG 1 TÀI LIỆU
    // ==========================================
    private void manageCopies() {
        String docId = view.getInput("Nhập mã tài liệu muốn quản lý bản sao: ");
        Document doc = findDocument(docId);
        
        if (doc == null) {
            view.printMessage("Không tìm thấy tài liệu!");
            return;
        }

        int choice = -1;
        do {
            // Hiện menu con chuyên dành cho quản lý bản sao
            choice = view.showCopyMenu(doc.getTitle());
            switch (choice) {
                case 1: // Thêm Bản sao
                    String copyId = view.getInput("Nhập mã bản sao: ");
                    CopyStatus status = view.selectStatusUI();
                    try {
                        // Gọi hàm addCopy của Document. Hàm này có thể ném ra Exception
                        doc.addCopy(new Copy(copyId, status));
                        view.printMessage("Thêm bản sao thành công!");
                    } catch (DuplicateIdException e) {
                        // Bắt lỗi trùng mã bằng Exception (Đúng yêu cầu đề bài)
                        view.printMessage("LỖI: " + e.getMessage());
                    }
                    break;
                case 2: // Xem danh sách
                    view.displayCopies(doc.getCopies());
                    break;
                case 3: // Sửa trạng thái
                    String updateId = view.getInput("Nhập mã bản sao cần cập nhật: ");
                    CopyStatus newStatus = view.selectStatusUI();
                    if (doc.updateCopyStatus(updateId, newStatus)) {
                        view.printMessage("Cập nhật thành công!");
                    } else {
                        view.printMessage("Không tìm thấy mã bản sao này!");
                    }
                    break;
                case 4: // Xóa bản sao
                    String delId = view.getInput("Nhập mã bản sao cần xóa: ");
                    if (doc.removeCopy(delId)) {
                        view.printMessage("Xóa bản sao thành công!");
                    } else {
                        view.printMessage("Không tìm thấy mã bản sao này!");
                    }
                    break;
                case 0:
                    break;
                default:
                    view.printMessage("Lựa chọn không hợp lệ!");
            }
        } while (choice != 0);
    }
}