package vn.edu.hcmuaf.fit.pkcn.controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;

import java.io.IOException;

@WebServlet(name = "EditUser", value = "/edit-user")
public class EditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            int id = Integer.parseInt(request.getParameter("id"));
            String fullName = request.getParameter("name");
            String avatar = request.getParameter("avatar");
            int roleId = Integer.parseInt(request.getParameter("role"));

            User user = new User();
            user.setId(id);
            user.setFullName(fullName);
            user.setAvatar(avatar);
            user.setRole(roleId);

            UserService userService = new UserService(JDBI.getJdbi());
            userService.updateUserAdmin(user);

            response.sendRedirect(request.getContextPath() + "/view-user");
        }catch (Exception e){
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/view-user?error = true");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
