package vn.edu.hcmuaf.fit.pkcn.controller.admin.user.response_json;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.category.CategoryDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductImageDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductService;
import vn.edu.hcmuaf.fit.pkcn.service.user.UserService;
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProductImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "JsonGetFolderIdUserServlet", value = "/get-folder-id-user")
public class JsonGetFolderIdUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean hasImageInStorage = false;
        String folderId = null;
        String message = "";
        UserService userService = new UserService(
                JDBI.getJdbi()
        );
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            folderId = userService.getFolderId(userId);
            if (folderId == null) throw new Exception("Không tìm thấy folder id");
            message = "Ok";
            hasImageInStorage = true;
        } catch (Exception e) {
            e.printStackTrace();
            message = e.getMessage();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("folderId", folderId);
        map.put("hasImageInStorage", hasImageInStorage);
        map.put("message", message);
        String jsonRes = new Gson().toJson(map);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonRes);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}