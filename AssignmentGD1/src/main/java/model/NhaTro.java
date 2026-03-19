package model;

import java.util.ArrayList;
import java.util.List;

public class NhaTro {
    private String maNT;
    private LoaiNha loaiNha; 
    private double dienTich;
    private double giaPhong;
    private String diaChi;
    private String quan;
    private String moTa;
    private String ngayDang;
    private String maNguoiLienHe; 
    
    // Quan hệ 1-N: 1 Nhà trọ có nhiều Đánh giá
    private List<DanhGia> danhSachDanhGia;

    public NhaTro() {
        this.danhSachDanhGia = new ArrayList<>();
    }

    public NhaTro(String maNT, LoaiNha loaiNha, double dienTich, double giaPhong, String diaChi, String quan, String moTa, String ngayDang, String maNguoiLienHe) {
        this.maNT = maNT;
        this.loaiNha = loaiNha;
        this.dienTich = dienTich;
        this.giaPhong = giaPhong;
        this.diaChi = diaChi;
        this.quan = quan;
        this.moTa = moTa;
        this.ngayDang = ngayDang;
        this.maNguoiLienHe = maNguoiLienHe;
        this.danhSachDanhGia = new ArrayList<>();
    }

    public String getMaNT() { return maNT; }
    public void setMaNT(String maNT) { this.maNT = maNT; }

    public LoaiNha getLoaiNha() { return loaiNha; }
    public void setLoaiNha(LoaiNha loaiNha) { this.loaiNha = loaiNha; }

    public double getDienTich() { return dienTich; }
    public void setDienTich(double dienTich) { this.dienTich = dienTich; }

    public double getGiaPhong() { return giaPhong; }
    public void setGiaPhong(double giaPhong) { this.giaPhong = giaPhong; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getQuan() { return quan; }
    public void setQuan(String quan) { this.quan = quan; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public String getNgayDang() { return ngayDang; }
    public void setNgayDang(String ngayDang) { this.ngayDang = ngayDang; }

    public String getMaNguoiLienHe() { return maNguoiLienHe; }
    public void setMaNguoiLienHe(String maNguoiLienHe) { this.maNguoiLienHe = maNguoiLienHe; }

    public List<DanhGia> getDanhSachDanhGia() { return danhSachDanhGia; }
    public void setDanhSachDanhGia(List<DanhGia> danhSachDanhGia) { this.danhSachDanhGia = danhSachDanhGia; }

    // Thêm đánh giá vào nhà trọ
    public void themDanhGia(DanhGia dg) throws NgoaiLeTrungMa {
        for (DanhGia d : danhSachDanhGia) {
            if (d.getMaDG().equalsIgnoreCase(dg.getMaDG())) {
                throw new NgoaiLeTrungMa("Mã đánh giá '" + dg.getMaDG() + "' đã tồn tại!");
            }
        }
        danhSachDanhGia.add(dg);
    }

    // Sửa thái độ đánh giá
    public boolean suaThaiDoDanhGia(String maDG, ThaiDoDanhGia thaiDoMoi) {
        for (DanhGia d : danhSachDanhGia) {
            if (d.getMaDG().equalsIgnoreCase(maDG)) {
                d.setThaiDo(thaiDoMoi);
                return true;
            }
        }
        return false;
    }

    // Xóa đánh giá
    public boolean xoaDanhGia(String maDG) {
        return danhSachDanhGia.removeIf(d -> d.getMaDG().equalsIgnoreCase(maDG));
    }

    @Override
    public String toString() {
        return String.format("Mã NT: %s | Loại: %s | Giá: %.0f | Quận: %s | Lượt đánh giá: %d",
                maNT, loaiNha.getTenLoai(), giaPhong, quan, danhSachDanhGia.size());
    }
}