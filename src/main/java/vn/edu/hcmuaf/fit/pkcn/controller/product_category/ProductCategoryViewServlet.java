package vn.edu.hcmuaf.fit.pkcn.controller.product_category;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ProductCategoryViewServlet", value = "/product-category")
public class ProductCategoryViewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int categoryId = Integer.parseInt(request.getParameter("CategoryId"));

            request.getRequestDispatcher("WEB-INF/views/client/product_category.jsp")
                    .forward(request, response);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}