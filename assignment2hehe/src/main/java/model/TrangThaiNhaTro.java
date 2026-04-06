package model;

public enum TrangThaiNhaTro {
    TRONG("Trống"),
    DANG_O("Đang ở"),
    DANG_SUA_CHUA("Đang sửa chữa");

    private final String moTa;

    TrangThaiNhaTro(String moTa) {
        this.moTa = moTa;
    }

    public String getMoTa() { return moTa; }

    public static TrangThaiNhaTro fromLuaChon(String choice) {
        switch (choice) {
            case "1": return TRONG;
            case "2": return DANG_O;
            case "3": return DANG_SUA_CHUA;
            default: return TRONG;
        }
    }

    public static TrangThaiNhaTro fromDBString(String dbVal) {
        if (dbVal == null) return TRONG;
        for (TrangThaiNhaTro tt : values()) {
            if (tt.name().equalsIgnoreCase(dbVal)) return tt;
        }
        return TRONG;
    }

    @Override
    public String toString() { return moTa; }
}