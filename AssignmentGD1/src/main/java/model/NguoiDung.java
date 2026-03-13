package model;

public class NguoiDung {
    private String maND;
    private String tenND;
    private String gioiTinh;
    private String dienThoai;
    private String diaChi; 
    private String quan;
    private String email;

    public NguoiDung(String maND, String tenND, String gioiTinh, String dienThoai, String diaChi, String quan, String email) {
        this.maND = maND;
        this.tenND = tenND;
        this.gioiTinh = gioiTinh;
        this.dienThoai = dienThoai;
        this.diaChi = diaChi;
        this.quan = quan;
        this.email = email;
    }

    public String getMaND() { return maND; }
    public String getTenND() { return tenND; }
    
    // Thêm các hàm setter để hỗ trợ chức năng Sửa
    public void setTenND(String tenND) { this.tenND = tenND; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }

    @Override
    public String toString() {
        return String.format("Người Dùng [Mã: %s, Tên: %s, Giới tính: %s, SĐT: %s, Quận: %s]", 
                maND, tenND, gioiTinh, dienThoai, quan);
    }
}