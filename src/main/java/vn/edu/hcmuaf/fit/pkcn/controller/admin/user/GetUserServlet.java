package vn.edu.hcmuaf.fit.pkcn.controller.admin.user;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;

import java.io.IOException;

@WebServlet(name = "JsonGetUserServlet", value = "/get-user-edit")
public class GetUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = null;
        UserService userService = new UserService(
                JDBI.getJdbi()
        );

        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            user = userService.getUserById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/fragments/edit_user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}