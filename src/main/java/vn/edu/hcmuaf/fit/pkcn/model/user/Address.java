package vn.edu.hcmuaf.fit.pkcn.model.user;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.Objects;

public class Address {
    private int id;
    private int userId;
    private String receiverName;
    private String phoneNumber;
    private String provinceCity;
    private String district;
    private String addressDetail;
    private int isSelected;

    public Address(int id, int userId, String receiverName, String phoneNumber, String provinceCity, String district, String addressDetail, int isSelected) {
        this.id = id;
        this.userId = userId;
        this.receiverName = receiverName;
        this.phoneNumber = phoneNumber;
        this.provinceCity = provinceCity;
        this.district = district;
        this.addressDetail = addressDetail;
        this.isSelected = isSelected;
    }

    public Address() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    @ColumnName("phoneNumber")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }
    @ColumnName("id")
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    @ColumnName("userId")
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReceiverName() {
        return receiverName;
    }
    @ColumnName("receiverName")
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getProvinceCity() {
        return provinceCity;
    }
    @ColumnName("provinceCity")
    public void setProvinceCity(String provinceCity) {
        this.provinceCity = provinceCity;
    }

    public String getDistrict() {
        return district;
    }
    @ColumnName("district")
    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddressDetail() {
        return addressDetail;
    }
    @ColumnName("addressDetail")
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public int getIsSelected() {
        return isSelected;
    }
    @ColumnName("isSelected")
    public void setIsSelect(int isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id && userId == address.userId && isSelected == address.isSelected && Objects.equals(receiverName, address.receiverName) && Objects.equals(phoneNumber, address.phoneNumber) && Objects.equals(provinceCity, address.provinceCity) && Objects.equals(district, address.district) && Objects.equals(addressDetail, address.addressDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, receiverName, phoneNumber, provinceCity, district, addressDetail, isSelected);
    }
}
