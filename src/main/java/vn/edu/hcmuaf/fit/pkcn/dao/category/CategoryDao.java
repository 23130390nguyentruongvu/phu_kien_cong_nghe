package vn.edu.hcmuaf.fit.pkcn.dao.category;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.category.Category;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public List<Category> getParentCategories() {
        String sql = "SELECT * " +
                "FROM categories " +
                "WHERE parent_id IS NULL";
        return jdbi.withHandle(handle -> {
            List<Category> lst = new ArrayList<>();
            Iterator<Category> iter = handle.createQuery(sql)
                    .mapToBean(Category.class).stream().iterator();

            while (iter.hasNext()) {
                lst.add(iter.next());
            }
            return lst;
        });
    }

    //Trả về true nếu categoryId có tồn tại và parent_id của nó là null
    public boolean isCategoryParent(int categoryId) {
        String sql = "SELECT 1 " +
                "FROM categories " +
                "WHERE id = :categoryId AND parent_id IS NULL";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("categoryId", categoryId)
                        .mapTo(Integer.class)
                        .findOne()
                        .isPresent()

        );
    }

    //Lấy SubCategories
    public List<Category> getSubCategories(int parentId) {
        String sql = """
                    SELECT *
                    FROM categories
                    WHERE parent_id = :parentId
                """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("parentId", parentId)
                        .mapToBean(Category.class)
                        .list()
        );
    }

    public void insertCategoryProductWithTransaction(Handle handle, int categoryId, int productId) {
        String sql = """
                INSERT INTO product_categories(product_id, category_id)
                VALUES(:productId, :categoryId)
                """;
        handle.createUpdate(sql)
                .bind("productId", productId)
                .bind("categoryId", categoryId)
                .execute();
    }

    public List<Category> getSubCategories() {
        String sql = """
                    SELECT *
                    FROM categories
                    WHERE parent_id IS NOT NULL
                """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(Category.class)
                        .list()
        );
    }
}
