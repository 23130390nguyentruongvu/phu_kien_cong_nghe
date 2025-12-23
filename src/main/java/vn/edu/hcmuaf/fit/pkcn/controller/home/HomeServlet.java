package vn.edu.hcmuaf.fit.pkcn.controller.home;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.category.CategoryDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.service.category.CategoryService;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "/")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Lay du lieu cho cac section san pham
        ProductService ps = new ProductService(
                new ProductDao(JDBI.getJdbi())
        );
        CategoryService cs = new CategoryService(
                new CategoryDao(JDBI.getJdbi())
        );

        int limit = 12;
        List<ProductShowAsItem> newProducts = ps.getNewProducts(limit);
        List<ProductShowAsItem> featuredProducts = ps.getFeaturedProducts(limit, 4);
        List<ProductShowAsItem> vgaProducts = ps.getProductsByChildCategory(6, 12);
        List<ProductShowAsItem> accessoriesKeyboard = ps.getProductsByChildCategory(6, 16);

        //thiet lap cac du lieu vao request
        request.setAttribute("NewProducts", newProducts);
        request.setAttribute("FeaturedProducts", featuredProducts);
        request.setAttribute("VGAProducts", vgaProducts);
        request.setAttribute("KeyboardProducts", accessoriesKeyboard);

        request.setAttribute("NameVGA", cs.getNameCategoryById(12));
        request.setAttribute("KeyboardName", cs.getNameCategoryById(16));

        request.getRequestDispatcher("WEB-INF/views/client/home.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}