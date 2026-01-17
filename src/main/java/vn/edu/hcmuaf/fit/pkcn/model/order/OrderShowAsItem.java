package vn.edu.hcmuaf.fit.pkcn.model.order;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariantWrapOrder;
import vn.edu.hcmuaf.fit.pkcn.utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderShowAsItem {
    private int orderId;
    private String status;
    private String address;
    private double totalPrice;
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

    public String getPriceFormat() {
        return FormatUtils.formatPrice(FormatUtils.PATTERN_VND, totalPrice);
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
