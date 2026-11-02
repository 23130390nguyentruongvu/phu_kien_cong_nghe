package vn.edu.hcmuaf.fit.pkcn.controller.admin.user.response_json;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.controller.admin.user.AdminUserAvatarStorage;
import vn.edu.hcmuaf.fit.pkcn.model.admin.add.JsonAddUser;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "JsonAddUserServlet", value = "/add-user")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 6 * 1024 * 1024)
public class JsonAddUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService(JDBI.getJdbi());
        boolean success = false;
        String msg;
        String savedAvatar = null;

        try {
            JsonAddUser user = new JsonAddUser();
            user.setUsername(request.getParameter("userName"));
            user.setEmail(request.getParameter("email"));
            user.setFullName(request.getParameter("name"));
            user.setPassword(request.getParameter("password"));
            user.setRole(Integer.parseInt(request.getParameter("role")));
            savedAvatar = AdminUserAvatarStorage.save(
                    request.getPart("avatar-file"), getServletContext(), request.getContextPath()
            );
            user.setUrl(savedAvatar);
            if (userService.insertUser(user) == 0) throw new Exception("Không thể thêm user");
            success = true;
            msg = "Thêm user thành công";
        } catch (Exception e) {
            if (!success && savedAvatar != null) {
                try {
                    AdminUserAvatarStorage.deleteIfManaged(savedAvatar, getServletContext());
                } catch (IOException cleanupException) {
                    e.addSuppressed(cleanupException);
                }
            }
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