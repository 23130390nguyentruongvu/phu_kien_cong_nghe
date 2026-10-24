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
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/admin/articles", "/admin/article"})
public class ArticleManagementServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ArticleManagementServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!requireAdmin(request, response)) {
            return;
        }

        String searchQuery = normalizeSearchQuery(request.getParameter("q"));
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("articleFlashMessage", request.getSession().getAttribute("articleFlashMessage"));
        request.setAttribute("articleFlashType", request.getSession().getAttribute("articleFlashType"));
        request.getSession().removeAttribute("articleFlashMessage");
        request.getSession().removeAttribute("articleFlashType");
        request.setAttribute("articles", JDBI.getJdbi().withHandle(handle ->
                handle.createQuery("""
                                SELECT a.id, a.author_id, a.title, a.subdescription, a.post_date,
                                       a.content, u.user_name AS author_name,
                                       (SELECT COUNT(*) FROM comment_articles c
                                        WHERE c.article_id = a.id) AS comment_count
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
        String searchQuery = normalizeSearchQuery(request.getParameter("q"));
        String title = normalizeSearchQuery(request.getParameter("title"));
        String description = normalizeSearchQuery(request.getParameter("subdescription"));
        String content = normalizeSearchQuery(request.getParameter("content"));
        String currentAction = action == null ? "" : action;
        if (("create".equals(currentAction) || "update".equals(currentAction))
                && (title.isEmpty() || description.isEmpty() || content.isEmpty())) {
            setFlash(request, "Vui lòng nhập đầy đủ tiêu đề, mô tả ngắn và nội dung bài viết.", "error");
            redirectToManagement(request, response, searchQuery);
            return;
        }
        if (("create".equals(currentAction) || "update".equals(currentAction))
                && title.codePointCount(0, title.length()) > 191) {
            setFlash(request, "Tiêu đề không được vượt quá 191 ký tự.", "error");
            redirectToManagement(request, response, searchQuery);
            return;
        }

        try {
            switch (currentAction) {
                case "create" -> {
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
                    setFlash(request, "Đã tạo bài viết.", "success");
                }
                case "update" -> {
                    int id = requiredId(request.getParameter("id"));
                    if (id < 1) {
                        setFlash(request, "Mã bài viết không hợp lệ.", "error");
                        redirectToManagement(request, response, searchQuery);
                        return;
                    }
                    int updated = jdbi.withHandle(handle -> handle.createUpdate("""
                                    UPDATE articles
                                    SET title = :title, subdescription = :description, content = :content
                                    WHERE id = :id
                                    """)
                            .bind("id", id)
                            .bind("title", title)
                            .bind("description", description)
                            .bind("content", content)
                            .execute());
                    setFlash(request, updated == 1 ? "Đã cập nhật bài viết."
                            : "Không tìm thấy bài viết cần cập nhật.", updated == 1 ? "success" : "error");
                }
                case "delete" -> {
                    int id = requiredId(request.getParameter("id"));
                    if (id < 1) {
                        setFlash(request, "Mã bài viết không hợp lệ.", "error");
                        redirectToManagement(request, response, searchQuery);
                        return;
                    }
                    int deleted = jdbi.withHandle(handle -> handle.createUpdate("DELETE FROM articles WHERE id = :id")
                            .bind("id", id)
                            .execute());
                    setFlash(request, deleted == 1 ? "Đã xóa bài viết."
                            : "Không tìm thấy bài viết cần xóa.", deleted == 1 ? "success" : "error");
                }
                default -> {
                    setFlash(request, "Thao tác quản lý bài viết không hợp lệ.", "error");
                }
            }
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Unable to process admin article action " + currentAction, e);
            setFlash(request, "Không thể xử lý bài viết lúc này. Vui lòng thử lại.", "error");
        }
        redirectToManagement(request, response, searchQuery);
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

    private int requiredId(String value) {
        try {
            int id = Integer.parseInt(value);
            if (id > 0) return id;
        } catch (NumberFormatException | NullPointerException ignored) {
        }
        return -1;
    }

    private void setFlash(HttpServletRequest request, String message, String type) {
        request.getSession().setAttribute("articleFlashMessage", message);
        request.getSession().setAttribute("articleFlashType", type);
    }

    private void redirectToManagement(HttpServletRequest request, HttpServletResponse response, String searchQuery)
            throws IOException {
        String redirect = request.getContextPath() + "/admin/articles";
        if (!searchQuery.isEmpty()) {
            redirect += "?q=" + URLEncoder.encode(searchQuery, StandardCharsets.UTF_8);
        }
        response.sendRedirect(redirect);
    }
}
