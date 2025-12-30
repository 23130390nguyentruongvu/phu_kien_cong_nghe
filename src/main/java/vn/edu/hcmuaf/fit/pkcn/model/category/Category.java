package vn.edu.hcmuaf.fit.pkcn.model.category;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class Category {
    private int id;
    private Integer parentId;
    private String nameCategory;
    private String description;

    public Category() {
    }

    public int getId() {
        return id;
    }

    @ColumnName("id")
    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    @ColumnName("parent_id")
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    @ColumnName("category_name")
    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getDescription() {
        return description;
    }

    @ColumnName("description")
    public void setDescription(String description) {
        this.description = description;
    }
}
