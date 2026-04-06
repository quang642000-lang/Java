package model;

import java.io.Serializable;
import java.time.LocalDate;

public class NhaTro implements Serializable {
    private String id;
    private LoaiNhaTro loaiNha;
    private double dienTich;
    private double giaPhong;
    private String diaChi;
    private String quan;
    private String moTa;
    private TrangThaiNhaTro trangThai;
    private LocalDate ngayDang;
    private String nguoiLienHeId;

    public NhaTro() {
    }

    public NhaTro(String id, LoaiNhaTro loaiNha, double dienTich, double giaPhong, String diaChi, String quan, String moTa, TrangThaiNhaTro trangThai, LocalDate ngayDang, String nguoiLienHeId) {
        this.id = id; this.loaiNha = loaiNha; this.dienTich = dienTich;
        this.giaPhong = giaPhong; this.diaChi = diaChi; this.quan = quan;
        this.moTa = moTa; this.trangThai = trangThai; this.ngayDang = ngayDang; this.nguoiLienHeId = nguoiLienHeId;
    }

    public String getId() { return id; }
    public LoaiNhaTro getLoaiNha() { return loaiNha; }
    public double getDienTich() { return dienTich; }
    public double getGiaPhong() { return giaPhong; }
    public String getDiaChi() { return diaChi; }
    public String getQuan() { return quan; }
    public String getMoTa() { return moTa; }
    public TrangThaiNhaTro getTrangThai() { return trangThai; }
    public LocalDate getNgayDang() { return ngayDang; }
    public String getNguoiLienHeId() { return nguoiLienHeId; }

    public void setId(String id) { this.id = id; }
    public void setLoaiNha(LoaiNhaTro loaiNha) { this.loaiNha = loaiNha; }
    public void setDienTich(double dienTich) { this.dienTich = dienTich; }
    public void setGiaPhong(double giaPhong) { this.giaPhong = giaPhong; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public void setQuan(String quan) { this.quan = quan; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public void setTrangThai(TrangThaiNhaTro trangThai) { this.trangThai = trangThai; }
    public void setNgayDang(LocalDate ngayDang) { this.ngayDang = ngayDang; }
    public void setNguoiLienHeId(String nguoiLienHeId) { this.nguoiLienHeId = nguoiLienHeId; }

    @Override
    public String toString() {
        return String.format("Nhà Trọ [ID: %s | Loại: %s | Diện tích: %.1f m2 | Giá: %,.0f VND | Quận: %s | Trạng thái: %s]", 
                id, loaiNha.getTenLoai(), dienTich, giaPhong, quan, trangThai.getMoTa());
    }
}