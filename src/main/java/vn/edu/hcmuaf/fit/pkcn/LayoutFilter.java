package vn.edu.hcmuaf.fit.pkcn;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.category.CategoryDao;
import vn.edu.hcmuaf.fit.pkcn.dao.contactshop.ContactShopDao;
import vn.edu.hcmuaf.fit.pkcn.model.category.Category;
import vn.edu.hcmuaf.fit.pkcn.model.contactshop.ContactShop;
import vn.edu.hcmuaf.fit.pkcn.service.category.CategoryService;
import vn.edu.hcmuaf.fit.pkcn.service.contactshop.ContactShopService;

import java.io.IOException;
import java.util.List;

@WebFilter("/*")
public class LayoutFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        ServletContext context = req.getServletContext();//Lay ra application scope
        if (context.getAttribute("ContactShop") == null) {
            try {
                ContactShopDao csDao = new ContactShopDao(JDBI.getJdbi());
                ContactShopService csService = new ContactShopService(csDao);
                ContactShop shopInfo = csService.getContactShopByStatus(1);

                context.setAttribute("ContactShop", shopInfo);
                if (shopInfo != null)
                    System.out.println("Đã load info shop\n" + shopInfo.toString());
                else
                    System.out.println("Không thể load info shop");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (context.getAttribute("ParentCategories") == null) {
            try {
                CategoryService cateService = new CategoryService(
                        new CategoryDao(JDBI.getJdbi())
                );
                List<Category> parentCategories = cateService.getParentCategories();
                context.setAttribute("ParentCategories", parentCategories);
                System.out.println("Đã load categories parent + " + parentCategories);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        chain.doFilter(request, response);
    }
}