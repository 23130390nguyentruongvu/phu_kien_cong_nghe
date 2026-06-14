package vn.edu.hcmuaf.fit.pkcn.controller.user.user_key.json_response;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserDao;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserKeyDao;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserKeyService;
import vn.edu.hcmuaf.fit.pkcn.utils.GsonProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "JsonRevokeUserKeyServlet", value = "/json-revoke-user-key")
public class JsonRevokeUserKeyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
           //Service
           UserKeyService userKeyService = new UserKeyService(userKeyDao, userDao);

           Gson gson = GsonProvider.getGson();

           Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
           Map<String, Object> body = gson.fromJson(stringBuilder.toString(), mapType);

           Integer userId = Integer.parseInt(body.get("userId").toString());
           Integer id = Integer.parseInt(body.get("id").toString());

           boolean res = userKeyService.revokeUserKeyById(userId, id);

           map.put("message", res?"Vô hiệu hóa thành công":"Vô hiệu hóa thất bại");
           map.put("success", res);
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