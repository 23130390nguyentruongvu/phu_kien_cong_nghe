package vn.edu.hcmuaf.fit.pkcn.controller.category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.category.CategoryDao;
import vn.edu.hcmuaf.fit.pkcn.model.category.Category;
import vn.edu.hcmuaf.fit.pkcn.service.category.CategoryService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CategorySidebarServlet", value = "/category-sidebar")
public class CategorySidebarServlet extends HttpServlet {

    private CategoryService categoryService;

    @Override
    public void init() {
        categoryService = new CategoryService(
                new CategoryDao(JDBI.getJdbi())
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Category> parentCategories = categoryService.getParentCategories();

        Map<Integer, List<Category>> subCategoryMap = new HashMap<>();
        for (Category parent : parentCategories) {
            List<Category> subs = categoryService.getSubCategories(parent.getId());
            subCategoryMap.put(parent.getId(), subs);
        }

        request.setAttribute("parentCategories", parentCategories);
        request.setAttribute("subCategoryMap", subCategoryMap);

        request.getRequestDispatcher("/WEB-INF/views/common/category_sidebar.jsp")
                .forward(request, response);
    }
}

