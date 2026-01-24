package vn.edu.hcmuaf.fit.pkcn.dao.order;

import org.jdbi.v3.core.Jdbi;
public class ShippingFeeDao {
    private Jdbi jdbi;
    public ShippingFeeDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }
    public double getPriceByAddress(String province){
        String sql = "SELECT price FROM ship_fee_by_address WHERE province_city = :province";
        Double price = jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("province", province)
                        .mapTo(Double.class)
                        .findOne()
                        .orElse(null)
        );
        if (price == null) {
            return getDefaultPrice();
        }

        return price;
    }
    private double getDefaultPrice() {
        String sql = "SELECT price FROM ship_fee_by_address WHERE province_city = 'Các tỉnh khác'";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapTo(Double.class)
                        .findOne()
                        .orElse(50000.0) // Giá trị mặc định cuối cùng nếu DB không có dòng "Các tỉnh khác"
        );
    }
}

