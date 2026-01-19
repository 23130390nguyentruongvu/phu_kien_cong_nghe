package vn.edu.hcmuaf.fit.pkcn.dao.product;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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

    public List<String> getSkusBySkus(List<String> skus) {
        String sql = """
                SELECT sku
                FROM product_variants
                WHERE sku IN (<skus>)
                """;
        return jdbi.withHandle(handle -> handle.createQuery(sql)
                .bindList("skus", skus)
                .mapTo(String.class)
                .list()
        );
    }

    public List<ProductVariant> getProdVarsByProdId(int prodId) {
        String sql = """
                SELECT pv.*, pi.url_image
                FROM product_variants pv
                JOIN product_images pi ON pi.product_variant_id = pv.id
                WHERE pv.product_id = :prodId
                """;
        return jdbi.withHandle(handle -> {
            Iterator<ProductVariant> iter = handle.createQuery(sql)
                    .bind("prodId", prodId)
                    .mapToBean(ProductVariant.class)
                    .stream().iterator();
            List<ProductVariant> res = new ArrayList<>();
            while (iter.hasNext()) res.add(iter.next());
            return res;
        });
    }

    public HashMap<Integer, List<ProductVariant>> getProdVarByProdId(List<Integer> ids) {
        String sql = "SELECT pv.*, pi.url_image " +
                "FROM product_variants pv " +
                "JOIN product_images pi ON pi.product_variant_id = pv.id " +
                "WHERE pv.product_id IN (<ids>)";
        return jdbi.withHandle(handle -> {
            HashMap<Integer, List<ProductVariant>> res = new HashMap<>();
            Iterator<ProductVariant> iter = handle.createQuery(sql)
                    .bindList("ids", ids)
                    .mapToBean(ProductVariant.class)
                    .stream().iterator();

            while (iter.hasNext()) {
                ProductVariant tmp = iter.next();
                if (res.containsKey(tmp.getProductId()))
                    res.get(tmp.getProductId()).add(tmp);
                else {
                    List<ProductVariant> lst = new ArrayList<>();
                    lst.add(tmp);
                    res.put(tmp.getProductId(), lst);
                }
            }
            return res;
        });
    }
}
