package vn.edu.hcmuaf.fit.pkcn.controller.home;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.article.ArticleDao;
import vn.edu.hcmuaf.fit.pkcn.dao.category.CategoryDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductImageDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.dao.slidershow.SliderShowDao;
import vn.edu.hcmuaf.fit.pkcn.model.article.ArticleShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.model.slidershow.SliderShow;
import vn.edu.hcmuaf.fit.pkcn.service.article.ArticleService;
import vn.edu.hcmuaf.fit.pkcn.service.category.CategoryService;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductService;
import vn.edu.hcmuaf.fit.pkcn.service.slidershow.SliderShowService;
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProductImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "/")
public class HomeViewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Lay du lieu article items
        ArticleService aService = new ArticleService(
                new ArticleDao(JDBI.getJdbi())
        );
        List<ArticleShowAsItem> articleItems;
        try {
            articleItems = aService.getArticleItems(4);
        } catch (Exception e) {
            e.printStackTrace();
            articleItems = new ArrayList<>();
        }

        //Lay du lieu slidershows
        SliderShowService ssService = new SliderShowService(
                new SliderShowDao(JDBI.getJdbi())
        );
        List<SliderShow> sliderShows;
        try {
            sliderShows = ssService.getSliderShowByStatus(1, true);
        } catch (Exception e) {
            sliderShows = new ArrayList<>();
        }


        //Lay du lieu cho cac section san pham
        ProductService ps = new ProductService(
                new ProductDao(JDBI.getJdbi()),
                new SortProductImpl(),
                new ProductImageDao(JDBI.getJdbi()),
                new ProductVariantDao(JDBI.getJdbi()),
                new CategoryDao(JDBI.getJdbi())
        );
        CategoryService cs = new CategoryService(
                new CategoryDao(JDBI.getJdbi())
        );

        int limit = 12;
        int categoryIdVGA = 12;
        int categoryIdKeyboard = 16;
        List<ProductShowAsItem> newProducts;
        try {
            newProducts = ps.getNewProducts(limit);
        } catch (Exception e) {
            e.printStackTrace();
            newProducts = new ArrayList<>();
        }

        List<ProductShowAsItem> featuredProducts;
        try {
            featuredProducts = ps.getFeaturedProducts(limit, 4);
        } catch (Exception e) {
            e.printStackTrace();
            featuredProducts = new ArrayList<>();
        }

        List<ProductShowAsItem> vgaProducts;
        try {
            vgaProducts = ps.getProductsByChildCategory(6, categoryIdVGA);
        } catch (Exception e) {
            e.printStackTrace();
            vgaProducts = new ArrayList<>();
        }

        List<ProductShowAsItem> accessoriesKeyboard = ps.getProductsByChildCategory(6, categoryIdKeyboard);
        try {
            accessoriesKeyboard = ps.getProductsByChildCategory(6, categoryIdKeyboard);
        } catch (Exception e) {
            e.printStackTrace();
            accessoriesKeyboard = new ArrayList<>();
        }

        //thiet lap cac du lieu vao request
        //sections
        request.setAttribute("NewProducts", newProducts);
        request.setAttribute("FeaturedProducts", featuredProducts);
        request.setAttribute("VGAProducts", vgaProducts);
        request.setAttribute("KeyboardProducts", accessoriesKeyboard);

        //slidershow
        request.setAttribute("SliderShows", sliderShows);

        //articleItems
        request.setAttribute("ArticleItems", articleItems);

        try {
            request.setAttribute("NameVGA", cs.getNameCategoryById(categoryIdVGA));
        } catch (Exception e) {
            request.setAttribute("NameVGA", "Không thấy name vga");
        }

        try {
            request.setAttribute("KeyboardName", cs.getNameCategoryById(categoryIdKeyboard));
        } catch (Exception e) {
            request.setAttribute("KeyboardName", "Không thấy name keyboard");
        }

        request.setAttribute("CategoryIdVga", categoryIdVGA);
        request.setAttribute("CategoryIdKeyboard", categoryIdKeyboard);
        // thiet lap active cho header
        request.setAttribute("activeHeader", 1);

        request.getRequestDispatcher("WEB-INF/views/client/home.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}