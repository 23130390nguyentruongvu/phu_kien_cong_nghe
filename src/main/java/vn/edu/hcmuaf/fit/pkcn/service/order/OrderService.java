package vn.edu.hcmuaf.fit.pkcn.service.order;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.order.OrderDao;
import vn.edu.hcmuaf.fit.pkcn.dao.order.snap.OrderSnapshotDAO;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.model.admin.order.AdminOrderShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.model.admin.order.OrderOverView;
import vn.edu.hcmuaf.fit.pkcn.model.cart.Cart;
import vn.edu.hcmuaf.fit.pkcn.model.cart.CartItem;
import vn.edu.hcmuaf.fit.pkcn.model.order.OrderDetail;
import vn.edu.hcmuaf.fit.pkcn.model.order.OrderDetailItem;
import vn.edu.hcmuaf.fit.pkcn.model.order.OrderShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.OrderDetailSnapshot;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariantWrapOrder;

import java.util.*;

public class OrderService {
    private OrderDao orderDao;
    private OrderSnapshotDAO orderSnapshotDAO;

    public OrderService(OrderDao orderDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.orderSnapshotDAO = new OrderSnapshotDAO(JDBI.getJdbi());
    }

    public OrderService(Jdbi jdbi) {
        this.orderDao = new OrderDao(jdbi);
        this.orderSnapshotDAO = new OrderSnapshotDAO(jdbi);
    }

    public int getUserIdByOrderId(int orderId) {
        return orderDao.getUserIdByOrderId(orderId);
    }

    public List<OrderShowAsItem> getOrdersShowAsItem(int userId, String status) {
        HashMap<Integer, OrderShowAsItem> res = orderDao.getOrdersShowAsItem(userId, status);
        if (res.isEmpty()) return null;
        List<Integer> orderIds = res.keySet().stream().toList();

        HashMap<Integer, List<OrderDetailSnapshot>> snapDetails = orderSnapshotDAO.getOrderDetailSnapshotsByOrderIds(orderIds);
        for (Map.Entry<Integer, List<OrderDetailSnapshot>> entry : snapDetails.entrySet()) {
            List<ProductVariantWrapOrder> wrapList = new ArrayList<>();
            for (OrderDetailSnapshot snap : entry.getValue()) {
                ProductVariantWrapOrder wrap = new ProductVariantWrapOrder();
                wrap.setOrderDetailId(snap.getId());
                wrap.setOrderId(snap.getOrderId());
                wrap.setProdVarId(snap.getProductVariantId());
                wrap.setName(snap.getProductNameSnapshot());
                wrap.setType(snap.getVariantNameSnapshot());
                wrap.setQuantity(snap.getQuantity());
                wrap.setTotalPrice(snap.getPriceTotal() != null ? snap.getPriceTotal().doubleValue() : 0);
//                wrap.setUrlImage(snap.getUrlImage());
                wrapList.add(wrap);
            }
            OrderShowAsItem item = res.get(entry.getKey());
            if (item != null) {
                item.setOrderDetails(wrapList);
            }
        }

        return res.values().stream().toList();
    }


    public OrderDetail getOrderDetailByOrderId(int orderId) throws Exception {
        OrderDetail orderDetail = orderDao.getOrderDetail(orderId);
        if (orderDetail == null) throw new Exception("Không tìm thấy order detail với order id = " + orderId);
        //Lấy các detail item
        List<OrderDetailItem> items = orderDao.getOrderDetailItems(orderId);
        if (items == null || items.isEmpty())
            throw new Exception("Không tìm thấy các order item với order id = " + orderId);

        orderDetail.setItems(items);
        return orderDetail;
    }

    public void checkOut(int userId, int addressId, String note, Cart cart, double shipFee, int paymentMethodId) throws Exception {
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
            double totalMustPay = cart.priceTotal() + shipFee;
            int orderId = orderDao.insertOrder(handle, userId, addressOrderId, totalMustPay, note, shipFee, paymentMethodId);

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

    public boolean cancelOrderWithTransaction(Handle handle, int orderId) {
        return orderDao.setStatusOrder(handle, orderId, "cancel") > 0;
    }

    //Map có key là mã biến thể và value là quantity của order detail
    public HashMap<Integer, Integer> getVariantIdsAndQuantitiesByOrderIdWithTransaction(Handle handle, int orderId) {
        return orderDao.getVariantIdsAndQuantitiesByOrderIdWithTransaction(handle, orderId);
    }

    public int getOrderDelivered() {
        return orderDao.getQuantityOrderDelivered();
    }

    public double getRevenue() {
        return orderDao.getRevenue();
    }

    public List<AdminOrderShowAsItem> getOrdersForAdmin(String key, String status) {
        return orderDao.getOrdersForAdmin(key, status);
    }

    public List<OrderOverView> getOrderOverView(boolean isFilter, int week) {
        return orderDao.getOrderOverView(isFilter, week);
    }
}
