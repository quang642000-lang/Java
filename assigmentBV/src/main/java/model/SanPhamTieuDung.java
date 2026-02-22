package model;

import java.util.Scanner;

// ĐÁP ỨNG Y9: Lớp con Sản Phẩm Tiêu Dùng kế thừa Sản Phẩm
public class SanPhamTieuDung extends SanPham {

    // Thuộc tính đặc thù
    private String hanSuDung;
    private double giamGia;

    public SanPhamTieuDung() {
    }

    public SanPhamTieuDung(String hanSuDung, double giamGia, String maSP, String tenSP, double donGia, int soLuong) {
        super(maSP, tenSP, donGia, soLuong);
        this.hanSuDung = hanSuDung;
        this.giamGia = giamGia;
    }

    public String getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(String hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    @Override
    public void nhap(Scanner sc) {
        super.nhap(sc); // Tái sử dụng code lớp cha
        System.out.print("Nhập hạn sử dụng: ");
        this.hanSuDung = sc.nextLine();
        System.out.print("Nhập % giảm giá (VD: 10 là 10%): ");
        this.giamGia = Double.parseDouble(sc.nextLine());
    }

    // Ghi đè phương thức tính tiền vì logic tính tiền của SP Tiêu Dùng khác với SP thường
    @Override
    public double getThanhTien() {
        // Tính tiền gốc từ công thức của lớp cha
        double tienGoc = super.getThanhTien();
        // Áp dụng trừ đi % giảm giá
        return tienGoc - (tienGoc * giamGia / 100);
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.printf(" | HSD: %s | Giảm: %.1f%%", hanSuDung, giamGia);
    }
}
