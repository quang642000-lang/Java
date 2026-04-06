package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NguoiDung implements Serializable {
    private String id;
    private String ten;
    private String gioiTinh;
    private String sdt;
    private String diaChi;
    private String quan;
    private String email;
    private List<NhaTro> danhSachNhaTroDaDang;

    public NguoiDung() {
        this.danhSachNhaTroDaDang = new ArrayList<>();
    }

    public NguoiDung(String id, String ten, String gioiTinh, String sdt, String diaChi, String quan, String email) {
        this.id = id; this.ten = ten; this.gioiTinh = gioiTinh;
        this.sdt = sdt; this.diaChi = diaChi; this.quan = quan; this.email = email;
        this.danhSachNhaTroDaDang = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getTen() { return ten; }
    public String getGioiTinh() { return gioiTinh; }
    public String getSdt() { return sdt; }
    public String getDiaChi() { return diaChi; }
    public String getQuan() { return quan; }
    public String getEmail() { return email; }

    public void setId(String id) { this.id = id; }
    public void setTen(String ten) { this.ten = ten; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public void setQuan(String quan) { this.quan = quan; }
    public void setEmail(String email) { this.email = email; }

    public List<NhaTro> getDanhSachNhaTroDaDang() { return danhSachNhaTroDaDang; }
    public void setDanhSachNhaTroDaDang(List<NhaTro> danhSachNhaTroDaDang) { this.danhSachNhaTroDaDang = danhSachNhaTroDaDang; }
    public void addNhaTro(NhaTro nt) { this.danhSachNhaTroDaDang.add(nt); }

    @Override
    public String toString() {
        return String.format("Người Dùng [ID: %s | Tên: %s | Giới tính: %s | SĐT: %s | Email: %s | Đã đăng: %d nhà trọ]", 
                id, ten, gioiTinh, sdt, email, danhSachNhaTroDaDang.size());
    }
}