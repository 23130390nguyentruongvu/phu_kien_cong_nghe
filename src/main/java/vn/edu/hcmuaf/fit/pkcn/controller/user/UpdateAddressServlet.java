package vn.edu.hcmuaf.fit.pkcn.controller.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.Address;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.user.AddressService;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;

import java.io.IOException;

@WebServlet(name = "UpdateAddress", value = "/update-address")
public class UpdateAddressServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("addressId"));
        String name = request.getParameter("receiverName");
        String phone = request.getParameter("phone");
        String province = request.getParameter("province");
        String details = request.getParameter("addressDetail");

        Address address = new Address();
        address.setId(id);
        address.setReceiverName(name);
        address.setPhoneNumber(phone);
        address.setProvinceCity(province);
        address.setAddressDetail(details);

        AddressService service = new AddressService(JDBI.getJdbi());
        boolean suc = service.updateAddress(address);
        if (suc) {
            response.sendRedirect("address-user");
        } else {
            request.setAttribute("error", "Cập nhật thất bại!");
            request.setAttribute("address", address);
            request.getRequestDispatcher("WEB-INF/views/client/edit_address.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || CheckUserHelper.checkUserInValid(user.getId())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String idRaw = request.getParameter("id");
        if (idRaw == null) {
            response.sendRedirect(request.getContextPath() + "/address-user");
            return;
        }

        int id = Integer.parseInt(idRaw);
        AddressService service = new AddressService(JDBI.getJdbi());
        Address address = service.getAddressById(id);

        if (address != null) {
            request.setAttribute("address", address);
            request.getRequestDispatcher("/WEB-INF/views/client/edit_address.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/address-user");
        }
    }
}
