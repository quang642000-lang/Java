package model;

// Bài 2: Ánh xạ dữ liệu từ bảng tree sang đối tượng Java
public class Tree {
    private int nodeId;
    private String nodeName;
    private Integer parentId; // Dùng Integer thay vì int vì có thể mang giá trị NULL
    private int level;

    public Tree() {
    }

    public Tree(int nodeId, String nodeName, Integer parentId, int level) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.parentId = parentId;
        this.level = level;
    }

    public int getNodeId() { return nodeId; }
    public void setNodeId(int nodeId) { this.nodeId = nodeId; }
    public String getNodeName() { return nodeName; }
    public void setNodeName(String nodeName) { this.nodeName = nodeName; }
    public Integer getParentId() { return parentId; }
    public void setParentId(Integer parentId) { this.parentId = parentId; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    @Override
    public String toString() {
        return "Tree{" + "nodeId=" + nodeId + ", nodeName='" + nodeName + '\'' +
               ", parentId=" + parentId + ", level=" + level + '}';
    }
}