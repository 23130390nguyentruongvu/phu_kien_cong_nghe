package vn.edu.hcmuaf.fit.pkcn.controller.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.cart.Cart;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.order.OrderService;

import java.io.IOException;

@WebServlet(name = "Checkout",value ="/checkout")
public class CheckoutServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderService(JDBI.getJdbi());
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");
        String addressId1 = request.getParameter("selectedAddressId");
        String note = request.getParameter("note");

        try{
            if (addressId1 == null || addressId1.isEmpty()) {
                request.setAttribute("error", "Vui lòng thêm địa chỉ nhận hàng trước khi đặt hàng");
                request.getRequestDispatcher("/WEB-INF/views/client/payment.jsp")
                        .forward(request, response);
                return;
            }
            int addressId = Integer.parseInt(addressId1);
            orderService.checkOut(user.getId(),addressId, note, cart);

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
