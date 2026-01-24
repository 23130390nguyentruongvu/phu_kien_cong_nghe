package vn.edu.hcmuaf.fit.pkcn.controller.admin.user.response_json;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "JsonDeleteUserServlet", value = "/delete-user")
public class JsonDeleteUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isSuccess = false;
        String msg = "";
        try {
            int id = Integer.parseInt(request.getParameter("userId"));
            UserService userService = new UserService(JDBI.getJdbi());

            String uid = userService.getUid(id);

            if (uid != null && !uid.isEmpty()) {
                try {
                    FirebaseAuth.getInstance().deleteUser(uid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            isSuccess = userService.deleteUser(id);
            msg = isSuccess ? "Xóa user thành công!" : "Xóa user thất bại";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Lỗi: " + e.getMessage();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> map = new HashMap<>();
        map.put("message", msg);
        map.put("success", isSuccess);
        String jsonResponse = new Gson().toJson(map);
        Writer writer = response.getWriter();
        writer.write(jsonResponse);
        writer.flush();
    }
}