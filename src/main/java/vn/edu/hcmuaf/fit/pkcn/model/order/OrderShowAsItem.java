package vn.edu.hcmuaf.fit.pkcn.model.order;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariantWrapOrder;
import vn.edu.hcmuaf.fit.pkcn.utils.PriceFormatUtils;
import vn.edu.hcmuaf.fit.pkcn.utils.enums.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderShowAsItem {
    private int orderId;
    private String status;
    private String address;
    private double totalPrice;
    private String description;
    private String signature;
    private Boolean snapshotValid;
    private List<ProductVariantWrapOrder> orderDetails;

    public OrderShowAsItem() {
        orderDetails = new ArrayList<>();
    }

    public int getOrderId() {
        return orderId;
    }

    @ColumnName("id")
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    @ColumnName("status_order")
    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    @ColumnName("address_detail")
    public void setAddress(String address) {
        this.address = address;
    }

    @ColumnName("signature")
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @ColumnName("snapshot_valid")
    public Boolean getSnapshotValid() {
        return snapshotValid;
    }

    public void setSnapshotValid(Boolean snapshotValid) {
        this.snapshotValid = snapshotValid;
    }

    public String getDescription() {
        return description;
    }

    @ColumnName("note")
    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @ColumnName("total_must_pay")
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ProductVariantWrapOrder> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<ProductVariantWrapOrder> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getStatusDisplay() {
        if (status == null) return "";
        try {
            return OrderStatus.fromCode(status).getDisplayName();
        } catch (IllegalArgumentException e) {
            return status;
        }
    }

    public String getVerifyStatus() {
        if (status == null) return "unsigned";
        if ("security_alert".equals(status)) return "tampered";
        if (signature == null || signature.isEmpty()) return "unsigned";
        if ("signed".equals(status) || "approved".equals(status)) return "verified";
        return "unsigned";
    }

    public String getPriceFormat() {
        return PriceFormatUtils.formatPrice(PriceFormatUtils.PATTERN_VND, totalPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderShowAsItem that = (OrderShowAsItem) o;
        return orderId == that.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderId);
    }
}
