package vn.edu.hcmuaf.fit.pkcn.dao.product;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariant;

public class ProductVariantDao {
    private Jdbi jdbi;

    public ProductVariantDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public ProductVariant getProdVarById(int prodVarId) {
        String sql = "SELECT * FROM product_variants WHERE id = :prodVarId";
        return jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("prodVarId", prodVarId)
                .mapToBean(ProductVariant.class)
                .one()
        );
    }
}
