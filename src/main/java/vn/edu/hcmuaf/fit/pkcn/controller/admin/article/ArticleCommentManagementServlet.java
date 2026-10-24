package vn.edu.hcmuaf.fit.pkcn.controller.admin.article;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.article.ArticleCommentDao;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/admin/article-comment")
public class ArticleCommentManagementServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ArticleCommentManagementServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!requireAdmin(request, response)) return;

        Integer articleId = parseOptionalId(request.getParameter("articleId"));
        if (request.getParameter("articleId") != null && articleId == null) {
            setFlash(request, "Mã bài viết không hợp lệ.", "error");
            response.sendRedirect(request.getContextPath() + "/admin/article-comment");
            return;
        }

        try {
            ArticleCommentDao dao = new ArticleCommentDao(JDBI.getJdbi());
            request.setAttribute("comments", dao.getAdminComments(articleId));
            request.setAttribute("articles", dao.getArticlesForFilter());
            request.setAttribute("selectedArticleId", articleId);
            request.setAttribute("articleFlashMessage", request.getSession().getAttribute("articleFlashMessage"));
            request.setAttribute("articleFlashType", request.getSession().getAttribute("articleFlashType"));
            request.getSession().removeAttribute("articleFlashMessage");
            request.getSession().removeAttribute("articleFlashType");
            request.setAttribute("navLink", 5);
            request.getRequestDispatcher("/WEB-INF/views/admin/admin_article_comments.jsp").forward(request, response);
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Unable to load article comments for administration", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Không thể tải danh sách bình luận. Vui lòng thử lại.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!requireAdmin(request, response)) return;
        request.setCharacterEncoding("UTF-8");

        int commentId = parseId(request.getParameter("commentId"));
        Integer articleId = parseOptionalId(request.getParameter("articleId"));
        String action = request.getParameter("action");
        if (commentId < 1 || !isValidAction(action)) {
            setFlash(request, "Yêu cầu quản lý bình luận không hợp lệ.", "error");
            redirectToList(request, response, articleId);
            return;
        }

        try {
            ArticleCommentDao dao = new ArticleCommentDao(JDBI.getJdbi());
            boolean changed;
            String message;
            if ("delete".equals(action)) {
                changed = dao.deleteComment(commentId);
                message = changed ? "Đã xóa bình luận." : "Không tìm thấy bình luận cần xóa.";
            } else {
                String status = "approve".equals(action) ? "approved" : "hidden";
                changed = dao.updateStatus(commentId, status);
                message = changed
                        ? ("approve".equals(action) ? "Đã duyệt bình luận." : "Đã ẩn bình luận.")
                        : "Không tìm thấy bình luận cần cập nhật.";
            }
            setFlash(request, message, changed ? "success" : "error");
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Unable to update article comment " + commentId, e);
            setFlash(request, "Không thể cập nhật bình luận lúc này. Vui lòng thử lại.", "error");
        }
        redirectToList(request, response, articleId);
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

    private boolean isValidAction(String action) {
        return "approve".equals(action) || "hide".equals(action) || "delete".equals(action);
    }

    private int parseId(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }

    private Integer parseOptionalId(String value) {
        if (value == null || value.isBlank()) return null;
        int id = parseId(value);
        return id > 0 ? id : null;
    }

    private void setFlash(HttpServletRequest request, String message, String type) {
        request.getSession().setAttribute("articleFlashMessage", message);
        request.getSession().setAttribute("articleFlashType", type);
    }

    private void redirectToList(HttpServletRequest request, HttpServletResponse response, Integer articleId)
            throws IOException {
        String redirect = request.getContextPath() + "/admin/article-comment";
        if (articleId != null) redirect += "?articleId=" + articleId;
        response.sendRedirect(redirect);
    }
}
