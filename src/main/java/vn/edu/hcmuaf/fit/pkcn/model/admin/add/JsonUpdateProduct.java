package vn.edu.hcmuaf.fit.pkcn.model.admin.add;

import java.util.List;

public class JsonUpdateProduct {
    private int id;
    private boolean active;
    private String name;
    private int warrantyPeriod;
    private String subtitle;
    private String description;
    private String folderId;
    private List<String> newImages;    // Hứng mảng link ảnh mới từ Firebase
    private List<String> removeUrls;   // Hứng mảng link ảnh cũ cần xóa
    private String imageMainUrl;       // Link ảnh chính được chọn

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public List<String> getNewImages() {
        return newImages;
    }

    public void setNewImages(List<String> newImages) {
        this.newImages = newImages;
    }

    public List<String> getRemoveUrls() {
        return removeUrls;
    }

    public void setRemoveUrls(List<String> removeUrls) {
        this.removeUrls = removeUrls;
    }

    public String getImageMainUrl() {
        return imageMainUrl;
    }

    public void setImageMainUrl(String imageMainUrl) {
        this.imageMainUrl = imageMainUrl;
    }
}
