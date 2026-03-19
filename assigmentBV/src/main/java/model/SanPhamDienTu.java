package model;

import java.util.Scanner;

// ĐÁP ỨNG Y9: Lớp con kế thừa từ lớp cha SanPham bằng từ khóa "extends"
// Thể hiện tính KẾ THỪA (Inheritance)
public class SanPhamDienTu extends SanPham {

    // Thuộc tính riêng của sản phẩm điện tử
    private String baoHanh;

    public SanPhamDienTu() {
    }

    public SanPhamDienTu(String baoHanh, String maSP, String tenSP, double donGia, int soLuong) {
        super(maSP, tenSP, donGia, soLuong);
        this.baoHanh = baoHanh;
    }

    public String getBaoHanh() {
        return baoHanh;
    }

    public void setBaoHanh(String baoHanh) {
        this.baoHanh = baoHanh;
    }

    // Thể hiện tính ĐA HÌNH (Polymorphism): Ghi đè (Override) phương thức của lớp cha
    @Override
    public void nhap(Scanner sc) {
        // Từ khóa "super" dùng để gọi lại phương thức nhap() của lớp cha (tiết kiệm code)
        super.nhap(sc);

        // Sau đó nhập thêm thuộc tính riêng của lớp con
        System.out.print("Nhập thời gian bảo hành: ");
        this.baoHanh = sc.nextLine();
    }

    @Override
    public void xuat() {
        // Gọi hàm in thông tin cơ bản của lớp cha
        super.xuat();
        // In nối tiếp phần bảo hành trên cùng 1 dòng
        System.out.printf(" | Bảo hành: %s", baoHanh);
    }
}
