package vn.edu.hcmuaf.fit.pkcn.model.article;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleDetail {

    private int id;
    private int authorId;
    private String title;
    private String subDescription;
    private LocalDateTime postDate;
    private String content;

    // Formatter dùng chung
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ===== GETTERS & SETTERS =====

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    @ColumnName("author_id")
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubDescription() {
        return subDescription;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    @ColumnName("post_date")
    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // ===== FORMAT HELPER =====
    public String getPostDateFormat() {
        return postDate != null ? FORMATTER.format(postDate) : "";
    }
}
