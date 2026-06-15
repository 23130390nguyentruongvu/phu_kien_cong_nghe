package vn.edu.hcmuaf.fit.pkcn.controller.user.user_key.json_response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserDao;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserKeyDao;
import vn.edu.hcmuaf.fit.pkcn.model.user.json.request.UserKeyDTO;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserKeyService;
import vn.edu.hcmuaf.fit.pkcn.utils.GsonProvider;
import vn.edu.hcmuaf.fit.pkcn.utils.KeyValidator;
import vn.edu.hcmuaf.fit.pkcn.utils.enums.SignatureAlgorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "JsonAddUserKeyServlet", value = "/json-add-user-key")
public class JsonAddUserKeyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        try {
            Gson gson = GsonProvider.getGson();
            UserKeyDTO userKeyDTO = gson.fromJson(stringBuilder.toString(), UserKeyDTO.class);

            if (!userKeyDTO.getNameAlgorithm().equals(SignatureAlgorithm.DSA.name())
                    && !userKeyDTO.getNameAlgorithm().equals(SignatureAlgorithm.RSA.name())) {
                Map<String, Object> map = new HashMap<>();
                map.put("success", false);
                map.put("message", "Không hỗ trợ thuật toán này");
                String jsonRes = new Gson().toJson(map);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonRes);
                response.getWriter().flush();
                return;
            }

            boolean isValidKey = KeyValidator.isValidPublicKey(userKeyDTO.getPublicKey(), userKeyDTO.getNameAlgorithm());
            if (!isValidKey) {
                Map<String, Object> map = new HashMap<>();
                map.put("success", false);
                map.put("message", "Khóa công khai không hợp lệ");
                String jsonRes = new Gson().toJson(map);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonRes);
                response.getWriter().flush();
                return;
            }

            //DAO
            UserKeyDao userKeyDao = new UserKeyDao(JDBI.getJdbi());
            UserDao userDao = new UserDao(JDBI.getJdbi());
            //Service
            UserKeyService userKeyService = new UserKeyService(userKeyDao, userDao);

            boolean result = userKeyService.addUserKey(userKeyDTO);

            Map<String, Object> map = new HashMap<>();
            map.put("success", result);
            map.put("message", result ? "Lưu thành công" : "Lưu thất bại");
            String jsonRes = new Gson().toJson(map);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonRes);
            response.getWriter().flush();

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> map = new HashMap<>();

            map.put("success", false);
            map.put("message", e.getMessage());
            String jsonRes = new Gson().toJson(map);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonRes);
            response.getWriter().flush();
        }
    }
}