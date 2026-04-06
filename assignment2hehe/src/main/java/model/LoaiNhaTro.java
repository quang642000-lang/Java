package model;

public enum LoaiNhaTro {
    CAN_HO_CHUNG_CU("CH", "Căn hộ trung cư"),
    NHA_RIENG("NR", "Nhà riêng"),
    PHONG_TRO_KHEP_KIN("PT", "Phòng trọ khép kín");

    private final String maLoai;
    private final String tenLoai;

    LoaiNhaTro(String maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public String getMaLoai() { return maLoai; }
    public String getTenLoai() { return tenLoai; }

    public static LoaiNhaTro fromMa(String ma) {
        for (LoaiNhaTro loai : values()) {
            if (loai.maLoai.equalsIgnoreCase(ma)) return loai;
        }
        throw new IllegalArgumentException("Mã loại nhà không hợp lệ (Chỉ nhận CH/NR/PT)");
    }

    @Override
    public String toString() { return tenLoai; }
}