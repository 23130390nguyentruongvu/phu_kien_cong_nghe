package vn.edu.hcmuaf.fit.pkcn.controller.admin.user;

import vn.edu.hcmuaf.fit.pkcn.model.user.User;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;

import vn.edu.hcmuaf.fit.pkcn.config.JDBI;

import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;



import java.io.IOException;



@WebServlet(name = "LockUnLockUser", value = "/lock-unlock-user")

public class LockUnLockServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int id = Integer.parseInt(request.getParameter("id"));
        User userSession = (User) request.getSession().getAttribute("user");
        UserService userService = new UserService(JDBI.getJdbi());
        User target = userService.getUserById(id);
        if(target != null && userSession !=null){
            if(id!=userSession.getId() && target.getRole() == 2){
                if("lock".equals(action)) {
                    userService.updateStatusSv(id,"locked");
                }else if("unlock".equals(action)) {
                    userService.updateStatusSv(id,"active");
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/view-user");
    }
}