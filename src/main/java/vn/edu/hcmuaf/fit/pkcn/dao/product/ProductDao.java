package vn.edu.hcmuaf.fit.pkcn.dao.product;

import org.jdbi.v3.core.Jdbi;

import vn.edu.hcmuaf.fit.pkcn.model.product.ProductShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductDetail;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductDao {
    private Jdbi jdbi;

    public ProductDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<ProductShowAsItem> getAllProduct(String sortSql) {
        String sql = (sortSql == null)?"SELECT p.*, pi.url_image " +
                "FROM products p " +
                "JOIN product_images pi ON pi.product_id = p.id " +
                "WHERE pi.is_main = 1 AND pi.product_variant_id IS NULL"
                :sortSql;
        return jdbi.withHandle(handle -> {
            List<ProductShowAsItem> lst = new ArrayList<>();
            Iterator<ProductShowAsItem> iter = handle.createQuery(sql)
                    .mapToBean(ProductShowAsItem.class)
                    .stream().iterator();

            while (iter.hasNext()) {
                ProductShowAsItem i = iter.next();
                lst.add(i);
            }
            return lst;
        });
    }

    public List<ProductShowAsItem> getProductByParentCategoryId(int categoryId, String sortSql) {
        String sql = (sortSql == null)?"SELECT p.*, pi.url_image " +
                "FROM categories c " +
                "JOIN product_categories pc ON pc.category_id = c.id " +
                "JOIN products p ON p.id = pc.product_id " +
                "JOIN product_images pi ON pi.product_id = p.id " +
                "WHERE c.parent_id = :categoryId AND pi.is_main = 1 AND pi.product_variant_id IS NULL"
                :sortSql;
        return jdbi.withHandle(handle -> {
            List<ProductShowAsItem> lst = new ArrayList<>();
            Iterator<ProductShowAsItem> iter = handle.createQuery(sql)
                    .bind("categoryId", categoryId)
                    .mapToBean(ProductShowAsItem.class)
                    .stream().iterator();

            while (iter.hasNext()) {
                ProductShowAsItem i = iter.next();
                lst.add(i);
            }
            return lst;
        });
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

    public List<ProductShowAsItem> getFeatureProductsByOne(int limit) {
        String sql = "SELECT p.name, pi.url_image, p.min_price, p.id " +
                "FROM products p " +
                "JOIN product_images pi ON pi.product_id = p.id " +
                "WHERE p.is_featured = 1 AND p.status = 1 " +
                "AND pi.product_variant_id IS NULL AND pi.is_main = 1 " +
                "ORDER BY p.create_date DESC " +
                "LIMIT :limit";

        List<ProductShowAsItem> res = getProductShowAsItemBySql(sql, limit);
        return res.isEmpty() ? null : res;
    }

    //Lay cac san pham co trung binh star >= 4
    public List<ProductShowAsItem> getFeaturedProductsByReview(int limit, int avgStar) {
        try {
            if (avgStar > 5 || avgStar < 0) return null;
            String sql = "SELECT p.name, pi.url_image, p.min_price, p.id " +
                    "FROM products p " +
                    "JOIN product_images pi ON pi.product_id = p.id " +
                    "JOIN reviews r ON r.product_id = p.id " +
                    "WHERE p.status = 1 " +
                    "AND pi.product_variant_id IS NULL AND pi.is_main = 1 " +
                    "GROUP BY p.id, p.name, pi.url_image, p.min_price " +
                    "HAVING AVG(r.num_star) >= " + avgStar + " " +
                    "ORDER BY p.create_date DESC " +
                    "LIMIT :limit";

            List<ProductShowAsItem> res = getProductShowAsItemBySql(sql, limit);
            return res.isEmpty() ? null : res;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
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

    //Ham nay se tra ve mang rong neu khong thoa cau lenh sql
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

    public List<ProductShowAsItem> getProductsByCategoryId(int categoryId, String sqlSort) {
        String sql = (sqlSort == null) ? "SELECT p.name, pi.url_image, p.min_price, p.id " +
                "FROM products p " +
                "JOIN product_images pi ON pi.product_id = p.id " +
                "JOIN product_categories pc ON pc.product_id = p.id " +
                "WHERE p.status = 1 " +
                "AND pi.product_variant_id IS NULL " +
                "AND pi.is_main = 1 " +
                "AND pc.category_id = :categoryId" : sqlSort;
        return jdbi.withHandle(handle -> {
            List<ProductShowAsItem> res = new ArrayList<>();
            Iterator<ProductShowAsItem> iter = handle.createQuery(sql)
                    .bind("categoryId", categoryId)
                    .mapToBean(ProductShowAsItem.class)
                    .stream().iterator();
            while (iter.hasNext()) {
                res.add(iter.next());
            }
            return res;
        });
    }

    public ProductDetail getProductDetailById(int productId) {
        return jdbi.withHandle(handle -> {

            String productSql = ""
                    + "SELECT p.id, p.name, p.subtitle AS subDescription, p.description, "
                    + "       p.warranty_period AS warranty, c.category_name "
                    + "FROM products p "
                    + "JOIN product_categories pc ON pc.product_id = p.id "
                    + "JOIN categories c ON c.id = pc.category_id "
                    + "WHERE p.id = :productId AND p.status = 1";

            ProductDetail product = handle.createQuery(productSql)
                    .bind("productId", productId)
                    .mapToBean(ProductDetail.class)
                    .findOne()
                    .orElse(null);

            if (product == null) return null;

            String variantSql = ""
                    + "SELECT pv.id, pv.product_id, pv.sku, pv.name, pv.price, pv.stock, "
                    + "       pv.gram, pv.color, pv.size, pi.url_image "
                    + "FROM product_variants pv "
                    + "LEFT JOIN product_images pi ON pi.id = pv.product_variant_id AND pi.is_main = 1 "
                    + "WHERE pv.product_id = :productId";

            List<ProductVariant> variants = handle.createQuery(variantSql)
                    .bind("productId", productId)
                    .mapToBean(ProductVariant.class)
                    .list();

            product.setVariants(variants);

            String productImageSql = ""
                    + "SELECT pi.url_image "
                    + "FROM product_images pi "
                    + "WHERE pi.product_id = :productId AND pi.pv_id IS NULL";

            List<String> productImages = handle.createQuery(productImageSql)
                    .bind("productId", productId)
                    .mapTo(String.class)
                    .list();

            return product;
        });
    }



}
