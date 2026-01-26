package vn.edu.hcmuaf.fit.pkcn.controller.admin.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewAdminUser", value = "/view-user")
public class ViewAdminUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || CheckUserHelper.checkUserInValid(user.getId())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        } else {
            if (user.getRole() != 1) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trang không tồn tại");
            } else {
                UserService userService = new UserService(JDBI.getJdbi());
                List<User> users;
                users = userService.getAllUsers();
                request.setAttribute("users", users);
                request.setAttribute("navLink", 2);

                request.getRequestDispatcher("/WEB-INF/views/admin/admin_user.jsp").forward(request, response);
            }
        }

    }
}
