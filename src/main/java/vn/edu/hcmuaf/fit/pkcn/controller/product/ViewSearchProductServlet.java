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
        String price = request.getParameter("price");
        String category = request.getParameter("category");
        String sort = request.getParameter("sort");
        String rating = request.getParameter("rating");


        if (keyword == null || keyword.trim().isEmpty()) {
            keyword = "";
        }

        Integer minPrice = null;
        Integer maxPrice = null;

        if (price != null && !price.isEmpty()) {
            if ("1000-up".equals(price)) {
                minPrice = 1_000_000;
            } else {
                String[] parts = price.split("-");
                minPrice = Integer.parseInt(parts[0]) * 1000;
                maxPrice = Integer.parseInt(parts[1]) * 1000;
            }
        }

        ProductService productService = new ProductService(
                new ProductDao(JDBI.getJdbi()),
                new SortProductImpl()
        );

        List<ProductShowAsItem> results;

        if (price == null && category == null && sort == null && rating == null) {
            results = productService.searchProducts(keyword.trim());
        }
        else {
            results = productService.searchWithFilter(
                    keyword.trim(),
                    minPrice,
                    maxPrice,
                    category,
                    sort,
                    rating
            );
        }

        request.setAttribute("keyword", keyword);
        request.setAttribute("ProductsResult", results);

        request.setAttribute("price", price);
        request.setAttribute("category", category);
        request.setAttribute("sort", sort);
        request.setAttribute("rating", rating);


        request.getRequestDispatcher("/WEB-INF/views/client/search_result.jsp")
                .forward(request, response);

    }
}

