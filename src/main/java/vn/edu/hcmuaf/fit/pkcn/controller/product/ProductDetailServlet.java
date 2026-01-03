package vn.edu.hcmuaf.fit.pkcn.controller.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductDetail;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductService;
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProductImpl;

import java.io.IOException;

@WebServlet(name = "ProductDetailServlet", value = "/product-detail")
public class ProductDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String paramId = request.getParameter("id");
        if (paramId == null || paramId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            int productId = Integer.parseInt(paramId);

            ProductService productService = new ProductService(
                    new ProductDao(JDBI.getJdbi()),
                    new SortProductImpl()
            );

            ProductDetail product = productService.getProductDetailById(productId);

            System.out.println("Product detail: " + product);
            System.out.println("Variants size: " +
                    (product.getVariants() == null ? "null" : product.getVariants().size()));

            request.setAttribute("product", product);
            request.getRequestDispatcher("/WEB-INF/views/client/product_detail.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
