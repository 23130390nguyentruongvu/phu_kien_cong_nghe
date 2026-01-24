package vn.edu.hcmuaf.fit.pkcn.service.order;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.dao.order.ShippingFeeDao;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserAddressDao;
import vn.edu.hcmuaf.fit.pkcn.model.user.Address;

public class ShippingFeeService {
    private ShippingFeeDao dao;
    public ShippingFeeService(Jdbi shippingFeeDao) {
        this.dao = new ShippingFeeDao(shippingFeeDao);
    }
    public double getPriceShipByAddress(String province) {
        return dao.getPriceByAddress(province);
    }
}
