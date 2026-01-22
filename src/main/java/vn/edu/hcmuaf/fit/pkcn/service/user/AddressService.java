package vn.edu.hcmuaf.fit.pkcn.service.user;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.dao.user.UserAddressDao;
import vn.edu.hcmuaf.fit.pkcn.model.user.Address;

import java.util.List;

public class AddressService {
    private UserAddressDao addressDao;
    public UserAddressDao getAddressDao() {
        return addressDao;
    }

    public AddressService(Jdbi addressDao) {
        this.addressDao = new UserAddressDao(addressDao);
    }
    public boolean addAddressSv(Address address) {
        return addressDao.addAddress(address);
    }
    public List<Address> getAllAddress(int userId) {
        return addressDao.getAllAddress(userId);
    }
}
