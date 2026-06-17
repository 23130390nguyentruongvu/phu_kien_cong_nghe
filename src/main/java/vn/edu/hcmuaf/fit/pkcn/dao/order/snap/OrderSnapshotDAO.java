package vn.edu.hcmuaf.fit.pkcn.dao.order.snap;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.AddressOrderSnapshot;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.OrderDetailSnapshot;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.OrderSnapshot;

import java.util.*;

public class OrderSnapshotDAO {
    private final Jdbi jdbi;

    public OrderSnapshotDAO(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public OrderSnapshot getOrderDetailsByOrderId(int orderId) {
        return jdbi.withHandle(handle -> {
            OrderSnapshot order = handle.createQuery("SELECT * FROM orders WHERE id = :id")
                    .bind("id", orderId)
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
