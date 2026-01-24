package vn.edu.hcmuaf.fit.pkcn.model.order;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import vn.edu.hcmuaf.fit.pkcn.utils.DateFormatUtils;
import vn.edu.hcmuaf.fit.pkcn.utils.PriceFormatUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDetail {
    private int orderId;
    private int userId;
    private String receiverName, phoneNumber, addressDetail;
    private LocalDateTime oderDate, deliveryDate;
    private String statusOrder, methodPayment;
    private BigDecimal shipFee, totalMustPay;
    private List<OrderDetailItem> items;

    public int getOrderId() {
        return orderId;
    }

    @ColumnName("id")
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

    public String getMethodPayment() {
        return methodPayment;
    }

    @ColumnName("name_method")
    public void setMethodPayment(String methodPayment) {
        this.methodPayment = methodPayment;
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

    public LocalDateTime getOderDate() {
        return oderDate;
    }

    @ColumnName("order_date")
    public void setOderDate(LocalDateTime oderDate) {
        this.oderDate = oderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    @ColumnName("delivery_date")
    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    @ColumnName("status_order")
    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public BigDecimal getShipFee() {
        return shipFee;
    }

    @ColumnName("shipping_fee")
    public void setShipFee(BigDecimal shipFee) {
        this.shipFee = shipFee;
    }

    public BigDecimal getTotalMustPay() {
        return totalMustPay;
    }

    @ColumnName("total_must_pay")
    public void setTotalMustPay(BigDecimal totalMustPay) {
        this.totalMustPay = totalMustPay;
    }

    public List<OrderDetailItem> getItems() {
        return items;
    }

    public void setItems(List<OrderDetailItem> items) {
        this.items = items;
    }

    public String getTotalMustPayFormat() {
        return PriceFormatUtils.formatPrice(PriceFormatUtils.PATTERN_VND, totalMustPay);
    }

    public String getShipFeeFormat() {
        return PriceFormatUtils.formatPrice(PriceFormatUtils.PATTERN_VND, shipFee);
    }

    //không tính ship fee
    public String getTotalOrderFormat() {
        return PriceFormatUtils.formatPrice(PriceFormatUtils.PATTERN_VND, totalMustPay.doubleValue() - shipFee.doubleValue());
    }

    public String getOrderDateFormat() {
        return DateFormatUtils.formatDate(DateFormatUtils.PATTERN_DATE, oderDate);
    }

    public String getDeliveryDateFormat() {
        return DateFormatUtils.formatDate(DateFormatUtils.PATTERN_DATE, deliveryDate);
    }

    public boolean getIsStatusPending() {
        return statusOrder.trim().equalsIgnoreCase("pending");
    }
}
