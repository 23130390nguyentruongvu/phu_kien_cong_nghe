package vn.edu.hcmuaf.fit.pkcn.controller.order.snap;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.order.snap.OrderSnapshotDAO;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserDao;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserKeyDao;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.OrderSnapshot;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.order.snap.OrderSnapshotService;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserKeyService;
import vn.edu.hcmuaf.fit.pkcn.utils.HashSHA256;
import vn.edu.hcmuaf.fit.pkcn.utils.OrderHashDataFormatter;

import java.io.IOException;

@WebServlet(name = "JsonProcessDataBeforeSignServlet", value = "/process-sign-order")
public class ProcessDataSignServlet extends HttpServlet {
    //Nhan vao1 param orderId kieu Integer
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                throw new Exception("Phiên làm việc đã hết hạn. Vui lòng đăng nhập lại!");
            }
            Integer userId = ((User) session.getAttribute("user")).getId();
            Integer orderId = Integer.parseInt(request.getParameter("orderId"));
            OrderSnapshotService orderSnapshotService = new OrderSnapshotService(
                    new OrderSnapshotDAO(JDBI.getJdbi())
            );

            UserKeyDao userKeyDao = new UserKeyDao(JDBI.getJdbi());
            UserDao userDao = new UserDao(JDBI.getJdbi());
            UserKeyService userKeyService = new UserKeyService(userKeyDao, userDao);

            if (userKeyService.isNotFoundUserKeyACTIVE(userId))
                throw new Exception("Bạn chưa có hoặc các khóa công khai cũ " +
                        "của bạn không hoạt đông! Vui lòng thêm khóa " +
                        "công khai trước khi kí đơn hàng");

            OrderSnapshot orderSnapshot = orderSnapshotService.getOrderSnapshotsByOrderId(orderId);
            String formatData = OrderHashDataFormatter.buildOrderHashSource(orderSnapshot);
            String hashData = HashSHA256.SHA256(formatData);

            request.setAttribute("hashData", hashData);
            request.setAttribute("formatData", formatData);
            request.setAttribute("message", "VUI LÒNG ĐỌC KĨ CÁC THÔNG TIN VỀ ĐƠN HÀNG CỦA BẠN," +
                    " NẾU ĐỒNG Ý THÌ VUI LÒNG COPY HOẶC TẢI MÃ BĂM BÊN DƯỚI ĐỂ THỰC HIỆN KÍ ĐƠN HÀNG " +
                    "VÀ XÁC NHẬN CHỮ KÍ");
            request.setAttribute("userId", userId);
            request.setAttribute("orderId", orderId);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("ErrorMessage", ex.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/views/client/sign_order.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}