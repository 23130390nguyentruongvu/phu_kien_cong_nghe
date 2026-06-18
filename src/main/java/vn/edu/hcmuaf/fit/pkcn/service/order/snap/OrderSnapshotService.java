package vn.edu.hcmuaf.fit.pkcn.service.order.snap;

import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.order.snap.OrderSnapshotDAO;
import vn.edu.hcmuaf.fit.pkcn.model.cart.Cart;
import vn.edu.hcmuaf.fit.pkcn.model.cart.CartItem;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.OrderSnapshot;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariant;
import vn.edu.hcmuaf.fit.pkcn.model.user.Address;
import vn.edu.hcmuaf.fit.pkcn.utils.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    public OrderSnapshot getOrderSnapshotByOrderId(Integer orderId) {
        return orderSnapshotDAO.getOrderDetail(orderId);
    }

    public void checkOut(int userId,
                         Address address,
                         String note,
                         Cart cart,
                         double shipFee,
                         int paymentMethodId,
                         String paymentMethodSnapshot) throws Exception {

        JDBI.getJdbi().useTransaction(handle -> {
            for (CartItem item : cart.getCartItems()) {
                Integer currentStock = handle.createQuery("""
                                SELECT stock
                                FROM product_variants
                                WHERE id = :id
                                FOR UPDATE
                                """)
                        .bind("id", item.getProductVariantId())
                        .mapTo(Integer.class)
                        .findOne()
                        .orElse(0);

                if (item.getQuantity() > currentStock) {
                    throw new RuntimeException(
                            "Sản phẩm " + item.getNameProduct()
                                    + " không đủ hàng. Hiện còn "
                                    + currentStock + " sản phẩm"
                    );
                }
            }

            String insertAddressSql = """
                    INSERT INTO address_order (receiver_name, phone_number, address_detail, district, province_city, note)
                    VALUES (:name, :phone, :detail, :district, :province, :note)
                    """;

            int generatedAddressOrderId = handle.createUpdate(insertAddressSql)
                    .bind("name", address.getReceiverName())
                    .bind("phone", address.getPhoneNumber())
                    .bind("detail", address.getAddressDetail())
                    .bind("district", address.getDistrict())
                    .bind("province", address.getProvinceCity())
                    .bind("note", note)
                    .executeAndReturnGeneratedKeys()
                    .mapTo(Integer.class)
                    .one();

            BigDecimal totalMustPay =
                    BigDecimal.valueOf(cart.priceTotal())
                            .add(BigDecimal.valueOf(shipFee));

            int orderId = orderSnapshotDAO.insertOrder(
                    handle,
                    userId,
                    paymentMethodId,
                    paymentMethodSnapshot,
                    generatedAddressOrderId,
                    OrderStatus.PENDING.name(),
                    totalMustPay,
                    BigDecimal.valueOf(shipFee),
                    note
            );
            for (CartItem item : cart.getCartItems()) {
                ProductVariant pv = item.getProductVariant();
                BigDecimal lineTotal = pv.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

                orderSnapshotDAO.insertOrderDetails(
                        handle,
                        orderId,
                        pv.getId(),
                        item.getNameProduct(),
                        pv.getName(),
                        pv.getSku(),
                        pv.getPrice(),
                        pv.getGram(),
                        pv.getColor(),
                        pv.getSize(),
                        item.getQuantity(),
                        lineTotal
                );
                handle.createUpdate("""
                                UPDATE product_variants
                                SET stock = stock - :qty
                                WHERE id = :vid
                                """)
                        .bind("qty", item.getQuantity())
                        .bind("vid", pv.getId())
                        .execute();

                handle.createUpdate("""
                                UPDATE products p
                                JOIN product_variants pv ON p.id = pv.product_id
                                SET p.stock = p.stock - :qty
                                WHERE pv.id = :vid
                                """)
                        .bind("qty", item.getQuantity())
                        .bind("vid", pv.getId())
                        .execute();
            }

            cart.clearCart();
        });

    }
    public List<OrderSnapshot> getOrderSnapshotByUserId(Integer userId, String statusFilter) {
        return orderSnapshotDAO.getOrdersByUserId(userId, statusFilter);
    }
}
