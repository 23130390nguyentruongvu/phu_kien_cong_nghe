package vn.edu.hcmuaf.fit.pkcn.dao.order.snap;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.admin.order.AdminOrderShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.AddressOrderSnapshot;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.OrderDetailSnapshot;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.OrderSnapshot;
import vn.edu.hcmuaf.fit.pkcn.utils.enums.OrderStatus;

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

    public List<OrderSnapshot> getOrdersByUserId(Integer userId, String statusFilter) {
        StringBuilder sql = new StringBuilder("""
        SELECT o.* FROM orders o
        WHERE o.user_id = :userId
        """);

        if (statusFilter != null && !statusFilter.isEmpty()) {
            sql.append(" AND o.status_order = :statusFilter");
        }

        sql.append(" ORDER BY o.order_date DESC");

        String finalSql = sql.toString();

        return jdbi.withHandle(handle -> {
            var query = handle.createQuery(finalSql)
                    .bind("userId", userId);

            if (statusFilter != null && !statusFilter.isEmpty()) {
                query.bind("statusFilter", statusFilter);
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

    public OrderSnapshot getOrderSnapshotByOrderId(Integer orderId) {
        String sql = """
                SELECT *
                FROM orders
                WHERE id = :orderId
                """;

        return jdbi.withHandle(handle -> {
           return handle.createQuery(sql)
                   .bind("orderId", orderId)
                   .mapToBean(OrderSnapshot.class)
                   .findOne()
                   .orElse(null);
        });
    }
}
