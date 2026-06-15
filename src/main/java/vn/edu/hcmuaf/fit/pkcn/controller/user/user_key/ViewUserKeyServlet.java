package vn.edu.hcmuaf.fit.pkcn.controller.user.user_key;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserDao;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserKeyDao;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserKeyService;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;

import java.io.IOException;

@WebServlet(name = "ViewUserKeyServlet", value = "/user-key")
public class ViewUserKeyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null && !CheckUserHelper.checkUserInValid(user.getId())) {
            //DAO
            UserKeyDao userKeyDao = new UserKeyDao(JDBI.getJdbi());
            UserDao userDao = new UserDao(JDBI.getJdbi());
            //Service
            UserKeyService userKeyService = new UserKeyService(userKeyDao, userDao);

            request.setAttribute("userKeys", userKeyService.getAllUserKeyByUserId(user.getId()));
            request.setAttribute("linkNav", 4);
            request.getRequestDispatcher("/WEB-INF/views/client/user_key.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
