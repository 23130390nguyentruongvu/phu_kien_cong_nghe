package vn.edu.hcmuaf.fit.pkcn.controller.admin.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.order.OrderDetail;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.order.OrderService;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;

import java.io.IOException;

@WebServlet(name = "JsonGetOrderDetailServlet", value = "/get-order-detail")
public class JsonGetOrderDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || CheckUserHelper.checkUserInValid(user.getId()) || user.getRole() != 1) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            OrderService orderService = new OrderService(JDBI.getJdbi());
            OrderDetail orderDetail = orderService.getOrderDetailByOrderId(orderId);
            request.setAttribute("orderDetail", orderDetail);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("orderDetail", null);
        }
        request.getRequestDispatcher("/WEB-INF/fragments/view_order_detail_row.jsp").forward(request, response);
    }
}
