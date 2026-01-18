package vn.edu.hcmuaf.fit.pkcn.controller.admin.overview;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;

import java.io.IOException;

@WebServlet(name = "ViewOverViewServlet", value = "/overview")
public class ViewOverViewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null)
            response.sendRedirect(request.getContextPath() + "/login");
        else {
            if (user.getRole() != 1) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trang không tồn tại");
            } else {

                request.setAttribute("navLink", 1);
                request.getRequestDispatcher("/WEB-INF/views/admin/admin_overview.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}