package vn.edu.hcmuaf.fit.pkcn.model.admin.order;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import vn.edu.hcmuaf.fit.pkcn.utils.DateFormatUtils;
import vn.edu.hcmuaf.fit.pkcn.utils.PriceFormatUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderOverView {
    private int orderId;
    private int userId;
    private BigDecimal totalMustPay;
    private String statusOrder;
    private LocalDateTime orderDate, deliveryDate;

    public int getOrderId() {
        return orderId;
    }

    @ColumnName("order_id")
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    @ColumnName("user_id")
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalMustPay() {
        return totalMustPay;
    }

    @ColumnName("total_must_pay")
    public void setTotalMustPay(BigDecimal totalMustPay) {
        this.totalMustPay = totalMustPay;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    @ColumnName("status_order")
    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    @ColumnName("order_date")
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    @ColumnName("delivery_date")
    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getTotalPriceFormat() {
        return PriceFormatUtils.formatPrice(PriceFormatUtils.PATTERN_VND, totalMustPay);
    }
    public String getOrderDateFormat() {
        return DateFormatUtils.formatDate(DateFormatUtils.PATTERN_DATE, orderDate);
    }

    public String getDeliveryDateFormat() {
        return DateFormatUtils.formatDate(DateFormatUtils.PATTERN_DATE, deliveryDate);
    }
}
