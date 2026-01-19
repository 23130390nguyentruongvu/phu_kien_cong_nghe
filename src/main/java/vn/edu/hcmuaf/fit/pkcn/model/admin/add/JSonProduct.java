package vn.edu.hcmuaf.fit.pkcn.model.admin.add;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.math.BigDecimal;
import java.util.List;

public class JSonProduct {
    private String folderId;
    private String name;
    private int warrantyPeriod;
    private String categoryId;
    private String subtitle;
    private String description;
    private boolean status;
    private List<String> imageUrls;
    private int mainImageIndex;
    private List<JSonProductVariant> variants;
    private BigDecimal minPrice, maxPrice;
    private int stock;

    // Getters
    public String getFolderId() {
        return folderId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDescription() {
        return description;
    }

    public boolean getStatus() {
        return status;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public int getStock() {
        return stock;
    }

    public int getMainImageIndex() {
        return mainImageIndex;
    }

    public List<JSonProductVariant> getVariants() {
        return variants;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setMainImageIndex(int mainImageIndex) {
        this.mainImageIndex = mainImageIndex;
    }

    public void setVariants(List<JSonProductVariant> variants) {
        this.variants = variants;
    }

    public String getMainImageUrl() {
        if (imageUrls != null && mainImageIndex >= 0 && mainImageIndex < imageUrls.size()) {
            return imageUrls.get(mainImageIndex);
        }
        return null;
    }

    public void updatePriceAndStock() {
        double minPrice = Double.MAX_VALUE, maxPrice = Double.MIN_VALUE;
        for (JSonProductVariant variant : variants) {
            this.stock += variant.getStock();
            minPrice = Math.min(minPrice, variant.getPrice().doubleValue());
            maxPrice = Math.max(maxPrice, variant.getPrice().doubleValue());
        }

        this.maxPrice = new BigDecimal(maxPrice);
        this.minPrice = new BigDecimal(minPrice);
    }
}