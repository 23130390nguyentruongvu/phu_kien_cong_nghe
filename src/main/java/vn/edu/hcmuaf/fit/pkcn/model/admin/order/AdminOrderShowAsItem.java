package vn.edu.hcmuaf.fit.pkcn.model.admin.order;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import vn.edu.hcmuaf.fit.pkcn.utils.DateFormatUtils;
import vn.edu.hcmuaf.fit.pkcn.utils.PriceFormatUtils;
import vn.edu.hcmuaf.fit.pkcn.utils.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AdminOrderShowAsItem {
    private int orderId;
    private int userId;
    private String receiverName;
    private String phoneNumber;
    private String addressDetail;
    private BigDecimal totalMustPay;
    private String statusOrder;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private Boolean snapshotValid;
    private String signature;

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

    public String getReceiverName() {
        return receiverName;
    }

    @ColumnName("receiver_name")
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @ColumnName("phone_number")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    @ColumnName("address_detail")
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
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

    @ColumnName("snapshot_valid")
    public Boolean getSnapshotValid() {
        return snapshotValid;
    }

    public void setSnapshotValid(Boolean snapshotValid) {
        this.snapshotValid = snapshotValid;
    }

    @ColumnName("signature")
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    public String getStatusDisplay() {
        if (statusOrder == null) return "";
        try {
            return OrderStatus.fromCode(statusOrder).getDisplayName();
        } catch (IllegalArgumentException e) {
            return statusOrder;
        }
    }

//    public String getVerifyStatus() {
//        if (statusOrder == null) return "unsigned";
//        if ("security_alert".equals(statusOrder)) return "tampered";
//        if (signature == null || signature.isEmpty()) return "unsigned";
//        if ("signed".equals(statusOrder) || "approved".equals(statusOrder)) return "verified";
//        return "unsigned";
//    }
}
