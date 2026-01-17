package vn.edu.hcmuaf.fit.pkcn.model.product;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import vn.edu.hcmuaf.fit.pkcn.utils.FormatUtils;

import java.util.Objects;

//lớp này có nhiệm vụ bọc dữ liệu của từng item con bên trong 1 item order của order history
public class ProductVariantWrapOrder {
    private int orderDetailId;
    private int orderId;
    private int prodVarId;//mã của biến thể
    private String name;//Tên sp cha
    private String type;//loại biến thể
    private int quantity;//số lượng trong order detail
    private double totalPrice;
    private String urlImage;

    public ProductVariantWrapOrder() {
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    @ColumnName("id")
    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getProdVarId() {
        return prodVarId;
    }

    @ColumnName("product_variant_id")
    public void setProdVarId(int prodVarId) {
        this.prodVarId = prodVarId;
    }

    public String getName() {
        return name;
    }

    public int getOrderId() {
        return orderId;
    }

    @ColumnName("order_id")
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @ColumnName("name_product")
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

    public double getTotalPrice() {
        return totalPrice;
    }

    @ColumnName("price_total")
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUrlImage() {
        return urlImage;
    }

    @ColumnName("url_image")
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getPriceByFormat() {
        return FormatUtils.formatPrice(FormatUtils.PATTERN_VND, totalPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductVariantWrapOrder that = (ProductVariantWrapOrder) o;
        return orderDetailId == that.orderDetailId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderDetailId);
    }
}
