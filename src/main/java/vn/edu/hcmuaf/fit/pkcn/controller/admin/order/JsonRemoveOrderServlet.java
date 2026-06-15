package vn.edu.hcmuaf.fit.pkcn.controller.admin.order;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.order.OrderDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductVariantService;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;
import vn.edu.hcmuaf.fit.pkcn.utils.enums.OrderStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "JsonRemoveOrderServlet", value = "/remove-order")
public class JsonRemoveOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute("user");
        Map<String, Object> map = new HashMap<>();

        if (admin == null || CheckUserHelper.checkUserInValid(admin.getId()) || admin.getRole() != 1) {
            map.put("success", false);
            map.put("message", "Không có quyền thực hiện");
            writeJson(response, map);
            return;
        }

        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));

            JDBI.getJdbi().inTransaction(handle -> {
                OrderDao orderDao = new OrderDao(JDBI.getJdbi());
                ProductVariantService variantService = new ProductVariantService(new ProductVariantDao(JDBI.getJdbi()));

                HashMap<Integer, Integer> variants = orderDao.getVariantIdsAndQuantitiesByOrderIdWithTransaction(handle, orderId);
                if (variants != null && !variants.isEmpty()) {
                    variantService.appendStockVariantWithTransaction(handle, variants);
                    for (Map.Entry<Integer, Integer> entry : variants.entrySet()) {
                        handle.createUpdate("UPDATE products p JOIN product_variants pv ON p.id = pv.product_id SET p.stock = p.stock + :qty WHERE pv.id = :vid")
                                .bind("qty", entry.getValue())
                                .bind("vid", entry.getKey())
                                .execute();
                    }
                }

                handle.createUpdate("DELETE FROM order_details WHERE order_id = :oid")
                        .bind("oid", orderId).execute();
                handle.createUpdate("DELETE FROM orders WHERE id = :oid")
                        .bind("oid", orderId).execute();

                map.put("success", true);
                map.put("message", "Xóa đơn hàng #" + orderId + " thành công");
                return null;
            });

        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "Lỗi: " + e.getMessage());
        }

        writeJson(response, map);
    }

    private void writeJson(HttpServletResponse response, Map<String, Object> map) throws IOException {
        String jsonRes = new Gson().toJson(map);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonRes);
    }
}
