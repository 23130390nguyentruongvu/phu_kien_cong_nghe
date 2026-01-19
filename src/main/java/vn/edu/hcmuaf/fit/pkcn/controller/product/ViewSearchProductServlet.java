package vn.edu.hcmuaf.fit.pkcn.controller.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductService;
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProductImpl;

import java.io.IOException;
import java.util.*;

@WebServlet(name = "SearchProductServlet", value = "/search")
public class ViewSearchProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");

        if (keyword == null || keyword.trim().isEmpty()) {
            keyword = "";
        }

        ProductService productService = new ProductService(
                new ProductDao(JDBI.getJdbi()),
                new SortProductImpl()
        );

        List<ProductShowAsItem> results =
                productService.searchProducts(keyword.trim());

        request.setAttribute("keyword", keyword);
        request.setAttribute("ProductsResult", results);

        request.getRequestDispatcher("/WEB-INF/views/client/search_result.jsp")
                .forward(request, response);

    }
}

