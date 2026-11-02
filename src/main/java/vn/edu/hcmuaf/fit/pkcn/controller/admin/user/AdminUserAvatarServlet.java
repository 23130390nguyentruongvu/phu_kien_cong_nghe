package vn.edu.hcmuaf.fit.pkcn.controller.admin.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet(name = "AdminUserAvatarServlet", value = "/admin-user-avatars/*")
public class AdminUserAvatarServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String fileName = pathInfo == null ? null : pathInfo.substring(1);
        Path image = AdminUserAvatarStorage.findStoredAvatar(fileName);
        if (image == null || !Files.isRegularFile(image)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType(switch (image.getFileName().toString().substring(
                image.getFileName().toString().lastIndexOf('.') + 1)) {
            case "jpg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            default -> "application/octet-stream";
        });
        response.setContentLengthLong(Files.size(image));
        response.setHeader("X-Content-Type-Options", "nosniff");
        Files.copy(image, response.getOutputStream());
    }
}
