package model;

public enum LoaiDanhGia {
    LIKE("Thích"),
    DISLIKE("Không thích");

    private final String moTa;

    LoaiDanhGia(String moTa) { 
        this.moTa = moTa; 
    }

    public String getMoTa() { 
        return moTa; 
    }
}