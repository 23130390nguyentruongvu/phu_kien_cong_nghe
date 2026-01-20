package vn.edu.hcmuaf.fit.pkcn.model.product;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import vn.edu.hcmuaf.fit.pkcn.utils.FormatUtils;

import java.math.BigDecimal;
import java.util.List;

public class ProductAdminShowAsItem {
    private int prodId;
    private String name, subtitle, description, mainImage;
    private int warrantyPeriod;
    private List<String> images;
    private List<ProductVariant> productVariants;
    private boolean isActive;
    private BigDecimal minPrice, maxPrice;

    public int getProdId() {
        return prodId;
    }

    @ColumnName("id")
    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getName() {
        return name;
    }

    @ColumnName("name")
    public void setName(String name) {
        this.name = name;
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

    public String getMainImage() {
        return mainImage;
    }

    @ColumnName("url_image")
    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    @ColumnName("warranty_period")
    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<ProductVariant> getProductVariants() {
        return productVariants;
    }

    public void setProductVariants(List<ProductVariant> productVariants) {
        this.productVariants = productVariants;
    }

    public boolean isActive() {
        return isActive;
    }

    @ColumnName("status")
    public void setActive(boolean active) {
        isActive = active;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    @ColumnName("min_price")
    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    @ColumnName("max_price")
    public void setMaxPrice(BigDecimal maxPrice) {
        if (minPrice != null)
            if (maxPrice.doubleValue() == minPrice.doubleValue()) return;
        this.maxPrice = maxPrice;
    }

    public int getProductVariantQuant() {
        if (productVariants == null || productVariants.isEmpty()) return 0;
        return productVariants.size();
    }

    public int getTotalStock() {
        if (productVariants == null || productVariants.isEmpty()) return 0;
        int sum = 0;
        for (ProductVariant tmp : productVariants)
            sum += tmp.getStock();
        return sum;
    }

    public String getRangePriceFormat() {
        if (maxPrice == null) return FormatUtils.formatPrice(FormatUtils.PATTERN_VND, minPrice);
        return FormatUtils.formatPrice(FormatUtils.PATTERN_NONE_UNIT, minPrice)
                + " - " + FormatUtils.formatPrice(FormatUtils.PATTERN_VND, maxPrice);
    }
}
