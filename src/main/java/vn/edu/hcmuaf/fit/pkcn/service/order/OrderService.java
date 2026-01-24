package vn.edu.hcmuaf.fit.pkcn.service.order;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.order.OrderDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.model.cart.Cart;
import vn.edu.hcmuaf.fit.pkcn.model.cart.CartItem;
import vn.edu.hcmuaf.fit.pkcn.model.order.OrderShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariantWrapOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    private OrderDao orderDao;
    private ProductDao productDao;

    public OrderService(OrderDao orderDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
    }

    public OrderService(Jdbi orderDao) {
        this.orderDao = new OrderDao(orderDao);
    }

    public List<OrderShowAsItem> getOrdersShowAsItem(int userId, String status) {
        HashMap<Integer, OrderShowAsItem> res = orderDao.getOrdersShowAsItem(userId, status);
        if (res.isEmpty()) return null;
        //Lấy các id của order để lấy các order detail
        List<Integer> orderIds = res.keySet().stream().toList();

        HashMap<Integer, List<ProductVariantWrapOrder>> orderDetails = productDao.getProdVarWrapOrder(orderIds);
        for (Map.Entry<Integer, List<ProductVariantWrapOrder>> entry : orderDetails.entrySet())
            res.get(entry.getKey()).setOrderDetails(entry.getValue());

        return res.values().stream().toList();
    }
    public void checkOut(int userId, int addressId, String note, Cart cart,double shipFee, int paymentMethodId) throws Exception {
        JDBI.getJdbi().useTransaction(handle -> {
            for (CartItem item : cart.getCartItems()) {
                Integer currentStock = handle.createQuery("SELECT stock FROM product_variants WHERE id = :id FOR UPDATE")
                        .bind("id", item.getProductVariantId())
                        .mapTo(Integer.class).findOne().orElse(0);

                if (item.getQuantity() > currentStock) {
                    throw new Exception("Sản phẩm: " + item.getNameProduct() + " không đủ hàng (Hiện còn: " + currentStock + ")");
                }
            }

            int addressOrderId = orderDao.insertAddressOrder(handle, addressId, note);
            double totalMustPay =  cart.priceTotal() + shipFee;
            int orderId = orderDao.insertOrder(handle, userId, addressOrderId, totalMustPay, note, shipFee,paymentMethodId);

            for (CartItem item : cart.getCartItems()) {
                orderDao.insertOrderDetail(handle, orderId, item);

                handle.createUpdate("UPDATE product_variants SET stock = stock - :qty WHERE id = :vid")
                        .bind("qty", item.getQuantity())
                        .bind("vid", item.getProductVariantId())
                        .execute();

                handle.createUpdate("UPDATE products p Join product_variants pv On p.id = pv.product_id Set p.stock = p.stock - :qty Where pv.id =:vid")
                        .bind("qty", item.getQuantity())
                        .bind("vid", item.getProductVariantId())
                        .execute();
            }
        });
    }
}
