package model;

import java.util.Scanner;

public class SanPham {
    private String maSP;
    private String tenSP;
    private double donGia;
    private int soLuong;

    public SanPham() {
    }

    public SanPham(String maSP, String tenSP, double donGia, int soLuong) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.soLuong = soLuong;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void nhap(Scanner sc) {
        do {
            System.out.print("Nhập mã SP: ");
            this.maSP = sc.nextLine();
            if (!this.maSP.matches("SP\\d{3}")) {
                System.out.println("Mã sản phẩm không đúng định dạng (SPxxx).");
            }
        } while (!this.maSP.matches("SP\\d{3}"));

        System.out.print("Nhập tên SP: ");
        this.tenSP = sc.nextLine();
        
        System.out.print("Nhập đơn giá: ");
        this.donGia = Double.parseDouble(sc.nextLine());
        
        System.out.print("Nhập số lượng: ");
        this.soLuong = Integer.parseInt(sc.nextLine());
    }

    public double getThanhTien() {
        return soLuong * donGia;
    }

    public void xuat() {
        System.out.printf("Mã: %-8s | Tên: %-15s | Giá: %-10.1f | SL: %-4d | Tiền: %-12.1f", 
                          maSP, tenSP, donGia, soLuong, getThanhTien());
    }
    
    public void capNhat(Scanner sc) {
        System.out.print("Nhập tên mới: ");
        this.tenSP = sc.nextLine();
        System.out.print("Nhập giá mới: ");
        this.donGia = Double.parseDouble(sc.nextLine());
        System.out.print("Nhập số lượng mới: ");
        this.soLuong = Integer.parseInt(sc.nextLine());
    }
}
