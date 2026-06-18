package vn.edu.hcmuaf.fit.pkcn.controller.order.snap;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.order.snap.OrderSnapshotDAO;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserDao;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserKeyDao;
import vn.edu.hcmuaf.fit.pkcn.model.order.snap.OrderSnapshot;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.model.user.UserKey;
import vn.edu.hcmuaf.fit.pkcn.model.user.json.request.UserKeyDTO;
import vn.edu.hcmuaf.fit.pkcn.service.order.snap.OrderSnapshotService;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserKeyService;
import vn.edu.hcmuaf.fit.pkcn.utils.GsonProvider;
import vn.edu.hcmuaf.fit.pkcn.utils.HashSHA256;
import vn.edu.hcmuaf.fit.pkcn.utils.OrderHashDataFormatter;
import vn.edu.hcmuaf.fit.pkcn.utils.VerifySignature;
import vn.edu.hcmuaf.fit.pkcn.utils.enums.OrderStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

            UserKeyDTO userKeyCurr = userKeyService.getUserKeyActiveByUserId(userId);
            if (userKeyCurr == null)
                throw new Exception("Bạn chưa có hoặc các khóa công khai cũ " +
                        "của bạn không hoạt đông! Vui lòng thêm khóa " +
                        "công khai trước khi kí đơn hàng");

            OrderSnapshot orderSnapshot = orderSnapshotService.getOrderSnapshotsByOrderId(orderId, userId);
            if(orderSnapshot == null)
                throw new Exception("Không tìm thấy đơn hàng này");

            if(orderSnapshot.getExpireSignKey().isBefore(LocalDateTime.now()))
                throw new Exception("Đơn hàng này đã hết hạn kí, vui lòng tạo đơn mới");

            if(!orderSnapshot.getStatusOrder().equals(OrderStatus.PENDING_SIGNATURE.getCode())
            && !orderSnapshot.getStatusOrder().equals(OrderStatus.WAITING_RE_SIGN.getCode()))
                throw new Exception("Đơn hàng này không ở trạng thái kí");

            String formatData = OrderHashDataFormatter.buildOrderFormatForHash(orderSnapshot);
            String hashData = HashSHA256.SHA256(formatData);

            request.setAttribute("userKeyCurrent", userKeyCurr);
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

    //Nhan vao body: userId: String, orderId: String, signature: String, userKeyId: String
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //(String base64PublicKey, String base64Signature, String rawDataJson, String algorithm)
        Map<String, Object> map = new HashMap<>();
        request.setCharacterEncoding("UTF-8");
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        //lay thong tin ve body gui tu request
        Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> body = GsonProvider.getGson()
                .fromJson(stringBuilder.toString(), mapType);

        try {
            Integer userId = Integer.parseInt(body.get("userId").toString());
            Integer orderId = Integer.parseInt(body.get("orderId").toString());
            Integer userKeyId = Integer.parseInt(body.get("userKeyId").toString());
            String signature = (String) body.get("signature");

            UserKeyDao userKeyDao = new UserKeyDao(JDBI.getJdbi());
            UserDao userDao = new UserDao(JDBI.getJdbi());
            UserKeyService userKeyService = new UserKeyService(userKeyDao, userDao);

            OrderSnapshotService orderSnapshotService = new OrderSnapshotService(
                    new OrderSnapshotDAO(JDBI.getJdbi())
            );

            UserKeyDTO userKeyDTO = userKeyService.getActiveUserKeyByIdUser(userId);
            //xac thuc userKeyId con ACTIVE va hop le khong
            if(userKeyDTO == null)
                throw new Exception("Bạn chưa có hoặc các khóa công khai cũ " +
                        "của bạn không hoạt đông! Vui lòng thêm khóa " +
                        "công khai trước khi kí đơn hàng");
            if(!Objects.equals(userKeyDTO.getId(), userKeyId))
                throw new Exception("Chúng tôi nhận ra khóa của bạn hiện tại không" +
                        " khớp với khóa đang hoạt động. Vui long kiểm tra lại khóa!");

            //xac thuc chu ki cua nguoi dung
            OrderSnapshot orderSnapshot = orderSnapshotService.getOrderSnapshotsByOrderId(orderId, userId);
            if(orderSnapshot == null)
                throw new Exception("Không tìm thấy đơn hàng");
            if(orderSnapshot.getExpireSignKey().isBefore(LocalDateTime.now()))
                throw new Exception("Đơn hàng này đã hết hạn kí, vui lòng tạo đơn mới");

            if(!orderSnapshot.getStatusOrder().equals(OrderStatus.PENDING_SIGNATURE.getCode())
                    && !orderSnapshot.getStatusOrder().equals(OrderStatus.WAITING_RE_SIGN.getCode()))
                throw new Exception("Đơn hàng này không ở trạng thái kí");

            String formatData = OrderHashDataFormatter.buildOrderFormatForHash(orderSnapshot);

            boolean isVerify = VerifySignature.verifySignature(
                    userKeyDTO.getPublicKey(),
                    signature,
                    formatData,
                    userKeyDTO.getNameAlgorithm()
            );

            if(!isVerify)
                throw new Exception("Chữ kí này không hợp lệ");

            boolean res = orderSnapshotService.saveSignatureAndUserKeyId(
                    orderId,
                    userKeyId,
                    signature
            );

            if(!res)
                throw new Exception("Lỗi: Không thể cập nhật lại chữ kí và khóa cho đơn hàng");

            map.put("message", "Kí đơn thành công");
            map.put("success", res);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", e.getMessage());
            map.put("success", false);
        }

        String jsonRes = new Gson().toJson(map);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonRes);
        response.getWriter().flush();
    }
}