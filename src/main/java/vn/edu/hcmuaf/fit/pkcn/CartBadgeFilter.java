package vn.edu.hcmuaf.fit.pkcn;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.pkcn.model.cart.Cart;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;

import java.io.IOException;

@WebFilter("/*") // Áp dụng cho tất cả các trang
public class CartBadgeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        Cart cart = (Cart) session.getAttribute("cart");
        int totalQuantity = 0;
        if (cart != null) {
            totalQuantity = cart.getCartItems().size();
        }
        request.setAttribute("cartBadgeCount", totalQuantity);
        // Tiếp tục luồng xử lý
        chain.doFilter(request, response);
    }
}