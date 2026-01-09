package vn.edu.hcmuaf.fit.pkcn.model.cart;

import vn.edu.hcmuaf.fit.pkcn.model.product.ProductDetail;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariant;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.utils.FormatUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
    Cart sẽ được tạo trong trường hợp user click vào thêm giỏ hàng (check có đn hay không để bỏ user vào) và trong
    trường hợp user tiến vào trang view cart
 */
public class Cart implements Serializable {
    private HashMap<Integer, CartItem> cart;
    private User user;

    public Cart(User user) {
        cart = new HashMap<>();
        if (user != null) this.user = user;
    }

    /*
    Đối với prodVar chứa các thông tin của 1 biến thể, nameProd là tên cha của biến
    thể đó, ta cần hiển thị cả tên sản phẩm chung và loại biến thể

    Dùng cho các trang như sản phẩm chi tiết
     */
    public void addCartItem(ProductVariant prodVar, String nameProd, int quantity) {
        if (nameProd == null || prodVar == null) return;
        CartItem cartItem = cart.get(prodVar.getId());
        if (cartItem == null)
            cartItem = new CartItem(
                    prodVar.getId(),
                    quantity,
                    nameProd,
                    0,
                    prodVar
            );
        cartItem.updateQuantity(quantity);
        cart.put(prodVar.getId(), cartItem);
    }

    public boolean plusOrMinusOneQuantity(int prodVarId, boolean isPlus) {
        CartItem cartItem = cart.get(prodVarId);
        if (cartItem == null) return false;
        return isPlus ? cartItem.plusOneQuantity() : cartItem.minusOneQuantity();
    }

    public boolean updateCartItem(int prodVarId, int quantity) {
        CartItem cartItem = cart.get(prodVarId);
        if (cartItem == null) return false;
        cartItem.updateQuantity(quantity);
        return true;
    }

    public CartItem removeCartItem(int prodVarId) {
        return cart.remove(prodVarId);
    }

    public void clearCart() {
        cart.clear();
    }

    public List<CartItem> clearAndGetCartItems() {
        ArrayList<CartItem> list = new ArrayList<>(cart.values());
        cart.clear();
        return list;
    }

    public List<CartItem> getCartItems() {
        try {
            return cart.values().stream().toList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public boolean mergeUser(User user) {
        if (this.user != null) return false;
        this.user = user;
        return true;
    }

    public double priceTotal() {
        List<CartItem> items = getCartItems();
        double priceTotal = 0;
        for (CartItem item : items) {
            priceTotal += item.getPrice();
        }
        return priceTotal;
    }

    public String getPriceByFormat() {
        return FormatUtils.formatPrice(FormatUtils.PATTERN_VND, priceTotal());
    }
}
