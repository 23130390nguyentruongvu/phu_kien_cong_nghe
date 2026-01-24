package vn.edu.hcmuaf.fit.pkcn.controller.admin.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchUser", value = "/search-user")
public class SearchUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchName = request.getParameter("searchName");
        UserService  userService = new UserService(JDBI.getJdbi());
        List<User> users;
        if(searchName!=null &&  !searchName.isEmpty()){
            users = userService.getUserByName(searchName);
        }else{
            users = userService.getAllUsers();
        }
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/views/admin/admin_user.jsp").forward(request, response);
    }
}
