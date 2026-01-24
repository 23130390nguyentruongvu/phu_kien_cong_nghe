package vn.edu.hcmuaf.fit.pkcn.dao.review;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.review.ReviewProduct;

import java.util.List;

public class ReviewProductDao {
    private Jdbi jdbi;

    public ReviewProductDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<ReviewProduct> getReviewProducts(int productId) {
        String status = "ACTIVE";
        String sql = """
                SELECT *
                FROM reviews
                WHERE product_id = :prodId AND status = :status
                """;
        return jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("prodId", productId)
                .bind("status", status)
                .mapToBean(ReviewProduct.class)
                .list()
        );
    }
}
