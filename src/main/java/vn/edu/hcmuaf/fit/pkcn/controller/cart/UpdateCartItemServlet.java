package vn.edu.hcmuaf.fit.pkcn.controller.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.model.cart.Cart;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;

import java.io.IOException;

@WebServlet(name = "UpdateCartItemServlet", value = "/update-cart-item")
public class UpdateCartItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    // update lại số lượng của 1 cart item
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int prodVarId = Integer.parseInt(request.getParameter("id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        HttpSession session = request.getSession();
        System.out.println("OK " + prodVarId);
        try {
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart((User) session.getAttribute("user"));
                session.setAttribute("cart", cart);
                return;
            }
            if (quantity == -1 || quantity == 1) {
                cart.plusOrMinusOneQuantity(prodVarId, quantity == 1);
            } else {
                cart.updateCartItem(prodVarId, quantity);
            }
            session.setAttribute("cart", cart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //gọi get của view-cart
        response.sendRedirect(request.getContextPath() + "/view-cart");
    }
}