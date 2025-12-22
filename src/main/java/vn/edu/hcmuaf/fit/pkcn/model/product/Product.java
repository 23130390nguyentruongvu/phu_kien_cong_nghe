package vn.edu.hcmuaf.fit.pkcn.model.product;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.LocalDateTime;
import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private int warrantyPeriod;
    private String subtitle;
    private String description;
    private double minPrice, maxPrice;
    private int stock;
    private LocalDateTime create, update;
    private int isFeatured;

    public Product() {
    }

    public Product(int id, String name, int warrantyPeriod, String subtitle,
                   String description, double minPrice, double maxPrice, int stock,
                   LocalDateTime create, LocalDateTime update, int isFeatured) {
        this.id = id;
        this.name = name;
        this.warrantyPeriod = warrantyPeriod;
        this.subtitle = subtitle;
        this.description = description;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.stock = stock;
        this.create = create;
        this.update = update;
        this.isFeatured = isFeatured;
    }

    public int getId() {
        return id;
    }

    @ColumnName("id")
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @ColumnName("name")
    public void setName(String name) {
        this.name = name;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    @ColumnName("warranty_period")
    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getSubtitle() {
        return subtitle;
    }

    @ColumnName("subtitle")
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    @ColumnName("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public double getMinPrice() {
        return minPrice;
    }

    @ColumnName("min_price")
    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    @ColumnName("max_price")
    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getStock() {
        return stock;
    }

    @ColumnName("stock")
    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDateTime getCreate() {
        return create;
    }

    @ColumnName("create_date")
    public void setCreate(LocalDateTime create) {
        this.create = create;
    }

    public LocalDateTime getUpdate() {
        return update;
    }

    @ColumnName("update_date")
    public void setUpdate(LocalDateTime update) {
        this.update = update;
    }

    public int getIsFeatured() {
        return isFeatured;
    }

    @ColumnName("is_featured")
    public void setIsFeatured(int isFeatured) {
        this.isFeatured = isFeatured;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && warrantyPeriod == product.warrantyPeriod
                && Double.compare(minPrice, product.minPrice)
                == 0 && Double.compare(maxPrice, product.maxPrice) == 0
                && stock == product.stock && isFeatured == product.isFeatured
                && Objects.equals(name, product.name) && Objects.equals(subtitle, product.subtitle)
                && Objects.equals(description, product.description)
                && Objects.equals(create, product.create) && Objects.equals(update, product.update);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, warrantyPeriod, subtitle, description, minPrice,
                maxPrice, stock, create, update, isFeatured);
    }
}
