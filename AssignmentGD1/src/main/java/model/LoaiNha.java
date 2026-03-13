package model;

public enum LoaiNha {
    CHUNG_CU("Căn hộ chung cư"),
    NHA_RIENG("Nhà riêng"),
    PHONG_TRO("Phòng trọ khép kín");

    private final String moTa;

    LoaiNha(String moTa) { 
        this.moTa = moTa; 
    }

    public String getMoTa() { 
        return moTa; 
    }
}