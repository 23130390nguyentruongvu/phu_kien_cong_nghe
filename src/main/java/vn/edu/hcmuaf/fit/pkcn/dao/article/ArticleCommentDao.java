package vn.edu.hcmuaf.fit.pkcn.dao.article;

import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Map;

public class ArticleCommentDao {
    private final Jdbi jdbi;

    public ArticleCommentDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<Map<String, Object>> getApprovedComments(int articleId) {
        return jdbi.withHandle(handle -> handle.createQuery("""
                        SELECT id, article_id, content, name_display, post_date
                        FROM comment_articles
                        WHERE article_id = :articleId AND status = 'approved'
                        ORDER BY post_date DESC, id DESC
                        """)
                .bind("articleId", articleId)
                .mapToMap()
                .list());
    }

    public int countApprovedComments(int articleId) {
        return jdbi.withHandle(handle -> handle.createQuery("""
                        SELECT COUNT(*) FROM comment_articles
                        WHERE article_id = :articleId AND status = 'approved'
                        """)
                .bind("articleId", articleId)
                .mapTo(Integer.class)
                .one());
    }

    public void createComment(int articleId, String content, String name, String email) {
        jdbi.useHandle(handle -> handle.createUpdate("""
                        INSERT INTO comment_articles (article_id, content, name_display, email, status)
                        VALUES (:articleId, :content, :name, :email, 'pending')
                        """)
                .bind("articleId", articleId)
                .bind("content", content)
                .bind("name", name)
                .bind("email", email)
                .execute());
    }

    public List<Map<String, Object>> getAdminComments(Integer articleId) {
        String sql = """
                SELECT c.id, c.article_id, c.content, c.name_display, c.email, c.post_date,
                       c.status, a.title AS article_title
                FROM comment_articles c
                JOIN articles a ON a.id = c.article_id
                """ + (articleId == null ? "" : "WHERE c.article_id = :articleId\n")
                + "ORDER BY c.post_date DESC, c.id DESC";
        return jdbi.withHandle(handle -> {
            var query = handle.createQuery(sql);
            if (articleId != null) query.bind("articleId", articleId);
            return query.mapToMap().list();
        });
    }

    public List<Map<String, Object>> getArticlesForFilter() {
        return jdbi.withHandle(handle -> handle.createQuery("""
                        SELECT id, title FROM articles ORDER BY post_date DESC, id DESC
                        """)
                .mapToMap()
                .list());
    }

    public boolean updateStatus(int commentId, String status) {
        return jdbi.withHandle(handle -> handle.createUpdate("""
                        UPDATE comment_articles SET status = :status WHERE id = :id
                        """)
                .bind("status", status)
                .bind("id", commentId)
                .execute()) == 1;
    }

    public boolean deleteComment(int commentId) {
        return jdbi.withHandle(handle -> handle.createUpdate("""
                        DELETE FROM comment_articles WHERE id = :id
                        """)
                .bind("id", commentId)
                .execute()) == 1;
    }
}
