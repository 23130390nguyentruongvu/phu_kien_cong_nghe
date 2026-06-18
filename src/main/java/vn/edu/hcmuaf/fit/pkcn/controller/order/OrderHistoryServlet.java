package vn.edu.hcmuaf.fit.pkcn.controller.order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.order.OrderDao;
import vn.edu.hcmuaf.fit.pkcn.dao.order.snap.OrderSnapshotDAO;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserDao;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserKeyDao;
import vn.edu.hcmuaf.fit.pkcn.model.order.OrderShowAsItem;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "OrderHistoryServlet", value = "/order-history")
public class OrderHistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        OrderService orderService = new OrderService(
                new OrderDao(JDBI.getJdbi()),
                new ProductDao(JDBI.getJdbi())
        );
        String status = request.getParameter("filter-by");
        try {
            if (user == null || CheckUserHelper.checkUserInValid(user.getId()))
                response.sendRedirect(request.getContextPath() + "/login");
            else {
                 if (status != null && !status.isEmpty()) {
                        OrderStatus.fromCode(status);
                } else if (status != null && status.isEmpty()) {
                    status = null;
                }

                String verifyFilter = request.getParameter("verifyFilter");

                OrderSnapshotService orderSnapshotService = new OrderSnapshotService(
                        new OrderSnapshotDAO(JDBI.getJdbi())
                );

                UserKeyService userKeyService = new UserKeyService(
                        new UserKeyDao(JDBI.getJdbi()),
                        new UserDao(JDBI.getJdbi())
                );

                List<OrderSnapshot> orders = orderSnapshotService.getOrderSnapshotByUserId(
                        user.getId(),
                        status
                );

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

                List<OrderSnapshot> res;

                if (verifyFilter == null || verifyFilter.equalsIgnoreCase("both"))
                    request.setAttribute("orders", orders);
                else if (verifyFilter.equalsIgnoreCase("verify")) {
                    res = new ArrayList<>();
                    for(OrderSnapshot orderSnapshot: orders)
                        if(orderSnapshot.isVerify())
                            res.add(orderSnapshot);

                    request.setAttribute("orders", res);
                } else if (verifyFilter.equalsIgnoreCase("un-verify")) {
                    res = new ArrayList<>();
                    for(OrderSnapshot orderSnapshot: orders)
                        if(!orderSnapshot.isVerify())
                            res.add(orderSnapshot);

                    request.setAttribute("orders", res);
                }

                request.setAttribute("verifyFilter", verifyFilter);
                request.setAttribute("filterBy", status);
                request.setAttribute("linkNav", 2);

                request.getRequestDispatcher("/WEB-INF/views/client/history_order.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}