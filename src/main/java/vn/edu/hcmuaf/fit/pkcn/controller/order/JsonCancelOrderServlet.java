package vn.edu.hcmuaf.fit.pkcn.controller.order;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.order.OrderService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "CancelOrderServlet", value = "/cancel-order")
public class JsonCancelOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msg = "";
        boolean success = false;
        try {
            OrderService orderService = new OrderService(
                    JDBI.getJdbi()
            );

            //Lấy ra user id từ user session rồi check order có tồn tại và có thuộc về user đó hay không
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                msg = "Bạn chưa đăng nhập!";
                return;
            }
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            int userId = user.getId();

            //check xem mã đơn hàng đó có phải của user hay không
            int userIdByOrderId = orderService.getUserIdByOrderId(orderId);
            if (userIdByOrderId == -1 || userIdByOrderId != userId) {
                msg = "Mã đơn hàng không tồn tại hoặc bạn không có mã nào như vậy";
                return;
            }

            success = orderService.cancelOrder(orderId);
            msg = success ? "Hủy thành công" : "Hủy thất bại";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Lỗi hệ thống: " + e.getMessage();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("message", msg);
        map.put("success", success);
        String jsonRes = new Gson().toJson(map);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonRes);
        response.getWriter().flush();
    }
}