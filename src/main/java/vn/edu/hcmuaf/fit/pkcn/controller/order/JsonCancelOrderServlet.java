package vn.edu.hcmuaf.fit.pkcn.controller.order;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariant;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.order.OrderService;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductVariantService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CancelOrderServlet", value = "/cancel-order")
public class JsonCancelOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> map = new HashMap<>();
        try {
            OrderService orderService = new OrderService(
                    JDBI.getJdbi()
            );

            ProductVariantService productVariantService = new ProductVariantService(
                    new ProductVariantDao(JDBI.getJdbi())
            );

            //Lấy ra user id từ user session rồi check order có tồn tại và có thuộc về user đó hay không
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                throw new Exception("Bạn chưa đăng nhập!");
            }
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            int userId = user.getId();

            //check xem mã đơn hàng đó có phải của user hay không
            int userIdByOrderId = orderService.getUserIdByOrderId(orderId);
            if (userIdByOrderId == -1 || userIdByOrderId != userId) {
                throw new Exception("Mã đơn hàng không tồn tại hoặc bạn không có mã nào như vậy");
            }

            JDBI.getJdbi().inTransaction(handle -> {
                try {
                    if (!orderService.cancelOrderWithTransaction(handle, orderId))
                        throw new Exception("Không thể thiết lập lại trạng thái đơn hàng");
                    HashMap<Integer, Integer> variants = orderService.getVariantIdsAndQuantitiesByOrderIdWithTransaction(handle, orderId);
                    if (variants == null || variants.isEmpty())
                        throw new Exception("Không tìm thấy mã biến thể trong order id");
                    if (productVariantService.appendStockVariantWithTransaction(handle, variants) == 0)
                        throw new Exception("Không thể cập nhật lại stock cho biến thể");
                    map.put("message", "Hủy thành công");
                    map.put("success", true);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
                return null;
            });

        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "Hủy thất bại do: " + e.getMessage());
            map.put("success", false);
        }

        String jsonRes = new Gson().toJson(map);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonRes);
        response.getWriter().flush();
    }
}