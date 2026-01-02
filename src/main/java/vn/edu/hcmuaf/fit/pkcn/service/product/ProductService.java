package vn.edu.hcmuaf.fit.pkcn.service.product;

import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProduct;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private ProductDao productDao;
    private SortProduct sortSql;

    public ProductService(ProductDao productDao, SortProduct sortSql) {
        this.productDao = productDao;
        this.sortSql = sortSql;
    }

    /*
        kiểm tra nếu sortOption là null hoặc empty thì chỉ lấy các dữ liệu lên;
        Trong trường hợp có thì ta truyền câu sql của SortProduct đã xử lí vào hàm của productDao
     */
    public List<ProductShowAsItem> getAllProducts(String sortOption) {
        if (sortOption == null || sortOption.isEmpty()) return productDao.getAllProduct(null);
        String sql = checkSortOption(sortOption, false, false);
        if (sql == null) throw new IllegalArgumentException("Không có sort option tương ứng: " + sortOption);
        return productDao.getAllProduct(sql);
    }

    /*
        Kiểm tra tương tự cho sortOption, nếu câu sql nhận về là null thì tiến hành sql theo mặc định
        đã định nghĩa trong hàm của productDao
     */
    public List<ProductShowAsItem> getProductByCategory(int categoryId, boolean isParent, String sortOption) {
        if (sortOption == null || sortOption.isEmpty())
            return isParent
                    ? productDao.getProductByParentCategoryId(categoryId, null)
                    : productDao.getProductsByCategoryId(categoryId, null);
        String sql = checkSortOption(sortOption, isParent, true);

        if (sql == null) throw new IllegalArgumentException("Không có sort option tương ứng: " + sortOption);

        return isParent
                ? productDao.getProductByParentCategoryId(categoryId, sql)
                : productDao.getProductsByCategoryId(categoryId, sql);
    }

    public List<ProductShowAsItem> getNewProducts(int limit) {
        return productDao.getNewProducts(limit);
    }


    //Co the tra ve null
    public List<ProductShowAsItem> getFeaturedProducts(int limit, int avgStar) {
        List<ProductShowAsItem> lst;
        //Trong truong hop shop co san pham noi bat theo danh gia (trung binh >= 4.0) thi uu tien hien
        lst = productDao.getFeaturedProductsByReview(limit, avgStar);

        //Neu khong co cac san pham nao duoc danh gia hoac danh gia kem thi shop co the hien thi
        //cac san pham duoc admin danh dau la noi bat
        if (lst == null) lst = productDao.getFeatureProductsByOne(limit);

        //neu cung khong co san pham duoc danh dau noi bat thi tien hanh hidden section noi bat (tra ve null)
        return lst;
    }

    public List<ProductShowAsItem> getProductsByChildCategory(int limit, int categoryId) {
        return productDao.getProductsByChildCategory(categoryId, limit);
    }

    /*
        Kiểm tra sortOption, vì SortProduct nhận vào isParent và getByCategoryId nên ta nhận vào thêm 2 thằng này;
     */
    private String checkSortOption(String sortOption, boolean isParent, boolean getByCategoryId) {
        String sql = null;
        switch (sortOption) {
            case "newest-product": {
                sql = sortSql.sqlSortByNewestProduct(isParent, getByCategoryId);
                break;
            }
            case "oldest-product": {
                sql = sortSql.sqlSortByOldestProduct(isParent, getByCategoryId);
                break;
            }
            case "cheap-to-exp": {
                sql = sortSql.sqlSortByCheapestProduct(isParent, getByCategoryId);
                break;
            }
            case "exp-to-cheap": {
                sql = sortSql.sqlSortByMostExpensiveProduct(isParent, getByCategoryId);
                break;
            }
            case "low-to-high-score-evaluate": {
                sql = sortSql.sqlSortByLowestStarProduct(isParent, getByCategoryId);
                break;
            }
            case "high-to-low-score-evaluate": {
                sql = sortSql.sqlSortByHighestStarProduct(isParent, getByCategoryId);
            }
        }
        return sql;
    }
}
