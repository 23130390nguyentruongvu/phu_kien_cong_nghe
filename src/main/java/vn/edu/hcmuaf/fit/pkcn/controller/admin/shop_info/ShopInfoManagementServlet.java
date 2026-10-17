package vn.edu.hcmuaf.fit.pkcn.controller.admin.shop_info;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/admin/shop-info")
public class ShopInfoManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!requireAdmin(request, response)) {
            return;
        }

        String searchQuery = normalizeSearchQuery(request.getParameter("q"));
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("shops", JDBI.getJdbi().withHandle(handle ->
                handle.createQuery("""
                                SELECT *
                                FROM contact_shops
                                WHERE :searchQuery = ''
                                   OR CAST(id AS CHAR) LIKE :searchPattern
                                   OR shop_name LIKE :searchPattern
                                   OR shop_address LIKE :searchPattern
                                   OR shop_phone_number LIKE :searchPattern
                                   OR shop_hotline LIKE :searchPattern
                                   OR shop_email LIKE :searchPattern
                                   OR facebook_url LIKE :searchPattern
                                   OR instagram_url LIKE :searchPattern
                                ORDER BY id DESC
                                """)
                        .bind("searchQuery", searchQuery)
                        .bind("searchPattern", "%" + searchQuery + "%")
                        .mapToMap()
                        .list()));
        request.setAttribute("navLink", 6);
        request.getRequestDispatcher("/WEB-INF/views/admin/admin_shop_info.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!requireAdmin(request, response)) {
            return;
        }
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        Jdbi jdbi = JDBI.getJdbi();
        String status;
        switch (action == null ? "" : action) {
            case "create", "update" -> {
                int id = "update".equals(action) ? requiredId(request, response) : 0;
                String shopName = requiredParameter(request, response, "shop_name");
                String address = requiredParameter(request, response, "shop_address");
                String phone = requiredParameter(request, response, "shop_phone_number");
                String hotline = requiredParameter(request, response, "shop_hotline");
                String image = requiredParameter(request, response, "img_url");
                if (id < 0 || shopName == null || address == null || phone == null || hotline == null || image == null) return;

                String email = optionalParameter(request, "shop_email");
                String facebook = optionalParameter(request, "facebook_url");
                String instagram = optionalParameter(request, "instagram_url");
                boolean active = "1".equals(request.getParameter("status"));
                boolean saved = jdbi.inTransaction(handle -> {
                    if ("update".equals(action)) {
                        boolean exists = handle.createQuery("SELECT id FROM contact_shops WHERE id = :id")
                                .bind("id", id)
                                .mapTo(Integer.class)
                                .findOne()
                                .isPresent();
                        if (!exists) {
                            return false;
                        }
                    }
                    if (active) {
                        handle.createUpdate("UPDATE contact_shops SET status = 0 WHERE id <> :id")
                                .bind("id", id)
                                .execute();
                    }
                    if ("create".equals(action)) {
                        handle.createUpdate("""
                                        INSERT INTO contact_shops
                                            (shop_name, shop_address, shop_phone_number, shop_hotline, shop_email,
                                             facebook_url, instagram_url, img_url, status)
                                        VALUES
                                            (:name, :address, :phone, :hotline, :email, :facebook, :instagram, :image, :active)
                                        """)
                                .bind("name", shopName)
                                .bind("address", address)
                                .bind("phone", phone)
                                .bind("hotline", hotline)
                                .bind("email", email)
                                .bind("facebook", facebook)
                                .bind("instagram", instagram)
                                .bind("image", image)
                                .bind("active", active)
                                .execute();
                    } else {
                        handle.createUpdate("""
                                        UPDATE contact_shops
                                        SET shop_name = :name, shop_address = :address, shop_phone_number = :phone,
                                            shop_hotline = :hotline, shop_email = :email, facebook_url = :facebook,
                                            instagram_url = :instagram, img_url = :image, status = :active
                                        WHERE id = :id
                                        """)
                                .bind("id", id)
                                .bind("name", shopName)
                                .bind("address", address)
                                .bind("phone", phone)
                                .bind("hotline", hotline)
                                .bind("email", email)
                                .bind("facebook", facebook)
                                .bind("instagram", instagram)
                                .bind("image", image)
                                .bind("active", active)
                                .execute();
                    }
                    return true;
                });
                if (!saved) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy cửa hàng cần cập nhật");
                    return;
                }
                status = "create".equals(action) ? "created" : "updated";
            }
            case "delete" -> {
                int id = requiredId(request, response);
                if (id < 1) return;
                jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM contact_shops WHERE id = :id")
                        .bind("id", id)
                        .execute());
                status = "deleted";
            }
            default -> {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thao tác không hợp lệ");
                return;
            }
        }
        String redirect = request.getContextPath() + "/admin/shop-info?status=" + status;
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

    private String optionalParameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return value == null || value.isBlank() ? null : value.trim();
    }

    private int requiredId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            if (id > 0) return id;
        } catch (NumberFormatException | NullPointerException ignored) {
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã dữ liệu không hợp lệ");
        return -1;
    }
}
