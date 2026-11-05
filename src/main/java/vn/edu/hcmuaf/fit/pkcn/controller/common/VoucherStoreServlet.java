package vn.edu.hcmuaf.fit.pkcn.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.common.VoucherService;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

@WebServlet("/voucher-store")
public class VoucherStoreServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        Integer userId = user != null && !CheckUserHelper.checkUserInValid(user.getId()) ? user.getId() : null;

        var vouchers = new VoucherService(JDBI.getJdbi()).getAvailableVouchers(userId);
        for (Map<String, Object> voucher : vouchers) {
            Object endDate = voucher.get("end_date");
            if (endDate instanceof LocalDateTime dateTime) {
                voucher.put("end_date", Timestamp.valueOf(dateTime));
            }
        }

        request.setAttribute("vouchers", vouchers);
        request.setAttribute("activeHeader", 6);
        request.getRequestDispatcher("/WEB-INF/views/client/voucher_store.jsp").forward(request, response);
    }
}
