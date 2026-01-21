package vn.edu.hcmuaf.fit.pkcn.dao.user;
import jakarta.servlet.ServletException;
import org.jdbi.v3.core.Jdbi;

import vn.edu.hcmuaf.fit.pkcn.model.user.Address;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class UserAddressDao {
    private Jdbi jdbi;
    public UserAddressDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }
    public boolean addAddress(Address address) {
        String sql = "INSERT INTO user_address (user_id, receiver_name, phone_number, province_city, district, address_detail, is_selected) VALUES (:userId, :receiverName, :phoneNumber, :provinceCity, :district, :addressDetail, :isSelected)";
        return jdbi.withHandle(handle -> {
            return handle.createUpdate(sql)
                    .bindBean(address)
                    .execute()>0;
        });

    }
    public List<Address> getAllAddress(int userId) {
        String sql = "Select id, user_id, receiver_name, phone_number , address_detail, is_selected , province_city , district from user_address Where user_id=:userId";
        return jdbi.withHandle(handle -> {
            List<Address> listAddress = new ArrayList<>();
            Iterator<Address> iterator = handle.createQuery(sql)
                    .bind("userId", userId)
                    .mapToBean(Address.class).stream().iterator();
            while (iterator.hasNext()) {
                Address address = iterator.next();
                listAddress.add(address);
            }
            return listAddress;
        });
    }
}
