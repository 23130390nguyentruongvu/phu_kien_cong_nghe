package vn.edu.hcmuaf.fit.pkcn.controller.admin.user.response_json;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.category.CategoryDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductImageDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.model.admin.add.JsonAddUser;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductService;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProductImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "JsonAddUserServlet", value = "/add-user")
public class JsonAddUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService(JDBI.getJdbi());
        boolean success = false;
        String msg;

        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null)
                sb.append(line);
            Gson gson = new Gson();
            JsonAddUser user = gson.fromJson(sb.toString(), JsonAddUser.class);
            if (userService.insertUser(user) == 0) throw new Exception("Không thể thêm user");
            success = true;
            msg = "Thêm user thành công";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "lỗi: " + e.getMessage();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> map = new HashMap<>();
        map.put("message", msg);
        map.put("success", success);
        String jsonResponse = new Gson().toJson(map);
        Writer writer = response.getWriter();
        writer.write(jsonResponse);
        writer.flush();
    }
}