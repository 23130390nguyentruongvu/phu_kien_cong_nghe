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
    public void insertProductImageWithTransaction(Handle handle, Integer prodVarId, int productId, String urlImage) {
        String sql = """
                INSERT INTO product_images(product_variant_id, product_id, url_image, is_main)
                VALUES(:prodVarId, :prodId, :urlImage, :isMain)
                """;
        handle.createUpdate(sql)
                .bind("prodVarId", prodVarId)
                .bind("prodId", productId)
                .bind("urlImage", urlImage)
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
}
