package vn.edu.hcmuaf.fit.pkcn.model.order;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.Objects;

public class OrderDetails {
    private int id;
    private int orderId;
    private int productVariantId;
    private int quantity;
    private double priceTotal;

    public OrderDetails(int id, int orderId, int productVariantId, int quantity, double priceTotal) {
        this.id = id;
        this.orderId = orderId;
        this.productVariantId = productVariantId;
        this.quantity = quantity;
        this.priceTotal = priceTotal;
    }

    public int getId() {
        return id;
    }
    @ColumnName("id")
    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }
    @ColumnName("order_id")
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductVariantId() {
        return productVariantId;
    }
    @ColumnName("product_variant_id")
    public void setProductVariantId(int productVariantId) {
        this.productVariantId = productVariantId;
    }

    public int getQuantity() {
        return quantity;
    }
    @ColumnName("quantity")
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceTotal() {
        return priceTotal;
    }
    @ColumnName("price_total")
    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetails that = (OrderDetails) o;
        return id == that.id && orderId == that.orderId && productVariantId == that.productVariantId && quantity == that.quantity && Double.compare(priceTotal, that.priceTotal) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, productVariantId, quantity, priceTotal);
    }
}
