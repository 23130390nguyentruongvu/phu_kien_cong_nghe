package vn.edu.hcmuaf.fit.pkcn.service.order.snap;

import vn.edu.hcmuaf.fit.pkcn.dao.order.snap.OrderSnapshotDAO;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.OrderSnapshot;

import java.util.List;

public class OrderSnapshotService {
    private final OrderSnapshotDAO orderSnapshotDAO;

    public OrderSnapshotService(OrderSnapshotDAO orderSnapshotDAO) {
        this.orderSnapshotDAO = orderSnapshotDAO;
    }

    //phuong thuc nay kiem tra don hang co phai cua nguoi dung khong truoc khi lay ra don hang do
    public OrderSnapshot getOrderSnapshotsByOrderId(Integer orderId, Integer userId) {
        return orderSnapshotDAO.getOrderDetailsByOrderId(orderId, userId);
    }

    public boolean saveSignatureAndUserKeyId(Integer orderId, Integer userKeyId, String signature) {
        return orderSnapshotDAO.saveSignatureAndUserKeyId(orderId, userKeyId, signature);
    }

    public List<OrderSnapshot> getOrderSnapshotsForAdmin(String key, String status) {
        return orderSnapshotDAO.getOrdersForAdmin(key, status);
    }

    public List<OrderSnapshot> getOrderSnapshotByUserId(Integer userId, String statusFilter) {
        return orderSnapshotDAO.getOrdersByUserId(userId, statusFilter);
    }
}
