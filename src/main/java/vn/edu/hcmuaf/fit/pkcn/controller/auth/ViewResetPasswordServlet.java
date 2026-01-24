package vn.edu.hcmuaf.fit.pkcn.controller.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.reset_password.ResetPasswordDao;
import vn.edu.hcmuaf.fit.pkcn.service.reset_password.ResetPasswordService;

import java.io.IOException;

@WebServlet(name = "ViewResetPasswordServlet", value = "/view-reset-password")
public class ViewResetPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = "";
        String email = "";
        String msg = "";
        try {
            ResetPasswordService resetPasswordService = new ResetPasswordService(
                    new ResetPasswordDao(JDBI.getJdbi())
            );

            token = request.getParameter("token");
            email = resetPasswordService.getEmailByToken(token);
            if (token == null || token.isEmpty()) {
                msg = "Token không có";
            } else if (email == null) {
                token = null;
                msg = "Token hết hạn hoặc không tìm thấy email từ token này";
            } else {
                msg = "Ok token";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Lỗi server: " + e.getMessage();
            token = null;
            email = null;
        }

        request.setAttribute("token", token);
        request.setAttribute("message", msg);
        request.setAttribute("email", email);
        request.getRequestDispatcher("/WEB-INF/views/client/reset_password.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}