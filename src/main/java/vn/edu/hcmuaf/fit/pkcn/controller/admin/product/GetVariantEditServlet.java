package vn.edu.hcmuaf.fit.pkcn.controller.admin.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.product.ProductVariantDao;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariant;
import vn.edu.hcmuaf.fit.pkcn.service.product.ProductVariantService;

import java.io.IOException;

@WebServlet(name = "GetVariantEditServlet", value = "/get-variant-edit")
public class GetVariantEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductVariantService productVariantService = new ProductVariantService(
                new ProductVariantDao(JDBI.getJdbi())
        );

        try {
            int varId = Integer.parseInt(request.getParameter("variantId"));
            ProductVariant variant = productVariantService.getProductVariantById(varId);
            request.setAttribute("variant", variant);
            request.getRequestDispatcher("/WEB-INF/fragments/edit_variant_row.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("variant", null);
            request.getRequestDispatcher("/WEB-INF/fragments/edit_variant_row.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}