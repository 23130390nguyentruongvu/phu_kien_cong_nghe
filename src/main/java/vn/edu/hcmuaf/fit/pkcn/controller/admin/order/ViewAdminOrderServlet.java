package vn.edu.hcmuaf.fit.pkcn.controller.admin.order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.order.snap.OrderSnapshotDAO;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserDao;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserKeyDao;
import vn.edu.hcmuaf.fit.pkcn.model.admin.order.AdminOrderShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.OrderSnapshot;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.model.user.json.request.UserKeyDTO;
import vn.edu.hcmuaf.fit.pkcn.service.order.OrderService;
import vn.edu.hcmuaf.fit.pkcn.service.order.snap.OrderSnapshotService;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserKeyService;
import vn.edu.hcmuaf.fit.pkcn.utils.CheckUserHelper;
import vn.edu.hcmuaf.fit.pkcn.utils.OrderHashDataFormatter;
import vn.edu.hcmuaf.fit.pkcn.utils.VerifySignature;
import vn.edu.hcmuaf.fit.pkcn.utils.enums.OrderStatus;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewAdminOrderServlet", value = "/admin-order")
public class ViewAdminOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || CheckUserHelper.checkUserInValid(user.getId())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (user.getRole() != 1) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trang không tồn tại");
            return;
        }

        try {
            OrderSnapshotService orderSnapshotService = new OrderSnapshotService(
                    new OrderSnapshotDAO(JDBI.getJdbi())
            );

            UserKeyService userKeyService = new UserKeyService(
                    new UserKeyDao(JDBI.getJdbi()),
                    new UserDao(JDBI.getJdbi())
            );

            String key = request.getParameter("keySearch");
            String statusFilter = request.getParameter("statusFilter");
            if (statusFilter != null && statusFilter.isEmpty()) {
                statusFilter = null;
            }
            List<OrderSnapshot> orders = orderSnapshotService.getOrderSnapshotsForAdmin(key, statusFilter);

            //check verify
            for(OrderSnapshot orderSnapshot: orders) {
                if(orderSnapshot.getSignature() == null) {
                    orderSnapshot.setVerify(false);
                    continue;
                }
//                if(orderSnapshot.getStatusOrder().equals(OrderStatus.PENDING_SIGNATURE.getCode())
//                || orderSnapshot.getStatusOrder().equals(OrderStatus.WAITING_RE_SIGN.getCode())) {
//                    orderSnapshot.setVerify(false);
//                    continue;
//                }
                UserKeyDTO userKeyDTO = userKeyService.getUserKeyById(orderSnapshot.getUserKeyId());
                if(userKeyDTO == null) {
                    orderSnapshot.setVerify(false);
                    continue;
                }

                String formatData = OrderHashDataFormatter.buildOrderFormatForHash(orderSnapshot);
                boolean isVerity = VerifySignature.verifySignature(
                        userKeyDTO.getPublicKey(),
                        orderSnapshot.getSignature(),
                        formatData,
                        userKeyDTO.getNameAlgorithm()
                );

                orderSnapshot.setVerify(isVerity);
            }

            request.setAttribute("orders", orders);
            request.setAttribute("keySearch", key);
            request.setAttribute("statusFilter", statusFilter);
            request.setAttribute("navLink", 4);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("orders", null);
            request.setAttribute("navLink", 4);
        }

        request.getRequestDispatcher("/WEB-INF/views/admin/admin_order.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
