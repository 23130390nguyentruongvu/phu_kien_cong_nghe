package vn.edu.hcmuaf.fit.pkcn.controller.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.user.AddressService;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;

import java.io.IOException;

@WebServlet(name = "DeleteAddress", value = "/delete-address")
public class DeleteAddressServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || CheckUserHelper.checkUserInValid(user.getId())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try {
            int id1 = Integer.parseInt(id);
            AddressService service = new AddressService(JDBI.getJdbi());
            boolean suc = service.deleteAddress(id1, user.getId());

            if (suc) {
                response.sendRedirect(request.getContextPath() + "/address-user");
            } else {
                request.setAttribute("error", "Không thể xóa!");
                request.getRequestDispatcher("WEB-INF/views/client/address_user.jsp").forward(request, response);
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/address-user");
        }
    }
}
