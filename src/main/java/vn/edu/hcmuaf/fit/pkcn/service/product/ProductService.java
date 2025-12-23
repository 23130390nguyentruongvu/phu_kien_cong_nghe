package vn.edu.hcmuaf.fit.pkcn.service.product;

import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductShowAsItem;

import java.util.List;

public class ProductService {
    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductShowAsItem> getNewProducts(int limit) {
        return productDao.getNewProducts(limit);
    }

    //Co the tra ve null
    public List<ProductShowAsItem> getFeaturedProducts(int limit, int avgStar) {
        List<ProductShowAsItem> lst;
        //Trong truong hop shop co san pham noi bat theo danh gia (trung binh >= 4.0) thi uu tien hien
        lst = productDao.getFeaturedProductsByReview(limit, avgStar);

        //Neu khong co cac san pham nao duoc danh gia hoac danh gia kem thi shop co the hien thi
        //cac san pham duoc admin danh dau la noi bat
        if(lst == null) lst = productDao.getFeatureProductsByOne(limit);

        //neu cung khong co san pham duoc danh dau noi bat thi tien hanh hidden section noi bat (tra ve null)
        return lst;
    }

    public List<ProductShowAsItem> getProductsByChildCategory(int limit, int categoryId) {
        return productDao.getProductsByChildCategory(categoryId, limit);
    }
}
