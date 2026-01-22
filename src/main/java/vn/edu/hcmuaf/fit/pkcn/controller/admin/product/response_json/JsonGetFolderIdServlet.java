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

@WebServlet(name = "JsonGetFolderIdServlet", value = "/get-folder-id")
public class JsonGetFolderIdServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean hasImageInStorage = false;
        String folderId = null;
        String message = "";
        ProductService ps = new ProductService(
                new ProductDao(JDBI.getJdbi()),
                new SortProductImpl(),
                new ProductImageDao(JDBI.getJdbi()),
                new ProductVariantDao(JDBI.getJdbi()),
                new CategoryDao(JDBI.getJdbi())
        );
        try {
            String productId = request.getParameter("productId");
            String variantId = request.getParameter("variantId");
            if (productId != null && !productId.isEmpty()) {
                int prodId = Integer.parseInt(productId);
                folderId = ps.getFolderIdWithProdId(prodId);
                if(folderId == null) throw new Exception("Không tìm thấy folder id");
                message = "OK";
                hasImageInStorage = true;
            } else if (variantId != null && !variantId.isEmpty()) {
                int varId = Integer.parseInt(variantId);
                folderId = ps.getFolderIdWithVarId(varId);
                message = "OK";
                hasImageInStorage = true;
            } else {
                throw new IllegalArgumentException("Thiếu productId hoặc variantId");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = e.getMessage();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("folderId", folderId);
        map.put("hasImageInStorage", hasImageInStorage);
        map.put("message", message);
        String jsonRes = new Gson().toJson(map);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonRes);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

    }
}