package vn.edu.hcmuaf.fit.pkcn.controller.auth;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.reset_password.ResetPasswordDao;
import vn.edu.hcmuaf.fit.pkcn.service.reset_password.ResetPasswordService;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ResetPasswordServlet", value = "/reset-password")
public class JsonResetPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> map = new HashMap<>();

        try {
            String token = request.getParameter("token");
            String newPassword = request.getParameter("newPassword");
            UserService userService = new UserService(
                    JDBI.getJdbi()
            );
            ResetPasswordService resetPasswordService = new ResetPasswordService(
                    new ResetPasswordDao(JDBI.getJdbi())
            );

            //Check token còn lấy được email không
            String email = resetPasswordService.getEmailByToken(token);
            if (email == null) {
                map.put("success", false);
                map.put("message", "Token không tồn tại hoặc đã hết hạn");
            } else {
                JDBI.getJdbi().inTransaction(handle -> {
                    try {
                        if (resetPasswordService.deleteTokenWithTransaction(handle, email) == 0)
                            throw new Exception("Không thể xóa token");
                        if (userService.updatePasswordWithTransaction(handle, email, newPassword) == 0)
                            throw new Exception("Không thể cập nhật mật khẩu");
                        map.put("success", true);
                        map.put("message", "Cập nhật mật khẩu thành công");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "Lỗi: " + e.getMessage());
        }

        String jsonRes = new Gson().toJson(map);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonRes);
        response.getWriter().flush();
    }
}