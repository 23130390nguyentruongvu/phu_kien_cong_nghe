package vn.edu.hcmuaf.fit.pkcn.controller.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;
import vn.edu.hcmuaf.fit.pkcn.utils.HashMD5;

import java.io.IOException;

@WebServlet(name = "ChangePass", value = "/change-password")
public class ChangePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService service = new UserService(JDBI.getJdbi());
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null){
            response.sendRedirect(request.getContextPath()+"/login");
            return;
        }
        String currentPassword = request.getParameter("currentPass");
        String newPassword = request.getParameter("newPass");
        String confirmPassword = request.getParameter("confirmPass");

        String error = "";
        String suc = "";

        String passwordOld = HashMD5.MD5(currentPassword);
        if(!user.getPassword().equals(passwordOld)){
            error = "Mật khẩu hiện tại không chính xác!";
        } else if (!newPassword.equalsIgnoreCase(confirmPassword)) {
            error = "Xác nhận mật khẩu mới không khớp!";
        }else if(newPassword.length() < 6){
            error = "Mật khẩu mới phải từ 6 ký tự trở lên!";
        }else{
            boolean isChange = service.changePassword(user.getId(), newPassword);
            if(isChange){
                suc = "Đổi mật khẩu thành công!";
                user.setPassword(HashMD5.MD5(newPassword));
            }else{
                error = "Lỗi thay đổi, vui lòng thử lại!";
            }
        }

        request.setAttribute("linkNav", 1);
        request.setAttribute("suc", suc);
        request.setAttribute("error", error);
        request.getRequestDispatcher("/WEB-INF/views/client/personal_info.jsp").forward(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
