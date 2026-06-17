package vn.edu.hcmuaf.fit.pkcn.controller.admin.order;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserKeyDao;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.model.user.json.request.UserKeyDTO;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;
import vn.edu.hcmuaf.fit.pkcn.utils.EmailUtils;
import vn.edu.hcmuaf.fit.pkcn.utils.enums.OrderStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "JsonAddOrderItemServlet", value = "/add-order-item")
public class JsonAddOrderItemServlet extends HttpServlet {
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
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }

            Gson gson = new Gson();
            JsonObject json = gson.fromJson(jsonBuffer.toString(), JsonObject.class);

            int orderId = json.get("orderId").getAsInt();
            int variantId = json.get("variantId").getAsInt();
            int quantity = json.get("quantity").getAsInt();

            if (quantity <= 0) {
                map.put("success", false);
                map.put("message", "Số lượng phải lớn hơn 0");
                writeJson(response, map);
                return;
            }

            JDBI.getJdbi().inTransaction(handle -> {
                String currentStatus = handle.createQuery("SELECT status_order FROM orders WHERE id = :id FOR UPDATE")
                        .bind("id", orderId)
                        .mapTo(String.class)
                        .findOne()
                        .orElse("");

                if (!currentStatus.equals(OrderStatus.PENDING_SIGNATURE.getCode())
                        && !currentStatus.equals(OrderStatus.WAITING_RE_SIGN.getCode())
                        && !currentStatus.equals(OrderStatus.REJECTED.getCode())) {
                    throw new Exception("Đơn hàng ở trạng thái này không thể thêm sản phẩm");
                }

                Integer currentStock = handle.createQuery("SELECT stock FROM product_variants WHERE id = :id FOR UPDATE")
                        .bind("id", variantId)
                        .mapTo(Integer.class)
                        .findOne()
                        .orElse(0);

                if (quantity > currentStock) {
                    throw new Exception("Sản phẩm không đủ hàng (Hiện còn: " + currentStock + ")");
                }

                // Lấy thông tin snapshot cho biến thể
                String snapshotSql = """
                        SELECT pv.id, pv.name as variant_name, pv.sku, pv.price, pv.gram, pv.color, pv.size,
                               p.name as product_name
                        FROM product_variants pv
                        JOIN products p ON p.id = pv.product_id
                        WHERE pv.id = :vid
                        """;
                var snapshotData = handle.createQuery(snapshotSql)
                        .bind("vid", variantId)
                        .mapToMap()
                        .one();

                BigDecimal price = (BigDecimal) snapshotData.get("price");
                BigDecimal priceTotal = price.multiply(BigDecimal.valueOf(quantity));

                handle.createUpdate("INSERT INTO order_details (order_id, product_variant_id, " +
                        "product_name_snapshot, variant_name_snapshot, sku_snapshot, " +
                        "variant_price_snapshot, gram_snapshot, color_snapshot, size_snapshot, " +
                        "quantity, price_total) " +
                        "VALUES (:oid, :vid, :prodName, :varName, :sku, :varPrice, :gram, :color, :size, :qty, :price)")
                        .bind("oid", orderId)
                        .bind("vid", variantId)
                        .bind("prodName", snapshotData.get("product_name"))
                        .bind("varName", snapshotData.get("variant_name"))
                        .bind("sku", snapshotData.get("sku"))
                        .bind("varPrice", price)
                        .bind("gram", snapshotData.get("gram"))
                        .bind("color", snapshotData.get("color"))
                        .bind("size", snapshotData.get("size"))
                        .bind("qty", quantity)
                        .bind("price", priceTotal)
                        .execute();

                handle.createUpdate("UPDATE product_variants SET stock = stock - :qty WHERE id = :vid")
                        .bind("qty", quantity)
                        .bind("vid", variantId)
                        .execute();

                handle.createUpdate("UPDATE products p JOIN product_variants pv ON p.id = pv.product_id SET p.stock = p.stock - :qty WHERE pv.id = :vid")
                        .bind("qty", quantity)
                        .bind("vid", variantId)
                        .execute();

                handle.createUpdate("UPDATE orders SET total_must_pay = total_must_pay + :price, status_order = :status, signature = NULL, user_key_id = NULL WHERE id = :id")
                        .bind("price", priceTotal)
                        .bind("status", OrderStatus.WAITING_RE_SIGN.getCode())
                        .bind("id", orderId)
                        .execute();

                int userId = handle.createQuery("SELECT user_id FROM orders WHERE id = :id")
                        .bind("id", orderId)
                        .mapTo(Integer.class)
                        .one();

                UserKeyDao userKeyDao = new UserKeyDao(JDBI.getJdbi());
                UserKeyDTO dto = new UserKeyDTO();
                dto.setUserId(userId);
                userKeyDao.revokedAllUserKey(dto);

                UserService userService = new UserService(JDBI.getJdbi());
                User orderUser = userService.getUserById(userId);
                if (orderUser != null && orderUser.getEmail() != null && !orderUser.getEmail().isEmpty()) {
                    String subject = "Đơn hàng #" + orderId + " đã được Admin thêm sản phẩm";
                    String body = "<h2>Thông báo thêm sản phẩm vào đơn hàng</h2>"
                            + "<p>Đơn hàng #" + orderId + " của bạn đã được Admin thêm sản phẩm.</p>"
                            + "<p>Vui lòng đăng nhập vào hệ thống và ký lại đơn hàng để tiếp tục.</p>"
                            + "<p>Xin cảm ơn!</p>";
                    EmailUtils.sendEmail(orderUser.getEmail(), subject, body);
                }

                map.put("success", true);
                map.put("message", "Thêm sản phẩm vào đơn hàng #" + orderId + " thành công");
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
