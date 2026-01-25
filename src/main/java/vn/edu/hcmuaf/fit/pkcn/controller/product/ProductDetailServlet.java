package vn.edu.hcmuaf.fit.pkcn.controller.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.category.CategoryDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductImageDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.dao.review.ReviewProductDao;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductDetail;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.model.review.ReviewProduct;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductService;
import vn.edu.hcmuaf.fit.pkcn.service.review.ReviewProductService;
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProductImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductDetailServlet", value = "/product-detail")
public class ProductDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String paramId = request.getParameter("id");
        if (paramId == null || paramId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        try {
            int productId = Integer.parseInt(paramId);

            ProductService productService = new ProductService(
                    new ProductDao(JDBI.getJdbi()),
                    new SortProductImpl(),
                    new ProductImageDao(JDBI.getJdbi()),
                    new ProductVariantDao(JDBI.getJdbi()),
                    new CategoryDao(JDBI.getJdbi())
            );

            ReviewProductService reviewProductService = new ReviewProductService(
                    new ReviewProductDao(JDBI.getJdbi())
            );

            //Load đánh giá sản phẩm
            List<ReviewProduct> reviews = reviewProductService.getReviewProducts(productId);

            //Load sản phẩm chi tiết bao gồm cả biến thể
            ProductDetail product = productService.getProductDetailById(productId);

            if (product == null) {
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }

            //Load sản phẩm liên quan
            List<ProductShowAsItem> relatedProducts =
                    productService.getRelatedProducts(productId);


            request.setAttribute("product", product);
            request.setAttribute("relatedProducts", relatedProducts);
            request.setAttribute("reviews", reviews);

            request.getRequestDispatcher("/WEB-INF/views/client/product_detail.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
