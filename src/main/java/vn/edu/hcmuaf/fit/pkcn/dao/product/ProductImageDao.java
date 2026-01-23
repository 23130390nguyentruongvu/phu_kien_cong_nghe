package vn.edu.hcmuaf.fit.pkcn.dao.product;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ProductImageDao {
    private Jdbi jdbi;

    public ProductImageDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<String> getImagesProduct(int prodId, boolean getMainImage) {
        String getMain = getMainImage ? "1=1" : "is_main = 0";
        String sql = """
                SELECT url_image
                FROM product_images 
                WHERE product_variant_id IS NULL AND product_id = :prodId
                 AND 
                """ + getMain;

        return jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("prodId", prodId)
                .mapTo(String.class)
                .list()
        );
    }

    //Lấy theo các hình ảnh của product chung, nếu getMainImage là false thì chỉ lấy các ảnh phụ
    public HashMap<Integer, List<String>> getImagesProduct(List<Integer> ids, boolean getMainImage) {
        String getMain = getMainImage ? "1=1" : "pi.is_main = 0";
        String sql = """
                SELECT url_image, product_id
                FROM product_images 
                WHERE product_variant_id IS NULL AND product_id IN(<ids>) AND 
                """ + getMain;
        return jdbi.withHandle(handle -> {
            HashMap<Integer, List<String>> res = new HashMap<>();
            handle.createQuery(sql)
                    .bindList("ids", ids)
                    .map((rs, _) -> {
                        String urlImage = rs.getString("url_image");
                        int prodId = rs.getInt("product_id");
                        if (res.containsKey(prodId))
                            res.get(prodId).add(urlImage);
                        else {
                            List<String> images = new ArrayList<>();
                            images.add(urlImage);
                            res.put(prodId, images);
                        }
                        return "";
                    });
            return res;
        });
    }

    //Product variant id co the null
    public int insertProductImageWithTransaction(Handle handle, Integer prodVarId, int productId, String urlImage) {
        String sql = """
                INSERT INTO product_images(product_variant_id, product_id, url_image, is_main)
                VALUES(:prodVarId, :prodId, :urlImage, :isMain)
                """;
        return handle.createUpdate(sql)
                .bind("prodVarId", prodVarId)
                .bind("prodId", productId)
                .bind("urlImage", urlImage)
                .bind("isMain", 0)
                .execute();
    }

    public int updateProductImageWithTransaction(Handle handle, Integer prodVarId, int productId, String urlImage) {
        String sql = """
               UPDATE product_images
               SET url_image = :url
               WHERE product_id = :prodId
                AND (:prodVarId IS NULL || product_variant_id = :prodVarId)
                """;
        return handle.createUpdate(sql)
                .bind("prodVarId", prodVarId)
                .bind("prodId", productId)
                .bind("url", urlImage)
                .bind("isMain", 0)
                .execute();
    }

    public void updateMainImageWithTransaction(Handle handle, Integer prodVarId, int productId, String urlImage) {
        String sql1 = """
                UPDATE product_images
                SET is_main = :isMain
                WHERE product_id = :prodId
                """;
        handle.createUpdate(sql1)
                .bind("prodId", productId)
                .bind("isMain", 0)
                .execute();

        String sql = """
                UPDATE product_images
                SET is_main = :isMain
                WHERE (:prodVarId IS NULL || product_variant_id = :prodVarId)
                    AND product_id = :prodId
                    AND url_image = :urlImage
                """;
        handle.createUpdate(sql)
                .bind("prodVarId", prodVarId)
                .bind("prodId", productId)
                .bind("urlImage", urlImage)
                .bind("isMain", 1)
                .execute();
    }

    public int deleteUrlsProdWithTransaction(Handle handle, int prodId, List<String> urls) {
        if (urls == null || urls.isEmpty()) {
            return 0;
        }
        String sql = """
                DELETE FROM product_images
                WHERE  product_id = :prodId
                    AND url_image IN (<urls>)
                """;
        return handle.createUpdate(sql)
                .bind("prodId", prodId)
                .bindList("urls", urls)
                .execute();
    }

}
