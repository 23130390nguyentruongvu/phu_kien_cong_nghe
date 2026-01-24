package vn.edu.hcmuaf.fit.pkcn.model.order;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.Objects;

public class ShippingFee {
    private int id;
    private String provinceCity;
    private String type;
    private double price;

    public int getId() {
        return id;
    }
    @ColumnName("id")
    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceCity() {
        return provinceCity;
    }
    @ColumnName("province_city")
    public void setProvinceCity(String provinceCity) {
        this.provinceCity = provinceCity;
    }

    public String getType() {
        return type;
    }
    @ColumnName("type")
    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }
    @ColumnName("price")
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShippingFee that = (ShippingFee) o;
        return id == that.id && Double.compare(price, that.price) == 0 && Objects.equals(provinceCity, that.provinceCity) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, provinceCity, type, price);
    }
}
