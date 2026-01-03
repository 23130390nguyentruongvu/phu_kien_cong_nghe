package vn.edu.hcmuaf.fit.pkcn.model.cart;

import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariant;

public class CartItem {
    private int productVariantId;
    private int quantity;
    private String nameProduct; //Đây là tên của product chung chứa biến thể
    private ProductVariant productVariant;
    private double price; //Đây là giá của quantity*prodVar.price

    public CartItem(int productVariantId, int quantity, String nameProduct, double price) {
        this.productVariantId = productVariantId;
        this.quantity = quantity;
        this.nameProduct = nameProduct;
        this.price = price;
    }

    public void updateQuantity(int quantity) {
        if (quantity < 1) quantity = 1;
        this.quantity = quantity;
        this.price = this.quantity * this.productVariant.getPrice().doubleValue();
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
}
