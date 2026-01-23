package vn.edu.hcmuaf.fit.pkcn.controller.admin.product.response_json;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.category.CategoryDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductImageDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductService;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductVariantService;
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProductImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "RemoveProductVariantServlet", value = "/remove-product-variant")
public class JsonRemoveProductVariantServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean success = false;
        String message = "";
        String sku = "";

        ProductService ps = new ProductService(
                new ProductDao(JDBI.getJdbi()),
                new SortProductImpl(),
                new ProductImageDao(JDBI.getJdbi()),
                new ProductVariantDao(JDBI.getJdbi()),
                new CategoryDao(JDBI.getJdbi())
        );
        ProductVariantService productVariantService = new ProductVariantService(
                new ProductVariantDao(JDBI.getJdbi())
        );

        try {
            int variantId = Integer.parseInt(request.getParameter("variantId"));
            sku = productVariantService.getSku(variantId);
            success =  ps.removeVarAndUpdatePriceProductWithTransaction(productVariantService.getProductId(variantId), variantId);
            message = success ? "Xóa " + variantId + " thành công" : "Xóa " + variantId + " thất bại";
        } catch (Exception e) {
            e.printStackTrace();
            sku = null;
            message = "Đã có lỗi xảy ra: " + e.getMessage();
        }


        Map<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("sku", sku);
        map.put("message", message);
        String jsonRes = new Gson().toJson(map);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonRes);
        response.getWriter().flush();
    }
}