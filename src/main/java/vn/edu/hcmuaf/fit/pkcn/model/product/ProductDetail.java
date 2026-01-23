package vn.edu.hcmuaf.fit.pkcn.model.product;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import vn.edu.hcmuaf.fit.pkcn.utils.FormatUtils;

import java.math.BigDecimal;
import java.util.List;

public class ProductDetail {
    private int id;
    private String name;
    private List<Image> images;
    private Integer categoryId;
    private String categoryName;
    private BigDecimal minPrice;
    private int warranty;
    private String subDescription;
    private String description;
    private List<ProductVariant> variants;
    private ProductVariant defaultVariant;

    public ProductDetail() {}

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

    public Integer getCategoryId() {
        return categoryId;
    }

    @ColumnName("category_id")
    public void setCategoryId(Integer categoryId) {this.categoryId = categoryId;}

    public String getCategoryName() {
        return categoryName;
    }

    @ColumnName("category_name")
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    @ColumnName("min_price")
    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public String getMinPriceByFormat() {
        return FormatUtils.formatPrice(FormatUtils.PATTERN_VND, minPrice);
    }

    public int getWarranty() {
        return warranty;
    }

    @ColumnName("warranty_period")
    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public String getSubDescription() {
        return subDescription;
    }

    @ColumnName("subtitle")
    public void setSubDescription(String subDescription) {
        this.subDescription = subDescription;
    }

    public String getDescription() {
        return description;
    }

    @ColumnName("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductVariant> getVariants() {
        return variants;
    }

    public void setVariants(List<ProductVariant> variants) {
        this.variants = variants;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public ProductVariant getDefaultVariant() {
        return defaultVariant;
    }

    public void setDefaultVariant(ProductVariant defaultVariant) {
        this.defaultVariant = defaultVariant;
    }
}
