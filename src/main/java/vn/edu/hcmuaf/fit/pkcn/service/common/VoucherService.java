package vn.edu.hcmuaf.fit.pkcn.service.common;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.cart.Cart;
import vn.edu.hcmuaf.fit.pkcn.model.cart.CartItem;
import vn.edu.hcmuaf.fit.pkcn.model.common.VoucherApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VoucherService {
    private final Jdbi jdbi;

    public VoucherService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<Map<String, Object>> getAvailableVouchers(Integer userId) {
        return jdbi.withHandle(handle -> getAvailableVouchers(handle, userId));
    }

    public List<Map<String, Object>> getApplicableVouchers(Cart cart, Integer userId, BigDecimal shippingFee) {
        if (cart == null || cart.getCartItems().isEmpty()) {
            return List.of();
        }

        return jdbi.withHandle(handle -> {
            List<Map<String, Object>> offers = new ArrayList<>();
            for (Map<String, Object> voucher : getAvailableVouchers(handle, userId)) {
                VoucherApplication application = calculateApplication(handle, voucher, cart, shippingFee);
                if (application == null) {
                    continue;
                }
                Map<String, Object> offer = new HashMap<>(voucher);
                offer.put("eligibleSubtotal", application.getEligibleSubtotal());
                offer.put("discountAmount", application.getDiscountAmount());
                offers.add(offer);
            }
            return offers;
        });
    }

    public VoucherApplication applySelectedVoucher(Handle handle, int voucherId, int userId,
                                                    Cart cart, BigDecimal shippingFee) {
        Map<String, Object> voucher = handle.createQuery("""
                        SELECT *
                        FROM vouchers
                        WHERE id = :id AND status = 1
                          AND start_date <= CURRENT_TIMESTAMP AND end_date >= CURRENT_TIMESTAMP
                        FOR UPDATE
                        """)
                .bind("id", voucherId)
                .mapToMap()
                .findOne()
                .map(HashMap::new)
                .orElseThrow(() -> new IllegalArgumentException("Voucher không tồn tại hoặc đã hết hạn"));

        Object usageLimit = voucher.get("usage_limit");
        if (usageLimit != null && number(usageLimit).intValue() <= number(voucher.get("used_count")).intValue()) {
            throw new IllegalArgumentException("Voucher đã hết lượt sử dụng");
        }
        int perUserLimit = number(voucher.get("usage_limit_per_user")).intValue();
        int userUsageCount = handle.createQuery("""
                        SELECT COUNT(*)
                        FROM voucher_usages
                        WHERE voucher_id = :voucherId AND user_id = :userId
                        """)
                .bind("voucherId", voucherId)
                .bind("userId", userId)
                .mapTo(Integer.class)
                .one();
        if (userUsageCount >= perUserLimit) {
            throw new IllegalArgumentException("Bạn đã sử dụng hết lượt của voucher này");
        }

        VoucherApplication application = calculateApplication(handle, voucher, cart, shippingFee);
        if (application == null) {
            throw new IllegalArgumentException("Voucher không áp dụng cho đơn hàng hiện tại");
        }
        return application;
    }

    public List<Integer> getSelectedCategoryIds(Handle handle, int voucherId) {
        return handle.createQuery("SELECT category_id FROM voucher_categories WHERE voucher_id = :id")
                .bind("id", voucherId)
                .mapTo(Integer.class)
                .list();
    }

    public List<Integer> getSelectedProductIds(Handle handle, int voucherId) {
        return handle.createQuery("SELECT product_id FROM voucher_products WHERE voucher_id = :id")
                .bind("id", voucherId)
                .mapTo(Integer.class)
                .list();
    }

    private List<Map<String, Object>> getAvailableVouchers(Handle handle, Integer userId) {
        String sql = """
                SELECT v.*,
                       COALESCE((SELECT GROUP_CONCAT(c.category_name ORDER BY c.category_name SEPARATOR ', ')
                                 FROM voucher_categories vc
                                 JOIN categories c ON c.id = vc.category_id
                                 WHERE vc.voucher_id = v.id), '') AS category_names,
                       COALESCE((SELECT GROUP_CONCAT(p.name ORDER BY p.name SEPARATOR ', ')
                                 FROM voucher_products vp
                                 JOIN products p ON p.id = vp.product_id
                                 WHERE vp.voucher_id = v.id), '') AS product_names
                FROM vouchers v
                WHERE v.status = 1
                  AND v.start_date <= CURRENT_TIMESTAMP
                  AND v.end_date >= CURRENT_TIMESTAMP
                  AND (v.usage_limit IS NULL OR v.used_count < v.usage_limit)
                ORDER BY v.end_date, v.id
                """;
        List<Map<String, Object>> vouchers = new ArrayList<>();
        for (Map<String, Object> row : handle.createQuery(sql).mapToMap().list()) {
            Map<String, Object> voucher = new HashMap<>(row);
            if (userId != null) {
                int perUserLimit = number(voucher.get("usage_limit_per_user")).intValue();
                int usedByUser = handle.createQuery("""
                                SELECT COUNT(*) FROM voucher_usages
                                WHERE voucher_id = :voucherId AND user_id = :userId
                                """)
                        .bind("voucherId", voucher.get("id"))
                        .bind("userId", userId)
                        .mapTo(Integer.class)
                        .one();
                if (usedByUser >= perUserLimit) {
                    continue;
                }
            }
            vouchers.add(voucher);
        }
        return vouchers;
    }

    private VoucherApplication calculateApplication(Handle handle, Map<String, Object> voucher,
                                                      Cart cart, BigDecimal shippingFee) {
        Set<Integer> cartProductIds = new HashSet<>();
        for (CartItem item : cart.getCartItems()) {
            cartProductIds.add(item.getProductVariant().getProductId());
        }
        if (cartProductIds.isEmpty()) {
            return null;
        }

        int voucherId = number(voucher.get("id")).intValue();
        List<Integer> productTargets = getSelectedProductIds(handle, voucherId);
        List<Integer> categoryTargets = getSelectedCategoryIds(handle, voucherId);
        Set<Integer> eligibleProductIds = new HashSet<>();
        if (productTargets.isEmpty() && categoryTargets.isEmpty()) {
            eligibleProductIds.addAll(cartProductIds);
        } else {
            for (Integer productId : productTargets) {
                if (cartProductIds.contains(productId)) {
                    eligibleProductIds.add(productId);
                }
            }
            if (!categoryTargets.isEmpty()) {
                eligibleProductIds.addAll(handle.createQuery("""
                                SELECT DISTINCT product_id
                                FROM product_categories
                                WHERE product_id IN (<productIds>)
                                  AND category_id IN (<categoryIds>)
                                """)
                        .bindList("productIds", cartProductIds)
                        .bindList("categoryIds", categoryTargets)
                        .mapTo(Integer.class)
                        .list());
            }
        }

        BigDecimal eligibleSubtotal = BigDecimal.ZERO;
        BigDecimal orderSubtotal = BigDecimal.ZERO;
        for (CartItem item : cart.getCartItems()) {
            BigDecimal lineAmount = item.getProductVariant().getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            orderSubtotal = orderSubtotal.add(lineAmount);
            if (eligibleProductIds.contains(item.getProductVariant().getProductId())) {
                eligibleSubtotal = eligibleSubtotal.add(lineAmount);
            }
        }
        if (eligibleSubtotal.compareTo(BigDecimal.ZERO) <= 0
                || orderSubtotal.compareTo(number(voucher.get("min_order_value"))) < 0) {
            return null;
        }

        String discountType = String.valueOf(voucher.get("discount_type"));
        BigDecimal discount;
        if ("FREESHIP".equals(discountType)) {
            discount = shippingFee;
        } else if ("FIXED".equals(discountType)) {
            discount = number(voucher.get("discount_value")).min(eligibleSubtotal);
            Object maxDiscount = voucher.get("max_discount_amount");
            if (maxDiscount != null) {
                discount = discount.min(number(maxDiscount));
            }
        } else if ("PERCENT".equals(discountType)) {
            discount = eligibleSubtotal
                    .multiply(number(voucher.get("discount_value")))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            Object maxDiscount = voucher.get("max_discount_amount");
            if (maxDiscount != null && discount.compareTo(number(maxDiscount)) > 0) {
                discount = number(maxDiscount);
            }
            discount = discount.min(eligibleSubtotal);
        } else {
            return null;
        }

        Object maxDiscount = voucher.get("max_discount_amount");
        if ("FREESHIP".equals(discountType) && maxDiscount != null) {
            discount = discount.min(number(maxDiscount));
        }
        if (discount.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        return new VoucherApplication(
                voucherId,
                String.valueOf(voucher.get("code")),
                String.valueOf(voucher.get("name")),
                discountType,
                eligibleSubtotal,
                discount.setScale(2, RoundingMode.HALF_UP)
        );
    }

    private BigDecimal number(Object value) {
        if (value instanceof BigDecimal decimal) {
            return decimal;
        }
        if (value instanceof Number number) {
            return BigDecimal.valueOf(number.doubleValue());
        }
        return value == null ? BigDecimal.ZERO : new BigDecimal(value.toString());
    }
}
