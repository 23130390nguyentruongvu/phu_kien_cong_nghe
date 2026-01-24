package vn.edu.hcmuaf.fit.pkcn.model.review;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.LocalDateTime;

public class ReviewProduct {
    private int id;
    private int productId;
    private String email;
    private String name;
    private String evaluate;
    private int numStar;
    private LocalDateTime evaluateDate;
    private String status;

    public int getId() {
        return id;
    }

    @ColumnName("id")
    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    @ColumnName("product_id")
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getEmail() {
        return email;
    }

    @ColumnName("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    @ColumnName("name")
    public void setName(String name) {
        this.name = name;
    }

    public int getNumStar() {
        return numStar;
    }

    @ColumnName("num_star")
    public void setNumStar(int numStar) {
        this.numStar = numStar;
    }

    public LocalDateTime getEvaluateDate() {
        return evaluateDate;
    }

    @ColumnName("evaluate_date")
    public void setEvaluateDate(LocalDateTime evaluateDate) {
        this.evaluateDate = evaluateDate;
    }

    public String getStatus() {
        return status;
    }

    @ColumnName("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public String getEvaluate() {
        return evaluate;
    }

    @ColumnName("evaluate")
    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getFormattedDate() {
        if (this.evaluateDate == null) return "";
        return this.evaluateDate.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
