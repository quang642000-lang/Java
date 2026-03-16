package service;

import model.DanhGia;
import model.NgoaiLeTrungMa;
import model.NhaTro;
import model.ThaiDoDanhGia;

import java.util.ArrayList;
import java.util.List;

// Tầng Service (Xử lý nghiệp vụ lõi, thao tác với Collection)
public class NhaTroService {
    private List<NhaTro> danhSachNhaTro = new ArrayList<>();

    // --- NGHIỆP VỤ NHÀ TRỌ ---

    public void themNhaTro(NhaTro nt) throws NgoaiLeTrungMa {
        if (timNhaTroTheoMa(nt.getMaNT()) != null) {
            throw new NgoaiLeTrungMa("Mã nhà trọ '" + nt.getMaNT() + "' đã tồn tại trong hệ thống!");
        }
        danhSachNhaTro.add(nt);
    }

    public List<NhaTro> layDanhSachNhaTro() {
        return danhSachNhaTro;
    }

    public NhaTro timNhaTroTheoMa(String maNT) {
        for (NhaTro nt : danhSachNhaTro) {
            if (nt.getMaNT().equalsIgnoreCase(maNT)) {
                return nt;
            }
        }
        return null;
    }

    public boolean suaThongTinNhaTro(String maNT, double giaMoi, String moTaMoi) {
        NhaTro nt = timNhaTroTheoMa(maNT);
        if (nt != null) {
            nt.setGiaPhong(giaMoi);
            nt.setMoTa(moTaMoi);
            return true;
        }
        return false;
    }

    public boolean xoaNhaTro(String maNT) {
        return danhSachNhaTro.removeIf(nt -> nt.getMaNT().equalsIgnoreCase(maNT));
    }

    // --- NGHIỆP VỤ ĐÁNH GIÁ ---

    public void themDanhGia(String maNT, DanhGia dg) throws Exception {
        NhaTro nt = timNhaTroTheoMa(maNT);
        if (nt == null) {
            throw new Exception("Không tìm thấy nhà trọ với mã này!");
        }
        nt.themDanhGia(dg); // Chuyển tiếp xuống Model để kiểm tra trùng mã
    }

    public boolean suaThaiDoDanhGia(String maNT, String maDG, ThaiDoDanhGia thaiDoMoi) {
        NhaTro nt = timNhaTroTheoMa(maNT);
        if (nt != null) {
            return nt.suaThaiDoDanhGia(maDG, thaiDoMoi);
        }
        return false;
    }

    public boolean xoaDanhGia(String maNT, String maDG) {
        NhaTro nt = timNhaTroTheoMa(maNT);
        if (nt != null) {
            return nt.xoaDanhGia(maDG);
        }
        return false;
    }

    public List<DanhGia> layDanhSachDanhGia(String maNT) {
        NhaTro nt = timNhaTroTheoMa(maNT);
        if (nt != null) {
            return nt.getDanhSachDanhGia();
        }
        return null; 
    }
}