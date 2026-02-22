package model;

import java.util.Scanner;

public class SanPhamTieuDung extends SanPham {
    private String hanSuDung;
    private double giamGia;

    @Override
    public void nhap(Scanner sc) {
        super.nhap(sc);
        System.out.print("Nhập hạn sử dụng: ");
        this.hanSuDung = sc.nextLine();
        System.out.print("Nhập % giảm giá (VD nhập 10 là 10%): ");
        this.giamGia = Double.parseDouble(sc.nextLine()) / 100;
    }

    @Override
    public double getThanhTien() {
        double tienGoc = super.getThanhTien();
        return tienGoc - (tienGoc * giamGia);
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.printf(" | HSD: %-10s | Giảm: %.0f%%\n", hanSuDung, giamGia * 100);
    }
}
