package service;

import model.SanPham;
import model.SanPhamDienTu;
import model.SanPhamTieuDung;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

// Lớp Service chứa toàn bộ các mảng dữ liệu và thuật toán xử lý
public class QuanLySanPham {

    // Khai báo một mảng động ArrayList kiểu cha (SanPham). 
    // Mảng này có thể chứa được tất cả các đối tượng của lớp cha và các lớp con (Nhờ tính Đa hình Upcasting)
    private ArrayList<SanPham> listSP = new ArrayList<>();

    // ĐÁP ỨNG Y1: Hàm điều hướng nhập liệu
    public void nhap(Scanner sc) {
        while (true) {
            System.out.println("\n--- NHẬP SẢN PHẨM ---");
            System.out.println("1. Sản phẩm điện tử");
            System.out.println("2. Sản phẩm tiêu dùng");
            System.out.print("Mời chọn loại (1 hoặc 2): ");

            int loai = Integer.parseInt(sc.nextLine());
            SanPham sp = null; // Khai báo đối tượng kiểu cha

            // Dựa vào lựa chọn, ta khởi tạo (new) đối tượng của lớp con tương ứng
            if (loai == 1) {
                sp = new SanPhamDienTu();
            } else if (loai == 2) {
                sp = new SanPhamTieuDung();
            } else {
                System.out.println("Chọn sai, vui lòng chọn lại!");
                continue;
            }

            // Gọi hàm nhap(). Java sẽ tự động biết biến 'sp' đang trỏ tới class con nào 
            // để gọi đúng hàm nhap() của class con đó (Đa hình lúc chạy - Runtime Polymorphism)
            sp.nhap(sc);

            // Thêm đối tượng vào danh sách
            listSP.add(sp);
            System.out.println("=> Đã thêm sản phẩm thành công!");

            System.out.print("Bạn có muốn nhập tiếp không? (Y/N): ");
            String tiep = sc.nextLine();
            if (tiep.equalsIgnoreCase("N")) { // equalsIgnoreCase so sánh chuỗi không phân biệt hoa thường
                break; // Thoát vòng lặp nhập
            }
        }
    }

    // ĐÁP ỨNG Y2: Xuất danh sách
    public void xuat() {
        // Kiểm tra xem mảng có rỗng không
        if (listSP.isEmpty()) {
            System.out.println("Danh sách trống!");
            return; // Thoát hàm
        }
        System.out.println("\n--- DANH SÁCH SẢN PHẨM ---");

        // Vòng lặp for-each: duyệt qua từng phần tử trong danh sách
        for (SanPham sp : listSP) {
            sp.xuat(); // Gọi hàm xuất (Cũng áp dụng tính đa hình tương tự hàm nhập)
            System.out.println(); // Xuống dòng
        }
    }

    // ĐÁP ỨNG Y6: Tìm kiếm sản phẩm
    public void timKiem(Scanner sc) {
        System.out.print("\nNhập mã sản phẩm cần tìm: ");
        String ma = sc.nextLine();

        // Dùng cờ (flag) để đánh dấu trạng thái tìm kiếm
        boolean timThay = false;

        for (SanPham sp : listSP) {
            // Lấy mã của từng SP trong danh sách ra so sánh với mã người dùng nhập
            if (sp.getMaSP().equalsIgnoreCase(ma)) {
                System.out.println("=> Thông tin sản phẩm tìm thấy:");
                sp.xuat();
                System.out.println();
                timThay = true; // Bật cờ = true vì đã tìm thấy
                break; // Thoát vòng lặp ngay để chạy cho nhanh
            }
        }

        // Nếu chạy hết vòng lặp mà cờ vẫn là false tức là không có
        if (!timThay) {
            System.out.println("Không tìm thấy sản phẩm có mã: " + ma);
        }
    }

    // ĐÁP ỨNG Y4: Cập nhật thông tin sản phẩm
    public void capNhat(Scanner sc) {
        System.out.print("\nNhập mã sản phẩm cần sửa: ");
        String ma = sc.nextLine();
        boolean timThay = false;

        // Tương tự tìm kiếm, ta phải tìm xem đối tượng đó nằm ở đâu
        for (SanPham sp : listSP) {
            if (sp.getMaSP().equalsIgnoreCase(ma)) {
                System.out.println("Đã tìm thấy! Mời nhập thông tin mới:");

                // Gọi các hàm Setter để chép đè giá trị mới vào thuộc tính private
                System.out.print("Nhập tên mới: ");
                sp.setTenSP(sc.nextLine());

                System.out.print("Nhập giá mới: ");
                sp.setDonGia(Double.parseDouble(sc.nextLine()));

                System.out.print("Nhập số lượng mới: ");
                sp.setSoLuong(Integer.parseInt(sc.nextLine()));

                System.out.println("=> Cập nhật thành công!");
                timThay = true;
                break;
            }
        }

        if (!timThay) {
            System.out.println("Không tìm thấy mã sản phẩm này!");
        }
    }

    // ĐÁP ỨNG Y7: Xóa sản phẩm
    public void xoa(Scanner sc) {
        System.out.print("\nNhập mã sản phẩm cần xóa: ");
        String ma = sc.nextLine();

        int viTri = -1; // Biến lưu vị trí index của sản phẩm trong mảng

        // Dùng vòng lặp for thường (có i) để lấy ra vị trí (index) cần xóa
        for (int i = 0; i < listSP.size(); i++) {
            if (listSP.get(i).getMaSP().equalsIgnoreCase(ma)) {
                viTri = i; // Ghi nhớ lại vị trí
                break;
            }
        }

        // Nếu vị trí khác -1 tức là có tìm thấy
        if (viTri != -1) {
            listSP.remove(viTri); // Gọi hàm remove() có sẵn của ArrayList
            System.out.println("=> Đã xóa sản phẩm thành công!");
        } else {
            System.out.println("Không tìm thấy sản phẩm để xóa!");
        }
    }

    // ĐÁP ỨNG Y8: Sắp xếp danh sách theo tên
    public void sapXep() {
        if (listSP.isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }

        // Collections.sort là hàm sắp xếp mảng có sẵn của Java
        // Vì SanPham là đối tượng phức tạp, ta phải tạo một Comparator để chỉ cho Java biết tiêu chí sắp xếp
        Collections.sort(listSP, new Comparator<SanPham>() {
            @Override
            public int compare(SanPham sp1, SanPham sp2) {
                // compareToIgnoreCase giúp so sánh 2 chuỗi theo bảng chữ cái A-Z, không phân biệt hoa thường
                return sp1.getTenSP().compareToIgnoreCase(sp2.getTenSP());
            }
        });

        System.out.println("\n=> Đã sắp xếp danh sách theo tên A-Z!");
        xuat(); // Sắp xong thì in ra cho người dùng xem
    }

    // Tiện ích thêm: Minh họa tính kế thừa
    public void minhHoaKeThua() {
        System.out.println("\n--- CÁC SẢN PHẨM ĐIỆN TỬ ---");
        for (SanPham sp : listSP) {
            // Toán tử instanceof dùng để kiểm tra xem biến 'sp' có thực sự thuộc class SanPhamDienTu hay không
            if (sp instanceof SanPhamDienTu) {
                sp.xuat();
                System.out.println();
            }
        }
    }
}
