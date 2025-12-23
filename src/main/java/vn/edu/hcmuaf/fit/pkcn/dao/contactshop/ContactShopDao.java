package vn.edu.hcmuaf.fit.pkcn.dao.contactshop;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.model.contactshop.ContactShop;

public class ContactShopDao {
    private Jdbi jdbi;

    public ContactShopDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public ContactShop getInfoShopByStatus(int status) {
        String sql = "SELECT * FROM contact_shops WHERE status = :status LIMIT 1";
        return jdbi
                .withHandle(
                        handle -> handle
                                .createQuery(sql)
                                .bind("status", status)
                                .mapToBean(ContactShop.class)
                                .first()
                );
    }
}
