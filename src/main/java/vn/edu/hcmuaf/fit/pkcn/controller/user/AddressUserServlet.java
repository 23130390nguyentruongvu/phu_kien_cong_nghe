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

    import java.io.IOException;
    import java.util.List;

    @WebServlet(name = "AddressUser", value = "/address-user")
    public class AddressUserServlet extends HttpServlet {
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        }
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            User user = (User) request.getSession().getAttribute("user");
            if(user==null){
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            AddressService service = new AddressService(JDBI.getJdbi());
            int userId = user.getId();
            List<Address> listAddress = service.getAllAddress(userId);

            request.setAttribute("listAddress", listAddress);
            request.getRequestDispatcher("/WEB-INF/views/client/address_user.jsp").forward(request, response);
        }
    }
