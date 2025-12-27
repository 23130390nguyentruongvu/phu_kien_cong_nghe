package vn.edu.hcmuaf.fit.pkcn.controller.user;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;

import java.io.IOException;

    @WebServlet(name = "LoginController", value = "/login")
    public class LoginController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/client/login.jsp").forward(request, response);

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loginInfo = request.getParameter("loginInfo");
        String password = request.getParameter("password");

        UserService userService = new UserService(JDBI.getJdbi());
        User user = userService.loginUser(loginInfo, password);

        if(user!=null){
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            response.sendRedirect(request.getContextPath() + "/home");
        }else{
            request.setAttribute("error","Tài khoản hoặc mật khẩu không đúng");
            request.getRequestDispatcher("/WEB-INF/views/client/login.jsp").forward(request, response);
        }
    }
}
