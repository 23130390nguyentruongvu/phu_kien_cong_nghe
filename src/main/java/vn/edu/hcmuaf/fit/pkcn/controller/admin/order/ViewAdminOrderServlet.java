package vn.edu.hcmuaf.fit.pkcn.controller.admin.order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.order.snap.OrderSnapshotDAO;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserDao;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserKeyDao;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.OrderSnapshot;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.model.user.json.request.UserKeyDTO;
import vn.edu.hcmuaf.fit.pkcn.service.order.snap.OrderSnapshotService;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserKeyService;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;
import vn.edu.hcmuaf.fit.pkcn.utils.OrderHashDataFormatter;
import vn.edu.hcmuaf.fit.pkcn.utils.VerifySignature;
import vn.edu.hcmuaf.fit.pkcn.utils.enums.OrderStatus;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ViewAdminOrderServlet", value = "/admin-order")
public class ViewAdminOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || CheckUserHelper.checkUserInValid(user.getId())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (user.getRole() != 1) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trang không tồn tại");
            return;
        }

        try {
            request.setAttribute("createCustomers", JDBI.getJdbi().withHandle(handle ->
                    handle.createQuery("""
                                    SELECT id, full_name, user_name, email
                                    FROM users
                                    WHERE role_id = 2 AND status = 'active'
                                    ORDER BY full_name
                                    """)
                            .mapToMap()
                            .list()));
            request.setAttribute("createAddresses", JDBI.getJdbi().withHandle(handle ->
                    handle.createQuery("""
                                    SELECT a.id, a.user_id, a.receiver_name, a.phone_number, a.address_detail,
                                           a.district, a.province_city, a.is_selected
                                    FROM user_address a
                                    JOIN users u ON u.id = a.user_id
                                    WHERE u.role_id = 2 AND u.status = 'active'
                                    ORDER BY a.user_id, a.is_selected DESC, a.id DESC
                                    """)
                            .mapToMap()
                            .list()));
            request.setAttribute("createVariants", JDBI.getJdbi().withHandle(handle ->
                    handle.createQuery("""
                                    SELECT pv.id, pv.product_id, pv.name AS variant_name, pv.sku, pv.price, pv.stock,
                                           pv.color, pv.size, p.name AS product_name
                                    FROM product_variants pv
                                    JOIN products p ON p.id = pv.product_id
                                    WHERE p.status = 1 AND pv.stock > 0
                                    ORDER BY p.name, pv.name
                                    """)
                            .mapToMap()
                            .list()));
            request.setAttribute("createPaymentMethods", JDBI.getJdbi().withHandle(handle ->
                    handle.createQuery("SELECT id, name_method FROM payment_methods ORDER BY id")
                            .mapToMap()
                            .list()));

            OrderSnapshotService orderSnapshotService = new OrderSnapshotService(
                    new OrderSnapshotDAO(JDBI.getJdbi())
            );

            UserKeyService userKeyService = new UserKeyService(
                    new UserKeyDao(JDBI.getJdbi()),
                    new UserDao(JDBI.getJdbi())
            );

            String key = request.getParameter("keySearch");
            String statusFilter = request.getParameter("statusFilter");
            if (statusFilter != null && statusFilter.isEmpty()) {
                statusFilter = null;
            }

            String verifyFilter = request.getParameter("verifyFilter");
            List<OrderSnapshot> orders = orderSnapshotService.getOrderSnapshotsForAdmin(key, statusFilter);

            //check verify
            for (OrderSnapshot orderSnapshot : orders) {
                if (orderSnapshot.getSignature() == null) {
                    orderSnapshot.setVerify(false);
                    continue;
                }
//                if(orderSnapshot.getStatusOrder().equals(OrderStatus.PENDING_SIGNATURE.getCode())
//                || orderSnapshot.getStatusOrder().equals(OrderStatus.WAITING_RE_SIGN.getCode())) {
//                    orderSnapshot.setVerify(false);
//                    continue;
//                }
                UserKeyDTO userKeyDTO = userKeyService.getUserKeyById(orderSnapshot.getUserKeyId());
                if (userKeyDTO == null) {
                    orderSnapshot.setVerify(false);
                    continue;
                }

                String formatData = OrderHashDataFormatter.buildOrderFormatForHash(orderSnapshot);
                boolean isVerity = VerifySignature.verifySignature(
                        userKeyDTO.getPublicKey(),
                        orderSnapshot.getSignature(),
                        formatData,
                        userKeyDTO.getNameAlgorithm()
                );

                orderSnapshot.setVerify(isVerity);
            }

            List<OrderSnapshot> res;

            if (verifyFilter == null || verifyFilter.equalsIgnoreCase("both"))
                request.setAttribute("orders", orders);
            else if (verifyFilter.equalsIgnoreCase("verify")) {
                res = new ArrayList<>();
                for(OrderSnapshot orderSnapshot: orders)
                    if(orderSnapshot.isVerify())
                        res.add(orderSnapshot);

                request.setAttribute("orders", res);
            } else if (verifyFilter.equalsIgnoreCase("un-verify")) {
                res = new ArrayList<>();
                for(OrderSnapshot orderSnapshot: orders)
                    if(!orderSnapshot.isVerify())
                        res.add(orderSnapshot);

                request.setAttribute("orders", res);
            }

            request.setAttribute("verifyFilter", verifyFilter);
            request.setAttribute("keySearch", key);
            request.setAttribute("statusFilter", statusFilter);
            request.setAttribute("navLink", 4);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("orders", null);
            request.setAttribute("navLink", 4);
        }

        request.getRequestDispatcher("/WEB-INF/views/admin/admin_order.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User admin = session == null ? null : (User) session.getAttribute("user");
        if (admin == null || CheckUserHelper.checkUserInValid(admin.getId())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        if (admin.getRole() != 1) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trang không tồn tại");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        if (!"create".equals(request.getParameter("action"))) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thao tác không hợp lệ");
            return;
        }

        int userId = positiveInt(request.getParameter("customerId"));
        int addressId = positiveInt(request.getParameter("addressId"));
        int paymentMethodId = positiveInt(request.getParameter("paymentMethodId"));
        String note = request.getParameter("note");
        String[] variantValues = request.getParameterValues("variantIds");
        String[] quantityValues = request.getParameterValues("quantities");
        if (userId < 1 || addressId < 1 || paymentMethodId < 1
                || note != null && note.length() > 191
                || variantValues == null || quantityValues == null
                || variantValues.length == 0 || variantValues.length != quantityValues.length) {
            response.sendRedirect(request.getContextPath() + "/admin-order?createError=invalid");
            return;
        }

        Set<Integer> variantIds = new HashSet<>();
        int[] quantities = new int[variantValues.length];
        for (int i = 0; i < variantValues.length; i++) {
            int variantId = positiveInt(variantValues[i]);
            int quantity = positiveInt(quantityValues[i]);
            if (variantId < 1 || quantity < 1 || !variantIds.add(variantId)) {
                response.sendRedirect(request.getContextPath() + "/admin-order?createError=items");
                return;
            }
            quantities[i] = quantity;
        }

        try {
            int orderId = JDBI.getJdbi().inTransaction(handle -> {
                boolean customerExists = handle.createQuery("""
                                SELECT id FROM users
                                WHERE id = :userId AND role_id = 2 AND status = 'active'
                                FOR UPDATE
                                """)
                        .bind("userId", userId)
                        .mapTo(Integer.class)
                        .findOne()
                        .isPresent();
                if (!customerExists) {
                    throw new IllegalArgumentException("Khách hàng không tồn tại hoặc không hoạt động");
                }

                Map<String, Object> address = handle.createQuery("""
                                SELECT receiver_name, phone_number, address_detail, district, province_city
                                FROM user_address
                                WHERE id = :addressId AND user_id = :userId
                                """)
                        .bind("addressId", addressId)
                        .bind("userId", userId)
                        .mapToMap()
                        .findOne()
                        .orElseThrow(() -> new IllegalArgumentException("Địa chỉ không thuộc khách hàng đã chọn"));

                String paymentMethodName = handle.createQuery("SELECT name_method FROM payment_methods WHERE id = :id")
                        .bind("id", paymentMethodId)
                        .mapTo(String.class)
                        .findOne()
                        .orElseThrow(() -> new IllegalArgumentException("Phương thức thanh toán không hợp lệ"));

                double shippingFee = handle.createQuery("""
                                SELECT price FROM ship_fee_by_address
                                WHERE province_city = :province
                                """)
                        .bind("province", address.get("province_city"))
                        .mapTo(Double.class)
                        .findOne()
                        .orElseGet(() -> handle.createQuery("""
                                        SELECT price FROM ship_fee_by_address
                                        WHERE province_city = 'Các tỉnh khác'
                                        """)
                                .mapTo(Double.class)
                                .findOne()
                                .orElse(50000.0));

                int addressOrderId = handle.createUpdate("""
                                INSERT INTO address_order
                                    (receiver_name, phone_number, address_detail, district, province_city, note)
                                VALUES (:receiver, :phone, :detail, :district, :province, :note)
                                """)
                        .bind("receiver", address.get("receiver_name"))
                        .bind("phone", address.get("phone_number"))
                        .bind("detail", address.get("address_detail"))
                        .bind("district", address.get("district"))
                        .bind("province", address.get("province_city"))
                        .bind("note", note)
                        .executeAndReturnGeneratedKeys()
                        .mapTo(Integer.class)
                        .one();

                BigDecimal subtotal = BigDecimal.ZERO;
                Map<Integer, Map<String, Object>> variantSnapshots = new java.util.LinkedHashMap<>();
                for (int i = 0; i < variantValues.length; i++) {
                    int variantId = positiveInt(variantValues[i]);
                    int quantity = quantities[i];
                    Map<String, Object> variant = handle.createQuery("""
                                    SELECT pv.id, pv.product_id, pv.name AS variant_name, pv.sku,
                                           pv.price, pv.stock, pv.gram, pv.color, pv.size,
                                           p.name AS product_name, p.status AS product_status
                                    FROM product_variants pv
                                    JOIN products p ON p.id = pv.product_id
                                    WHERE pv.id = :id
                                    FOR UPDATE
                                    """)
                            .bind("id", variantId)
                            .mapToMap()
                            .findOne()
                            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy biến thể sản phẩm"));
                    int stock = ((Number) variant.get("stock")).intValue();
                    Object productStatus = variant.get("product_status");
                    boolean productActive = Boolean.TRUE.equals(productStatus)
                            || productStatus instanceof Number status && status.intValue() == 1;
                    if (!productActive || quantity > stock) {
                        throw new IllegalArgumentException("Sản phẩm " + variant.get("product_name")
                                + " không hoạt động hoặc không đủ tồn kho (còn " + stock + ")");
                    }
                    BigDecimal price = (BigDecimal) variant.get("price");
                    subtotal = subtotal.add(price.multiply(BigDecimal.valueOf(quantity)));
                    variantSnapshots.put(variantId, variant);
                }

                BigDecimal ship = BigDecimal.valueOf(shippingFee);
                int createdOrderId = new OrderSnapshotDAO(JDBI.getJdbi()).insertOrder(
                        handle, userId, paymentMethodId, paymentMethodName, addressOrderId,
                        OrderStatus.PENDING_SIGNATURE.getCode(), subtotal.add(ship), ship, note,
                        null, null, BigDecimal.ZERO);

                for (int i = 0; i < variantValues.length; i++) {
                    int variantId = positiveInt(variantValues[i]);
                    int quantity = quantities[i];
                    Map<String, Object> variant = variantSnapshots.get(variantId);
                    BigDecimal price = (BigDecimal) variant.get("price");
                    new OrderSnapshotDAO(JDBI.getJdbi()).insertOrderDetails(
                            handle, createdOrderId, variantId,
                            String.valueOf(variant.get("product_name")),
                            String.valueOf(variant.get("variant_name")),
                            String.valueOf(variant.get("sku")),
                            price,
                            ((Number) variant.get("gram")).intValue(),
                            String.valueOf(variant.get("color")),
                            variant.get("size") == null ? null : String.valueOf(variant.get("size")),
                            quantity,
                            price.multiply(BigDecimal.valueOf(quantity)));
                    handle.createUpdate("UPDATE product_variants SET stock = stock - :qty WHERE id = :id")
                            .bind("qty", quantity).bind("id", variantId).execute();
                    handle.createUpdate("""
                                    UPDATE products p
                                    JOIN product_variants pv ON pv.product_id = p.id
                                    SET p.stock = p.stock - :qty
                                    WHERE pv.id = :id
                                    """)
                            .bind("qty", quantity).bind("id", variantId).execute();
                }
                return createdOrderId;
            });
            response.sendRedirect(request.getContextPath() + "/admin-order?createdOrderId=" + orderId);
        } catch (Exception e) {
            getServletContext().log("Không thể tạo đơn hàng từ admin", e);
            String error = e instanceof IllegalArgumentException ? "business" : "server";
            response.sendRedirect(request.getContextPath() + "/admin-order?createError=" + error);
        }
    }

    private int positiveInt(String value) {
        try {
            int parsed = Integer.parseInt(value);
            return parsed > 0 ? parsed : -1;
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }
}
