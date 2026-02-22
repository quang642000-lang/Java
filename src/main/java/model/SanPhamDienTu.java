package model;

import java.util.Scanner;

public class SanPhamDienTu extends SanPham {
    private String baoHanh;

    @Override
    public void nhap(Scanner sc) {
        super.nhap(sc);
        System.out.print("Nhập thời gian bảo hành: ");
        this.baoHanh = sc.nextLine();
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.printf(" | BH: %s\n", baoHanh);
    }
}
