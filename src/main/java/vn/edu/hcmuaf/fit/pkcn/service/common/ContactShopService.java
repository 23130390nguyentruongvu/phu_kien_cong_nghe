package vn.edu.hcmuaf.fit.pkcn.service.common;

import vn.edu.hcmuaf.fit.pkcn.dao.common.ContactShopDao;
import vn.edu.hcmuaf.fit.pkcn.model.common.ContactShop;

public class ContactShopService {
    private ContactShopDao csDao;

    public ContactShopService(ContactShopDao csDao) {
        this.csDao = csDao;
    }

    public ContactShop getContactShopByStatus(int status) {
        if(status > 1 || status < 0) return null;
        return csDao.getInfoShopByStatus(status);
    }
}
