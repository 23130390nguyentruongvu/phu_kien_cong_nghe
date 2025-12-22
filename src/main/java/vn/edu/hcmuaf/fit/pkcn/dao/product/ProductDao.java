package vn.edu.hcmuaf.fit.pkcn.dao.product;

import org.jdbi.v3.core.Jdbi;

import vn.edu.hcmuaf.fit.pkcn.model.product.ProductShowAsItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductDao {
    private Jdbi jdbi;

    public ProductDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<ProductShowAsItem> getNewProducts(int limit) {
        String sql = "SELECT p.*, pi.url_image " +
                "FROM products p " +
                "JOIN product_images pi ON pi.product_id = p.id " +
                "WHERE pi.product_variant_id IS NULL AND pi.is_main = 1 AND p.status = 1 " +
                "ORDER BY p.create_date DESC " +
                "LIMIT :limit";
        return getProductShowAsItemBySql(sql, limit);
    }

    public List<ProductShowAsItem> getFeatureProducts(int limit) {
        String sql = "SELECT p.name, pi.url_image, p.min_price, p.id " +
                "FROM products p " +
                "JOIN product_images pi ON pi.product_id = p.id " +
                "WHERE p.is_featured = 1 AND p.status = 1 " +
                "AND pi.product_variant_id IS NULL AND pi.is_main = 1 " +
                "ORDER BY p.create_date DESC " +
                "LIMIT :limit";
        return getProductShowAsItemBySql(sql, limit);
    }

    public List<ProductShowAsItem> getProductsByChildCategory(int categoryId, int limit) {
        String sql = "SELECT p.name, pi.url_image, p.min_price, p.id, c.category_name, pc.category_id " +
                "FROM products p " +
                "JOIN product_images pi ON pi.product_id = p.id " +
                "JOIN product_categories pc ON pc.product_id = p.id " +
                "JOIN categories c ON c.id = pc.category_id AND c.id = :categoryId " +
                "WHERE p.status = 1 " +
                "AND pi.product_variant_id IS NULL AND pi.is_main = 1 " +
                "ORDER BY p.create_date DESC " +
                "LIMIT :limit";
        return getProductShowAsItemBySql(sql, limit, categoryId);
    }

    private List<ProductShowAsItem> getProductShowAsItemBySql(String sql, int limit) {
        return jdbi.withHandle(handle -> {
            Iterator<ProductShowAsItem> i =
                    handle.createQuery(sql)
                            .bind("limit", limit)
                            .mapToBean(ProductShowAsItem.class).stream().iterator();
            List<ProductShowAsItem> lst = new ArrayList<>();
            while (i.hasNext()) {
                lst.add(i.next());
            }
            return lst;
        });
    }

    private List<ProductShowAsItem> getProductShowAsItemBySql(String sql, int limit, int categoryId) {
        return jdbi.withHandle(handle -> {
            Iterator<ProductShowAsItem> i =
                    handle.createQuery(sql)
                            .bind("categoryId", categoryId)
                            .bind("limit", limit)
                            .mapToBean(ProductShowAsItem.class).stream().iterator();
            List<ProductShowAsItem> lst = new ArrayList<>();
            while (i.hasNext()) {
                lst.add(i.next());
            }
            return lst;
        });
    }
}
