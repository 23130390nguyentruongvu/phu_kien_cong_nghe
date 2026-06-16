package vn.edu.hcmuaf.fit.pkcn.model.order.snap;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import java.math.BigDecimal;

public class OrderDetailSnapshot {
    private Integer id;
    private Integer orderId;
    private Integer productVariantId;
    private String productNameSnapshot;
    private String variantNameSnapshot;
    private String skuSnapshot;
    private BigDecimal variantPriceSnapshot;
    private Integer gramSnapshot;
    private String colorSnapshot;
    private String sizeSnapshot;
    private Integer quantity;
    private BigDecimal priceTotal;

    public OrderDetailSnapshot() {}

    public OrderDetailSnapshot(Integer id, Integer orderId, Integer productVariantId, String productNameSnapshot,
                               String variantNameSnapshot, String skuSnapshot, BigDecimal variantPriceSnapshot,
                               Integer gramSnapshot, String colorSnapshot, String sizeSnapshot, Integer quantity,
                               BigDecimal priceTotal) {
        this.id = id;
        this.orderId = orderId;
        this.productVariantId = productVariantId;
        this.productNameSnapshot = productNameSnapshot;
        this.variantNameSnapshot = variantNameSnapshot;
        this.skuSnapshot = skuSnapshot;
        this.variantPriceSnapshot = variantPriceSnapshot;
        this.gramSnapshot = gramSnapshot;
        this.colorSnapshot = colorSnapshot;
        this.sizeSnapshot = sizeSnapshot;
        this.quantity = quantity;
        this.priceTotal = priceTotal;
    }

    @ColumnName("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ColumnName("order_id")
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @ColumnName("product_variant_id")
    public Integer getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(Integer productVariantId) {
        this.productVariantId = productVariantId;
    }

    @ColumnName("product_name_snapshot")
    public String getProductNameSnapshot() {
        return productNameSnapshot;
    }

    public void setProductNameSnapshot(String productNameSnapshot) {
        this.productNameSnapshot = productNameSnapshot;
    }

    @ColumnName("variant_name_snapshot")
    public String getVariantNameSnapshot() {
        return variantNameSnapshot;
    }

    public void setVariantNameSnapshot(String variantNameSnapshot) {
        this.variantNameSnapshot = variantNameSnapshot;
    }

    @ColumnName("sku_snapshot")
    public String getSkuSnapshot() {
        return skuSnapshot;
    }

    public void setSkuSnapshot(String skuSnapshot) {
        this.skuSnapshot = skuSnapshot;
    }

    @ColumnName("variant_price_snapshot")
    public BigDecimal getVariantPriceSnapshot() {
        return variantPriceSnapshot;
    }

    public void setVariantPriceSnapshot(BigDecimal variantPriceSnapshot) {
        this.variantPriceSnapshot = variantPriceSnapshot;
    }

    @ColumnName("gram_snapshot")
    public Integer getGramSnapshot() {
        return gramSnapshot;
    }

    public void setGramSnapshot(Integer gramSnapshot) {
        this.gramSnapshot = gramSnapshot;
    }

    @ColumnName("color_snapshot")
    public String getColorSnapshot() {
        return colorSnapshot;
    }

    public void setColorSnapshot(String colorSnapshot) {
        this.colorSnapshot = colorSnapshot;
    }

    @ColumnName("size_snapshot")
    public String getSizeSnapshot() {
        return sizeSnapshot;
    }

    public void setSizeSnapshot(String sizeSnapshot) {
        this.sizeSnapshot = sizeSnapshot;
    }

    @ColumnName("quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @ColumnName("price_total")
    public BigDecimal getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(BigDecimal priceTotal) {
        this.priceTotal = priceTotal;
    }
}