package view;

import model.DanhGia;
import model.LoaiNha;
import model.NhaTro;
import model.NgoaiLeTrungMa;
import model.ThaiDoDanhGia;
import service.NhaTroService;

import java.util.List;
import java.util.Scanner;

// Tầng View (Xử lý màn hình Console, nhận Input và gọi Service)
public class NhaTroView {
    private Scanner scanner;
    private NhaTroService service;

    public NhaTroView() {
        scanner = new Scanner(System.in);
        service = new NhaTroService();
    }

    // Tích hợp trực tiếp hàm Main vào View để chạy chương trình
    public static void main(String[] args) {
        NhaTroView appView = new NhaTroView();
        appView.hienThiMenu();
    }

    public void hienThiMenu() {
        int chon = -1;
        do {
            System.out.println("\n===== QUẢN LÝ NHÀ TRỌ (MÔ HÌNH MSV) =====");
            System.out.println("1. Đăng tin nhà trọ mới");
            System.out.println("2. Sửa thông tin nhà trọ (Giá, Mô tả)");
            System.out.println("3. Xóa tin nhà trọ");
            System.out.println("4. Xem danh sách nhà trọ");
            System.out.println("------------------------------------------");
            System.out.println("5. Thêm đánh giá (Bình luận) cho nhà trọ");
            System.out.println("6. Sửa thái độ đánh giá (Like/Dislike)");
            System.out.println("7. Xóa đánh giá");
            System.out.println("8. Xem danh sách đánh giá của một nhà trọ");
            System.out.println("------------------------------------------");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            
            try {
                chon = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Vui lòng nhập một số nguyên!");
                chon = -1;
                continue; // Bỏ qua vòng lặp này và bắt đầu lại
            }

            switch (chon) {
                case 1: uiThemNhaTro(); break;
                case 2: uiSuaNhaTro(); break;
                case 3: uiXoaNhaTro(); break;
                case 4: uiHienThiTatCaNhaTro(); break;
                case 5: uiThemDanhGia(); break;
                case 6: uiSuaDanhGia(); break;
                case 7: uiXoaDanhGia(); break;
                case 8: uiXemDanhGia(); break;
                case 0: System.out.println("Đã thoát chương trình."); break;
                default: System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại!");
            }
        } while (chon != 0);
    }

    private void uiThemNhaTro() {
        try {
            System.out.print("Nhập mã nhà trọ: ");
            String maNT = scanner.nextLine();
            
            LoaiNha loaiNha = nhapLoaiNha(); 
            
            System.out.print("Nhập diện tích (m2): ");
            double dienTich = Double.parseDouble(scanner.nextLine());
            System.out.print("Nhập giá phòng: ");
            double giaPhong = Double.parseDouble(scanner.nextLine());

            System.out.print("Nhập địa chỉ: ");
            String diaChi = scanner.nextLine();
            System.out.print("Nhập quận: ");
            String quan = scanner.nextLine();
            System.out.print("Nhập mô tả: ");
            String moTa = scanner.nextLine();
            System.out.print("Nhập ngày đăng: ");
            String ngayDang = scanner.nextLine();
            System.out.print("Nhập mã người liên hệ (Chủ nhà): ");
            String maNguoiLienHe = scanner.nextLine();

            NhaTro nt = new NhaTro(maNT, loaiNha, dienTich, giaPhong, diaChi, quan, moTa, ngayDang, maNguoiLienHe);
            service.themNhaTro(nt); // Gửi xuống tầng Service
            System.out.println("Đăng tin nhà trọ thành công!");
        } catch (NgoaiLeTrungMa e) {
            System.out.println("LỖI: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("LỖI: Nhập sai định dạng số (Diện tích/Giá phòng)!");
        }
    }

    private void uiSuaNhaTro() {
        System.out.print("Nhập mã nhà trọ cần sửa: ");
        String maNT = scanner.nextLine();
        
        NhaTro nt = service.timNhaTroTheoMa(maNT);
        if (nt == null) {
            System.out.println("Không tìm thấy nhà trọ với mã này!");
            return;
        }

        try {
            System.out.print("Nhập giá phòng mới (hiện tại: " + nt.getGiaPhong() + "): ");
            double giaMoi = Double.parseDouble(scanner.nextLine());

            System.out.print("Nhập mô tả mới: ");
            String moTaMoi = scanner.nextLine();

            service.suaThongTinNhaTro(maNT, giaMoi, moTaMoi);
            System.out.println("Cập nhật thông tin nhà trọ thành công!");
        } catch (Exception e) {
             System.out.println("LỖI: Giá phòng phải là số!");
        }
    }

    private void uiXoaNhaTro() {
        System.out.print("Nhập mã nhà trọ cần xóa: ");
        String maNT = scanner.nextLine();
        if (service.xoaNhaTro(maNT)) {
            System.out.println("Xóa nhà trọ thành công!");
        } else {
            System.out.println("Không tìm thấy nhà trọ để xóa!");
        }
    }

    private void uiHienThiTatCaNhaTro() {
        List<NhaTro> ds = service.layDanhSachNhaTro();
        if (ds.isEmpty()) {
            System.out.println("Danh sách nhà trọ trống.");
            return;
        }
        for (NhaTro nt : ds) {
            System.out.println(nt.toString());
        }
    }

    private void uiThemDanhGia() {
        System.out.print("Nhập mã nhà trọ muốn đánh giá: ");
        String maNT = scanner.nextLine();
        
        System.out.print("Nhập mã đánh giá: ");
        String maDG = scanner.nextLine();
        System.out.print("Nhập mã người đánh giá: ");
        String nguoiDG = scanner.nextLine();
        ThaiDoDanhGia thaiDo = nhapThaiDoDanhGia();
        System.out.print("Nhập nội dung đánh giá: ");
        String noiDung = scanner.nextLine();

        try {
            // Yêu cầu Service xử lý thêm
            service.themDanhGia(maNT, new DanhGia(maDG, nguoiDG, thaiDo, noiDung));
            System.out.println("Thêm đánh giá thành công!");
        } catch (NgoaiLeTrungMa e) {
            System.out.println("LỖI: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("LỖI: " + e.getMessage()); // Không tìm thấy nhà trọ
        }
    }

    private void uiSuaDanhGia() {
        System.out.print("Nhập mã nhà trọ chứa đánh giá: ");
        String maNT = scanner.nextLine();
        
        System.out.print("Nhập mã đánh giá cần sửa thái độ: ");
        String maDG = scanner.nextLine();
        ThaiDoDanhGia thaiDoMoi = nhapThaiDoDanhGia();

        if (service.suaThaiDoDanhGia(maNT, maDG, thaiDoMoi)) {
            System.out.println("Cập nhật thái độ đánh giá thành công!");
        } else {
            System.out.println("LỖI: Không tìm thấy nhà trọ hoặc đánh giá với mã này!");
        }
    }

    private void uiXoaDanhGia() {
        System.out.print("Nhập mã nhà trọ chứa đánh giá: ");
        String maNT = scanner.nextLine();
        
        System.out.print("Nhập mã đánh giá cần xóa: ");
        String maDG = scanner.nextLine();

        if (service.xoaDanhGia(maNT, maDG)) {
            System.out.println("Xóa đánh giá thành công!");
        } else {
            System.out.println("LỖI: Không tìm thấy nhà trọ hoặc đánh giá để xóa!");
        }
    }

    private void uiXemDanhGia() {
        System.out.print("Nhập mã nhà trọ để xem các đánh giá: ");
        String maNT = scanner.nextLine();
        
        List<DanhGia> dsDanhGia = service.layDanhSachDanhGia(maNT);
        if (dsDanhGia == null) {
            System.out.println("Không tìm thấy nhà trọ!");
            return;
        }

        if (dsDanhGia.isEmpty()) {
            System.out.println("Nhà trọ này hiện chưa có đánh giá nào.");
        } else {
            System.out.println("--- Danh sách đánh giá của nhà trọ " + maNT + " ---");
            for (DanhGia dg : dsDanhGia) {
                System.out.println(dg.toString());
            }
        }
    }

    // Các hàm phụ trợ chọn Enum được code lại bằng if-else rất cơ bản
    private ThaiDoDanhGia nhapThaiDoDanhGia() {
        int chon = 0;
        while (true) {
            System.out.println("Chọn thái độ:");
            System.out.println("1. Thích (LIKE)");
            System.out.println("2. Không thích (DISLIKE)");
            System.out.print("Lựa chọn (1-2): ");
            
            try {
                chon = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số!");
            }
            
            if (chon == 1) {
                return ThaiDoDanhGia.LIKE;
            } else if (chon == 2) {
                return ThaiDoDanhGia.DISLIKE;
            } else {
                System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại!");
            }
        }
    }

    private LoaiNha nhapLoaiNha() {
        int chon = 0;
        while (true) {
            System.out.println("Chọn loại nhà trọ:");
            System.out.println("1. Căn hộ chung cư");
            System.out.println("2. Nhà riêng");
            System.out.println("3. Phòng trọ khép kín");
            System.out.print("Lựa chọn (1-3): ");
            
            try {
                chon = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số!");
            }
            
            if (chon == 1) {
                return LoaiNha.CAN_HO;
            } else if (chon == 2) {
                return LoaiNha.NHA_RIENG;
            } else if (chon == 3) {
                return LoaiNha.PHONG_TRO;
            } else {
                System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại!");
            }
        }
    }
}