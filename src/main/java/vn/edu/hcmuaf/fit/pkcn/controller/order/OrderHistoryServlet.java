package vn.edu.hcmuaf.fit.pkcn.controller.order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.order.OrderDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.model.order.OrderShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.order.OrderService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderHistoryServlet", value = "/order-history")
public class OrderHistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        OrderService orderService = new OrderService(
                new OrderDao(JDBI.getJdbi()),
                new ProductDao(JDBI.getJdbi())
        );
        String status = request.getParameter("filter-by");
        try {
            if (user == null)
                response.sendRedirect(request.getContextPath() + "/login");
            else {
                if (status != null)
                    if (status.isEmpty() || (
                            !status.equalsIgnoreCase("pending")
                                    && !status.equalsIgnoreCase("completed")
                                    && !status.equalsIgnoreCase("shipping")
                                    && !status.equalsIgnoreCase("cancel"))
                    ) status = null;

                List<OrderShowAsItem> res = orderService.getOrdersShowAsItem(user.getId(), status);
                request.setAttribute("orders", res);
                request.setAttribute("filterBy", status);
                request.setAttribute("linkNav", 2);

                request.getRequestDispatcher("/WEB-INF/views/client/history_order.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}