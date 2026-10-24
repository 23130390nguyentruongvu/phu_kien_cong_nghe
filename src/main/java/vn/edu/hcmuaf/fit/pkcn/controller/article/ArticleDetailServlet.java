package vn.edu.hcmuaf.fit.pkcn.controller.article;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.article.ArticleCommentDao;
import vn.edu.hcmuaf.fit.pkcn.dao.article.ArticleDao;
import vn.edu.hcmuaf.fit.pkcn.model.article.ArticleDetail;
import vn.edu.hcmuaf.fit.pkcn.service.article.ArticleService;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ArticleDetailServlet", value = "/news-detail")
public class ArticleDetailServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ArticleDetailServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = parseArticleId(request.getParameter("id"));
        if (id < 1) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã bài viết không hợp lệ.");
            return;
        }
        try {
            ArticleService service = new ArticleService(
                    new ArticleDao(JDBI.getJdbi())
            );

            ArticleDetail article = service.getArticleDetail(id);

            if (article == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            request.setAttribute("article", article);
            request.setAttribute("approvedCommentCount",
                    new ArticleCommentDao(JDBI.getJdbi()).countApprovedComments(id));
            request.setAttribute("commentName", "");
            request.setAttribute("commentEmail", "");
            HttpSession session = request.getSession(false);
            User user = session == null ? null : (User) session.getAttribute("user");
            if (user != null) {
                request.setAttribute("commentName", user.getFullName());
                request.setAttribute("commentEmail", user.getEmail());
            }
            HttpSession currentSession = request.getSession(false);
            Object flashMessage = currentSession == null ? null : currentSession.getAttribute("articleFlashMessage");
            Object flashType = currentSession == null ? null : currentSession.getAttribute("articleFlashType");
            if (flashMessage != null) {
                request.setAttribute("articleFlashMessage", flashMessage);
                request.setAttribute("articleFlashType", flashType);
                currentSession.removeAttribute("articleFlashMessage");
                currentSession.removeAttribute("articleFlashType");
            }
            request.getRequestDispatcher("/WEB-INF/views/client/news_detail.jsp")
                    .forward(request, response);
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Unable to load article details for id " + id, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Không thể tải bài viết. Vui lòng thử lại.");
        }
    }

    private int parseArticleId(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }
}
