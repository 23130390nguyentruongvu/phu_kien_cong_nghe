package vn.edu.hcmuaf.fit.pkcn.controller.admin.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductImageDao;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductAdminShowAsItem;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariant;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductService;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductVariantService;
import vn.edu.hcmuaf.fit.pkcn.sort.product.SortProductImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "GetVariantsServlet", value = "/get-variants")
public class GetVariantsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        ProductVariantService productVariantService = new ProductVariantService(
                new ProductVariantDao(JDBI.getJdbi())
        );

        try {
            if (user == null)
                request.getRequestDispatcher("variant-rows.jsp").forward(request, response);
            else {
                if (user.getRole() != 1) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trang không tồn tại");
                } else {
                    int id = Integer.parseInt(request.getParameter("productId"));
                    List<ProductVariant> res = productVariantService.getProductVariantsByProdId(id);

                    request.setAttribute("variants", res);
                    request.getRequestDispatcher("/WEB-INF/fragments/variant_row.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("variants", null);
            request.getRequestDispatcher("/WEB-INF/fragments/variant_row.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}