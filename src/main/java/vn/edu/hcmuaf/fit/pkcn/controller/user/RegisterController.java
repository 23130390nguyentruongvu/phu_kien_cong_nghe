package vn.edu.hcmuaf.fit.pkcn.controller.user;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "RegisterController", value = "/register")

public class RegisterController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/client/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msg;
        boolean success = false;
        try {
            String full_name = request.getParameter("full_name");
            String email = request.getParameter("email");
            String userName = request.getParameter("user_name");
            String password = request.getParameter("password");
            String confirm_password = request.getParameter("confirm_password");
            String uid = request.getParameter("uid");

            if (!password.equals(confirm_password)) {
                msg = "Mật khẩu không khớp";
                return;
            }
            String passRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+={}\\[\\]:;\"'<>,.?/-]).{8,16}$";
            if (!password.matches(passRegex)) {
                msg = "Mật khẩu phải có 8-16 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt";
                return;
            }

            String usernameRegex = "^(?=.*[a-zA-Z])[a-zA-Z0-9]+$";

            if (userName == null || !userName.matches(usernameRegex)) {
                msg = "Username phải có ít nhất 1 chữ cái và không chứa ký tự đặc biệt!";
                return;
            }
            UserService userService = new UserService(JDBI.getJdbi());

            if (userService.checkExist(userName, email)) {
                msg = "Username hoặc email đã tồn tại";
                return;
            }
            User user = new User();
            user.setFullName(full_name);
            user.setEmail(email);
            user.setUserName(userName);
            user.setPassword(password);
            user.setStatus("active");
            user.setRole(2);
            user.setUid(uid);

            success = userService.registerUser(user);
            msg = success ? "Đăng kí thành công" : "Đăng kí thất bại";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Lỗi đăng kí";
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
