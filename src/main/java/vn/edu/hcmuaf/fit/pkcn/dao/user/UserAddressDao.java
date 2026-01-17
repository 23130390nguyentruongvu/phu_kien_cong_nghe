package vn.edu.hcmuaf.fit.pkcn.dao.user;
import jakarta.servlet.ServletException;
import org.jdbi.v3.core.Jdbi;

import vn.edu.hcmuaf.fit.pkcn.model.user.Address;

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
}
