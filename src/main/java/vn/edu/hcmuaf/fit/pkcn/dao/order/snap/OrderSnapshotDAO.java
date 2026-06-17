package vn.edu.hcmuaf.fit.pkcn.dao.order.snap;

import org.jdbi.v3.core.Jdbi;
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
}
