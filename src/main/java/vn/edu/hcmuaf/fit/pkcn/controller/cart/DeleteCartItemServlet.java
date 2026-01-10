package vn.edu.hcmuaf.fit.pkcn.controller.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.model.cart.Cart;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;

import java.io.IOException;

@WebServlet(name = "DeleteCartItemServlet", value = "/delete-cart-item")
public class DeleteCartItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int prodVarId;
        try {
            prodVarId = Integer.parseInt(request.getParameter("id"));
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                session.setAttribute("cart", new Cart((User) session.getAttribute("user")));
                return;
            }
            cart.removeCartItem(prodVarId);
            session.setAttribute("cart", cart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //gọi get của view-cart
        response.sendRedirect(request.getContextPath() + "/view-cart");
    }
}