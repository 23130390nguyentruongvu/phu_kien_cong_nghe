package vn.edu.hcmuaf.fit.pkcn.controller.admin.user.response_json;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.admin.add.JsonAddUser;
import vn.edu.hcmuaf.fit.pkcn.model.admin.edit.JsonUpdateUser;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "JsonEditUserServlet", value = "/edit-user")
public class JsonEditUserServlet extends HttpServlet {
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
            JsonUpdateUser user = gson.fromJson(sb.toString(), JsonUpdateUser.class);
            Map<String, Object> res = JDBI.getJdbi().inTransaction(handle -> {
                boolean isSuccess = userService.updateUserWithTransaction(handle, user) > 0;

                Map<String, Object> resMap = new HashMap<>();
                resMap.put("success", isSuccess);
                resMap.put("message", isSuccess ? "Cập nhật người dùng thành công" : "Cập nhật người dùng thất bại");
                return resMap;
            });

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String jsonResponse = new Gson().toJson(res);
            Writer writer = response.getWriter();
            writer.write(jsonResponse);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());
            map.put("success", success);
            String jsonResponse = new Gson().toJson(map);
            Writer writer = response.getWriter();
            writer.write(jsonResponse);
            writer.flush();
        }
    }
}