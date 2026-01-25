package vn.edu.hcmuaf.fit.pkcn.dao.product;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.admin.add.JSonAddVariant;
import vn.edu.hcmuaf.fit.pkcn.model.admin.add.JSonProductVariant;
import vn.edu.hcmuaf.fit.pkcn.model.admin.edit.JsonUpdateVariant;
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
        String sql = "SELECT pv.*, pi.url_image " +
                "FROM product_variants pv " +
                "JOIN product_images pi ON pi.product_variant_id = pv.id " +
                "WHERE pv.id = :prodVarId";
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

    public int insertProductVariantWithTransaction(Handle handle, int prodId, JSonProductVariant variant) {
        String sql = """
                INSERT INTO product_variants (product_id, sku, name, price, stock, gram, color, size)
                VALUES(:prodId, :sku, :name, :price, :stock, :gram, :color, :size)
                """;
        return handle.createUpdate(sql)
                .bind("prodId", prodId) // Tham số id sản phẩm cha
                .bind("sku", variant.getSku())
                .bind("name", variant.getName())
                .bind("price", variant.getPrice())
                .bind("stock", variant.getStock())
                .bind("gram", variant.getGram())
                .bind("color", variant.getColor())
                .bind("size", variant.getSize())
                .executeAndReturnGeneratedKeys()
                .mapTo(Integer.class)
                .one();
    }

    public int insertProductVariantWithTransaction(Handle handle, int prodId, JSonAddVariant variant) {
        String sql = """
                INSERT INTO product_variants (product_id, sku, name, price, stock, gram, color, size)
                VALUES(:prodId, :sku, :name, :price, :stock, :gram, :color, :size)
                """;
        return handle.createUpdate(sql)
                .bind("prodId", prodId) // Tham số id sản phẩm cha
                .bind("sku", variant.getSku())
                .bind("name", variant.getName())
                .bind("price", variant.getPrice())
                .bind("stock", variant.getStock())
                .bind("gram", variant.getGram())
                .bind("color", variant.getColor())
                .bind("size", variant.getSize())
                .executeAndReturnGeneratedKeys()
                .mapTo(Integer.class)
                .one();
    }

    public boolean removeProductVariant(Handle handle, int variantId) {
        String sql = """
                DELETE FROM product_variants
                WHERE id = :variantId
                """;
        return handle.createUpdate(sql).bind("variantId", variantId).execute() > 0;
    }

    public int getProductId(int variantId) {
        String sql = """
                SELECT product_id
                FROM product_variants
                WHERE id = :variantId
                """;
        return jdbi
                .withHandle(
                        handle -> handle.createQuery(sql)
                                .bind("variantId", variantId)
                                .mapTo(Integer.class)
                                .findOne()
                                .orElse(-1));
    }

    public String getSku(int variantId) {
        String sql = """
                SELECT sku
                FROM product_variants
                WHERE id = :variantId
                """;
        return jdbi.withHandle(
                handle -> handle.createQuery(sql)
                        .bind("variantId", variantId)
                        .mapTo(String.class)
                        .findOne()
                        .orElse(null));
    }

    public int updateVariantWithTransaction(Handle handle, JsonUpdateVariant variant) {
        String sql = """
                UPDATE product_variants
                SET name = :name,
                    price = :price,
                    stock = :stock,
                    gram = :gram,
                    color = :color,
                    size = :size
                WHERE id = :variantId
                """;
        return handle.createUpdate(sql)
                .bind("name", variant.getName())
                .bind("price", variant.getPrice())
                .bind("stock", variant.getStock())
                .bind("gram", variant.getGram())
                .bind("color", variant.getColor())
                .bind("size", variant.getSize())
                .bind("variantId", variant.getVariantId())
                .execute();
    }

    public int appendStockVariantWithTransaction(Handle handle, Integer varId, Integer stock) {
        String sql = """
                UPDATE product_variants
                SET stock = stock + :stock
                WHERE id = :varId
                """;
        return handle.createUpdate(sql)
                .bind("varId", varId)
                .bind("stock", stock)
                .execute();
    }
}
