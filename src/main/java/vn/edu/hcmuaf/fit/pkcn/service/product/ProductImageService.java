package vn.edu.hcmuaf.fit.pkcn.service.product;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductImageDao;

public class ProductImageService {
    private ProductImageDao productImageDao;

    public ProductImageService(ProductImageDao productImageDao) {
        this.productImageDao = productImageDao;
    }

    public int insertProductImageWithTransaction(Handle handle, int varId, int prodId, String url) {
        return productImageDao.insertProductImageWithTransaction(handle, varId, prodId, url);
    }
}
