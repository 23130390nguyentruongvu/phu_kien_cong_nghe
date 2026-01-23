package vn.edu.hcmuaf.fit.pkcn.controller.admin.user.response_json;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserAddressDao;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "JsonCheckUsernameEmailServlet", value = "/check-username-email")
public class JsonCheckUsernameEmailServlet extends HttpServlet {
    /*
    request username and email
    response email: true/false, username: true/false
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        boolean emailExist = false, usernameExist = false;

        UserService userService = new UserService(
                JDBI.getJdbi()
        );

        try {
            emailExist = userService.checkEmail(email);
            usernameExist = userService.checkUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("email", emailExist);
        map.put("username", usernameExist);

        String jsonRes = new Gson().toJson(map);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonRes);
        response.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}