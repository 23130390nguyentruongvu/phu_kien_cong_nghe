package vn.edu.hcmuaf.fit.pkcn.dao.article;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.article.ArticleDetail;
import vn.edu.hcmuaf.fit.pkcn.model.article.ArticleShowAsItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArticleDao {
    private Jdbi jdbi;

    public ArticleDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<ArticleShowAsItem> getItemArticles(int limit) {
        String sql = "SELECT id, author_id, post_date, subdescription, title " +
                "FROM articles " +
                "ORDER BY post_date DESC " +
                "LIMIT :limit";


        List<ArticleShowAsItem> res = jdbi.withHandle(handle -> {
            List<ArticleShowAsItem> lst = new ArrayList<>();
            Iterator<ArticleShowAsItem> i = handle.createQuery(sql)
                    .bind("limit", limit)
                    .mapToBean(ArticleShowAsItem.class)
                    .stream().iterator();
            while (i.hasNext()) {
                lst.add(i.next());
            }
            return lst;
        });
        return res.isEmpty() ? null : res;
    }

    // Trang chi tiết
    public ArticleDetail getArticleById(int id) {

        String sql = "SELECT id, author_id, title, subdescription, post_date, content " +
                "FROM articles " +
                "WHERE id = :id";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind("id", id)
                    .mapToBean(ArticleDetail.class)
                    .findOne()
                    .orElse(null);
        });
    }

}
