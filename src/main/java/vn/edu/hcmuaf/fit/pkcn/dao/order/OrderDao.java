package vn.edu.hcmuaf.fit.pkcn.dao.order;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.order.OrderShowAsItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrderDao {
    private Jdbi jdbi;

    public OrderDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<OrderShowAsItem> getOrdersShowAsItem(int userId) {
        String sql = "SELECT o.id, o.status_order, o.total_must_pay, ao.address_detail " +
                "FROM orders o " +
                "JOIN address_order ao ON ao.id = o.address_order_id " +
                "WHERE o.user_id = :userId";
        return jdbi.withHandle(handle -> {
            List<OrderShowAsItem> res = new ArrayList<>();
            Iterator<OrderShowAsItem> iter = handle.createQuery(sql)
                    .bind("userId", userId)
                    .mapToBean(OrderShowAsItem.class)
                    .stream().iterator();
            while (iter.hasNext()) res.add(iter.next());
            return res;
        });
    }
}
