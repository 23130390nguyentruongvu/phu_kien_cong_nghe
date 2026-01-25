package vn.edu.hcmuaf.fit.pkcn.controller.common;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.dao.common.BankInfoDao;
import vn.edu.hcmuaf.fit.pkcn.model.common.BankInfo;
import vn.edu.hcmuaf.fit.pkcn.model.order.PaymentMethod;
import vn.edu.hcmuaf.fit.pkcn.service.common.BankInfoService;
import vn.edu.hcmuaf.fit.pkcn.service.order.PaymentMethodService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewPaymentMethodServlet", value = "/view-payment-method")
public class ViewPaymentMethodServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PaymentMethodService paymentMethodService = new PaymentMethodService(
                    JDBI.getJdbi()
            );

            BankInfoService bankInfoService = new BankInfoService(
                    new BankInfoDao(JDBI.getJdbi())
            );

            List<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethods();
            List<BankInfo> bankInfos = bankInfoService.getBankInfos();

            request.setAttribute("methods", paymentMethods);
            request.setAttribute("bankInfos", bankInfos);
            request.getRequestDispatcher("/WEB-INF/views/client/payment_method.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}