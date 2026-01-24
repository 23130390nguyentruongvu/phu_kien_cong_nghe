package vn.edu.hcmuaf.fit.pkcn.model.order;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import vn.edu.hcmuaf.fit.pkcn.utils.PriceFormatUtils;

import java.math.BigDecimal;

public class OrderDetailItem {
    private int orderId;
    private int orderDetailId;
    private int variantId;
    private String name, type;
    private int quantity;
    private BigDecimal priceVariant, priceTotal;

    public int getOrderId() {
        return orderId;
    }

    @ColumnName("order_id")
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    @ColumnName("id")
    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getVariantId() {
        return variantId;
    }

    @ColumnName("variant_id")
    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public String getName() {
        return name;
    }

    @ColumnName("name")
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    @ColumnName("type")
    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    @ColumnName("quantity")
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceVariant() {
        return priceVariant;
    }

    @ColumnName("price")
    public void setPriceVariant(BigDecimal priceVariant) {
        this.priceVariant = priceVariant;
    }

    public BigDecimal getPriceTotal() {
        return priceTotal;
    }

    @ColumnName("price_total")
    public void setPriceTotal(BigDecimal priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getPriceVariantFormat() {
        return PriceFormatUtils.formatPrice(PriceFormatUtils.PATTERN_VND, priceVariant);
    }

    public String getTotalPriceFormat() {
        return PriceFormatUtils.formatPrice(PriceFormatUtils.PATTERN_VND, priceTotal);
    }
}
