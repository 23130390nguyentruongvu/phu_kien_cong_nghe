package vn.edu.hcmuaf.fit.pkcn.controller.user.user_key.json_response;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.utils.GsonProvider;
import vn.edu.hcmuaf.fit.pkcn.utils.KeyValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "JsonCheckPublicKeyValidServlet", value = "/check-pub-key-valid")
public class JsonCheckPublicKeyValidServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    //Nhận vào tên thuật toán như RSA/DSA (nameAlgorithm: String)
    // và chuổi key public (publicKey: String)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> map = new HashMap<>();
        request.setCharacterEncoding("UTF-8");
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> body = GsonProvider.getGson()
                .fromJson(stringBuilder.toString(), mapType);

        String nameAlgo = (String) body.get("nameAlgorithm");
        String publicKey = (String) body.get("publicKey");

        boolean res = KeyValidator.isValidPublicKey(publicKey, nameAlgo);

        map.put("success", res);
        map.put("message", res?"Key hợp lệ":"Key không hợp lệ, vui lòng kiểm tra lại thuật toán hoặc khóa công khai");
        String jsonRes = new Gson().toJson(map);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonRes);
        response.getWriter().flush();
    }
}