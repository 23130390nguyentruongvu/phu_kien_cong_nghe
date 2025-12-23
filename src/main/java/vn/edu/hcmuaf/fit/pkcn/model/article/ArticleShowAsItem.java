package vn.edu.hcmuaf.fit.pkcn.model.article;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleShowAsItem {
    private int id;
    private int authorId;
    private String title;
    private String subDescription;
    private LocalDateTime postDate;
    private DateTimeFormatter dateTimeFormatter;

    public ArticleShowAsItem() {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

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

    @ColumnName("subdescription")
    public void setSubDescription(String subDescription) {
        if (subDescription.length() > 350) subDescription = subDescription.substring(0, 350) + "...";
        this.subDescription = subDescription;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    @ColumnName("post_date")
    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }

    public String getPostDateFormat() {
        try {
            return dateTimeFormatter.format(postDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
