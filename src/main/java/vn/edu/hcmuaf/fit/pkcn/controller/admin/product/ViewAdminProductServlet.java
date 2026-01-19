package vn.edu.hcmuaf.fit.pkcn.controller.admin.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.category.CategoryDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductImageDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.model.category.Category;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductAdminShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.category.CategoryService;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductService;
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProductImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminProductServlet", value = "/product-manager")
public class ViewAdminProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ProductService ps = new ProductService(
                new ProductDao(JDBI.getJdbi()),
                new SortProductImpl(),
                new ProductImageDao(JDBI.getJdbi()),
                new ProductVariantDao(JDBI.getJdbi())
        );

        CategoryService categoryService = new CategoryService(
                new CategoryDao(JDBI.getJdbi())
        );

        if (user == null)
            response.sendRedirect(request.getContextPath() + "/login");
        else {
            if (user.getRole() != 1) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trang không tồn tại");
            } else {
                String key = request.getParameter("keySearch");
                List<ProductAdminShowAsItem> res = ps.getProductsForAdmin(key);
                List<Category> categories = categoryService.getSubCategories();

                request.setAttribute("categories", categories);
                request.setAttribute("products", res);
                request.setAttribute("navLink", 3);
                request.getRequestDispatcher("/WEB-INF/views/admin/admin_product.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}