package vn.edu.hcmuaf.fit.pkcn;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.common.ContactShopDao;
import vn.edu.hcmuaf.fit.pkcn.model.common.ContactShop;
import vn.edu.hcmuaf.fit.pkcn.service.common.ContactShopService;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
       try{
           ContactShopDao csDao = new ContactShopDao(JDBI.getJdbi());
           ContactShopService csService = new ContactShopService(csDao);
           ContactShop shopInfo = csService.getContactShopByStatus(1);

           ServletContext context = sce.getServletContext();
           context.setAttribute("ContactShop", shopInfo);
           if (shopInfo != null)
               System.out.println("Đã load info shop\n" + shopInfo.toString());
           else
               System.out.println("Không thể load info shop");
       }catch (Exception ex) {
           ex.printStackTrace();
       }
    }
}