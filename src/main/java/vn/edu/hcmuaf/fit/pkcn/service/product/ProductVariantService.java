package vn.edu.hcmuaf.fit.pkcn.service.product;

import org.jdbi.v3.core.Handle;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.model.admin.add.JSonAddVariant;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariant;

import java.util.List;

public class ProductVariantService {
    private ProductVariantDao productVariantDao;

    public ProductVariantService(ProductVariantDao productVariantDao) {
        this.productVariantDao = productVariantDao;
    }

    public int getProductId(int variantId) {
        return productVariantDao.getProductId(variantId);
    }

    public ProductVariant getProductVariantById(int prodVarId) {
        try {
            return productVariantDao.getProdVarById(prodVarId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int insertVariantWithTransaction(Handle handle, int prodId, JSonAddVariant variant) {
        return productVariantDao.insertProductVariantWithTransaction(handle, prodId, variant);
    }

    public String getSku(int variantId) throws Exception {
        String sku = productVariantDao.getSku(variantId);
        if (sku == null) throw new Exception("Không tìm thấy mã sku");
        return sku;
    }

    public List<ProductVariant> getProductVariantsByProdId(int prodId) {
        return productVariantDao.getProdVarsByProdId(prodId);
    }

    public List<String> getSkusBySkus(List<String> skus) {
        return productVariantDao.getSkusBySkus(skus);
    }
}
