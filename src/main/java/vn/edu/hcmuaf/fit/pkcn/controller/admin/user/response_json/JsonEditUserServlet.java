package vn.edu.hcmuaf.fit.pkcn.controller.admin.user.response_json;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.controller.admin.user.AdminUserAvatarStorage;
import vn.edu.hcmuaf.fit.pkcn.model.admin.edit.JsonUpdateUser;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "JsonEditUserServlet", value = "/edit-user")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 6 * 1024 * 1024)
public class JsonEditUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService(JDBI.getJdbi());
        boolean success = false;
        String msg;
        String savedAvatar = null;
        String oldAvatar = null;

        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            User existingUser = userService.getUserById(userId);
            if (existingUser == null) throw new Exception("Không tìm thấy người dùng");

            JsonUpdateUser user = new JsonUpdateUser();
            user.setUserId(userId);
            user.setRole(Integer.parseInt(request.getParameter("role")));
            user.setFullName(request.getParameter("name"));
            savedAvatar = AdminUserAvatarStorage.save(request.getPart("fileAvatar"), request.getContextPath());
            user.setHasImage(savedAvatar != null);
            user.setUrl(savedAvatar);
            oldAvatar = existingUser.getAvatar();
            Map<String, Object> res = JDBI.getJdbi().inTransaction(handle -> {
                boolean isSuccess = userService.updateUserWithTransaction(handle, user) > 0;

                Map<String, Object> resMap = new HashMap<>();
                resMap.put("success", isSuccess);
                resMap.put("message", isSuccess ? "Cập nhật người dùng thành công" : "Cập nhật người dùng thất bại");
                return resMap;
            });
            success = Boolean.TRUE.equals(res.get("success"));
            if (success && savedAvatar != null) {
                try {
                    AdminUserAvatarStorage.deleteIfManaged(oldAvatar);
                } catch (IOException cleanupException) {
                    cleanupException.printStackTrace();
                }
            } else if (!success && savedAvatar != null) {
                AdminUserAvatarStorage.deleteIfManaged(savedAvatar);
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String jsonResponse = new Gson().toJson(res);
            Writer writer = response.getWriter();
            writer.write(jsonResponse);
            writer.flush();
        } catch (Exception e) {
            if (savedAvatar != null && !success) {
                try {
                    AdminUserAvatarStorage.deleteIfManaged(savedAvatar);
                } catch (IOException cleanupException) {
                    e.addSuppressed(cleanupException);
                }
            }
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