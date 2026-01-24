package vn.edu.hcmuaf.fit.pkcn.service.review;

import vn.edu.hcmuaf.fit.pkcn.dao.review.ReviewProductDao;
import vn.edu.hcmuaf.fit.pkcn.model.review.ReviewProduct;

import java.util.List;

public class ReviewProductService {
    private ReviewProductDao reviewProductDao;

    public ReviewProductService(ReviewProductDao reviewProductDao) {
        this.reviewProductDao = reviewProductDao;
    }

    public List<ReviewProduct> getReviewProducts(int prodId) {
        return reviewProductDao.getReviewProducts(prodId);
    }
}
