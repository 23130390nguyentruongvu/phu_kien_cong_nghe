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
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProductImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "JsonRemoveProductServlet", value = "/remove-product")
public class JsonRemoveProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean success = false;
        String message = "";
        String folderId = null;

        try {
            int prodId = Integer.parseInt(request.getParameter("productId"));
            ProductService ps = new ProductService(
                    new ProductDao(JDBI.getJdbi()),
                    new SortProductImpl(),
                    new ProductImageDao(JDBI.getJdbi()),
                    new ProductVariantDao(JDBI.getJdbi()),
                    new CategoryDao(JDBI.getJdbi())
            );

            folderId = ps.getFolderIdWithProdId(prodId);
            success = ps.removeProduct(prodId);
            message = success ? "Xóa sản phẩm " + prodId + " thành công" : "Xóa sản phẩm " + prodId + " thất bại";
        } catch (Exception e) {
            e.printStackTrace();
            message = "Lỗi xảy ra khi xóa sản phẩm " + e.getMessage();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("folderId", folderId);
        map.put("success", success);
        map.put("message", message);
        String jsonRes = new Gson().toJson(map);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonRes);
    }
}