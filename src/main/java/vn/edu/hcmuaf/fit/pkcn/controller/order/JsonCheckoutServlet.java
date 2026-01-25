package vn.edu.hcmuaf.fit.pkcn.controller.order;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.cart.Cart;
import vn.edu.hcmuaf.fit.pkcn.model.user.Address;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.order.OrderService;
import vn.edu.hcmuaf.fit.pkcn.service.order.ShippingFeeService;
import vn.edu.hcmuaf.fit.pkcn.service.user.AddressService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Checkout", value = "/checkout")
public class JsonCheckoutServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderService(JDBI.getJdbi());
        ShippingFeeService shippingFeeService = new ShippingFeeService(JDBI.getJdbi());
        AddressService addressService = new AddressService(JDBI.getJdbi());
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");
        //params
        String addressId1 = request.getParameter("selectedAddressId");
        String note = request.getParameter("note");
        String paymentMethodIdStr = request.getParameter("paymentMethodId");

        Map<String, Object> map = new HashMap<>();
        String msg = null;
        boolean isSuccess = false;
        try {
            if (addressId1 == null || addressId1.isEmpty()) {
                msg = "Vui lòng thêm địa chỉ nhận hàng trước khi đặt hàng";
            }
            if (paymentMethodIdStr == null || paymentMethodIdStr.isEmpty()) {
                msg += "\nVui lòng thêm địa chỉ nhận hàng trước khi đặt hàng";
            }
            if (msg == null) {
                int addressId = Integer.parseInt(addressId1);
                int paymentMethodId = Integer.parseInt(paymentMethodIdStr);
                Address address = addressService.getAddressById(addressId);
                double shippingFee = shippingFeeService.getPriceShipByAddress(address.getProvinceCity());
                orderService.checkOut(user.getId(), addressId, note, cart, shippingFee, paymentMethodId);

                cart.clearCart();
                isSuccess = true;
                msg = "Đặt hàng thành công!";
            }

        } catch (Exception e) {
            msg = "Lỗi: " + e.getMessage();
        }
        map.put("success", isSuccess);
        map.put("message", msg);
        String jsonRes = new Gson().toJson(map);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonRes);
        response.getWriter().flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
