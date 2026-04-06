package view;

import service.AppController;
import model.*;

import java.time.LocalDate;
import java.util.Scanner;

public class AppView {
    private Scanner scanner;
    private AppController controller;

    public AppView() {
        this.scanner = new Scanner(System.in);
        this.controller = new AppController();
    }

    public void start() {
        controller.loadDataFromDB(); 
        while (true) {
            System.out.println("\n===== HỆ THỐNG QUẢN LÝ NHÀ TRỌ =====");
            System.out.println("1. Quản lý Người dùng");
            System.out.println("2. Quản lý Nhà trọ");
            System.out.println("3. Tìm kiếm nhà trọ");
            System.out.println("4. Sao lưu File");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": menuNguoiDung(); break;
                case "2": menuNhaTro(); break;
                case "3":
                    System.out.print("Nhập tên Quận: ");
                    controller.timKiemVaPhanTrang(scanner.nextLine());
                    break;
                case "4": controller.backupToFile(); break;
                case "0": System.exit(0);
                default: System.out.println("Nhập sai!");
            }
        }
    }

    private void menuNguoiDung() {
        while (true) {
            System.out.println("\n--- MENU NGƯỜI DÙNG ---");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm người dùng");
            System.out.println("3. Xóa người dùng");
            System.out.println("0. Quay lại");
            String c = scanner.nextLine();
            switch (c) {
                case "1": controller.hienThiDanhSachNguoiDung(); break;
                case "2":
                    try {
                        System.out.print("ID: "); String id = scanner.nextLine();
                        System.out.print("Tên: "); String ten = scanner.nextLine();
                        System.out.print("Giới tính: "); String gioiTinh = scanner.nextLine();
                        System.out.print("SĐT: "); String sdt = scanner.nextLine();
                        System.out.print("Địa chỉ: "); String diaChi = scanner.nextLine();
                        System.out.print("Quận: "); String quan = scanner.nextLine();
                        System.out.print("Email: "); String email = scanner.nextLine();
                        controller.themNguoiDung(new NguoiDung(id, ten, gioiTinh, sdt, diaChi, quan, email));
                    } catch (Exception e) { System.out.println("Lỗi: " + e.getMessage()); }
                    break;
                case "3":
                    System.out.print("ID cần xóa: ");
                    controller.xoaNguoiDung(scanner.nextLine());
                    break;
                case "0": return;
            }
        }
    }

    private void menuNhaTro() {
        while (true) {
            System.out.println("\n--- MENU NHÀ TRỌ ---");
            System.out.println("1. Xem chi tiết");
            System.out.println("2. Thêm nhà trọ");
            System.out.println("0. Quay lại");
            String c = scanner.nextLine();
            switch (c) {
                case "1": controller.xemNhaTroKemThongTinGhep(); break;
                case "2":
                    try {
                        System.out.print("ID Nhà trọ: "); String id = scanner.nextLine();
                        System.out.print("Loại (CH/NR/PT): "); 
                        LoaiNhaTro loai = LoaiNhaTro.fromMa(scanner.nextLine());
                        System.out.print("Diện tích: "); double dt = Double.parseDouble(scanner.nextLine());
                        System.out.print("Giá: "); double gia = Double.parseDouble(scanner.nextLine());
                        System.out.print("Quận: "); String quan = scanner.nextLine();
                        System.out.print("ID Người đăng: "); String idND = scanner.nextLine();
                        System.out.print("Trạng thái (1: Trống, 2: Đang ở, 3: Sửa): ");
                        TrangThaiNhaTro tt = TrangThaiNhaTro.fromLuaChon(scanner.nextLine());
                        controller.themNhaTro(new NhaTro(id, loai, dt, gia, "HN", quan, "Mô tả", tt, LocalDate.now(), idND));
                    } catch (Exception e) { System.out.println("Lỗi: " + e.getMessage()); }
                    break;
                case "0": return;
            }
        }
    }
}