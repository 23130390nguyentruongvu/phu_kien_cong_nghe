package vn.edu.hcmuaf.fit.pkcn.model.admin.add;

import java.math.BigDecimal;

public class JSonProductVariant {
    private String name;
    private String sku;
    private double price;
    private int stock;
    private double gram;
    private String color;
    private String size;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public String getSku() {
        return sku;
    }

    public BigDecimal getPrice() {
        return new BigDecimal(price);
    }

    public int getStock() {
        return stock;
    }

    public double getGram() {
        return gram;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setGram(double gram) {
        this.gram = gram;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
