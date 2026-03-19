package model;

public class NguoiDung {
    private String maND;
    private String tenND;
    private String gioiTinh;
    private String dienThoai;
    private String diaChi;
    private String quan;
    private String email;

    public NguoiDung() {}

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
    public void setMaND(String maND) { this.maND = maND; }
    
    public String getTenND() { return tenND; }
    public void setTenND(String tenND) { this.tenND = tenND; }
    
    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }
    
    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }
    
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    
    public String getQuan() { return quan; }
    public void setQuan(String quan) { this.quan = quan; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}