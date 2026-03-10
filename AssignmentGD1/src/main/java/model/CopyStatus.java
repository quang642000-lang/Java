package model;

// Chữ M trong MVC - Dữ liệu Trạng thái
public enum CopyStatus {
    CON_TOT("Còn tốt"),
    HU_HONG("Hư hỏng"),
    MAT("Mất");

    private String description;

    CopyStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}