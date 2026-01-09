package vn.edu.hcmuaf.fit.pkcn.model.cart;

import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariant;
import vn.edu.hcmuaf.fit.pkcn.utils.FormatUtils;

import java.io.Serializable;
import java.util.Objects;

public class CartItem implements Serializable {
    private int productVariantId;
    private int quantity;
    private String nameProduct; //Đây là tên của product chung chứa biến thể
    private ProductVariant productVariant;
    private double price; //Đây là giá của quantity*prodVar.price

    public CartItem(int productVariantId, int quantity, String nameProduct, double price, ProductVariant prodVar) {
        this.productVariantId = productVariantId;
        this.quantity = quantity;
        this.nameProduct = nameProduct;
        this.price = price;
        this.productVariant = prodVar;
    }

    public boolean plusOneQuantity() {
        this.quantity++;
        this.price = this.quantity * this.productVariant.getPrice().doubleValue();
        return true;
    }

    public boolean minusOneQuantity() {
        this.quantity--;
        if (this.quantity <= 0) {
            this.quantity = 1;
            this.price = this.quantity * this.productVariant.getPrice().doubleValue();
            return false;
        }
        this.price = this.quantity * this.productVariant.getPrice().doubleValue();
        return true;
    }

    public void updateQuantity(int quantity) {
        if (quantity < 1) quantity = 1;
        this.quantity = quantity;
        this.price = this.quantity * this.productVariant.getPrice().doubleValue();
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public int getProductVariantId() {
        return productVariantId;
    }

    public double getPrice() {
        return price;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public String getNameDetail() {
        return nameProduct + ", loại: " + productVariant.getName();
    }

    public String getPriceByFormat() {
        return FormatUtils.formatPrice(FormatUtils.PATTERN_VND, price);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return productVariantId == cartItem.productVariantId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productVariantId);
    }
}
