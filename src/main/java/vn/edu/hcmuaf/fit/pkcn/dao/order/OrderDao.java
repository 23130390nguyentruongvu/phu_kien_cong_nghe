package vn.edu.hcmuaf.fit.pkcn.dao.order;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.cart.CartItem;
import vn.edu.hcmuaf.fit.pkcn.model.order.OrderShowAsItem;

import java.util.HashMap;
import java.util.Iterator;

public class OrderDao {
    private Jdbi jdbi;

    public OrderDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public HashMap<Integer, OrderShowAsItem> getOrdersShowAsItem(int userId, String status) {
        String filter = status == null?":status IS NULL":"o.status_order = :status";
        String sql = "SELECT o.id, o.status_order, o.total_must_pay, ao.address_detail " +
                "FROM orders o " +
                "JOIN address_order ao ON ao.id = o.address_order_id " +
                "WHERE o.user_id = :userId AND " + filter;
        return jdbi.withHandle(handle -> {
            HashMap<Integer, OrderShowAsItem> res = new HashMap<>();
            Iterator<OrderShowAsItem> iter = handle.createQuery(sql)
                    .bind("userId", userId)
                    .bind("status", status)
                    .mapToBean(OrderShowAsItem.class)
                    .stream().iterator();
            while (iter.hasNext()) {
                OrderShowAsItem tmp = iter.next();
                res.put(tmp.getOrderId(), tmp);
            }
            return res;
        });
    }
    public int insertAddressOrder(Handle handle, int addressId, String note){
        String sql = "INSERT INTO address_order (receiver_name, phone_number, address_detail, district, province_city, note) SELECT receiver_name, phone_number, address_detail, district, province_city, :note  FROM user_address WHERE id = :id";
        return handle.createUpdate(sql)
                .bind("id", addressId)
                .bind("note", note)
                .executeAndReturnGeneratedKeys()
                .mapTo(Integer.class)
                .one();
    }
    public int insertOrder(Handle handle, int userId, int addressOrderId, double total, String note, double shipFee, int paymentMethodId) {
        String sql = "INSERT INTO orders (user_id, address_order_id, total_must_pay, status_order, shipping_fee, payment_method_id, order_date, note, delivery_date) " +
                "VALUES (:userId, :addressOrderId, :total, 'PENDING', :shipFee, :paymentMethodId, NOW(), :note, DATE_ADD(NOW(), INTERVAL 3 DAY))";

        return handle.createUpdate(sql)
                .bind("userId", userId)
                .bind("addressOrderId", addressOrderId)
                .bind("total", total)
                .bind("shipFee", shipFee)
                .bind("paymentMethodId", paymentMethodId)
                .bind("note", note)
                .executeAndReturnGeneratedKeys()
                .mapTo(Integer.class)
                .one();
    }
    public void insertOrderDetail(Handle handle, int orderId, CartItem item) {
        String sql = "INSERT INTO order_details (order_id, product_variant_id, quantity, price_total) VALUES (:oid, :vid, :qty, :price)";
        handle.createUpdate(sql)
                .bind("oid", orderId)
                .bind("vid", item.getProductVariantId())
                .bind("qty", item.getQuantity())
                .bind("price", item.getPrice())
                .execute();
    }
}
