package vn.edu.hcmuaf.fit.pkcn.dao.product;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import vn.edu.hcmuaf.fit.pkcn.model.admin.add.JSonProduct;
import vn.edu.hcmuaf.fit.pkcn.model.admin.add.JsonUpdateProduct;
import vn.edu.hcmuaf.fit.pkcn.model.product.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ProductDao {
    private Jdbi jdbi;

    public ProductDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public HashMap<Integer, List<ProductVariantWrapOrder>> getProdVarWrapOrder(List<Integer> orderIds) {
        String sql = "SELECT od.order_id, od.id, od.product_variant_id, p.name as name_product, pv.name as type, od.quantity, od.price_total, pi.url_image " +
                "FROM order_details od " +
                "JOIN product_variants pv ON pv.id = od.product_variant_id " +
                "JOIN product_images pi ON pi.product_variant_id = pv.id " +
                "JOIN products p ON p.id = pv.product_id " +
                "WHERE od.order_id IN(<orderIds>)";
        return jdbi.withHandle(handle -> {
            HashMap<Integer, List<ProductVariantWrapOrder>> res = new HashMap<>();
            Iterator<ProductVariantWrapOrder> iter = handle.createQuery(sql)
                    .bindList("orderIds", orderIds)
                    .mapToBean(ProductVariantWrapOrder.class)
                    .stream().iterator();
            while (iter.hasNext()) {
                ProductVariantWrapOrder tmp = iter.next();
                if (res.containsKey(tmp.getOrderId())) res.get(tmp.getOrderId()).add(tmp);
                else {
                    List<ProductVariantWrapOrder> tmps = new ArrayList<>();
                    tmps.add(tmp);
                    res.put(tmp.getOrderId(), tmps);
                }
            }
            return res;
        });
    }

    public List<ProductShowAsItem> getAllProduct(String sortSql) {
        String sql = (sortSql == null) ? "SELECT p.*, pi.url_image " +
                "FROM products p " +
                "JOIN product_images pi ON pi.product_id = p.id " +
                "WHERE pi.is_main = 1 AND pi.product_variant_id IS NULL"
                : sortSql;
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

    public int updateProductWithTransaction(Handle handle, JsonUpdateProduct product) {
        String sql = """
                 UPDATE products
                 SET status = :active,
                 name = :name,
                 warranty_period = :warrantyPeriod,
                 subtitle = :subtitle,
                 description = :description,
                 folder_id = :folderId
                WHERE id = :prodId
                """;
        return handle.createUpdate(sql)
                .bind("name", product.getName())
                .bind("active", product.isActive())
                .bind("warrantyPeriod", product.getWarrantyPeriod())
                .bind("subtitle", product.getSubtitle())
                .bind("description", product.getDescription())
                .bind("folderId", product.getFolderId())
                .bind("prodId", product.getId())
                .execute();
    }

    public int updatePriceAndStock(Handle handle, int productId) {
        String sql = """
                  UPDATE products p
                     LEFT JOIN (
                         SELECT product_id, MIN(price) as min_p, MAX(price) as max_p, SUM(stock) as total_stock
                         FROM product_variants
                         WHERE product_id = :productId
                         GROUP BY product_id
                     ) pv_stats ON p.id = pv_stats.product_id
                    SET p.min_price = COALESCE(pv_stats.min_p, 0),
                    p.max_price = COALESCE(pv_stats.max_p, 0),
                    p.stock = COALESCE(pv_stats.total_stock, 0)
                WHERE p.id = :productId;
                """;
        return handle.createUpdate(sql).bind("productId", productId).execute();
    }

    public ProductAdminShowAsItem getProductAdmin(int prodId) {
        String sql = "SELECT p.*, pi.url_image " +
                "FROM products p " +
                "JOIN product_images pi ON p.id = pi.product_id " +
                "WHERE p.id = :prodId " +
                "AND pi.is_main = 1";
        return jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("prodId", prodId)
                .mapToBean(ProductAdminShowAsItem.class)
                .findOne()
                .orElse(null));
    }

    public HashMap<Integer, ProductAdminShowAsItem> getProducts(String key) {
        String sql = "SELECT p.*, pi.url_image " +
                "FROM products p " +
                "JOIN product_images pi ON p.id = pi.product_id " +
                "WHERE (:key IS NULL OR (p.id = :key OR name LIKE CONCAT('%', :key, '%'))) " +
                "AND pi.is_main = 1";
        return jdbi.withHandle(handle -> {
            HashMap<Integer, ProductAdminShowAsItem> res = new HashMap<>();
            Iterator<ProductAdminShowAsItem> iter = handle.createQuery(sql)
                    .bind("key", key)
                    .mapToBean(ProductAdminShowAsItem.class)
                    .stream().iterator();
            while (iter.hasNext()) {
                ProductAdminShowAsItem tmp = iter.next();
                res.put(tmp.getProdId(), tmp);
            }
            return res;
        });
    }

    public List<ProductShowAsItem> getProductByParentCategoryId(int categoryId, String sortSql) {
        String sql = (sortSql == null) ? "SELECT p.*, pi.url_image " +
                "FROM categories c " +
                "JOIN product_categories pc ON pc.category_id = c.id " +
                "JOIN products p ON p.id = pc.product_id " +
                "JOIN product_images pi ON pi.product_id = p.id " +
                "WHERE c.parent_id = :categoryId AND pi.is_main = 1 AND pi.product_variant_id IS NULL"
                : sortSql;
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

            // Load product thông tin chung + category
            String productSql = ""
                    + "SELECT p.id, p.name, p.subtitle AS subtitle, p.description, "
                    + "       p.warranty_period AS warranty_period, c.category_name "
                    + "FROM products p "
                    + "LEFT JOIN product_categories pc ON pc.product_id = p.id "
                    + "LEFT JOIN categories c ON c.id = pc.category_id "
                    + "WHERE p.id = :productId AND p.status = 1";

            ProductDetail product = handle.createQuery(productSql)
                    .bind("productId", productId)
                    .mapToBean(ProductDetail.class)
                    .findOne()
                    .orElse(null);

            if (product == null) return null; // sản phẩm không tồn tại

            // Load variants
            String variantSql = ""
                    + "SELECT pv.id, pv.product_id, pv.sku, pv.name, pv.price, pv.stock, "
                    + "       pv.gram, pv.color, pv.size "
                    + "FROM product_variants pv "
                    + "WHERE pv.product_id = :productId";

            List<ProductVariant> variants = handle.createQuery(variantSql)
                    .bind("productId", productId)
                    .mapToBean(ProductVariant.class)
                    .list();

            product.setVariants(variants);

            // Load tất cả ảnh sản phẩm (main + variant)
            String imageSql = ""
                    + "SELECT pi.id, pi.product_id, pi.product_variant_id AS pv_id, pi.url_image "
                    + "FROM product_images pi "
                    + "WHERE pi.product_id = :productId "
                    + "ORDER BY pi.is_main DESC, pi.id ASC"; // main image lên đầu

            List<Image> images = handle.createQuery(imageSql)
                    .bind("productId", productId)
                    .map((rs, ctx) -> {
                        Image img = new Image();
                        img.setId(rs.getInt("id"));
                        img.setUrlImage(rs.getString("url_image"));
                        img.setPvId(rs.getObject("pv_id") != null ? rs.getInt("pv_id") : null);
                        return img;
                    })
                    .list();

            product.setImages(images);

            return product;
        });
    }

    public List<ProductShowAsItem> getRelatedProducts(int productId) {
        String sql = """
                    SELECT 
                        p.id,
                        p.name AS name,
                        p.min_price,
                        (
                            SELECT pi.url_image
                            FROM product_images pi
                            WHERE pi.product_id = p.id
                              AND pi.is_main = 1
                            LIMIT 1
                        ) AS url_image
                    FROM products p
                    JOIN product_categories pc ON p.id = pc.product_id
                    WHERE pc.category_id IN (
                        SELECT category_id
                        FROM product_categories
                        WHERE product_id = :productId
                    )
                    AND p.id != :productId
                    ORDER BY RAND()
                    LIMIT 4
                """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("productId", productId)
                        .mapToBean(ProductShowAsItem.class)
                        .list()
        );
    }

    public List<ProductShowAsItem> searchByKeyword(String keyword) {

        String sql = """
                    SELECT
                        p.id,
                        p.name,
                        p.min_price,
                        (
                            SELECT pi.url_image
                            FROM product_images pi
                            WHERE pi.product_id = p.id
                              AND pi.is_main = 1
                            LIMIT 1
                        ) AS url_image
                    FROM products p
                    WHERE p.name LIKE :keyword
                    AND p.status = 1
                """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("keyword", "%" + keyword + "%")
                        .mapToBean(ProductShowAsItem.class)
                        .list()
        );
    }

    public List<ProductShowAsItem> searchWithFilter(
            String keyword,
            Integer minPrice,
            Integer maxPrice,
            String categoryId,
            String sort,
            String rating
    ) {
        String sql = """
                    SELECT
                        p.id,
                        p.name,
                        p.min_price,
                        (
                            SELECT pi.url_image
                            FROM product_images pi
                            WHERE pi.product_id = p.id
                              AND pi.is_main = 1
                            LIMIT 1
                        ) AS url_image
                    FROM products p
                    LEFT JOIN product_categories pc
                        ON pc.product_id = p.id
                    WHERE p.status = 1
                    AND (:keyword IS NULL OR :keyword = '' 
                         OR p.name LIKE :keyword)
                    AND (:minPrice IS NULL OR p.min_price >= :minPrice)
                    AND (:maxPrice IS NULL OR p.min_price <= :maxPrice)
                    AND (:categoryId IS NULL OR pc.category_id = :categoryId)
                    AND (
                        :rating IS NULL
                        OR (
                            SELECT AVG(r.num_star)
                            FROM reviews r
                            WHERE r.product_id = p.id
                        ) >= :rating
                    )
                    ORDER BY
                        CASE WHEN :sort = 'price-asc'  THEN p.min_price END ASC,
                        CASE WHEN :sort = 'price-desc' THEN p.min_price END DESC,
                        CASE WHEN :sort = 'newest'     THEN p.create_date END DESC,
                        p.id ASC
                """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("keyword", "%" + keyword + "%")
                        .bind("minPrice", minPrice)
                        .bind("maxPrice", maxPrice)
                        .bind("categoryId", categoryId)
                        .bind("sort", sort)
                        .bind("rating", rating)
                        .mapToBean(ProductShowAsItem.class)
                        .list()
        );
    }

    public int insertProductWithTransaction(Handle handle, JSonProduct product) {
        String sql = """
                INSERT INTO products(folder_id, name, warranty_period, subtitle, description, min_price, max_price, status, stock)
                VALUES(:folderId, :name, :warrantyPeriod, :subtitle, :description, :minPrice, :maxPrice, :status, :stock)
                """;
        return handle.createUpdate(sql)
                .bindBean(product)
                .executeAndReturnGeneratedKeys()
                .mapTo(Integer.class)
                .one();
    }

    public String getFolderId(int prodId) {
        String sql = """
                SELECT folder_id
                FROM products
                WHERE id = :prodId
                """;
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("prodId", prodId)
                        .mapTo(String.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public boolean removeProduct(int prodId) {
        String sql = """
                DELETE 
                FROM products
                WHERE id = :prodId
                """;
        return jdbi.withHandle(handle -> handle.createUpdate(sql).bind("prodId", prodId).execute() > 0);
    }

    public int updateFolderIdWithTransaction(Handle handle, int prodId, String folderId) {
        String sql = """
                UPDATE products
                SET folder_id = :folderId
                WHERE id = :prodId
                """;
        return handle.createUpdate(sql)
                .bind("prodId", prodId)
                .bind("folderId", folderId)
                .execute();
    }
}
