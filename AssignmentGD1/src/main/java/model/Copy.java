package model;

// Chữ M trong MVC - Thực thể Bản sao
public class Copy {
    private String id;
    private CopyStatus status;

    public Copy(String id, CopyStatus status) {
        this.id = id;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public CopyStatus getStatus() { return status; }
    public void setStatus(CopyStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Bản sao [Mã: " + id + ", Trạng thái: " + status.getDescription() + "]";
    }
}