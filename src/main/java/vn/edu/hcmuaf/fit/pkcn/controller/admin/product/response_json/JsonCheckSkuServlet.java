package vn.edu.hcmuaf.fit.pkcn.controller.admin.product.response_json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductVariantService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "JsonCheckSkuServlet", value = "/check-sku")
public class JsonCheckSkuServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    /*
Request: {
 "skus": ["SKU001", "SKU002", "SKU003"]
}


Response: {
  "exists": false,  // true nếu có SKU trùng
  "duplicateSKUs": [] // Danh sách SKU bị trùng
}
*/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ProductVariantService productVariantService = new ProductVariantService(
                    new ProductVariantDao(JDBI.getJdbi())
            );

            //Lấy json gửi từ request đọc theo từng dòng
            BufferedReader reader = request.getReader();
            //Tăng tốc nối chuỗi
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                sb.append(line);

            String json = sb.toString();

            //Lấy ra key-value
            JsonObject obj = new Gson().fromJson(json, JsonObject.class);
            JsonArray skus = obj.getAsJsonArray("skus"); //Lấy các mã sku của key là skus
            List<String> skusRequest = new ArrayList<>();
            for (JsonElement tmp : skus.asList()) skusRequest.add(tmp.getAsString());
            List<String> res = productVariantService.getSkusBySkus(skusRequest);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            boolean exist = !res.isEmpty();

            Map<String, Object> respObj = new HashMap<>();
            respObj.put("exists", exist);
            respObj.put("duplicateSKUs", res);

            //tạo response json
            String jsonResponse = new Gson().toJson(respObj);
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> respObj = new HashMap<>();
            respObj.put("exists", false);
            respObj.put("duplicateSKUs", new ArrayList<>());

            //tạo response json
            String jsonResponse = new Gson().toJson(respObj);
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
        }
    }
}