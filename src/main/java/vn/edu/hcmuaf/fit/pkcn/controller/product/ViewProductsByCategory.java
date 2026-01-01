package vn.edu.hcmuaf.fit.pkcn.controller.product;

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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ViewProductsByCategory", value = "/product-category")
public class ViewProductsByCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ProductShowAsItem> productsByParentCate = new ArrayList<>();
        ProductService productService = new ProductService(
                new ProductDao(JDBI.getJdbi())
        );
        CategoryService categoryService = new CategoryService(
                new CategoryDao(JDBI.getJdbi())
        );

        String categoryName = "";
        String paramId = "";

        try {
            categoryName = request.getParameter("name-category");
            paramId = request.getParameter("id");
            //Neu param cua id khong co thi lay ra tat ca san pham
            if (paramId.isEmpty()) {
                productsByParentCate = productService.getAllProducts();
            } else {
                int id = Integer.parseInt(paramId);
                boolean isParentId = categoryService.isCategoryParent(id);
                productsByParentCate = productService.getProductByCategory(id, isParentId);
            }
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Ben servlet kiem tra neu empty thi thong bao khong tim thay
        request.setAttribute("categoryId", paramId);
        request.setAttribute("ProductsResult", productsByParentCate);
        request.setAttribute("CategoryName", categoryName);
        // thiet lap active cho header
        request.setAttribute("activeHeader", 3);
        request.getRequestDispatcher("/WEB-INF/views/client/product_category.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}