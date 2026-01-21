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
import vn.edu.hcmuaf.fit.pkcn.model.admin.add.JSonProduct;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductService;
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProductImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AddProductServlet", value = "/add-product")
public class JsonAddProductServlet extends HttpServlet {
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

            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null)
                sb.append(line);

            //parse json sang object
            Gson gson = new Gson();
            JSonProduct product = gson.fromJson(sb.toString(), JSonProduct.class);

            product.updatePriceAndStock();
            boolean res = ps.addProduct(product);
            //Phản hồi
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Map<String, Object> resps = new HashMap<>();
            resps.put("success", res);
            resps.put("message", res?"Thêm sản phẩm thành công":"Thêm sản phẩm thất bại");

            String jsonResponse = gson.toJson(resps);
            Writer writer = response.getWriter();
            writer.write(jsonResponse);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> resps = new HashMap<>();
            resps.put("success", false);
            resps.put("message", "Thêm sản phẩm thất bại, nguyên nhân: " + e.getMessage());
            String jsonResponse = new Gson().toJson(resps);
            Writer writer = response.getWriter();
            writer.write(jsonResponse);
            writer.flush();
        }
    }
}