package vn.edu.hcmuaf.fit.pkcn.controller.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ViewProductsByCategory", value = "/product-category-parent")
public class ViewProductsByParentCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ProductShowAsItem> productsByParentCate = new ArrayList<>();
        String categoryName = request.getParameter("name-category");

        try {
            ProductService productService = new ProductService(
                    new ProductDao(JDBI.getJdbi())
            );

            String paramId = request.getParameter("id");
            //Neu param cua id khong co thi lay ra tat ca san pham
            if (paramId.isEmpty()) {
                productsByParentCate = productService.getAllProducts();
            } else {
                int id = Integer.parseInt(paramId);
                productsByParentCate = productService.getProductByParentId(id);
            }
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Ben servlet kiem tra neu empty thi thong bao khong tim thay
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