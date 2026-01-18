package vn.edu.hcmuaf.fit.pkcn.dao.product;

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
}
