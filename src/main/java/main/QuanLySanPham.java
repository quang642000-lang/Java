package main;

import model.SanPham;
import model.SanPhamDienTu;
import model.SanPhamTieuDung;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class QuanLySanPham {
    static ArrayList<SanPham> list = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n========== MENU ==========");
            System.out.println("1. Nhập danh sách sản phẩm");
            System.out.println("2. Xuất danh sách sản phẩm");
            System.out.println("3. Tìm kiếm sản phẩm theo mã");
            System.out.println("4. Cập nhật thông tin sản phẩm");
            System.out.println("5. Xóa sản phẩm theo mã");
            System.out.println("6. Sắp xếp theo tên");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            
            int chon;
            try {
                chon = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                chon = -1;
            }

            switch (chon) {
                case 1: nhap(); break;
                case 2: xuat(); break;
                case 3: timKiem(); break;
                case 4: capNhat(); break;
                case 5: xoa(); break;
                case 6: sapXep(); break;
                case 0: 
                    System.exit(0);
                default: System.out.println("Chọn sai, vui lòng chọn lại!");
            }
        }
    }

    public static void nhap() {
        System.out.print("Nhập số lượng sản phẩm: ");
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            System.out.println("Sản phẩm thứ " + (i + 1) + ":");
            System.out.print("Loại (1: Điện tử, 2: Tiêu dùng): ");
            int loai = Integer.parseInt(sc.nextLine());

            SanPham sp;
            if (loai == 1) {
                sp = new SanPhamDienTu();
            } else {
                sp = new SanPhamTieuDung();
            }
            
            sp.nhap(sc);
            list.add(sp);
        }
    }

    public static void xuat() {
        if (list.isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }
        for (SanPham sp : list) {
            sp.xuat();
        }
    }

    public static void timKiem() {
        System.out.print("Nhập mã cần tìm: ");
        String ma = sc.nextLine();
        boolean found = false;
        
        for (SanPham sp : list) {
            if (sp.getMaSP().equalsIgnoreCase(ma)) {
                sp.xuat();
                found = true;
                break;
            }
        }
        if (!found) System.out.println("Không tìm thấy!");
    }

    public static void capNhat() {
        System.out.print("Nhập mã SP cần sửa: ");
        String ma = sc.nextLine();
        boolean found = false;
        
        for (SanPham sp : list) {
            if (sp.getMaSP().equalsIgnoreCase(ma)) {
                sp.capNhat(sc);
                System.out.println("Cập nhật thành công!");
                found = true;
                break;
            }
        }
        if (!found) System.out.println("Không tìm thấy!");
    }

    public static void xoa() {
        System.out.print("Nhập mã SP cần xóa: ");
        String ma = sc.nextLine();
        SanPham spDelete = null;
        
        for (SanPham sp : list) {
            if (sp.getMaSP().equalsIgnoreCase(ma)) {
                spDelete = sp;
                break;
            }
        }
        
        if (spDelete != null) {
            list.remove(spDelete);
            System.out.println("Đã xóa thành công.");
        } else {
            System.out.println("Không tìm thấy để xóa.");
        }
    }

    public static void sapXep() {
        Comparator<SanPham> comp = new Comparator<SanPham>() {
            @Override
            public int compare(SanPham o1, SanPham o2) {
                return o1.getTenSP().compareTo(o2.getTenSP());
            }
        };
        Collections.sort(list, comp);
        System.out.println("Đã sắp xếp xong.");
    }
}