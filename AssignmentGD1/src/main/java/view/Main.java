package view;

import model.NguoiDung;
import model.NhaTro;
import model.LoaiNha;
import service.QuanLyHeThong;
import exception.DuplicateIdException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuanLyHeThong quanLy = new QuanLyHeThong();   

        int luaChon;

        do {
            System.out.println("\n====== HỆ THỐNG QUẢN LÝ NHÀ TRỌ (GIAI ĐOẠN 1) ======");
            System.out.println("--- Quản lý Người Dùng ---");
            System.out.println("1. Thêm người dùng (Thành viên)");
            System.out.println("2. Sửa thông tin người dùng");
            System.out.println("3. Xóa người dùng");
            System.out.println("4. Xem danh sách người dùng");
            System.out.println("--- Quản lý Nhà Trọ ---");
            System.out.println("5. Đăng tin nhà trọ (Thêm)");
            System.out.println("6. Sửa giá nhà trọ");
            System.out.println("7. Xóa nhà trọ");
            System.out.println("8. Xem danh sách tin nhà trọ");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            luaChon = Integer.parseInt(scanner.nextLine());

            try {
                switch (luaChon) {
                    case 1:
                        System.out.print("Mã ND: "); String maND = scanner.nextLine();
                        System.out.print("Tên: "); String ten = scanner.nextLine();
                        System.out.print("Giới tính (Nam/Nữ): "); String gt = scanner.nextLine();
                        System.out.print("SĐT: "); String sdt = scanner.nextLine();
                        System.out.print("Địa chỉ (Số nhà, đường...): "); String dcND = scanner.nextLine();
                        System.out.print("Quận: "); String quanND = scanner.nextLine();
                        System.out.print("Email: "); String email = scanner.nextLine();
                        
                        quanLy.themNguoiDung(new NguoiDung(maND, ten, gt, sdt, dcND, quanND, email));
                        break;
                        
                    case 2:
                        System.out.print("Nhập mã ND cần sửa: "); String maSua = scanner.nextLine();
                        System.out.print("Tên mới: "); String tenMoi = scanner.nextLine();
                        System.out.print("SĐT mới: "); String sdtMoi = scanner.nextLine();
                        quanLy.suaNguoiDung(maSua, tenMoi, sdtMoi);
                        break;
                        
                    case 3:
                        System.out.print("Nhập mã ND cần xóa: "); String maXoa = scanner.nextLine();
                        quanLy.xoaNguoiDung(maXoa);
                        break;
                        
                    case 4:
                        quanLy.hienThiNguoiDung();
                        break;
                        
                    case 5:
                        System.out.print("Mã phòng: "); String maNT = scanner.nextLine();
                        System.out.println("Loại nhà (1-Chung cư, 2-Nhà riêng, 3-Phòng trọ): ");
                        int chonLoai = Integer.parseInt(scanner.nextLine());
                        LoaiNha loai = (chonLoai == 1) ? LoaiNha.CHUNG_CU : (chonLoai == 2 ? LoaiNha.NHA_RIENG : LoaiNha.PHONG_TRO);
                        
                        System.out.print("Diện tích (m2): "); double dt = Double.parseDouble(scanner.nextLine());
                        System.out.print("Giá phòng (VND): "); double gia = Double.parseDouble(scanner.nextLine());
                        System.out.print("Địa chỉ: "); String dcNT = scanner.nextLine();
                        System.out.print("Quận: "); String quanNT = scanner.nextLine();
                        System.out.print("Mô tả: "); String moTa = scanner.nextLine();
                        System.out.print("Ngày đăng (dd/mm/yyyy): "); String ngayDang = scanner.nextLine();
                        
                        System.out.print("Mã Người dùng (Người đăng tin/liên hệ): "); String maNguoiLienHe = scanner.nextLine();
                        NguoiDung ngLienHe = quanLy.timNguoiDung(maNguoiLienHe);
                        
                        if (ngLienHe == null) {
                            System.out.println("-> Lỗi: Mã người dùng không tồn tại. Vui lòng tạo người dùng trước.");
                        } else {
                            quanLy.dangTinNhaTro(new NhaTro(maNT, loai, dt, gia, dcNT, quanNT, moTa, ngayDang, ngLienHe));
                        }
                        break;
                        
                    case 6:
                        System.out.print("Nhập mã nhà trọ cần sửa: "); String maNTSua = scanner.nextLine();
                        System.out.print("Giá phòng mới (VND): "); double giaMoi = Double.parseDouble(scanner.nextLine());
                        quanLy.suaNhaTro(maNTSua, giaMoi);
                        break;
                        
                    case 7:
                        System.out.print("Nhập mã nhà trọ cần xóa: "); String maNTXoa = scanner.nextLine();
                        quanLy.xoaNhaTro(maNTXoa);
                        break;
                        
                    case 8:
                        quanLy.hienThiNhaTro();
                        break;
                        
                    case 0:
                        System.out.println("Thoát chương trình.");
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }
            } catch (DuplicateIdException e) {
                System.err.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("-> Lỗi định dạng nhập liệu, vui lòng nhập số!");
            }
            
        } while (luaChon != 0);
        
        scanner.close();
    }
}