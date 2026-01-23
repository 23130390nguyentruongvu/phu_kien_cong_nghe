package vn.edu.hcmuaf.fit.pkcn.controller.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductService;
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProductImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchProductServlet", value = "/search")
public class ViewSearchProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword  = request.getParameter("keyword");
        String price    = request.getParameter("price");
        String category = request.getParameter("category");
        String sort     = request.getParameter("sort");
        String rating   = request.getParameter("rating");

        keyword  = (keyword == null) ? "" : keyword.trim();
        price    = (price == null || price.isBlank()) ? null : price;
        category = (category == null || category.isBlank()) ? null : category;
        sort     = (sort == null || sort.isBlank()) ? null : sort;
        rating   = (rating == null || rating.isBlank()) ? null : rating;

        Integer minPrice = null;
        Integer maxPrice = null;

        if (price != null) {
            if ("1000-up".equals(price)) {
                minPrice = 1_000_000;
            } else {
                String[] parts = price.split("-");
                minPrice = Integer.parseInt(parts[0]) * 1000;
                maxPrice = Integer.parseInt(parts[1]) * 1000;
            }
        }

        boolean hasFilter =
                price != null ||
                        category != null ||
                        rating != null ||
                        sort != null;

        ProductService productService = new ProductService(
                new ProductDao(JDBI.getJdbi()),
                new SortProductImpl()
        );

        List<ProductShowAsItem> results;

        if (!hasFilter) {
            results = productService.searchProducts(keyword);
        } else {
            results = productService.searchWithFilter(
                    keyword,
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
