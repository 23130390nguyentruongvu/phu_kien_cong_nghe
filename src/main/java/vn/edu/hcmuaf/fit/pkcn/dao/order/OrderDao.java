package vn.edu.hcmuaf.fit.pkcn.dao.order;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.order.OrderShowAsItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
}
