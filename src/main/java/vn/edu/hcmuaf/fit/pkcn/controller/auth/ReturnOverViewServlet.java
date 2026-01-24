package vn.edu.hcmuaf.fit.pkcn.controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;

import java.io.IOException;
@WebServlet(name = "ReturnOverview", value = "/return-overview")
public class ReturnOverViewServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null && user.getRole() == 1){
            response.sendRedirect(request.getContextPath()+"/overview");
        }else{
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
