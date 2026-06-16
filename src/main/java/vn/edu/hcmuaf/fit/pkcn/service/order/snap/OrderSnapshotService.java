package vn.edu.hcmuaf.fit.pkcn.service.order.snap;

import vn.edu.hcmuaf.fit.pkcn.dao.order.snap.OrderSnapshotDAO;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.OrderSnapshot;

public class OrderSnapshotService {
    private final OrderSnapshotDAO orderSnapshotDAO;

    public OrderSnapshotService(OrderSnapshotDAO orderSnapshotDAO) {
        this.orderSnapshotDAO = orderSnapshotDAO;
    }

    public OrderSnapshot getOrderSnapshotsByOrderId(Integer orderId) {
        return orderSnapshotDAO.getOrderDetailsByOrderId(orderId);
    }
}
