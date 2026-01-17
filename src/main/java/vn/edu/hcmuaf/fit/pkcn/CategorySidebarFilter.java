package vn.edu.hcmuaf.fit.pkcn;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.category.CategoryDao;
import vn.edu.hcmuaf.fit.pkcn.model.category.Category;
import vn.edu.hcmuaf.fit.pkcn.service.category.CategoryService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebFilter(urlPatterns = "/*")
public class CategorySidebarFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        ServletContext context = req.getServletContext();

        if (context.getAttribute("ParentCategories") == null) {
            CategoryService cateService = new CategoryService(
                    new CategoryDao(JDBI.getJdbi())
            );

            List<Category> parentCategories = cateService.getParentCategories();
            Map<Integer, List<Category>> subCategoryMap = new HashMap<>();

            for (Category parent : parentCategories) {
                subCategoryMap.put(
                        parent.getId(),
                        cateService.getSubCategories(parent.getId())
                );
            }

            context.setAttribute("ParentCategories", parentCategories);
            context.setAttribute("SubCategoryMap", subCategoryMap);

            System.out.println("CategorySidebarFilter: loaded categories");
        }

        chain.doFilter(request, response);
    }
}
