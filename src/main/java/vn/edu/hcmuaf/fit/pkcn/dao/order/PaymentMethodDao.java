package vn.edu.hcmuaf.fit.pkcn.dao.order;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.order.PaymentMethod;
import java.util.List;

public class PaymentMethodDao {
    private Jdbi jdbi;

    public PaymentMethodDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }
    public List<PaymentMethod> getAll() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT id, name_method, subtitle FROM payment_methods")
                        .mapToBean(PaymentMethod.class)
                        .list()
        );
    }
    public PaymentMethod getById(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT id, name_method, subtitle FROM payment_methods WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(PaymentMethod.class)
                        .findOne()
                        .orElse(null)
        );
    }
}