package vn.edu.hcmuaf.fit.pkcn.controller.admin.order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.admin.order.AdminOrderShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.order.OrderService;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewAdminOrderServlet", value = "/admin-order")
public class ViewAdminOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || CheckUserHelper.checkUserInValid(user.getId())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (user.getRole() != 1) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trang không tồn tại");
            return;
        }

        try {
            OrderService orderService = new OrderService(JDBI.getJdbi());
            String key = request.getParameter("keySearch");
            String statusFilter = request.getParameter("statusFilter");
            if (statusFilter != null && statusFilter.isEmpty()) {
                statusFilter = null;
            }
            List<AdminOrderShowAsItem> orders = orderService.getOrdersForAdmin(key, statusFilter);

            request.setAttribute("orders", orders);
            request.setAttribute("keySearch", key);
            request.setAttribute("statusFilter", statusFilter);
            request.setAttribute("navLink", 4);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("orders", null);
            request.setAttribute("navLink", 4);
        }

        request.getRequestDispatcher("/WEB-INF/views/admin/admin_order.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
