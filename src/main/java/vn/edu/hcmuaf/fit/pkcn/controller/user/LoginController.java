package vn.edu.hcmuaf.fit.pkcn.controller.user;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.cart.Cart;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;

import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null && !CheckUserHelper.checkUserLocked(user.getId())) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/client/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loginInfo = request.getParameter("loginInfo");
        String password = request.getParameter("password");

        UserService userService = new UserService(JDBI.getJdbi());
        try {
            User user = userService.loginUser(loginInfo, password);

            if (user != null) {
                HttpSession session = request.getSession();
                Cart cart = (Cart) session.getAttribute("cart");
                if (cart == null) cart = new Cart(null);
                cart.mergeUser(user);

                session.setAttribute("user", user);
                session.setAttribute("cart", cart);
                if (user.getRole() == 1) {
                    response.sendRedirect(request.getContextPath() + "/overview");
                } else if (user.getRole() == 2) {
                    response.sendRedirect(request.getContextPath() + "/");
                }
            } else {
                request.setAttribute("error", "Tài khoản hoặc mật khẩu không đúng");
                request.getRequestDispatcher("/WEB-INF/views/client/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            if ("tài khoản đã bị khóa".equals(e.getMessage())) {
                request.setAttribute("error", "Tài khoản đã bị khóa. Liên hệ Admin để mở!");
            } else {
                request.setAttribute("error", e.getMessage());
            }
            request.getRequestDispatcher("/WEB-INF/views/client/login.jsp").forward(request, response);
        }
    }
}
