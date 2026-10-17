package vn.edu.hcmuaf.fit.pkcn.controller.admin.article;

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

@WebServlet("/admin/articles")
public class ArticleManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!requireAdmin(request, response)) {
            return;
        }

        String searchQuery = normalizeSearchQuery(request.getParameter("q"));
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("articles", JDBI.getJdbi().withHandle(handle ->
                handle.createQuery("""
                                SELECT a.id, a.author_id, a.title, a.subdescription, a.post_date,
                                       a.content, u.user_name AS author_name
                                FROM articles a
                                JOIN users u ON u.id = a.author_id
                                WHERE :searchQuery = ''
                                   OR CAST(a.id AS CHAR) LIKE :searchPattern
                                   OR a.title LIKE :searchPattern
                                   OR a.subdescription LIKE :searchPattern
                                   OR a.content LIKE :searchPattern
                                   OR u.user_name LIKE :searchPattern
                                ORDER BY a.post_date DESC
                                """)
                        .bind("searchQuery", searchQuery)
                        .bind("searchPattern", "%" + searchQuery + "%")
                        .mapToMap()
                        .list()));
        request.setAttribute("navLink", 5);
        request.getRequestDispatcher("/WEB-INF/views/admin/admin_articles.jsp").forward(request, response);
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
            case "create" -> {
                String title = requiredParameter(request, response, "title");
                String description = requiredParameter(request, response, "subdescription");
                String content = requiredParameter(request, response, "content");
                if (title == null || description == null || content == null) return;

                User user = (User) request.getSession().getAttribute("user");
                jdbi.useHandle(handle -> handle.createUpdate("""
                                INSERT INTO articles (author_id, title, subdescription, content)
                                VALUES (:authorId, :title, :description, :content)
                                """)
                        .bind("authorId", user.getId())
                        .bind("title", title)
                        .bind("description", description)
                        .bind("content", content)
                        .execute());
                status = "created";
            }
            case "update" -> {
                int id = requiredId(request, response);
                String title = requiredParameter(request, response, "title");
                String description = requiredParameter(request, response, "subdescription");
                String content = requiredParameter(request, response, "content");
                if (id < 1 || title == null || description == null || content == null) return;

                jdbi.useHandle(handle -> handle.createUpdate("""
                                UPDATE articles
                                SET title = :title, subdescription = :description, content = :content
                                WHERE id = :id
                                """)
                        .bind("id", id)
                        .bind("title", title)
                        .bind("description", description)
                        .bind("content", content)
                        .execute());
                status = "updated";
            }
            case "delete" -> {
                int id = requiredId(request, response);
                if (id < 1) return;
                jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM articles WHERE id = :id")
                        .bind("id", id)
                        .execute());
                status = "deleted";
            }
            default -> {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thao tác không hợp lệ");
                return;
            }
        }
        String redirect = request.getContextPath() + "/admin/articles?status=" + status;
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
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            if (id > 0) return id;
        } catch (NumberFormatException | NullPointerException ignored) {
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã dữ liệu không hợp lệ");
        return -1;
    }
}
