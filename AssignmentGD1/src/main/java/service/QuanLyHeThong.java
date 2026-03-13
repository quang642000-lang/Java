package service;

import model.NguoiDung;
import model.NhaTro;
import exception.DuplicateIdException;

import java.util.ArrayList;
import java.util.List;

public class QuanLyHeThong {
    private List<NguoiDung> danhSachNguoiDung = new ArrayList<>();
    private List<NhaTro> danhSachNhaTro = new ArrayList<>();

    // --- Quản lý Người dùng ---
    public NguoiDung timNguoiDung(String maND) {
        for (NguoiDung nd : danhSachNguoiDung) {
            if (nd.getMaND().equals(maND)) {
                return nd;
            }
        }
        return null;
    }

    public void themNguoiDung(NguoiDung nd) throws DuplicateIdException {
        if (timNguoiDung(nd.getMaND()) != null) {
            throw new DuplicateIdException("Lỗi: Mã người dùng đã tồn tại!");
        }
        danhSachNguoiDung.add(nd);
        System.out.println("-> Đã thêm người dùng mới thành công!");
    }
    
    public void suaNguoiDung(String maND, String tenMoi, String sdtMoi) {
        NguoiDung nd = timNguoiDung(maND);
        if (nd != null) {
            nd.setTenND(tenMoi);
            nd.setDienThoai(sdtMoi);
            System.out.println("-> Sửa thông tin người dùng thành công!");
        } else {
            System.out.println("-> Không tìm thấy người dùng!");
        }
    }
    
    public void xoaNguoiDung(String maND) {
        NguoiDung nd = timNguoiDung(maND);
        if (nd != null) {
            danhSachNguoiDung.remove(nd);
            System.out.println("-> Xóa người dùng thành công!");
        } else {
            System.out.println("-> Không tìm thấy người dùng để xóa!");
        }
    }

    public void hienThiNguoiDung() {
        if (danhSachNguoiDung.isEmpty()) {
            System.out.println("-> Chưa có người dùng nào.");
            return;
        }
        for (NguoiDung nd : danhSachNguoiDung) {
            System.out.println(nd.toString());
        }
    }

    // --- Quản lý Nhà trọ ---
    public NhaTro timNhaTro(String maNT) {
        for (NhaTro nt : danhSachNhaTro) {
            if (nt.getMaNT().equals(maNT)) {
                return nt;
            }
        }
        return null;
    }

    public void dangTinNhaTro(NhaTro nt) throws DuplicateIdException {
        if (timNhaTro(nt.getMaNT()) != null) {
            throw new DuplicateIdException("Lỗi: Mã nhà trọ đã tồn tại!");
        }
        danhSachNhaTro.add(nt);
        System.out.println("-> Đã đăng tin nhà trọ thành công!");
    }
    
    public void suaNhaTro(String maNT, double giaMoi) {
        NhaTro nt = timNhaTro(maNT);
        if (nt != null) {
            nt.setGiaPhong(giaMoi);
            System.out.println("-> Cập nhật giá nhà trọ thành công!");
        } else {
            System.out.println("-> Không tìm thấy nhà trọ!");
        }
    }
    
    public void xoaNhaTro(String maNT) {
        NhaTro nt = timNhaTro(maNT);
        if (nt != null) {
            danhSachNhaTro.remove(nt);
            System.out.println("-> Xóa nhà trọ thành công!");
        } else {
            System.out.println("-> Không tìm thấy nhà trọ để xóa!");
        }
    }

    public void hienThiNhaTro() {
        if (danhSachNhaTro.isEmpty()) {
            System.out.println("-> Chưa có tin nhà trọ nào.");
            return;
        }
        for (NhaTro nt : danhSachNhaTro) {
            System.out.println(nt.toString());
        }
    }
}