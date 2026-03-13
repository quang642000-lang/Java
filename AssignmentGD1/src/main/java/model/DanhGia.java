package model;

public class DanhGia {
    private NguoiDung nguoiDanhGia; 
    private NhaTro nhaTro;          
    private LoaiDanhGia danhGia;    
    private String noiDung;

    public DanhGia(NguoiDung nguoiDanhGia, NhaTro nhaTro, LoaiDanhGia danhGia, String noiDung) {
        this.nguoiDanhGia = nguoiDanhGia;
        this.nhaTro = nhaTro;
        this.danhGia = danhGia;
        this.noiDung = noiDung;
    }

    public NhaTro getNhaTro() { return nhaTro; }

    @Override
    public String toString() {
        return String.format("- %s đánh giá [%s]: %s", 
                nguoiDanhGia.getTenND(), danhGia.name(), noiDung);
    }
}