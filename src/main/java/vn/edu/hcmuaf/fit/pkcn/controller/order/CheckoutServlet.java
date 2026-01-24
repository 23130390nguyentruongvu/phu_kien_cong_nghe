package vn.edu.hcmuaf.fit.pkcn.controller.order;

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

@WebServlet(name = "Checkout",value ="/checkout")
public class CheckoutServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderService(JDBI.getJdbi());
        ShippingFeeService shippingFeeService = new ShippingFeeService(JDBI.getJdbi());
        AddressService addressService = new AddressService(JDBI.getJdbi());
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");
        String addressId1 = request.getParameter("selectedAddressId");
        String note = request.getParameter("note");
        String paymentMethodIdStr = request.getParameter("paymentMethodId");
        try{
            if (addressId1 == null || addressId1.isEmpty()) {
                request.setAttribute("error", "Vui lòng thêm địa chỉ nhận hàng trước khi đặt hàng");
                request.getRequestDispatcher("/WEB-INF/views/client/payment.jsp")
                        .forward(request, response);
                return;
            }
            if(paymentMethodIdStr == null || paymentMethodIdStr.isEmpty()) {
                request.setAttribute("error","Vui lòng chọn phương thức thanh toán");
            }
            int addressId = Integer.parseInt(addressId1);
            int paymentMethodId = Integer.parseInt(paymentMethodIdStr);
            Address address = addressService.getAddressById(addressId);
            double shippingFee = shippingFeeService.getPriceShipByAddress(address.getProvinceCity());
            orderService.checkOut(user.getId(),addressId, note, cart,shippingFee, paymentMethodId);

            cart.clearCart();
            request.setAttribute("successMessage", "Đặt hàng thành công!");
            request.getRequestDispatcher("/WEB-INF/views/client/payment.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/client/payment.jsp").forward(request, response);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
