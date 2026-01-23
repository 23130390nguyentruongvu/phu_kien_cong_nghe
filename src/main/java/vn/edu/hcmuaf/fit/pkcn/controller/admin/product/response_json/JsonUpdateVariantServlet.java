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
import vn.edu.hcmuaf.fit.pkcn.model.admin.edit.JsonUpdateProduct;
import vn.edu.hcmuaf.fit.pkcn.model.admin.edit.JsonUpdateVariant;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductImageService;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductService;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductVariantService;
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProductImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "JsonUpdateVariantServlet", value = "/update-variant")
public class JsonUpdateVariantServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean success = false;
        String message = "";
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

        ProductImageService productImageService = new ProductImageService(
                new ProductImageDao(JDBI.getJdbi())
        );
        try {
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jsonBuffer.append(line);

            Gson gson = new Gson();
            JsonUpdateVariant variant = gson.fromJson(jsonBuffer.toString(), JsonUpdateVariant.class);
            Map<String, Object> res = JDBI.getJdbi().inTransaction(handle -> {
                try {
                    //cập nhật folderid nếu có
                    if (variant.isHasUpdateImage())
                        if (ps.updateFolderIdWithTransaction(handle, variant.getProductId(), variant.getFolderId()) == 0)
                            throw new Exception("Không thể cập nhật folder id");
                    //cập nhật biến thể
                    if (productVariantService.updateVariantWithTransaction(handle, variant) == 0)
                        throw new Exception("Không thể cập nhật biến thể");
                    //cập nhật lại giá, stock
                    ps.updatePriceAndStockProductWithTransaction(handle, variant.getProductId());
                    //Cập nhật lại ảnh cho product_images nếu có
                    if (variant.isHasUpdateImage())
                        if (productImageService.updateProductImageWithTransaction(
                                handle, variant.getVariantId(),
                                variant.getProductId(),
                                variant.getImageUrl()
                        ) == 0)
                            throw new Exception("Không thể thêm ảnh biến thể vào bảng product_images");

                    Map<String, Object> map = new HashMap<>();
                    map.put("message", "cập nhật biến thể thành công");
                    map.put("success", true);
                    return map;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String jsonResponse = gson.toJson(res);
            Writer writer = response.getWriter();
            writer.write(jsonResponse);
            writer.flush();

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());
            map.put("success", false);
            String jsonResponse = new Gson().toJson(map);
            Writer writer = response.getWriter();
            writer.write(jsonResponse);
            writer.flush();
        }
    }
}