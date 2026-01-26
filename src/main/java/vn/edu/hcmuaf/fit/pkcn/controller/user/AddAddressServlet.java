package vn.edu.hcmuaf.fit.pkcn.controller.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.Address;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.user.AddressService;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;

import java.io.IOException;

@WebServlet(name = "AddAddress", value = "/add-address")
public class AddAddressServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || CheckUserHelper.checkUserInValid(user.getId())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String from = request.getParameter("from");
        String receiverName = request.getParameter("receiverName");
        String phoneNumber = request.getParameter("phone");
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String detail = request.getParameter("addressDetail");


        Address address = new Address();
        address.setUserId(user.getId());
        address.setReceiverName(receiverName);
        address.setPhoneNumber(phoneNumber);
        address.setProvinceCity(province);
        address.setDistrict(district);
        address.setAddressDetail(detail);
        address.setIsSelected(0);

        address.setIsSelected(0);
        AddressService addressService = new AddressService(JDBI.getJdbi());
        boolean re = addressService.addAddressSv(address);
        if (re) {
            if ("checkout".equals(from)) {
                response.sendRedirect(request.getContextPath() + "/view-payment");
            } else {
                response.sendRedirect(request.getContextPath() + "/address-user");
            }
        } else {
            request.setAttribute("error", "Không thể thêm địa chỉ mới, vui lòng thử lại!");
            request.getRequestDispatcher("/WEB-INF/views/client/add_address.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || CheckUserHelper.checkUserInValid(user.getId())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/client/add_address.jsp").forward(request, response);
    }
}
