package main;

import service.LibraryService;
import view.LibraryView;

// Nơi ghép nối 3 thành phần MSV lại với nhau để chạy
public class Main {
    public static void main(String[] args) {
        // 1. Tạo View (Chữ V)
        LibraryView view = new LibraryView();
        
        // 2. Tạo Service và truyền View vào (Chữ S)
        LibraryService service = new LibraryService(view);
        
        // 3. Bắt đầu vòng đời ứng dụng
        service.start();
    }
}