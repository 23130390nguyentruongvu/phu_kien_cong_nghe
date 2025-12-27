package vn.edu.hcmuaf.fit.pkcn.controller.user;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;

import java.io.IOException;
@WebServlet(name = "RegisterController", value = "/register")

public class RegisterController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/client/register.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String full_name = request.getParameter("full_name");
    String email = request.getParameter("email");
    String userName = request.getParameter("user_name");
    String password = request.getParameter("password");
    String confirm_password = request.getParameter("confirm_password");

    if(!password.equals(confirm_password)){
        request.setAttribute("error","Passwords do not match");
        request.getRequestDispatcher("/WEB-INF/views/client/register.jsp").forward(request, response);
        return;
    }
        String passRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+={}\\[\\]:;\"'<>,.?/-]).{8,16}$";
        if(!password.matches(passRegex)){
            request.setAttribute("error","Mật khẩu phải có 8-16 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt" );
            request.getRequestDispatcher("/WEB-INF/views/client/register.jsp").forward(request, response);
            return;
        }
        UserService userService = new UserService(JDBI.getJdbi());

        if(userService.checkExist(userName,email)){
            request.setAttribute("error","User already exists");
            request.getRequestDispatcher("/WEB-INF/views/client/register.jsp").forward(request, response);
            return;
        }
        User user = new User();
        user.setFullName(full_name);
        user.setEmail(email);
        user.setUserName(userName);
        user.setPassword(password);
        user.setStatus("active");
        user.setRole(2);

        if(userService.registerUser(user)){
            response.sendRedirect(request.getContextPath() + "/login");
        }else{
            request.setAttribute("error","Registration Failed");
            request.getRequestDispatcher("/WEB-INF/views/client/register.jsp").forward(request, response);
        }
    }
}
