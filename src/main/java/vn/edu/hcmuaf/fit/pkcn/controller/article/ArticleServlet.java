package vn.edu.hcmuaf.fit.pkcn.controller.article;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.article.ArticleDao;
import vn.edu.hcmuaf.fit.pkcn.model.article.ArticleShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.service.article.ArticleService;

import java.io.IOException;
import java.util.List;

@WebServlet("/news")
public class ArticleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArticleService service = new ArticleService(
                new ArticleDao(JDBI.getJdbi())
        );

        List<ArticleShowAsItem> articles = service.getArticleItems(10);

        request.setAttribute("ArticleItems", articles);

        request.getRequestDispatcher("/WEB-INF/views/client/news.jsp")
                .forward(request, response);
    }
}
