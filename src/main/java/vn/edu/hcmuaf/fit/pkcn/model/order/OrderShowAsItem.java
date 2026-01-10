package vn.edu.hcmuaf.fit.pkcn.model.order;

import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariantWrapOrder;

import java.util.List;

public class OrderShowAsItem {
    private int orderId;
    private String status;
    private String address;
    private double totalPrice;
    private List<ProductVariantWrapOrder> orderDetails;
}
