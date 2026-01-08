package vn.edu.hcmuaf.fit.pkcn.service.product;

import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariant;

public class ProductVariantService {
    private ProductVariantDao productVariantDao;

    public ProductVariantService(ProductVariantDao productVariantDao) {
        this.productVariantDao = productVariantDao;
    }
    public ProductVariant getProductVariantById(int prodVarId) {
        try {
            return productVariantDao.getProdVarById(prodVarId);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
