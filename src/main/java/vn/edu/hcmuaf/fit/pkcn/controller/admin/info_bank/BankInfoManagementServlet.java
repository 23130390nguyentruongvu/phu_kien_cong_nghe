package vn.edu.hcmuaf.fit.pkcn.controller.admin.info_bank;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/admin/bank-info")
public class BankInfoManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!requireAdmin(request, response)) {
            return;
        }

        String searchQuery = normalizeSearchQuery(request.getParameter("q"));
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("banks", JDBI.getJdbi().withHandle(handle ->
                handle.createQuery("""
                                SELECT b.id, b.payment_method_id, b.bank_name, b.bank_owner_name,
                                       b.bank_account_number, b.is_show, p.name_method
                                FROM info_banks b
                                JOIN payment_methods p ON p.id = b.payment_method_id
                                WHERE :searchQuery = ''
                                   OR CAST(b.id AS CHAR) LIKE :searchPattern
                                   OR b.bank_name LIKE :searchPattern
                                   OR b.bank_owner_name LIKE :searchPattern
                                   OR b.bank_account_number LIKE :searchPattern
                                   OR p.name_method LIKE :searchPattern
                                ORDER BY b.id DESC
                                """)
                        .bind("searchQuery", searchQuery)
                        .bind("searchPattern", "%" + searchQuery + "%")
                        .mapToMap()
                        .list()));
        request.setAttribute("paymentMethods", JDBI.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT id, name_method FROM payment_methods ORDER BY id")
                        .mapToMap()
                        .list()));
        request.setAttribute("navLink", 7);
        request.getRequestDispatcher("/WEB-INF/views/admin/admin_bank_info.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!requireAdmin(request, response)) {
            return;
        }
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String status;
        switch (action == null ? "" : action) {
            case "create", "update" -> {
                int id = "update".equals(action) ? requiredId(request, response) : 0;
                int methodId = parsePositiveInt(request.getParameter("payment_method_id"));
                String bankName = requiredParameter(request, response, "bank_name");
                String owner = requiredParameter(request, response, "bank_owner_name");
                String account = requiredParameter(request, response, "bank_account_number");
                if (id < 0 || methodId < 1 || bankName == null || owner == null || account == null) {
                    if (methodId < 1 && !response.isCommitted()) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Phương thức thanh toán không hợp lệ");
                    }
                    return;
                }

                boolean visible = "1".equals(request.getParameter("is_show"));
                if ("create".equals(action)) {
                    JDBI.getJdbi().useHandle(handle -> handle.createUpdate("""
                                    INSERT INTO info_banks
                                        (payment_method_id, bank_name, bank_owner_name, bank_account_number, is_show)
                                    VALUES (:methodId, :bankName, :owner, :account, :visible)
                                    """)
                            .bind("methodId", methodId)
                            .bind("bankName", bankName)
                            .bind("owner", owner)
                            .bind("account", account)
                            .bind("visible", visible)
                            .execute());
                    status = "created";
                } else {
                    JDBI.getJdbi().useHandle(handle -> handle.createUpdate("""
                                    UPDATE info_banks
                                    SET payment_method_id = :methodId, bank_name = :bankName,
                                        bank_owner_name = :owner, bank_account_number = :account, is_show = :visible
                                    WHERE id = :id
                                    """)
                            .bind("id", id)
                            .bind("methodId", methodId)
                            .bind("bankName", bankName)
                            .bind("owner", owner)
                            .bind("account", account)
                            .bind("visible", visible)
                            .execute());
                    status = "updated";
                }
            }
            case "delete" -> {
                int id = requiredId(request, response);
                if (id < 1) return;
                JDBI.getJdbi().useHandle(handle -> handle.createUpdate("DELETE FROM info_banks WHERE id = :id")
                        .bind("id", id)
                        .execute());
                status = "deleted";
            }
            default -> {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thao tác không hợp lệ");
                return;
            }
        }
        String redirect = request.getContextPath() + "/admin/bank-info?status=" + status;
        if ("deleted".equals(status)) {
            String searchQuery = normalizeSearchQuery(request.getParameter("q"));
            if (!searchQuery.isEmpty()) {
                redirect += "&q=" + URLEncoder.encode(searchQuery, StandardCharsets.UTF_8);
            }
        }
        response.sendRedirect(redirect);
    }

    private String normalizeSearchQuery(String value) {
        return value == null ? "" : value.trim();
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

    private String requiredParameter(HttpServletRequest request, HttpServletResponse response, String name) throws IOException {
        String value = request.getParameter(name);
        if (value == null || value.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Vui lòng nhập đầy đủ thông tin");
            return null;
        }
        return value.trim();
    }

    private int requiredId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = parsePositiveInt(request.getParameter("id"));
        if (id > 0) return id;
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã dữ liệu không hợp lệ");
        return -1;
    }

    private int parsePositiveInt(String value) {
        try {
            int parsed = Integer.parseInt(value);
            return parsed > 0 ? parsed : -1;
        } catch (NumberFormatException | NullPointerException ignored) {
            return -1;
        }
    }
}
