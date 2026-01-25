package vn.edu.hcmuaf.fit.pkcn.service.order;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.dao.order.PaymentMethodDao;
import vn.edu.hcmuaf.fit.pkcn.model.order.PaymentMethod;

import java.util.List;

public class PaymentMethodService {
    private PaymentMethodDao dao;
    public PaymentMethodService(Jdbi dao) {
        this.dao = new PaymentMethodDao(dao);
    }
    public List<PaymentMethod> getAllPaymentMethods() {
        return dao.getAll();
    }
    public PaymentMethod getPaymentMethodById(int id) {
        return dao.getById(id);
    }
}
