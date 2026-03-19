package model;

public class DanhGia {
    private String maDG;
    private String maNguoiDanhGia; // Liên kết tới NguoiDung
    private ThaiDoDanhGia thaiDo;
    private String noiDung;

    public DanhGia() {}

    public DanhGia(String maDG, String maNguoiDanhGia, ThaiDoDanhGia thaiDo, String noiDung) {
        this.maDG = maDG;
        this.maNguoiDanhGia = maNguoiDanhGia;
        this.thaiDo = thaiDo;
        this.noiDung = noiDung;
    }

    public String getMaDG() { return maDG; }
    public void setMaDG(String maDG) { this.maDG = maDG; }

    public String getMaNguoiDanhGia() { return maNguoiDanhGia; }
    public void setMaNguoiDanhGia(String maNguoiDanhGia) { this.maNguoiDanhGia = maNguoiDanhGia; }

    public ThaiDoDanhGia getThaiDo() { return thaiDo; }
    public void setThaiDo(ThaiDoDanhGia thaiDo) { this.thaiDo = thaiDo; }

    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }

    @Override
    public String toString() {
        return "  + Mã ĐG: " + maDG + " | Người ĐG: " + maNguoiDanhGia + " | Thái độ: " + thaiDo.getMoTa() + " | Nội dung: " + noiDung;
    }
}