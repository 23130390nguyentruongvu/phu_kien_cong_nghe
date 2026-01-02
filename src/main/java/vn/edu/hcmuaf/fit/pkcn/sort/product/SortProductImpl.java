package vn.edu.hcmuaf.fit.pkcn.sort.product;

/*
    Đây là lớp impl lại interface SortProduct có nhiệm vụ định nghĩa các câu sql sort
    sản phẩm, lớp service check và truyền sql của lớp này vô cho ProductDao thực hiện
 */
public class SortProductImpl implements SortProduct {

    @Override
    public String sqlSortByNewestProduct(boolean isParent, boolean getByCategoryId) {
        String part = isParent ? " c.parent_id = :categoryId " : " pc.category_id = :categoryId ";
        String joinCategory = getByCategoryId
                ? "JOIN product_categories pc ON pc.product_id = p.id " +
                "JOIN categories c ON pc.category_id = c.id "
                : "";
        part = getByCategoryId?part:" 1=1 ";

        return "SELECT " +
                "p.name, " +
                "p.min_price, " +
                "pi.url_image " +
                "FROM products p " +
                joinCategory +
                "JOIN product_images pi ON pi.product_id = p.id "+
                "WHERE " + part +
                "AND pi.is_main = 1 " +
                "AND p.status = 1 " +
                "ORDER BY p.create_date DESC";
    }

    @Override
    public String sqlSortByOldestProduct(boolean isParent, boolean getByCategoryId) {
        String part = isParent ? " c.parent_id = :categoryId " : " pc.category_id = :categoryId ";
        String joinCategory = getByCategoryId
                ? "JOIN product_categories pc ON pc.product_id = p.id " +
                "JOIN categories c ON pc.category_id = c.id "
                : "";
        part = getByCategoryId?part:" 1=1 ";

        return "SELECT " +
                "p.name, " +
                "p.min_price, " +
                "pi.url_image " +
                "FROM products p " +
                "JOIN product_images pi ON pi.product_id = p.id " +
                joinCategory +
                "WHERE " + part +
                "AND pi.is_main = 1 " +
                "AND p.status = 1 " +
                "ORDER BY p.create_date";
    }

    @Override
    public String sqlSortByCheapestProduct(boolean isParent, boolean getByCategoryId) {
        String part = isParent ? " c.parent_id = :categoryId " : " pc.category_id = :categoryId ";
        String joinCategory = getByCategoryId
                ? "JOIN product_categories pc ON pc.product_id = p.id " +
                "JOIN categories c ON pc.category_id = c.id "
                : "";
        part = getByCategoryId?part:" 1=1 ";

        return "SELECT " +
                "p.name, " +
                "p.min_price, " +
                "pi.url_image " +
                "FROM products p " +
                "JOIN product_images pi ON pi.product_id = p.id " +
                joinCategory +
                "WHERE " + part +
                "AND pi.is_main = 1 " +
                "AND p.status = 1 " +
                "ORDER BY p.min_price";
    }

    @Override
    public String sqlSortByMostExpensiveProduct(boolean isParent, boolean getByCategoryId) {
        String part = isParent ? " c.parent_id = :categoryId " : " pc.category_id = :categoryId ";
        String joinCategory = getByCategoryId
                ? "JOIN product_categories pc ON pc.product_id = p.id " +
                "JOIN categories c ON pc.category_id = c.id "
                : "";
        part = getByCategoryId?part:" 1=1 ";

        return "SELECT " +
                "p.name, " +
                "p.min_price, " +
                "pi.url_image " +
                "FROM products p " +
                "JOIN product_images pi ON pi.product_id = p.id " +
                joinCategory +
                "WHERE " + part +
                "AND pi.is_main = 1 " +
                "AND p.status = 1 " +
                "ORDER BY p.min_price DESC";
    }

    @Override
    public String sqlSortByHighestStarProduct(boolean isParent, boolean getByCategoryId) {
        String part = isParent ? " c.parent_id = :categoryId " : " pc.category_id = :categoryId ";
        String joinCategory = getByCategoryId
                ? "JOIN product_categories pc ON pc.product_id = p.id " +
                "JOIN categories c ON pc.category_id = c.id "
                : "";
        part = getByCategoryId?part:" 1=1 ";
        return "SELECT " +
                "p.name, " +
                "p.min_price, " +
                "pi.url_image " +
                "FROM products p " +
                joinCategory +
                "JOIN product_images pi ON pi.product_id = p.id " +
                "JOIN reviews r ON r.product_id = p.id " +
                "WHERE " + part +
                "AND pi.is_main = 1 " +
                "AND p.status = 1 " +
                "GROUP BY " +
                "p.id, " +
                "p.name, " +
                "p.min_price, " +
                "pi.url_image " +
                "ORDER BY SUM(r.num_star) DESC";
    }

    @Override
    public String sqlSortByLowestStarProduct(boolean isParent, boolean getByCategoryId) {
        String part = isParent ? " c.parent_id = :categoryId " : " pc.category_id = :categoryId ";
        String joinCategory = getByCategoryId
                ? "JOIN product_categories pc ON pc.product_id = p.id " +
                "JOIN categories c ON pc.category_id = c.id "
                : "";
        part = getByCategoryId?part:" 1=1 ";
        return "SELECT " +
                "p.name, " +
                "p.min_price, " +
                "pi.url_image " +
                "FROM products p " +
                joinCategory +
                "JOIN product_images pi ON pi.product_id = p.id " +
                "JOIN reviews r ON r.product_id = p.id " +
                "WHERE " + part +
                "AND pi.is_main = 1 " +
                "AND p.status = 1 " +
                "GROUP BY " +
                "p.id, " +
                "p.name, " +
                "p.min_price, " +
                "pi.url_image " +
                "ORDER BY SUM(r.num_star)";
    }
}
