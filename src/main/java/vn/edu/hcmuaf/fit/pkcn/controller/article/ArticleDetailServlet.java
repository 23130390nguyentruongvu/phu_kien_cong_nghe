package vn.edu.hcmuaf.fit.pkcn.controller.article;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.article.ArticleDao;
import vn.edu.hcmuaf.fit.pkcn.service.article.ArticleService;

import java.io.IOException;

@WebServlet(name = "ArticleDetailServlet", value = "/news-detail")
public class ArticleDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        ArticleService service = new ArticleService(
                new ArticleDao(JDBI.getJdbi())
        );

        service.getArticleDetail(id).ifPresent(article ->
                request.setAttribute("article", article)
        );

        request.getRequestDispatcher("/WEB-INF/views/client/news_detail.jsp")
                .forward(request, response);
    }
}
