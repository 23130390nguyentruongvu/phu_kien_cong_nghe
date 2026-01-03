package vn.edu.hcmuaf.fit.pkcn.model.product;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductVariant {
    private int id;
    private int productId;
    private String sku;
    private String urlImage;
    private String name;
    private BigDecimal price;
    private int stock;
    private int gram;
    private String color, size;
    private LocalDateTime create, update;

    public ProductVariant() {
    }

    public ProductVariant(int id, int productId, String sku, String name,
                          BigDecimal price, int stock, int gram, String color,
                          String size, LocalDateTime create, LocalDateTime update) {
        this.id = id;
        this.productId = productId;
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.gram = gram;
        this.color = color;
        this.size = size;
        this.create = create;
        this.update = update;
    }

    public int getId() {
        return id;
    }

    @ColumnName("id")
    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    @ColumnName("product_id")
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    @ColumnName("sku")
    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    @ColumnName("name")
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @ColumnName("price")
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    @ColumnName("stock")
    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getGram() {
        return gram;
    }

    @ColumnName("gram")
    public void setGram(int gram) {
        this.gram = gram;
    }

    public String getColor() {
        return color;
    }

    @ColumnName("color")
    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    @ColumnName("size")
    public void setSize(String size) {
        this.size = size;
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

    public String getUrlImage() {
        return urlImage;
    }

    @ColumnName("url_image")
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
