package vn.edu.hcmuaf.fit.pkcn.model.order;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private int id;
    private int userId;
    private int paymentMethodId;
    private int addressOrderId;
    private double shippingFee;
    private double totalMustPay;
    private String statusOrder;
    private String note;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;

    public Order(int id, int userId, int paymentMethodId, int addressOrderId, double shippingFee, double totalMustPay, String statusOrder, String note, LocalDateTime orderDate, LocalDateTime deliveryDate) {
        this.id = id;
        this.userId = userId;
        this.paymentMethodId = paymentMethodId;
        this.addressOrderId = addressOrderId;
        this.shippingFee = shippingFee;
        this.totalMustPay = totalMustPay;
        this.statusOrder = statusOrder;
        this.note = note;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
    }

    public int getId() {
        return id;
    }
    @ColumnName("id")
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    @ColumnName("user_id")
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }
    @ColumnName("payment_method_id")
    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public int getAddressOrderId() {
        return addressOrderId;
    }
    @ColumnName("address_order_id")
    public void setAddressOrderId(int addressOrderId) {
        this.addressOrderId = addressOrderId;
    }

    public double getShippingFee() {
        return shippingFee;
    }
    @ColumnName("shipping_fee")
    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public double getTotalMustPay() {
        return totalMustPay;
    }
    @ColumnName("total_must_pay")
    public void setTotalMustPay(double totalMustPay) {
        this.totalMustPay = totalMustPay;
    }

    public String getStatusOrder() {
        return statusOrder;
    }
    @ColumnName("status_order")
    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public String getNote() {
        return note;
    }
    @ColumnName("note")
    public void setNote(String note) {
        this.note = note;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && userId == order.userId && paymentMethodId == order.paymentMethodId && addressOrderId == order.addressOrderId && Double.compare(shippingFee, order.shippingFee) == 0 && Double.compare(totalMustPay, order.totalMustPay) == 0 && Objects.equals(statusOrder, order.statusOrder) && Objects.equals(note, order.note) && Objects.equals(orderDate, order.orderDate) && Objects.equals(deliveryDate, order.deliveryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, paymentMethodId, addressOrderId, shippingFee, totalMustPay, statusOrder, note, orderDate, deliveryDate);
    }
}
