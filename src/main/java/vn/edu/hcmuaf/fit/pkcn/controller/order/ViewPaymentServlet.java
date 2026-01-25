package vn.edu.hcmuaf.fit.pkcn.controller.order;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.model.cart.Cart;
import vn.edu.hcmuaf.fit.pkcn.model.order.PaymentMethod;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.model.user.Address;
import vn.edu.hcmuaf.fit.pkcn.service.order.PaymentMethodService;
import vn.edu.hcmuaf.fit.pkcn.service.order.ShippingFeeService;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductVariantService;
import vn.edu.hcmuaf.fit.pkcn.service.user.AddressService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewPayment", value = "/view-payment")
public class ViewPaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");
        AddressService addressService = new AddressService(JDBI.getJdbi());
        if (cart == null || cart.getCartItems().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/view-cart");
            return;
        }
        if(user==null){
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        Address defaultAddress = addressService.getAddressDefault(user.getId());
        ShippingFeeService shippingFeeService = new ShippingFeeService(JDBI.getJdbi());
        PaymentMethodService paymentMethodService = new PaymentMethodService(JDBI.getJdbi());
        List<PaymentMethod> methods = paymentMethodService.getAllPaymentMethods();
        double shipFee = 0;
        if(defaultAddress!=null){
            shipFee = shippingFeeService.getPriceShipByAddress(defaultAddress.getProvinceCity());
        }else{
            request.setAttribute("error","Bạn chưa thiết lập địa chỉ nhận hàng. Vui lòng thêm địa chỉ để tiến hành thanh toán");
        }

        double totalPrice = cart.priceTotal();
        double totalOrder = totalPrice + shipFee;
        request.setAttribute("paymentMethods", methods);
        request.setAttribute("subTotal", totalPrice);
        request.setAttribute("shipFee", shipFee);
        request.setAttribute("totalOrder",totalOrder);
        request.setAttribute("defaultAddress", defaultAddress);
        request.getRequestDispatcher("/WEB-INF/views/client/payment.jsp").forward(request, response);
    }
}
