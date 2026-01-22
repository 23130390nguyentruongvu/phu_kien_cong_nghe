package vn.edu.hcmuaf.fit.pkcn.controller.auth;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;

import java.io.IOException;

@WebServlet(name = "AddUserServlet", value = "/add-user")
public class AddUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fullName = request.getParameter("name");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("re-password");
        String avatar = request.getParameter("avatar");
        int roleId = Integer.parseInt(request.getParameter("role"));


        User newUser = new User();
        newUser.setFullName(fullName);
        newUser.setUserName(userName);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setAvatar(avatar);
        newUser.setRole(roleId);
        newUser.setStatus("active");

        UserService userService = new UserService(JDBI.getJdbi());
        userService.registerUser(newUser);

        response.sendRedirect(request.getContextPath() + "/view-user");
    }
}
