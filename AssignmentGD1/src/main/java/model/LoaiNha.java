package model;

public enum LoaiNha {
    CAN_HO("Căn hộ chung cư"),
    NHA_RIENG("Nhà riêng"),
    PHONG_TRO("Phòng trọ khép kín");

    private String tenLoai;

    LoaiNha(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }
}