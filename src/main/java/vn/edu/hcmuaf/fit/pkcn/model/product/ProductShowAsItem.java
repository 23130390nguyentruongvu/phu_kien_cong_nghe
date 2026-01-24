package vn.edu.hcmuaf.fit.pkcn.model.product;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import vn.edu.hcmuaf.fit.pkcn.utils.FormatUtils;

import java.math.BigDecimal;

public class ProductShowAsItem {
    private int productId;
    private String name;
    private String imageMain;
    private BigDecimal minPrice;
    private Integer categoryId;

    public ProductShowAsItem() {
    }

    public ProductShowAsItem(int productId, String name, String imageMain, BigDecimal minPrice) {
        this.productId = productId;
        this.name = name;
        this.imageMain = imageMain;
        this.minPrice = minPrice;
//        this.categoryId = categoryId;
    }

    public int getProductId() {
        return productId;
    }

    @ColumnName("id")
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    @ColumnName("name")
    public void setName(String name) {
        this.name = name;
    }

    public String getImageMain() {
        return imageMain;
    }

    @ColumnName("url_image")
    public void setImageMain(String imageMain) {
        this.imageMain = imageMain;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    @ColumnName("category_id")
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String toString() {
        return "ProductShowAsItem{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", imageMain='" + imageMain + '\'' +
                ", minPrice=" + minPrice +
                '}';
    }
}
