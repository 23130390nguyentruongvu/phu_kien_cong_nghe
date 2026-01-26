package vn.edu.hcmuaf.fit.pkcn.controller.admin.overview;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.model.admin.order.OrderOverView;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.order.OrderService;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductVariantService;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;
import vn.edu.hcmuaf.fit.pkcn.utils.PriceFormatUtils;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewOverViewServlet", value = "/overview")
public class ViewOverViewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null || CheckUserHelper.checkUserInValid(user.getId())) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            } else {
                if (user.getRole() != 1) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trang không tồn tại");
                } else {
                    OrderService orderService = new OrderService(
                            JDBI.getJdbi()
                    );

                    ProductVariantService productVariantService = new ProductVariantService(
                            new ProductVariantDao(JDBI.getJdbi())
                    );

                    UserService userService = new UserService(
                            JDBI.getJdbi()
                    );
                    String filterOrder = request.getParameter("filter-by-week");
                    int week = getValueFilterWeek(filterOrder);
                    List<OrderOverView> orders = orderService.getOrderOverView(checkValidFilterOrderValue(filterOrder), week);

                    request.setAttribute("orders", orders);
                    request.setAttribute("totalUser", userService.getQuantityUser());
                    request.setAttribute("totalVariant", productVariantService.getQuantityVariants());
                    request.setAttribute(
                            "revenue",
                            PriceFormatUtils.formatPrice(PriceFormatUtils.PATTERN_D, orderService.getRevenue())
                    );
                    request.setAttribute("option", filterOrder);
                    request.setAttribute("orderDelivered", orderService.getOrderDelivered());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("totalUser", 0);
            request.setAttribute("totalVariant", 0);
            request.setAttribute(
                    "revenue",
                    PriceFormatUtils.formatPrice(PriceFormatUtils.PATTERN_D, 0)
            );
            request.setAttribute("orderDelivered", 0);
        }
        request.setAttribute("navLink", 1);
        request.getRequestDispatcher("/WEB-INF/views/admin/admin_overview.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private int getValueFilterWeek(String filter) {
        if (filter == null || filter.isEmpty()) return 0;
        return switch (filter.toLowerCase()) {
            case "1-week" -> 1;
            case "2-week" -> 2;
            case "3-week" -> 3;
            case "4-week" -> 4;
            default -> 0;
        };
    }

    private boolean checkValidFilterOrderValue(String filter) {
        if (filter == null || filter.isEmpty()) return false;
        return switch (filter) {
            case "1-week", "2-week", "3-week", "4-week" -> true;
            default -> false;
        };
    }
}