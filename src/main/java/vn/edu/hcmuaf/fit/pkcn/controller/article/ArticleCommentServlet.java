package vn.edu.hcmuaf.fit.pkcn.controller.article;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.article.ArticleCommentDao;
import vn.edu.hcmuaf.fit.pkcn.dao.article.ArticleDao;
import vn.edu.hcmuaf.fit.pkcn.service.article.ArticleService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/article-comments")
public class ArticleCommentServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ArticleCommentServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int articleId = parseId(request.getParameter("articleId"));
        if (articleId < 1) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã bài viết không hợp lệ.");
            return;
        }

        try {
            ArticleService articleService = new ArticleService(new ArticleDao(JDBI.getJdbi()));
            if (articleService.getArticleDetail(articleId) == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bài viết không tồn tại hoặc đã bị xóa.");
                return;
            }
            ArticleCommentDao commentDao = new ArticleCommentDao(JDBI.getJdbi());
            List<Map<String, Object>> comments = commentDao.getApprovedComments(articleId);
            request.setAttribute("comments", comments);
            request.setAttribute("articleId", articleId);
            response.setContentType("text/html;charset=UTF-8");
            request.getRequestDispatcher("/WEB-INF/views/client/article_comments.jsp").forward(request, response);
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Unable to load comments for article " + articleId, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Không thể tải bình luận. Vui lòng thử lại.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        int articleId = parseId(request.getParameter("articleId"));
        if (articleId < 1) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã bài viết không hợp lệ.");
            return;
        }
        String content = trim(request.getParameter("content"));
        String name = trim(request.getParameter("name"));
        String email = trim(request.getParameter("email"));

        String error = validate(content, name, email);
        if (error != null) {
            setFlash(request, error, "error");
            redirectToArticle(request, response, articleId);
            return;
        }

        try {
            ArticleService articleService = new ArticleService(new ArticleDao(JDBI.getJdbi()));
            if (articleService.getArticleDetail(articleId) == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bài viết không tồn tại hoặc đã bị xóa.");
                return;
            }
            new ArticleCommentDao(JDBI.getJdbi()).createComment(articleId, content, name, email);
            setFlash(request, "Đã gửi bình luận. Bình luận sẽ hiển thị sau khi được duyệt.", "success");
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Unable to create comment for article " + articleId, e);
            setFlash(request, "Không thể gửi bình luận lúc này. Vui lòng thử lại.", "error");
        }
        redirectToArticle(request, response, articleId);
    }

    private String validate(String content, String name, String email) {
        if (content.isEmpty()) return "Vui lòng nhập nội dung bình luận.";
        if (name.isEmpty()) return "Vui lòng nhập tên hiển thị.";
        if (name.codePointCount(0, name.length()) > 50) return "Tên hiển thị không được vượt quá 50 ký tự.";
        if (email.isEmpty()) return "Vui lòng nhập email.";
        if (email.codePointCount(0, email.length()) > 191 || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            return "Email không hợp lệ.";
        }
        return null;
    }

    private int parseId(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }

    private void setFlash(HttpServletRequest request, String message, String type) {
        request.getSession().setAttribute("articleFlashMessage", message);
        request.getSession().setAttribute("articleFlashType", type);
    }

    private void redirectToArticle(HttpServletRequest request, HttpServletResponse response, int articleId)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/news-detail?id=" + articleId + "#comments");
    }
}
