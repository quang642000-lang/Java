package model;

import java.util.Scanner;

// Lớp cha SanPham. Thể hiện tính ĐÓNG GÓI (Encapsulation) bằng cách để các thuộc tính là private.
public class SanPham {

    // Các thuộc tính private: Chỉ có thể truy cập từ bên ngoài thông qua các hàm get/set
    private String maSP;
    private String tenSP;
    private double donGia;
    private int soLuong;

    // Hàm tạo (Constructor) không tham số
    public SanPham() {
    }

    // Hàm tạo (Constructor) có tham số
    public SanPham(String maSP, String tenSP, double donGia, int soLuong) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.soLuong = soLuong;
    }

    // Các hàm Getter và Setter dùng để lấy và gán giá trị cho các thuộc tính private
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

    // ĐÁP ỨNG Y1 & Y5: Nhập thông tin và bắt lỗi mã sản phẩm định dạng SPXXX
    public void nhap(Scanner sc) {
        // Vòng lặp do-while sẽ lặp đi lặp lại nếu người dùng nhập sai định dạng mã
        do {
            System.out.print("Nhập mã SP (Định dạng SPXXX): ");
            this.maSP = sc.nextLine();

            // Hàm matches() dùng biểu thức chính quy (Regex)
            // "SP\\d{3}": Bắt đầu bằng chữ "SP", theo sau là đúng 3 chữ số (\d{3})
            if (!this.maSP.matches("SP\\d{3}")) {
                System.out.println("Lỗi: Mã sản phẩm phải có dạng SPXXX (X là số)!");
            }
        } while (!this.maSP.matches("SP\\d{3}")); // Tiếp tục lặp nếu mã không khớp (false)

        System.out.print("Nhập tên SP: ");
        this.tenSP = sc.nextLine();

        System.out.print("Nhập đơn giá: ");
        // Ép kiểu Double.parseDouble thay vì dùng sc.nextDouble() để tránh lỗi trôi lệnh Enter
        this.donGia = Double.parseDouble(sc.nextLine());

        System.out.print("Nhập số lượng: ");
        this.soLuong = Integer.parseInt(sc.nextLine());
    }

    // ĐÁP ỨNG Y3: Phương thức tính thành tiền
    public double getThanhTien() {
        return this.soLuong * this.donGia;
    }

    // ĐÁP ỨNG Y2: Hiển thị thông tin sản phẩm
    public void xuat() {
        // printf dùng để in theo định dạng. %s là chuỗi, %.1f là số thập phân có 1 số sau dấu phẩy, %d là số nguyên
        System.out.printf("Mã: %s | Tên: %s | Giá: %.1f | SL: %d | Tiền: %.1f",
                maSP, tenSP, donGia, soLuong, getThanhTien());
    }
}
