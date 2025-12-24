package vn.edu.hcmuaf.fit.pkcn.controller;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserDao;

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
        UserDao userdao = new UserDao(JDBI.getJdbi());

        if(userdao.checkExist(userName,email)){
            request.setAttribute("error","User already exists");
            request.getRequestDispatcher("/WEB-INF/views/client/register.jsp").forward(request, response);
            return;
        }
    }
}
