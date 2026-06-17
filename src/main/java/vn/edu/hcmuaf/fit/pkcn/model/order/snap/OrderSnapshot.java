package vn.edu.hcmuaf.fit.pkcn.model.order.snap;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderSnapshot {
    private Integer id;
    private Integer userId;
    private Integer paymentMethodId;
    private String paymentMethodSnapshot;
    private Integer addressOrderId;
    private String statusOrder;
    private BigDecimal totalMustPay;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private BigDecimal shippingFee;
    private String note;
    private String signature;
    private Integer userKeyId;
    private LocalDateTime expireSignKey;
    private Boolean snapshotValid;
    private AddressOrderSnapshot addressOrderSnapshot;
    private List<OrderDetailSnapshot> orderDetailSnapshots;

    public OrderSnapshot() {}

    public OrderSnapshot(Integer id, Integer userId, Integer paymentMethodId, String paymentMethodSnapshot,
                         Integer addressOrderId, String statusOrder, BigDecimal totalMustPay, LocalDateTime orderDate,
                         LocalDateTime deliveryDate, BigDecimal shippingFee, String note, String signature,
                         Integer userKeyId, LocalDateTime expireSignKey, Boolean snapshotValid, AddressOrderSnapshot addressOrderSnapshot,
                         List<OrderDetailSnapshot> orderDetailSnapshots) {
        this.id = id;
        this.userId = userId;
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodSnapshot = paymentMethodSnapshot;
        this.addressOrderId = addressOrderId;
        this.statusOrder = statusOrder;
        this.totalMustPay = totalMustPay;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.shippingFee = shippingFee;
        this.note = note;
        this.signature = signature;
        this.userKeyId = userKeyId;
        this.expireSignKey = expireSignKey;
        this.snapshotValid = snapshotValid;
        this.addressOrderSnapshot = addressOrderSnapshot;
        this.orderDetailSnapshots = orderDetailSnapshots;
    }

    @ColumnName("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ColumnName("user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @ColumnName("payment_method_id")
    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    @ColumnName("payment_method_snapshot")
    public String getPaymentMethodSnapshot() {
        return paymentMethodSnapshot;
    }

    public void setPaymentMethodSnapshot(String paymentMethodSnapshot) {
        this.paymentMethodSnapshot = paymentMethodSnapshot;
    }

    @ColumnName("address_order_id")
    public Integer getAddressOrderId() {
        return addressOrderId;
    }

    public void setAddressOrderId(Integer addressOrderId) {
        this.addressOrderId = addressOrderId;
    }

    @ColumnName("status_order")
    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    @ColumnName("total_must_pay")
    public BigDecimal getTotalMustPay() {
        return totalMustPay;
    }

    public void setTotalMustPay(BigDecimal totalMustPay) {
        this.totalMustPay = totalMustPay;
    }

    @ColumnName("order_date")
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @ColumnName("delivery_date")
    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @ColumnName("shipping_fee")
    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    @ColumnName("note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @ColumnName("signature")
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @ColumnName("user_key_id")
    public Integer getUserKeyId() {
        return userKeyId;
    }

    public void setUserKeyId(Integer userKeyId) {
        this.userKeyId = userKeyId;
    }

    @ColumnName("expire_sign_key")
    public LocalDateTime getExpireSignKey() {
        return expireSignKey;
    }

    public void setExpireSignKey(LocalDateTime expireSignKey) {
        this.expireSignKey = expireSignKey;
    }

    @ColumnName("snapshot_valid")
    public Boolean getSnapshotValid() {
        return snapshotValid;
    }

    public void setSnapshotValid(Boolean snapshotValid) {
        this.snapshotValid = snapshotValid;
    }

    public AddressOrderSnapshot getAddressOrderSnapshot() {
        return addressOrderSnapshot;
    }

    public void setAddressOrderSnapshot(AddressOrderSnapshot addressOrderSnapshot) {
        this.addressOrderSnapshot = addressOrderSnapshot;
    }

    public List<OrderDetailSnapshot> getOrderDetailSnapshots() {
        return orderDetailSnapshots;
    }

    public void setOrderDetailSnapshots(List<OrderDetailSnapshot> orderDetailSnapshots) {
        this.orderDetailSnapshots = orderDetailSnapshots;
    }
}