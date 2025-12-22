package vn.edu.hcmuaf.fit.pkcn.dao.category;

import org.jdbi.v3.core.Jdbi;

public class CategoryDao {
    private Jdbi jdbi;
    public CategoryDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }
    public String getNameCategoryById(int categoryId) {
        String sql = "SELECT category_name FROM categories WHERE id = :categoryId";
        return jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("categoryId", categoryId)
                .mapTo(String.class).first());
    }
}
