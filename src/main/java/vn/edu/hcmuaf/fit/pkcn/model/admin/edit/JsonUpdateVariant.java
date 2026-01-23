package vn.edu.hcmuaf.fit.pkcn.model.admin.edit;

import java.math.BigDecimal;

public class JsonUpdateVariant {
    private int variantId;
    private int productId;
    private String folderId;
    private boolean hasUpdateImage;
    private String name;
    private BigDecimal price; // Dùng BigDecimal để tránh sai số tiền tệ
    private int stock;
    private int gram;
    private String color;
    private String size;
    private String imageUrl;

    public JsonUpdateVariant() {
    }

    // Getter và Setter

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public boolean isHasUpdateImage() {
        return hasUpdateImage;
    }

    public void setHasUpdateImage(boolean hasUpdateImage) {
        this.hasUpdateImage = hasUpdateImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getGram() {
        return gram;
    }

    public void setGram(int gram) {
        this.gram = gram;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
