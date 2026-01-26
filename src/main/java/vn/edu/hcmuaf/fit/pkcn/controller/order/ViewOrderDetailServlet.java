package vn.edu.hcmuaf.fit.pkcn.controller.order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.order.OrderDetail;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.order.OrderService;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;

import java.io.IOException;

@WebServlet(name = "ViewOrderDetailServlet", value = "/view-order-detail")
public class ViewOrderDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderDetail orderDetail = null;
        try {
            OrderService orderService = new OrderService(
                    JDBI.getJdbi()
            );

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if(user == null || CheckUserHelper.checkUserInValid(user.getId())) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            int orderId = Integer.parseInt(request.getParameter("orderId"));
            int userId = user.getId();

            //check xem mã đơn hàng đó có phải của user hay không
            int userIdByOrderId = orderService.getUserIdByOrderId(orderId);
            if (userIdByOrderId == -1 || userIdByOrderId != userId) {
                request.setAttribute("orderDetail", null);
                request.getRequestDispatcher("/WEB-INF/views/client/order_detail.jsp").forward(request, response);
                return;
            }
            //Lấy dữ liệu
            orderDetail = orderService.getOrderDetailByOrderId(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("orderDetail", orderDetail);
        request.getRequestDispatcher("/WEB-INF/views/client/order_detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}