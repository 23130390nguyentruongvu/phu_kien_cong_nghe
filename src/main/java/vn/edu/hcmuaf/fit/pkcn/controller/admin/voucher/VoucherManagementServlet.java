package vn.edu.hcmuaf.fit.pkcn.controller.admin.voucher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jdbi.v3.core.Handle;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.common.VoucherService;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class VoucherManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!requireAdmin(request, response)) {
            return;
        }

        VoucherService voucherService = new VoucherService(JDBI.getJdbi());
        List<Map<String, Object>> vouchers = JDBI.getJdbi().withHandle(handle -> {
            List<Map<String, Object>> rows = new ArrayList<>();
            for (Map<String, Object> source : handle.createQuery("SELECT * FROM vouchers ORDER BY create_date DESC")
                    .mapToMap().list()) {
                Map<String, Object> row = new HashMap<>(source);
                int id = ((Number) row.get("id")).intValue();
                row.put("start_date_input", formatDateTime(row.get("start_date")));
                row.put("end_date_input", formatDateTime(row.get("end_date")));
                row.put("category_ids", voucherService.getSelectedCategoryIds(handle, id));
                row.put("product_ids", voucherService.getSelectedProductIds(handle, id));
                rows.add(row);
            }
            return rows;
        });

        request.setAttribute("vouchers", vouchers);
        request.setAttribute("categories", JDBI.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT id, category_name FROM categories ORDER BY category_name")
                        .mapToMap().list()));
        request.setAttribute("products", JDBI.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT id, name, status FROM products ORDER BY name")
                        .mapToMap().list()));
        request.setAttribute("navLink", 8);
        request.getRequestDispatcher("/WEB-INF/views/admin/admin_vouchers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!requireAdmin(request, response)) {
            return;
        }
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if ("deactivate".equals(action)) {
            int id = parsePositiveInt(request.getParameter("id"));
            if (id < 1) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã voucher không hợp lệ");
                return;
            }
            int updated = JDBI.getJdbi().withHandle(handle ->
                    handle.createUpdate("UPDATE vouchers SET status = 0 WHERE id = :id")
                            .bind("id", id).execute());
            if (updated == 0) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy voucher");
                return;
            }
            response.sendRedirect(request.getContextPath() + "/admin/vouchers?status=deactivated");
            return;
        }

        boolean creating = "create".equals(action);
        boolean updating = "update".equals(action);
        if (!creating && !updating) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thao tác không hợp lệ");
            return;
        }

        int id = creating ? 0 : parsePositiveInt(request.getParameter("id"));
        String code = parameter(request, "code");
        String name = parameter(request, "name");
        String description = optionalParameter(request, "description");
        String type = parameter(request, "discount_type");
        String startDateValue = parameter(request, "start_date");
        String endDateValue = parameter(request, "end_date");
        BigDecimal discountValue = decimal(request.getParameter("discount_value"));
        BigDecimal maxDiscount = optionalDecimal(request.getParameter("max_discount_amount"));
        BigDecimal minOrderValue = decimal(request.getParameter("min_order_value"));
        Integer usageLimit = optionalPositiveInt(request.getParameter("usage_limit"));
        int usageLimitPerUser = parsePositiveInt(request.getParameter("usage_limit_per_user"));
        boolean active = "1".equals(request.getParameter("status"));
        List<Integer> categoryIds = parseIds(request.getParameterValues("categoryIds"));
        List<Integer> productIds = parseIds(request.getParameterValues("productIds"));

        if (updating && id < 1 || code == null || code.isBlank() || code.length() > 50
                || !code.matches("[A-Za-z0-9_-]+") || name == null || name.isBlank() || name.length() > 100
                || description != null && description.length() > 191
                || type == null || !List.of("PERCENT", "FIXED", "FREESHIP").contains(type)
                || discountValue == null || discountValue.signum() < 0
                || "PERCENT".equals(type) && discountValue.compareTo(BigDecimal.valueOf(100)) > 0
                || maxDiscount != null && maxDiscount.signum() < 0
                || maxDiscount == null && notBlank(request.getParameter("max_discount_amount"))
                || minOrderValue == null || minOrderValue.signum() < 0
                || usageLimit == null && notBlank(request.getParameter("usage_limit"))
                || usageLimitPerUser < 1 || categoryIds == null || productIds == null) {
            response.sendRedirect(request.getContextPath() + "/admin/vouchers?error=invalid");
            return;
        }

        LocalDateTime startDate;
        LocalDateTime endDate;
        try {
            startDate = LocalDateTime.parse(startDateValue);
            endDate = LocalDateTime.parse(endDateValue);
        } catch (DateTimeParseException | NullPointerException e) {
            response.sendRedirect(request.getContextPath() + "/admin/vouchers?error=date");
            return;
        }
        if (!endDate.isAfter(startDate)) {
            response.sendRedirect(request.getContextPath() + "/admin/vouchers?error=date");
            return;
        }

        int duplicateCode = JDBI.getJdbi().withHandle(handle -> handle.createQuery("""
                        SELECT COUNT(*) FROM vouchers
                        WHERE code = :code AND (:id = 0 OR id <> :id)
                        """)
                .bind("code", code.toUpperCase())
                .bind("id", id)
                .mapTo(Integer.class)
                .one());
        if (duplicateCode > 0) {
            response.sendRedirect(request.getContextPath() + "/admin/vouchers?error=duplicate");
            return;
        }

        User user = (User) request.getSession(false).getAttribute("user");
        JDBI.getJdbi().inTransaction(handle -> {
            validateReferences(handle, "categories", categoryIds);
            validateReferences(handle, "products", productIds);

            int voucherId = id;
            if (updating) {
                boolean exists = handle.createQuery("SELECT id FROM vouchers WHERE id = :id")
                        .bind("id", voucherId).mapTo(Integer.class).findOne().isPresent();
                if (!exists) {
                    throw new IllegalArgumentException("Không tìm thấy voucher cần cập nhật");
                }
                handle.createUpdate("""
                                UPDATE vouchers
                                SET code = :code, name = :name, description = :description,
                                    discount_type = :type, discount_value = :discountValue,
                                    max_discount_amount = :maxDiscount, min_order_value = :minOrderValue,
                                    usage_limit = :usageLimit, usage_limit_per_user = :perUser,
                                    start_date = :startDate, end_date = :endDate, status = :active
                                WHERE id = :id
                                """)
                        .bind("id", voucherId)
                        .bind("code", code.toUpperCase())
                        .bind("name", name.trim())
                        .bind("description", description)
                        .bind("type", type)
                        .bind("discountValue", discountValue)
                        .bind("maxDiscount", maxDiscount)
                        .bind("minOrderValue", minOrderValue)
                        .bind("usageLimit", usageLimit)
                        .bind("perUser", usageLimitPerUser)
                        .bind("startDate", Timestamp.valueOf(startDate))
                        .bind("endDate", Timestamp.valueOf(endDate))
                        .bind("active", active)
                        .execute();
            } else {
                handle.createUpdate("""
                                INSERT INTO vouchers
                                    (code, name, description, discount_type, discount_value, max_discount_amount,
                                     min_order_value, usage_limit, usage_limit_per_user, start_date, end_date, status, created_by)
                                VALUES
                                    (:code, :name, :description, :type, :discountValue, :maxDiscount,
                                     :minOrderValue, :usageLimit, :perUser, :startDate, :endDate, :active, :createdBy)
                                """)
                        .bind("code", code.toUpperCase())
                        .bind("name", name.trim())
                        .bind("description", description)
                        .bind("type", type)
                        .bind("discountValue", discountValue)
                        .bind("maxDiscount", maxDiscount)
                        .bind("minOrderValue", minOrderValue)
                        .bind("usageLimit", usageLimit)
                        .bind("perUser", usageLimitPerUser)
                        .bind("startDate", Timestamp.valueOf(startDate))
                        .bind("endDate", Timestamp.valueOf(endDate))
                        .bind("active", active)
                        .bind("createdBy", user.getId())
                        .execute();
                voucherId = handle.createQuery("SELECT LAST_INSERT_ID()").mapTo(Integer.class).one();
            }

            handle.createUpdate("DELETE FROM voucher_categories WHERE voucher_id = :id")
                    .bind("id", voucherId).execute();
            handle.createUpdate("DELETE FROM voucher_products WHERE voucher_id = :id")
                    .bind("id", voucherId).execute();
            for (Integer categoryId : categoryIds) {
                handle.createUpdate("INSERT INTO voucher_categories (voucher_id, category_id) VALUES (:voucherId, :targetId)")
                        .bind("voucherId", voucherId).bind("targetId", categoryId).execute();
            }
            for (Integer productId : productIds) {
                handle.createUpdate("INSERT INTO voucher_products (voucher_id, product_id) VALUES (:voucherId, :targetId)")
                        .bind("voucherId", voucherId).bind("targetId", productId).execute();
            }
            return null;
        });
        response.sendRedirect(request.getContextPath() + "/admin/vouchers?status=" + (creating ? "created" : "updated"));
    }

    private boolean requireAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        if (user == null || CheckUserHelper.checkUserInValid(user.getId())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        if (user.getRole() != 1) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trang không tồn tại");
            return false;
        }
        return true;
    }

    private void validateReferences(Handle handle, String table, List<Integer> ids) {
        if (ids.isEmpty()) return;
        int found = handle.createQuery("SELECT COUNT(*) FROM " + table + " WHERE id IN (<ids>)")
                .bindList("ids", ids).mapTo(Integer.class).one();
        if (found != ids.size()) {
            throw new IllegalArgumentException("Một số danh mục hoặc sản phẩm không còn tồn tại");
        }
    }

    private String parameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return value == null ? null : value.trim();
    }

    private String optionalParameter(HttpServletRequest request, String name) {
        String value = parameter(request, name);
        return value == null || value.isBlank() ? null : value;
    }

    private boolean notBlank(String value) {
        return value != null && !value.isBlank();
    }

    private BigDecimal decimal(String value) {
        try {
            return value == null || value.isBlank() ? null : new BigDecimal(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private BigDecimal optionalDecimal(String value) {
        return value == null || value.isBlank() ? null : decimal(value);
    }

    private Integer optionalPositiveInt(String value) {
        if (value == null || value.isBlank()) return null;
        int parsed = parsePositiveInt(value);
        return parsed < 1 ? null : parsed;
    }

    private int parsePositiveInt(String value) {
        try {
            int parsed = Integer.parseInt(value);
            return parsed > 0 ? parsed : -1;
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }

    private List<Integer> parseIds(String[] values) {
        LinkedHashSet<Integer> result = new LinkedHashSet<>();
        if (values == null) return new ArrayList<>();
        try {
            for (String value : values) {
                int id = Integer.parseInt(value);
                if (id < 1) return null;
                result.add(id);
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return new ArrayList<>(result);
    }

    private String formatDateTime(Object value) {
        if (value instanceof Timestamp timestamp) {
            return timestamp.toLocalDateTime().withSecond(0).withNano(0).toString();
        }
        if (value instanceof LocalDateTime dateTime) {
            return dateTime.withSecond(0).withNano(0).toString();
        }
        return "";
    }
}
