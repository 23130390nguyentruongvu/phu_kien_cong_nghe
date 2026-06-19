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
import vn.edu.hcmuaf.fit.pkcn.dao.order.OrderDao;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "JsonUpdateOrderServlet", value = "/update-order")
public class JsonUpdateOrderServlet extends HttpServlet {
    private static final Set<String> EDITABLE_STATUSES = new HashSet<>();

    static {
        EDITABLE_STATUSES.add(OrderStatus.PENDING_SIGNATURE.getCode());
        EDITABLE_STATUSES.add(OrderStatus.SIGNED.getCode());
        EDITABLE_STATUSES.add(OrderStatus.WAITING_RE_SIGN.getCode());
        EDITABLE_STATUSES.add(OrderStatus.APPROVED.getCode());
    }

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
            String receiverName = json.get("receiverName").getAsString();
            String phoneNumber = json.get("phoneNumber").getAsString();
            String addressDetail = json.get("addressDetail").getAsString();
            String province = (json.has("province") && !json.get("province").isJsonNull())
                    ? json.get("province").getAsString() : "";
            String district = (json.has("district") && !json.get("district").isJsonNull())
                    ? json.get("district").getAsString() : "";
            JDBI.getJdbi().inTransaction(handle -> {
                OrderDao orderDao = new OrderDao(JDBI.getJdbi());

                String currentStatus = handle.createQuery("SELECT status_order FROM orders WHERE id = :id FOR UPDATE")
                        .bind("id", orderId)
                        .mapTo(String.class)
                        .findOne()
                        .orElse("");

                if (!EDITABLE_STATUSES.contains(currentStatus)) {
                    throw new Exception("Đơn hàng ở trạng thái \"" + OrderStatus.fromCode(currentStatus).getDisplayName() + "\" không thể chỉnh sửa");
                }

                int addressOrderId = handle.createQuery("SELECT address_order_id FROM orders WHERE id = :id")
                        .bind("id", orderId)
                        .mapTo(Integer.class)
                        .one();

                handle.createUpdate("UPDATE address_order SET receiver_name = :name, phone_number = :phone, address_detail = :addr, province_city = :province, district  = :district WHERE id = :id")
                        .bind("name", receiverName)
                        .bind("phone", phoneNumber)
                        .bind("addr", addressDetail)
                        .bind("province",province)
                        .bind("district",district)
                        .bind("id", addressOrderId)
                        .execute();

                String[] orderDetailIdsArr = gson.fromJson(json.get("orderDetailIds"), String[].class);
                String[] variantIdsArr = gson.fromJson(json.get("variantIds"), String[].class);
                String[] quantitiesArr = gson.fromJson(json.get("quantities"), String[].class);

                HashMap<Integer, Integer> oldVariants = orderDao.getVariantIdsAndQuantitiesByOrderIdWithTransaction(handle, orderId);
                double newTotalMustPay = 0;

                for (int i = 0; i < orderDetailIdsArr.length; i++) {
                    int orderDetailId = Integer.parseInt(orderDetailIdsArr[i]);
                    int variantId = Integer.parseInt(variantIdsArr[i]);
                    int newQty = Integer.parseInt(quantitiesArr[i]);
                    int oldQty = oldVariants.getOrDefault(variantId, 0);

                    BigDecimal price = handle.createQuery("SELECT variant_price_snapshot FROM order_details WHERE id = :id AND order_id = :oid")
                            .bind("id", orderDetailId)
                            .bind("oid", orderId)
                            .mapTo(BigDecimal.class)
                            .one();

                    BigDecimal priceTotal = price.multiply(BigDecimal.valueOf(newQty));
                    newTotalMustPay += priceTotal.doubleValue();

                    handle.createUpdate("UPDATE order_details SET quantity = :qty, price_total = :priceTotal, variant_price_snapshot = :varPrice WHERE id = :id AND order_id = :oid")
                            .bind("qty", newQty)
                            .bind("priceTotal", priceTotal)
                            .bind("varPrice", price)
                            .bind("id", orderDetailId)
                            .bind("oid", orderId)
                            .execute();

                    int diff = newQty - oldQty;
                    if (diff != 0) {
                        handle.createUpdate("UPDATE product_variants SET stock = GREATEST(stock - :diff, 0) WHERE id = :vid")
                                .bind("diff", diff)
                                .bind("vid", variantId)
                                .execute();

                        handle.createUpdate("UPDATE products p JOIN product_variants pv ON p.id = pv.product_id SET p.stock = GREATEST(p.stock - :diff, 0) WHERE pv.id = :vid")
                                .bind("diff", diff)
                                .bind("vid", variantId)
                                .execute();
                    }
                }

                BigDecimal shipFee = handle.createQuery("SELECT shipping_fee FROM orders WHERE id = :id")
                        .bind("id", orderId)
                        .mapTo(BigDecimal.class)
                        .one();

                newTotalMustPay += shipFee.doubleValue();

                handle.createUpdate("UPDATE orders SET total_must_pay = :total, status_order = :status, signature = NULL, user_key_id = NULL, expire_sign_key = DATE_ADD(NOW(), INTERVAL 2 DAY) WHERE id = :id")
                        .bind("total", BigDecimal.valueOf(newTotalMustPay))
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

                UserService userService = new UserService(JDBI.getJdbi());
                User orderUser = userService.getUserById(userId);
                if (orderUser != null && orderUser.getEmail() != null && !orderUser.getEmail().isEmpty()) {
                    String url = "http://localhost:8080/pkcn/process-sign-order?orderId=" + orderId ;
                    String subject = "Đơn hàng #" + orderId + " đã được Admin chỉnh sửa";
                    String body = "<h2>Thông báo chỉnh sửa đơn hàng</h2>"
                            + "<p>Đơn hàng #" + orderId + " của bạn đã được Admin chỉnh sửa.</p>"
                            + "<p>Vui lòng đăng nhập vào hệ thống và ký lại đơn hàng để tiếp tục.</p>"
                            + "<p><a href=\"" + url + "\">Bấm vào đây để ký lại đơn hàng</a></p>"
                            + "<p>Xin cảm ơn!</p>";
                    EmailUtils.sendEmail(orderUser.getEmail(), subject, body);
                }

                map.put("success", true);
                map.put("message", "Cập nhật đơn hàng #" + orderId + " thành công");
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
