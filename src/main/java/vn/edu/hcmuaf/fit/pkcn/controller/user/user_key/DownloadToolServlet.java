package vn.edu.hcmuaf.fit.pkcn.controller.user.user_key;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(name = "DownloadToolServlet", value = "/download-tool")
public class DownloadToolServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String relativePath = "/resources/ToolKiDienTu.zip";
        String fileName = "ToolKiDonHang.zip";

        response.setContentType("application/zip");

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", fileName);
        response.setHeader(headerKey, headerValue);

        try (InputStream inStream = getServletContext().getResourceAsStream(relativePath);
             OutputStream outStream = response.getOutputStream()) {

            if (inStream == null) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("Lỗi: Không tìm thấy file ZIP trong hệ thống!");
                return;
            }

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}