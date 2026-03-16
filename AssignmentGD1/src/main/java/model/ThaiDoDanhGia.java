package model;

public enum ThaiDoDanhGia {
    LIKE("Thích"),
    DISLIKE("Không thích");

    private String moTa;

    ThaiDoDanhGia(String moTa) {
        this.moTa = moTa;
    }

    public String getMoTa() {
        return moTa;
    }
}