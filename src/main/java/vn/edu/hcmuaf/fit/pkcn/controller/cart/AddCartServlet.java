package vn.edu.hcmuaf.fit.pkcn.controller.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.model.cart.Cart;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductVariantService;

import java.io.IOException;

@WebServlet(name = "AddCartServlet", value = "/add-cart")
public class AddCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    // Yêu cầu truyền vào id của prod biến thể + quantity + name của product chung của biến thể đó
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductVariantService pvService = new ProductVariantService(
                new ProductVariantDao(JDBI.getJdbi())
        );

        try {
            int idProdVar = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) cart = new Cart((User) session.getAttribute("user"));

            cart.addCartItem(pvService.getProductVariantById(idProdVar), name, quantity);

            session.setAttribute("cart", cart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getHeader("Referer"));
    }
}