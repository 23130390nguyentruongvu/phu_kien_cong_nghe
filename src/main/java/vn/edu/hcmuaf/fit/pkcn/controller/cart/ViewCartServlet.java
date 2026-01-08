package vn.edu.hcmuaf.fit.pkcn.controller.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.model.cart.Cart;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariant;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@WebServlet(name = "CartServlet", value = "/view-cart")
public class ViewCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart;
        try {
            HttpSession session = request.getSession();
            cart = (Cart) session.getAttribute("cart");
            if (cart == null) cart = new Cart((User) session.getAttribute("user"));

            session.setAttribute("cart", cart);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/WEB-INF/views/client/shopping_cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}