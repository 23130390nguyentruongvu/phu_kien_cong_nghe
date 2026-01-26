package vn.edu.hcmuaf.fit.pkcn.controller.admin.user.response_json;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "JsonUpdateAvatarUserServlet", value = "/update-avatar-user")
public class JsonUpdateAvatarUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> map = new HashMap<>();
        try {
            UserService userService = new UserService(JDBI.getJdbi());

            //params request
            int userId = Integer.parseInt(request.getParameter("userId"));
            String newImage = request.getParameter("url");
            String folderId = request.getParameter("folderId");

            boolean success = userService.updateAvatarAndFolderId(userId, newImage, folderId) > 0;
            map.put("success", success);
            map.put("message", success ? "Cập nhật ảnh thành công" : "Cập nhật ảnh thất bại");
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user != null) user.setAvatar(success ? newImage : user.getAvatar());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "Cập nhật ảnh thất bại: " + e.getMessage());
        }
        String jsonRes = new Gson().toJson(map);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonRes);
        response.getWriter().flush();
    }
}