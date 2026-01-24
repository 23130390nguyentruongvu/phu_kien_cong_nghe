package vn.edu.hcmuaf.fit.pkcn.controller.auth;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.reset_password.ResetPasswordDao;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.reset_password.ResetPasswordService;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;
import vn.edu.hcmuaf.fit.pkcn.utils.EmailUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "ForgotPasswordServlet", value = "/forgot-email")
public class JsonForgotPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msg = "";
        boolean isSuccess = false;
        String email;
        try {
            email = request.getParameter("email");
            UserService userService = new UserService(
                    JDBI.getJdbi()
            );
            ResetPasswordService resetPasswordService = new ResetPasswordService(
                    new ResetPasswordDao(JDBI.getJdbi())
            );

            // Kiểm tra Email có tồn tại trong hệ thống k
            User user = userService.findByEmail(email);
            if (user != null) {
                //Tạo Token duy nhất và thời gian hết hạn
                String token = UUID.randomUUID().toString();
                LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(15);

                // lưu vào bảng password_resets
                // Nếu email đó có tồn tại trong bảng trên thì ta xóa token cũ
                boolean isSaved = resetPasswordService.insertEmailToken(email, token, expiryDate);

                if (isSaved) {
                    //Tạo link reset dẫn đến trang JSP forgot password
                    String resetLink = request.getScheme() + "://" + request.getServerName()
                            + ":" + request.getServerPort()
                            + request.getContextPath()
                            + "/view-reset-password?token=" + token;

                    //Gửi mail qua SMTP
                    String subject = "ShopPhuKienCongNghe: Cập nhật lại mật khẩu";
                    String content = "<h3>Yêu cầu thay đổi mật khẩu</h3>"
                            + "<p>Bạn đã yêu cầu đặt lại mật khẩu. Vui lòng click vào link dưới đây:</p>"
                            + "<a href='" + resetLink + "'>Đổi mật khẩu ngay</a>"
                            + "<p>Link này sẽ hết hạn sau 15 phút.</p>";

                    EmailUtils.sendEmail(email, subject, content);

                    isSuccess = true;
                    msg = "Đã gửi link cập nhật lại mật khẩu trong email: " + email;
                }
            } else {
                isSuccess = false;
                msg = "Có vẻ như email này không có trong hệ thống của chúng tôi";
            }
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
            msg = "Đã có lỗi xảy ra: " + e.getMessage();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("success", isSuccess);
        map.put("message", msg);
        String jsonRes = new Gson().toJson(map);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonRes);
        response.getWriter().flush();
    }
}