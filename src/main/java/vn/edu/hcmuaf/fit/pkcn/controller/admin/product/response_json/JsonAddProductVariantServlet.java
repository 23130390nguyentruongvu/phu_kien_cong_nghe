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
import vn.edu.hcmuaf.fit.pkcn.model.admin.add.JSonAddVariant;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductImageService;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductService;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductVariantService;
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProductImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "JsonAddProductVariantServlet", value = "/add-product-variant")
public class JsonAddProductVariantServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
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

            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null)
                sb.append(line);

            //parse json sang object
            Gson gson = new Gson();
            JSonAddVariant variant = gson.fromJson(sb.toString(), JSonAddVariant.class);

            Map<String, Object> res = JDBI.getJdbi().inTransaction(handle -> {
                try {
                    //cập nhật folderid
                    if (ps.updateFolderIdWithTransaction(handle, variant.getProductId(), variant.getFolderId()) == 0)
                        throw new Exception("Không thể cập nhật folder id");
                    //thêm product variant
                    int varId = productVariantService.insertVariantWithTransaction(handle, variant.getProductId(), variant);
                    //cập nhật lại giá, stock
                    ps.updatePriceAndStockProductWithTransaction(handle, variant.getProductId());
                    //Thêm ảnh cho product_images
                    if (productImageService.insertProductImageWithTransaction(handle, varId, variant.getProductId(), variant.getImageUrl()) == 0)
                        throw new Exception("Không thể thêm ảnh biến thể vào bảng product_images");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                Map<String, Object> map = new HashMap<>();
                map.put("message", "thêm biến thể thành công");
                map.put("success", true);
                return map;
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