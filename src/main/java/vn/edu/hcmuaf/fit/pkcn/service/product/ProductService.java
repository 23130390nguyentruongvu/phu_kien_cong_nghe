package vn.edu.hcmuaf.fit.pkcn.service.product;

import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductShowAsItem;

import java.util.List;

public class ProductService {
    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductShowAsItem> getNewProducts(int limit) {
        return productDao.getNewProducts(limit);
    }

    public List<ProductShowAsItem> getFeaturedProducts(int limit) {
        return productDao.getFeatureProducts(limit);
    }

    public List<ProductShowAsItem> getProductsByChildCategory(int limit, int categoryId) {
        return productDao.getProductsByChildCategory(categoryId, limit);
    }
}
