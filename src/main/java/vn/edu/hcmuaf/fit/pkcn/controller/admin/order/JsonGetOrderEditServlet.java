package vn.edu.hcmuaf.fit.pkcn.controller.admin.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.order.snap.OrderSnapshotDAO;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.order.snap.OrderSnapshotService;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;

import java.io.IOException;

@WebServlet(name = "JsonGetOrderEditServlet", value = "/get-order-edit")
public class JsonGetOrderEditServlet extends HttpServlet {
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
            OrderSnapshotService snapshotService = new OrderSnapshotService(new OrderSnapshotDAO(JDBI.getJdbi()));
            OrderSnapshot orderSnapshot = snapshotService.getOrderSnapshotsByOrderId(orderId);
            request.setAttribute("orderSnapshot", orderSnapshot);
            if (orderSnapshot != null && orderSnapshot.getStatusOrder() != null) {
                try {
                    request.setAttribute("statusDisplay", vn.edu.hcmuaf.fit.pkcn.utils.enums.OrderStatus.fromCode(orderSnapshot.getStatusOrder()).getDisplayName());
                } catch (IllegalArgumentException e) {
                    request.setAttribute("statusDisplay", orderSnapshot.getStatusOrder());
                }
            } else {
                request.setAttribute("statusDisplay", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("orderSnapshot", null);
        }
        request.getRequestDispatcher("/WEB-INF/fragments/edit_order_row.jsp").forward(request, response);
    }
}
