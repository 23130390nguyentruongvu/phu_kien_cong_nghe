package vn.edu.hcmuaf.fit.pkcn.service.product;

import org.jdbi.v3.core.Handle;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.category.CategoryDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductImageDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.model.admin.add.JSonProduct;
import vn.edu.hcmuaf.fit.pkcn.model.admin.add.JSonProductVariant;
import vn.edu.hcmuaf.fit.pkcn.model.admin.edit.JsonUpdateProduct;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductAdminShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariant;
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProduct;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductDetail;
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProductImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {
    private ProductDao productDao;
    private SortProduct sortSql;
    private ProductImageDao productImageDao;
    private ProductVariantDao productVariantDao;
    private CategoryDao categoryDao;

    public ProductService(ProductDao productDao,
                          SortProduct sortSql,
                          ProductImageDao productImageDao,
                          ProductVariantDao productVariantDao,
                          CategoryDao categoryDao
    ) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
        this.sortSql = sortSql;
        this.productImageDao = productImageDao;
        this.productVariantDao = productVariantDao;
    }

    public ProductService(ProductDao productDao,
                          SortProductImpl sortSql
    ) {
        this.productDao = productDao;
        this.sortSql = sortSql;
    }

    public boolean updateProduct(JsonUpdateProduct product) {
        return JDBI.getJdbi().inTransaction(handle -> {
            //Cập nhật thông tin sản phẩm
            int updated = productDao.updateProductWithTransaction(handle, product);
            if (updated <= 0) return false;
            //Xóa các ảnh dưới dbF
            if (product.getRemoveUrls() != null && !product.getRemoveUrls().isEmpty()) {
                productImageDao.deleteUrlsProdWithTransaction(handle, product.getId(), product.getRemoveUrls());
            }
            //Thêm các ảnh mới
            if (product.getNewImages() != null && !product.getNewImages().isEmpty()) {
                for (String newUrl : product.getNewImages()) {
                    int inserted = productImageDao.insertProductImageWithTransaction(handle, null, product.getId(), newUrl);
                    if (inserted <= 0) return false;
                }
            }
            //Cập nhật ảnh chính
            productImageDao.updateMainImageWithTransaction(handle, null, product.getId(), product.getImageMainUrl());

            return true;
        });
    }

    public void updatePriceAndStockProductWithTransaction(Handle handle, int prodId) {
        productDao.updatePriceAndStock(handle, prodId);
    }

    public boolean removeProduct(int prodId) {
        return productDao.removeProduct(prodId);
    }

    public boolean removeVarAndUpdatePriceProductWithTransaction(int prodId, int variantId) {
        return JDBI.getJdbi().inTransaction(handle -> {
            boolean isSuccess = true;
            isSuccess = isSuccess && productVariantDao.removeProductVariant(handle, variantId);

            isSuccess = isSuccess && productDao.updatePriceAndStock(handle, prodId) > 0;

            return isSuccess;
        });
    }

    public ProductAdminShowAsItem getProductAdmin(int prodId) throws Exception {
        ProductAdminShowAsItem res = productDao.getProductAdmin(prodId);
        if (res == null) throw new Exception("Lỗi không tìm thấy product");
        //Đi lấy ảnh phụ
        res.setImages(productImageDao.getImagesProduct(prodId, false));
        return res;
    }

    public String getFolderIdWithVarId(int variantId) throws Exception {
        int prodId = productVariantDao.getProductId(variantId);
        if (prodId == -1) throw new Exception("Không tìm thấy product id");
        String folderId = productDao.getFolderId(prodId);
        if (folderId == null) throw new Exception("Không tìm thấy folder id");
        return folderId;
    }

    public String getFolderIdWithProdId(int productId) throws Exception {
        return productDao.getFolderId(productId);
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

    public ProductDetail getProductDetailById(int productId) {
        if (productId <= 0) return null;

        ProductDetail product = productDao.getProductDetailById(productId);
        if (product == null) return null;

        List<ProductVariant> variants = product.getVariants();
        if (variants != null && !variants.isEmpty()) {

            // default variant
            ProductVariant defaultVariant = variants.stream()
                    .filter(v -> v.getStock() > 0)
                    .findFirst()
                    .orElse(variants.get(0));
            product.setDefaultVariant(defaultVariant);


        }
        return product;
    }

    public List<ProductAdminShowAsItem> getProductsForAdmin(String key) {
        HashMap<Integer, ProductAdminShowAsItem> res = productDao.getProducts(key);
        if (res.isEmpty()) return null;
        List<Integer> ids = res.keySet().stream().toList();

        //Lấy các ảnh phụ
        HashMap<Integer, List<String>> images = productImageDao.getImagesProduct(ids, false);
        for (Map.Entry<Integer, List<String>> entry : images.entrySet())
            res.get(entry.getKey()).setImages(entry.getValue());

        //Lấy các biến thể
        HashMap<Integer, List<ProductVariant>> variants = productVariantDao.getProdVarByProdId(ids);
        for (Map.Entry<Integer, List<ProductVariant>> entry : variants.entrySet())
            res.get(entry.getKey()).setProductVariants(entry.getValue());

        return res.values().stream().toList();
    }

    public List<ProductShowAsItem> getRelatedProducts(int productId) {
        return productDao.getRelatedProducts(productId);
    }

    public List<ProductShowAsItem> searchProducts(String keyword) {
        return productDao.searchByKeyword(keyword);
    }

    public List<ProductShowAsItem> searchWithFilter(
            String keyword,
            Integer minPrice,
            Integer maxPrice,
            String category,
            String sort,
            String rating
    ) {
        return productDao.searchWithFilter(
                keyword, minPrice, maxPrice, category, sort, rating
        );
    }

    public int updateFolderIdWithTransaction(Handle handle, int prodId, String folderId) {
        return productDao.updateFolderIdWithTransaction(handle, prodId, folderId);
    }

    public boolean addProduct(JSonProduct product) {
        try {
            return JDBI.getJdbi().inTransaction(handle -> {
                //add product
                int idProd = productDao.insertProductWithTransaction(handle, product);
                HashMap<Integer, JSonProductVariant> variantHashMap = new HashMap<>();

                //add product variant
                for (JSonProductVariant variant : product.getVariants()) {
                    int idVar = productVariantDao.insertProductVariantWithTransaction(handle, idProd, variant);
                    variantHashMap.put(idVar, variant);//Vì variant có chứa ảnh nên cần nhớ id của nó cho product image
                }

                //add image
                for (String url : product.getImageUrls())
                    productImageDao.insertProductImageWithTransaction(handle, null, idProd, url);

                for (Map.Entry<Integer, JSonProductVariant> entry : variantHashMap.entrySet())
                    productImageDao.insertProductImageWithTransaction(
                            handle, entry.getKey(), idProd, entry.getValue().getImageUrl()
                    );
                productImageDao.updateMainImageWithTransaction(handle, null, idProd, product.getMainImageUrl());
                categoryDao.insertCategoryProductWithTransaction(handle, Integer.parseInt(product.getCategoryId()), idProd);

                return true;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
