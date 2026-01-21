package vn.edu.hcmuaf.fit.pkcn.controller.admin.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.category.CategoryDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductImageDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.model.product.Product;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductAdminShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductService;
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProductImpl;

import java.io.IOException;

@WebServlet(name = "GetProductEditServlet", value = "/get-product-edit")
public class GetProductEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int prodId = Integer.parseInt(request.getParameter("productId"));
            ProductService ps = new ProductService(
                    new ProductDao(JDBI.getJdbi()),
                    new SortProductImpl(),
                    new ProductImageDao(JDBI.getJdbi()),
                    new ProductVariantDao(JDBI.getJdbi()),
                    new CategoryDao(JDBI.getJdbi())
            );
            ProductAdminShowAsItem res = ps.getProductAdmin(prodId);

            request.setAttribute("product", res);
            request.getRequestDispatcher("/WEB-INF/fragments/edit_product_row.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("product", null);
            request.getRequestDispatcher("/WEB-INF/fragments/edit_product_row.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}