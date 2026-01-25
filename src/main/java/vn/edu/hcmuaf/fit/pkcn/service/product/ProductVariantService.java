package vn.edu.hcmuaf.fit.pkcn.service.product;

import org.jdbi.v3.core.Handle;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.model.admin.add.JSonAddVariant;
import vn.edu.hcmuaf.fit.pkcn.model.admin.edit.JsonUpdateVariant;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public int updateVariantWithTransaction(Handle handle, JsonUpdateVariant variant) {
        return productVariantDao.updateVariantWithTransaction(handle, variant);
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

    //Map này có key là mã biến thể và value là số lượng
    public int appendStockVariantWithTransaction(Handle handle, HashMap<Integer, Integer> map) {
        if (map == null || map.isEmpty()) return 0;
        int rowEffect = 0;
        for (Map.Entry<Integer, Integer> tmp : map.entrySet()) {
            rowEffect += productVariantDao.appendStockVariantWithTransaction(handle, tmp.getKey(), tmp.getValue());
        }
        return rowEffect;
    }
}
