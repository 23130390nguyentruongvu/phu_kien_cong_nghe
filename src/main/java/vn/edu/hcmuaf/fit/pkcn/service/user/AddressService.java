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
        int count  = addressDao.countAddressByUserId(address.getUserId());
        if (count == 0) {
            address.setIsSelected(1);
        }else{
            address.setIsSelected(0);
        }
        return addressDao.addAddress(address);
    }
    public List<Address> getAllAddress(int userId) {
        return addressDao.getAllAddress(userId);
    }
    public boolean updateStatusAddress(int userId, int addressId) {
        return addressDao.upDateStatusAddress(userId, addressId);
    }
    public Address getAddressDefault(int addressId) {
        return addressDao.getAddressDefault(addressId);
    }
    public boolean deleteAddress(int addressId, int userId) {

        Address addressMustDelete = addressDao.getAddressById(addressId);
        boolean result = addressDao.deleteAddress(addressId);

        if(result && addressMustDelete != null && addressMustDelete.getIsSelected() == 1){
            Integer nextId = addressDao.getTopAddressIdByUserId(userId);
            if(nextId != null){
                addressDao.setDefaultAddress(nextId, userId);
            }
        }
        return result;
    }
    public boolean updateAddress(Address address) {
        return addressDao.updateAddress(address);
    }
    public Address getAddressById(int addressId) {
        return addressDao.getAddressById(addressId);
    }
}
