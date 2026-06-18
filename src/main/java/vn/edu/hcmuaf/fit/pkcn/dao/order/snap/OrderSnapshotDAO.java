package vn.edu.hcmuaf.fit.pkcn.dao.order.snap;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.admin.order.AdminOrderShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.model.order.OrderDetail;
import vn.edu.hcmuaf.fit.pkcn.model.order.OrderDetailItem;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.AddressOrderSnapshot;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.OrderDetailSnapshot;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.OrderSnapshot;
import vn.edu.hcmuaf.fit.pkcn.utils.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class OrderSnapshotDAO {
    private final Jdbi jdbi;

    public OrderSnapshotDAO(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public OrderSnapshot getOrderDetailsByOrderId(Integer orderId, Integer userId) {
        return jdbi.withHandle(handle -> {
            OrderSnapshot order = handle.createQuery("SELECT * FROM orders WHERE id = :id AND user_id = :userId")
                    .bind("id", orderId)
                    .bind("userId", userId)
                    .mapToBean(OrderSnapshot.class)
                    .findOne()
                    .orElse(null);

            if (order == null) {
                return null;
            }

            AddressOrderSnapshot address = handle.createQuery("SELECT * FROM address_order WHERE id = :id")
                    .bind("id", order.getAddressOrderId())
                    .mapToBean(AddressOrderSnapshot.class)
                    .findOne()
                    .orElse(null);
            order.setAddressOrderSnapshot(address);

            List<OrderDetailSnapshot> details = handle.createQuery("SELECT * FROM order_details WHERE order_id = :orderId")
                    .bind("orderId", order.getId())
                    .mapToBean(OrderDetailSnapshot.class)
                    .list();
            order.setOrderDetailSnapshots(details);

            return order;
        });
    }

    public boolean saveSignatureAndUserKeyId(Integer orderId, Integer userKeyId, String signature) {
        String sql = """
                UPDATE orders
                SET user_key_id = :userKeyId,
                    signature = :signature,
                    status_order = :statusOrder
                WHERE id = :orderId
                """;

        return jdbi.withHandle(handle -> {
           return handle.createUpdate(sql)
                   .bind("statusOrder", OrderStatus.SIGNED)
                   .bind("orderId", orderId)
                   .bind("signature", signature)
                   .bind("userKeyId", userKeyId)
                   .execute() > 0;
        });
    }

    public HashMap<Integer, List<OrderDetailSnapshot>> getOrderDetailSnapshotsByOrderIds(List<Integer> orderIds) {
        if (orderIds == null || orderIds.isEmpty()) {
            return new HashMap<>();
        }
        return jdbi.withHandle(handle -> {
            HashMap<Integer, List<OrderDetailSnapshot>> result = new LinkedHashMap<>();
            for (Integer id : orderIds) {
                result.put(id, new ArrayList<>());
            }
            List<OrderDetailSnapshot> details = handle.createQuery(
                    "SELECT * FROM order_details WHERE order_id IN (<orderIds>)")
                    .bindList("orderIds", orderIds)
                    .mapToBean(OrderDetailSnapshot.class)
                    .list();
            for (OrderDetailSnapshot detail : details) {
                List<OrderDetailSnapshot> list = result.get(detail.getOrderId());
                if (list != null) {
                    list.add(detail);
                }
            }
            return result;
        });
    }

    public List<OrderSnapshot> getOrdersForAdmin(String key, String status) {
        StringBuilder sql = new StringBuilder("""
        SELECT o.* FROM orders o
        JOIN users u ON u.id = o.user_id
        JOIN address_order ao ON ao.id = o.address_order_id
        WHERE 1=1
        """);

        if (key != null && !key.isEmpty()) {
            sql.append(" AND (CAST(o.id AS CHAR) LIKE :keyPattern OR ao.receiver_name LIKE :keyPattern OR u.full_name LIKE :keyPattern)");
        }

        if (status != null && !status.isEmpty()) {
            sql.append(" AND o.status_order = :status");
        }

        sql.append(" ORDER BY o.order_date DESC");

        String finalSql = sql.toString();

        return jdbi.withHandle(handle -> {
            var query = handle.createQuery(finalSql);
            if (key != null && !key.isEmpty()) {
                query.bind("keyPattern", "%" + key + "%");
            }
            if (status != null && !status.isEmpty()) {
                query.bind("status", status);
            }

            List<OrderSnapshot> orders = query.mapToBean(OrderSnapshot.class).list();

            for (OrderSnapshot order : orders) {

                AddressOrderSnapshot address = handle.createQuery("SELECT * FROM address_order WHERE id = :id")
                        .bind("id", order.getAddressOrderId())
                        .mapToBean(AddressOrderSnapshot.class)
                        .findOne()
                        .orElse(null);
                order.setAddressOrderSnapshot(address);

                List<OrderDetailSnapshot> details = handle.createQuery("SELECT * FROM order_details WHERE order_id = :orderId")
                        .bind("orderId", order.getId())
                        .mapToBean(OrderDetailSnapshot.class)
                        .list();
                order.setOrderDetailSnapshots(details);
            }

            return orders;
        });
    }
    public List<OrderDetailSnapshot> getOrderDetailItems(int orderId) {
        String sql = """
              SELECT id, order_id, product_variant_id, product_name_snapshot, variant_name_snapshot, sku_snapshot, variant_price_snapshot, gram_snapshot, color_snapshot, size_snapshot, quantity, price_total
              FROM order_details 
              WHERE order_id = :orderId
              """;
        return jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("orderId",orderId)
                .mapToBean(OrderDetailSnapshot.class)
                .list());

    }
    public OrderSnapshot getOrderDetail(int orderId) {
        String sql = """
                SELECT id, user_id, payment_method_id, payment_method_snapshot,address_order_id,status_order,total_must_pay,order_date,delivery_date,shipping_fee, note,signature,user_key_id,expire_sign_key
                FROM orders
                WHERE id = :orderId
                """;
        OrderSnapshot orderSnapshot = jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("orderId", orderId)
                .mapToBean(OrderSnapshot.class)
                .findOne()
                .orElse(null)
        );
        if (orderSnapshot == null) {
            return null;
        }
        String addressSql = """
            SELECT id, receiver_name, phone_number, address_detail, district, province_city, note
            FROM address_order
            WHERE id = :addressOrderId
            """;
        AddressOrderSnapshot addressSnapshot = jdbi.withHandle(handle -> handle.createQuery(addressSql)
                .bind("addressOrderId", orderSnapshot.getAddressOrderId())
                .mapToBean(AddressOrderSnapshot.class)
                .findOne()
                .orElse(null)
        );
        orderSnapshot.setAddressOrderSnapshot(addressSnapshot);
        List<OrderDetailSnapshot> detailSnapshots = getOrderDetailItems(orderId);
        orderSnapshot.setOrderDetailSnapshots(detailSnapshots);
        return orderSnapshot;
    }
    public int insertOrder(Handle handle,Integer userId,Integer paymentMethodId, String paymentMethodSnapshot, Integer addressOrderId,String statusOrder, BigDecimal totalMustPay, BigDecimal shippingFee, String note) {
        String sql =
                "INSERT INTO orders (user_id, payment_method_id, payment_method_snapshot, address_order_id, status_order, total_must_pay, order_date, delivery_date, shipping_fee, note)" +
                        "VALUES(:userId, :paymentMethodId,:paymentMethodSnapshot, :addressOrderId, :statusOrder, :totalMustPay, NOW(),DATE_ADD(NOW(), INTERVAL 3 DAY),:shippingFee, :note)";
        return handle.createUpdate(sql)
                .bind("userId",userId)
                .bind("paymentMethodId",paymentMethodId)
                .bind("paymentMethodSnapshot",paymentMethodSnapshot)
                .bind("addressOrderId",addressOrderId)
                .bind("statusOrder", statusOrder)
                .bind("totalMustPay",totalMustPay)
                .bind("shippingFee",shippingFee)
                .bind("note", note)
                .executeAndReturnGeneratedKeys()
                .mapTo(Integer.class)
                .one();
    }
    public void insertOrderDetails(Handle handle, Integer orderId,Integer productVariantId, String productNameSnapshot, String variantNameSnapshot, String skuSnapshot, BigDecimal variantPriceSnapshot, Integer gramSnapshot, String colorSnapshot, String sizeSnapshot, Integer quantity, BigDecimal priceTotal){
        String sql = "INSERT INTO order_details (order_id, product_variant_id, product_name_snapshot, variant_name_snapshot, sku_snapshot, variant_price_snapshot, gram_snapshot, color_snapshot, size_snapshot, quantity, price_total)" +
                "VALUES (:orderId, :vid, :proName, :varName, :sku, :varPrice, :gram, :color, :size, :qty, :price)";
        handle.createUpdate(sql)
                .bind("orderId", orderId)
                .bind("vid", productVariantId)
                .bind("proName", productNameSnapshot)
                .bind("varName", variantNameSnapshot)
                .bind("sku", skuSnapshot)
                .bind("varPrice", variantPriceSnapshot)
                .bind("gram", gramSnapshot)
                .bind("color", colorSnapshot)
                .bind("size", sizeSnapshot)
                .bind("qty", quantity)
                .bind("price", priceTotal)
                .execute();

    }
}
