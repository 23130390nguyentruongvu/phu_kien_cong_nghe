package vn.edu.hcmuaf.fit.pkcn.controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;

import java.io.IOException;

@WebServlet(name = "DeleteUser", value = "/delete-user")
public class DeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        UserService userService = new UserService(JDBI.getJdbi());

        userService.deleteUserSv(id);
        response.sendRedirect(request.getContextPath() + "/view-user");
    }
}
