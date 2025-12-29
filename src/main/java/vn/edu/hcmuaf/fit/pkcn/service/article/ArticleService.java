package vn.edu.hcmuaf.fit.pkcn.service.article;

import vn.edu.hcmuaf.fit.pkcn.dao.article.ArticleDao;
import vn.edu.hcmuaf.fit.pkcn.dao.category.CategoryDao;
import vn.edu.hcmuaf.fit.pkcn.model.article.ArticleDetail;
import vn.edu.hcmuaf.fit.pkcn.model.article.ArticleShowAsItem;

import java.util.List;
import java.util.Optional;

public class ArticleService {
    private ArticleDao articleDao;

    public ArticleService(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public List<ArticleShowAsItem> getArticleItems(int limit) {
        return articleDao.getItemArticles(limit);
    }

    public Optional<ArticleDetail> getArticleDetail(int id) { return articleDao.getArticleById(id); }

}
