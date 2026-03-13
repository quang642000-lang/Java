package model;

public class NhaTro {
    private String maNT;
    private LoaiNha loaiNha;
    private double dienTich;
    private double giaPhong;
    private String diaChi; 
    private String quan;
    private String moTa;
    private String ngayDang;
    private NguoiDung nguoiLienHe; 

    public NhaTro(String maNT, LoaiNha loaiNha, double dienTich, double giaPhong, 
                  String diaChi, String quan, String moTa, String ngayDang, NguoiDung nguoiLienHe) {
        this.maNT = maNT;
        this.loaiNha = loaiNha;
        this.dienTich = dienTich;
        this.giaPhong = giaPhong;
        this.diaChi = diaChi;
        this.quan = quan;
        this.moTa = moTa;
        this.ngayDang = ngayDang;
        this.nguoiLienHe = nguoiLienHe;
    }

    public String getMaNT() { return maNT; }
    public String getDiaChi() { return diaChi; }
    
    // Thêm hàm setter để hỗ trợ chức năng Sửa
    public void setGiaPhong(double giaPhong) { this.giaPhong = giaPhong; }
    
    @Override
    public String toString() {
        return String.format("Nhà Trọ [Mã: %s, Loại: %s, Diện tích: %.1fm2, Giá: %,.0f VND, Quận: %s, Người LH: %s]", 
                maNT, loaiNha.getMoTa(), dienTich, giaPhong, quan, nguoiLienHe.getTenND());
    }
}