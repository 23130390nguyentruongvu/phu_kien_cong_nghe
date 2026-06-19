package vn.edu.hcmuaf.fit.pkcn.controller.user.user_key.json_response;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.user.KeyRevokeRequestDao;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserDao;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserKeyDao;
import vn.edu.hcmuaf.fit.pkcn.model.user.KeyRevokeRequest;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.model.user.json.request.UserKeyDTO;
import vn.edu.hcmuaf.fit.pkcn.service.user.KeyRevokeRequestService;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserKeyService;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;
import vn.edu.hcmuaf.fit.pkcn.utils.EmailUtils;
import vn.edu.hcmuaf.fit.pkcn.utils.GsonProvider;
import vn.edu.hcmuaf.fit.pkcn.utils.enums.StatusUserKey;

import javax.swing.text.DateFormatter;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "JsonRevokeUserKeyServlet", value = "/json-revoke-user-key")
public class JsonRevokeUserKeyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msg;
        try {
            String token = request.getParameter("token");

            //DAO
            UserKeyDao userKeyDao = new UserKeyDao(JDBI.getJdbi());
            UserDao userDao = new UserDao(JDBI.getJdbi());
            KeyRevokeRequestDao keyRevokeRequestDao = new KeyRevokeRequestDao(JDBI.getJdbi());
            //Service
            UserKeyService userKeyService = new UserKeyService(userKeyDao, userDao);
            KeyRevokeRequestService keyRevokeRequestService = new KeyRevokeRequestService(
                    userKeyDao,
                    keyRevokeRequestDao
            );

            KeyRevokeRequest keyRevokeRequest = keyRevokeRequestService.getKeyRevokeRequestByToken(token);
            if(keyRevokeRequest == null || keyRevokeRequest.getExpiredAt().isBefore(LocalDateTime.now()))
                throw new Exception("Yêu cầu thu hồi không tồn tại hoặc yêu cầu đã hết hạn");

            UserKeyDTO userKeyDTO = userKeyService.getUserKeyById(keyRevokeRequest.getUserKeyId());

            if(userKeyDTO == null || userKeyDTO.getStatus().equalsIgnoreCase(StatusUserKey.REVOKED.name()))
                throw new Exception("Khóa người dùng không tồn tại hoặc đã bị thu hồi");

           if(!userKeyService.revokeUserKeyById(userKeyDTO.getUserId(), userKeyDTO.getId()))
               throw new Exception("Lỗi thu hồi khóa thất bại");

            boolean res = keyRevokeRequestService.updateIsCompleted(true, token);
            msg = res ? "Thu hồi thành công" : "Thu hồi thất bại";
        } catch (Exception e) {
            msg = e.getMessage();
            e.printStackTrace();
        }

        request.setAttribute("message", msg);
        request.getRequestDispatcher("/WEB-INF/views/client/revoke_user_key.jsp").forward(request, response);
    }

    //Nhan vao body co dang userId: String va id: String
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> map = new HashMap<>();
        try {
            request.setCharacterEncoding("UTF-8");
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }

            //DAO
            UserKeyDao userKeyDao = new UserKeyDao(JDBI.getJdbi());
            UserDao userDao = new UserDao(JDBI.getJdbi());
            KeyRevokeRequestDao keyRevokeRequestDao = new KeyRevokeRequestDao(JDBI.getJdbi());
            //Service
            UserKeyService userKeyService = new UserKeyService(userKeyDao, userDao);
            KeyRevokeRequestService keyRevokeRequestService = new KeyRevokeRequestService(
                    userKeyDao,
                    keyRevokeRequestDao
            );
            UserService userService = new UserService(JDBI.getJdbi());

            Gson gson = GsonProvider.getGson();

            Type mapType = new TypeToken<Map<String, Object>>() {
            }.getType();
            Map<String, Object> body = gson.fromJson(stringBuilder.toString(), mapType);

            Integer userId = Integer.parseInt(body.get("userId").toString());
            Integer id = Integer.parseInt(body.get("id").toString());

            UserKeyDTO userKeyDTO = userKeyService.getUserKeyById(id);

            User user = userService.getUserById(userId);

            if (user == null)
                throw new Exception("Không tìm thấy người dùng này");

            if (userKeyDTO.getStatus().equalsIgnoreCase(StatusUserKey.REVOKED.name()))
                throw new Exception("Khóa này đã bị thu hồi lúc: " + userKeyDTO.getRevokedAt().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                ));

//           boolean res = userKeyService.revokeUserKeyById(userId, id);
//
//           map.put("message", res?"Vô hiệu hóa thành công":"Vô hiệu hóa thất bại");
//           map.put("success", res);

            KeyRevokeRequest keyRevokeRequest = new KeyRevokeRequest(
                    id,
                    UUID.randomUUID().toString(),
                    LocalDateTime.now().plusMinutes(15),
                    false
            );

            boolean res = keyRevokeRequestService.insertKeyRevokeRequest(keyRevokeRequest);
            if (!res)
                throw new Exception("Không thể tạo yêu cầu thu hồi khóa");

            String revokeLink = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort()
                    + request.getContextPath()
                    + "/json-revoke-user-key?token=" + keyRevokeRequest.getToken();
            String subject = "ShopPhuKienCongNghe: Thu hồi khóa";
            String content = "<h3>Bạn đã yêu cầu thu hồi khóa có tên: " + userKeyDTO.getKeyName() + " </h3>"
                    + "<p>Vui lòng click vào link dưới đây:</p>"
                    + "<a href='" + revokeLink + "'>Thu hồi ngay</a>"
                    + "<p>Link này sẽ hết hạn sau 15 phút.</p>";

            EmailUtils.sendEmail(
                    user.getEmail(),
                    subject,
                    content
            );

            map.put("message", "Chúng tôi đã gửi link thu hồi khóa tới email: " + user.getEmail() +
                    " vui lòng kiểm tra hộp thư và xác nhận thu hồi");
            map.put("success", true);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            map.put("message", "Lỗi không thể hủy khóa");
            map.put("success", false);
        }

        String jsonRes = new Gson().toJson(map);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonRes);
        response.getWriter().flush();
    }
}